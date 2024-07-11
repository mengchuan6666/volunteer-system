<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="活动名称" prop="name">
        <el-input
            v-model="queryParams.name"
            placeholder="请输入活动名称"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker clearable
                        v-model="queryParams.startTime"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker clearable
                        v-model="queryParams.endTime"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="举办组织" prop="organizer">
        <el-input
            v-model="queryParams.organizer"
            placeholder="请输入举办组织"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择活动状态" clearable style="width: 200px">
          <el-option
              v-for="dict in sys_activity_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="活动类型" prop="activityType">
        <el-select v-model="queryParams.activityType" placeholder="请选择活动类型" clearable style="width: 200px">
          <el-option
              v-for="dict in sys_activity_type"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="activityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id" width="60"/>
      <el-table-column label="活动名称" align="center" prop="name"/>
      <el-table-column label="举办组织" align="center" prop="organizer"/>
      <el-table-column label="活动类型" align="center" prop="activityType">
        <template #default="scope">
          <dict-tag :options="sys_activity_type" :value="scope.row.activityType"/>
        </template>
      </el-table-column>
      <el-table-column label="联系人" align="center" prop="contacts"/>
      <el-table-column label="联系电话" align="center" prop="contactsPhone"/>
      <el-table-column label="活动人数" align="center" prop="numberLimit">
        <template #default="scope">
          <span>{{ scope.row.attendPeopleCount }}/{{ scope.row.numberLimit }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动时间" align="center" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}至{{
              parseTime(scope.row.endTime, '{y}-{m}-{d}')
            }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_activity_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="管理" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" @click="handleRoute('/activity/manage/'+scope.row.id)"
          >人员管理
          </el-button>
          <el-button link type="primary" @click="handleRoute('/activity/talk/'+scope.row.id)"
          >评论管理
          </el-button>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">

          <el-popconfirm confirm-button-text="通过"
                         cancel-button-text="拒绝" title="请审活动信息"
                         @confirm="handleView(scope.row,'yes')" @cancel="handleView(scope.row,'no')">
            <template #reference>
              <el-button link type="primary"
                         v-hasRole="['admin','admin2','common2']"
              >审核
              </el-button>
            </template>
          </el-popconfirm>
          <el-button link type="primary" v-hasRole="['admin','admin2','common2']" @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button link type="primary" v-hasRole="['admin','admin2','common2']" @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 添加或修改活动信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="activityRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="活动名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入活动名称"/>
        </el-form-item>
        <el-form-item label="活动组织" prop="organizationId">
          <el-select v-model="form.organizationId" placeholder="请输入活动组织">
            <el-option
                v-for="dict in volunteerDict"
                :key="dict.id"
                :label="dict.name"
                :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="活动类型" prop="activityType">
          <el-select v-model="form.activityType" placeholder="请选择活动类型">
            <el-option
                v-for="dict in sys_activity_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="活动地点" prop="location">
          <el-input v-model="form.location" placeholder="请输入活动地点"/>
        </el-form-item>
        <el-form-item label="志愿要求" prop="ask">
          <el-input v-model="form.ask" placeholder="请输入志愿要求"/>
        </el-form-item>
        <el-form-item label="活动人数" prop="numberLimit">
          <el-input-number min="0" step="1" step-strictly v-model="form.numberLimit"></el-input-number>
        </el-form-item>
        <el-form-item label="联系人" prop="contacts">
          <el-input v-model="form.contacts" placeholder="请输入联系人"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactsPhone">
          <el-input v-model="form.contactsPhone" placeholder="请输入联系人电话"/>
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable
                          v-model="form.startTime"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择开始时间" style="width: 100%">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable
                          v-model="form.endTime"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择结束时间" style="width: 100%">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="活动描述" prop="describe">
          <el-input type="textarea" v-model="form.describe" placeholder="请输入活动描述"/>
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

<script setup name="Activity">
import {addActivity, delActivity, getActivity, listActivity, updateActivity} from "@/api/system/activity";
import {listOrganization} from "@/api/system/organization.js";
import {useRouter} from "vue-router";
import useUserStore from "@/store/modules/user.js";

const {proxy} = getCurrentInstance();
const {sys_activity_status, sys_activity_type} = proxy.useDict('sys_activity_status', 'sys_activity_type');
const router = useRouter()

const volunteerDict = ref([])

const activityList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const userInfo = useUserStore()

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: null,
    describe: null,
    location: null,
    startTime: null,
    endTime: null,
    organizer: null,
    status: null,
    contacts: null,
    contactsPhone: null,
    substance: null,
    ask: null,
    imgs: null,
    activityType: null,
    organizationId: null,
    extra: null
  },
  rules: {
    name: [
      {required: true, message: "活动名称不能为空", trigger: "blur"}
    ],
    describe: [
      {required: true, message: "活动描述不能为空", trigger: "blur"}
    ],
    location: [
      {required: true, message: "活动地点不能为空", trigger: "blur"}
    ],
    startTime: [
      {required: true, message: "开始时间不能为空", trigger: "blur"}
    ],
    endTime: [
      {required: true, message: "结束时间不能为空", trigger: "blur"}
    ],
    organizer: [
      {required: true, message: "组织不能为空", trigger: "blur"}
    ],
    contacts: [
      {required: true, message: "联系人不能为空", trigger: "blur"}
    ],
    contactsPhone: [
      {required: true, message: "联系电话不能为空", trigger: "blur"}
    ],
    ask: [
      {required: true, message: "志愿者要求不能为空", trigger: "blur"}
    ],
    activityType: [
      {required: true, message: "活动类型不能为空", trigger: "change"}
    ],
    numberLimit: [
      {required: true, message: "活动人数不能为空", trigger: "blur"}
    ],
  }
});

const {queryParams, form, rules} = toRefs(data);

/** 查询活动信息列表 */
function getList() {
  loading.value = true;
  listActivity(queryParams.value).then(response => {
    activityList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

function getOrgList() {
  listOrganization().then(response => {
    volunteerDict.value = response.rows;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    name: null,
    describe: null,
    location: null,
    startTime: null,
    endTime: null,
    organizer: null,
    status: null,
    contacts: null,
    contactsPhone: null,
    substance: null,
    ask: null,
    imgs: null,
    createTime: null,
    updateTime: null,
    activityType: null,
    organizationId: null,
    extra: null
  };
  proxy.resetForm("activityRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加活动信息";
}

/*审核按钮操作*/
function handleView(row, type) {
  let data = {...row}
  if (type === 'yes') {
    data.status = "2"
  } else if (type === 'no') {
    data.status = '3'
  } else {
    data.status = '1'
  }
  updateActivity(data).then(response => {
    proxy.$modal.msgSuccess("修改成功");
    getList();
  });
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getActivity(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改活动信息";
  });
}

/*人员管理*/
function handleRoute(route) {
  router.push(route)
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["activityRef"].validate(valid => {
    if (valid) {
      form.value.organizer = volunteerDict.value.find(item => item.id == form.value.organizationId).name
      form.value.userId = userInfo.user.userId
      if (form.value.id != null) {
        updateActivity(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addActivity(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除活动信息编号为"' + _ids + '"的数据项？').then(function () {
    return delActivity(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}


getOrgList()
getList();
</script>
