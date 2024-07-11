<template>
  <div class="app-container">
    <el-card shadow="never">
      <template #header>
        <span>活动信息</span>
      </template>
      <el-form label-width="68px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="活动编号">
              {{ activeInfo.id }}
            </el-form-item>
            <el-form-item label="活动名称">
              {{ activeInfo.name }}
            </el-form-item>
            <el-form-item label="活动类型">
              <dict-tag :options="sys_activity_type" :value="activeInfo.activityType"/>
            </el-form-item>
            <el-form-item label="活动人数">
              {{ activeInfo.attendPeopleCount }}/{{ activeInfo.numberLimit }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="举办组织">
              <!--{{ volunteerDict.find(item => item.id == activeInfo.organizationId).name }}-->
              {{ activeInfo.orgName }}
            </el-form-item>

            <el-form-item label="联系人">
              {{ activeInfo.contacts }}
            </el-form-item>
            <el-form-item label="联系电话">
              {{ activeInfo.contactsPhone }}
            </el-form-item>

            <el-form-item label="活动时间">
              {{ parseTime(activeInfo.startTime, '{y}-{m}-{d}') }}至{{
                parseTime(activeInfo.endTime, '{y}-{m}-{d}')
              }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="描述">
              {{ activeInfo.describe }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
    <!--人员信息-->
    <el-form class="mt20" :model="queryParams" ref="queryRef" :inline="true" label-width="68px">
      <el-form-item label="用户名称" prop="volunteerName">
        <el-input
            v-model="queryParams.volunteerName"
            placeholder="请输入用户名称"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审批状态" prop="status">
        <el-select v-model="queryParams.userApprovalStatus" placeholder="请选择审批状态" clearable style="width: 200px">
          <el-option
              v-for="dict in sys_examine_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table class="mt20" :data="voluList">
      <el-table-column label="活动编号" align="center" prop="activityId" width="80"/>
      <el-table-column label="活动名称" align="center" prop="activityName"/>
      <el-table-column label="用户编号" align="center" prop="volunteerId"/>
      <el-table-column label="志愿编号" align="center" prop="volunteerId"/>
      <el-table-column label="用户账号" align="center" prop="volunteerName"/>
      <el-table-column label="联系电话" align="center" prop="contactsPhone"/>
      <!--      <el-table-column label="申请时间" align="center" width="180">
              <template #default="scope">
                <span>{{ parseTime(scope.row.createTime) }}</span>
              </template>
            </el-table-column>-->
      <el-table-column label="审批状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_examine_status" :value="scope.row.userApprovalStatus"/>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" v-hasRole="['admin','admin2','common2']" @click="handleUpdate(scope.row)">审核
          </el-button>
          <el-button link type="primary" v-hasRole="['admin','admin2','common2']" @click="handleDelete(scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getVoluList"
    />
    <!--审核弹出层-->
    <el-dialog title="审核" v-model="open" width="400px" append-to-body>
      <el-form ref="activityRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="审批状态" prop="status">
          <el-select v-model="form.userApprovalStatus" placeholder="请选择审批状态" style="width: 100%">
            <el-option
                v-for="dict in sys_examine_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>

</template>

<script setup>
import {useRoute} from "vue-router";
import {getActivity} from "@/api/system/activity.js";
import {listOrganization} from "@/api/system/organization.js";
import {delRelation, getRelation, listRelation, updateRelation} from "@/api/system/volunteerActivityRelation.js";
import {ElMessage} from "element-plus";

const {proxy} = getCurrentInstance();
const {
  sys_activity_status,
  sys_activity_type,
  sys_examine_status
} = proxy.useDict('sys_activity_status', 'sys_activity_type', 'sys_examine_status');

const route = useRoute()
let activeId = null

const activeInfo = ref({})
const voluList = ref([])

const total = ref(0);
const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    activityName: undefined,
    volunteerName: undefined,
    userApprovalStatus: undefined
  },
});

const {queryParams, form, rules} = toRefs(data);

const open = ref(false)

function init() {
  activeId = route.params.id
  getAciInfo(activeId)
  getVoluList()
}

/*获取活动信息*/
async function getAciInfo(activeId) {
  const resAct = await getActivity(activeId)
  activeInfo.value = resAct.data
}

/*获取人员信息*/
function getVoluList() {
  listRelation({activityId: activeInfo.value.id, ...queryParams.value}).then(res => {
    voluList.value = res.rows
    total.value = res.total
  })
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getVoluList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

/*审批*/
function handleUpdate(row) {
  form.value = {...row}
  open.value = true
}

/*审核状态提交*/
function submitForm() {
  let data = {...form.value}
  updateRelation(data).then(res => {
    getVoluList()
    open.value = false
    ElMessage.success(res.msg)
  })
}

/*审核取消*/
function cancel() {
  open.value = false
  form.value = {};
  proxy.resetForm("activityRef");
}

/*删除*/
function handleDelete(row) {
  delRelation(row.relationId).then(res => {
    ElMessage.success(res.msg)
  })
}

init()
</script>

<style scoped lang="scss">
:deep(.el-form-item) {
  margin-bottom: 0 !important;
}
</style>
