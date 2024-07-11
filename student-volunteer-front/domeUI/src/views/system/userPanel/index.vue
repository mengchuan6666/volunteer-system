<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <!--个人信息-->
      <el-col :span="6">
        <el-card>
          <template v-slot:header>
            <span>个人信息</span>
          </template>
          <div>
            <div class="text-center">
              <userAvatar/>
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                用户账号
                <div class="pull-right">{{ state.user.userName }}</div>
              </li>
              <li class="list-group-item">
                用户昵称
                <div class="pull-right">{{ state.user.nickName || '暂无昵称' }}</div>
              </li>
              <li class="list-group-item">
                手机号码
                <div class="pull-right">{{ state.user.phonenumber }}</div>
              </li>
              <li class="list-group-item">
                用户邮箱
                <div class="pull-right">{{ state.user.email }}</div>
              </li>
              <li class="list-group-item">
                是否志愿者
                <div class="pull-right">
                  <div v-if="state.user.volunteerId">是</div>
                  <div v-else>
                    <el-link type="primary" @click="addVolun(state.user)">
                      否（点击加入）
                    </el-link>
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <!--活动列表-->
      <el-col :span="18">
        <el-card shadow="never">
          <template #header>
            <span>活动列表</span>
          </template>

          <el-form :model="queryParams" ref="queryRef" :inline="true" label-width="68px">
            <el-form-item label="活动名称" prop="name">
              <el-input
                  v-model="queryParams.activityName"
                  placeholder="请输入活动名称"
                  clearable
                  @keyup.enter="handleQuery"
              />
            </el-form-item>
            <el-form-item label="举办组织" prop="organizer">
              <el-input
                  v-model="queryParams.organizer"
                  placeholder="请输入举办组织"
                  clearable
                  @keyup.enter="handleQuery"
              />
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
            <el-form-item label="审批状态" prop="userApprovalStatus">
              <el-select v-model="queryParams.userApprovalStatus" placeholder="请选择审批状态" clearable
                         style="width: 200px">
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

          <el-table :data="activityList">
            <el-table-column label="编号" align="center" prop="activityId" width="60"/>
            <el-table-column label="活动名称" align="center" prop="activityName"/>
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
            <el-table-column label="加入状态" align="center" width="180">
              <template #default="scope">
                <dict-tag :options="sys_examine_status" :value="scope.row.userApprovalStatus"/>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template #default="scope">
                <el-button link type="primary" @click="handleAdd(scope.row)" :disabled="scope.row.userApprovalStatus!=0&&scope.row.userApprovalStatus!=3&&scope.row.userApprovalStatus!=5">
                    加入
                </el-button>
                <el-button link type="primary" @click="handleUpdate(scope.row)"
                           :disabled="scope.row.userApprovalStatus!=2"
                >退出
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <pagination
              v-show="total>0"
              :total="total"
              v-model:page="queryParams.pageNum"
              v-model:limit="queryParams.pageSize"
              @pagination="getActList"
          />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="Index">
import {listOrganization} from "@/api/system/organization.js";
import UserAvatar from "@/views/system/user/profile/userAvatar.vue";
import {addVolunteer} from "@/api/system/volunteer.js";
import useUserStore from '@/store/modules/user.js'
import {getUser, getUserProfile} from "@/api/system/user.js";
import {addRelation, listRelation, updateRelation} from "@/api/system/volunteerActivityRelation.js";
import {ElMessage} from "element-plus";
import {Avatar} from "@element-plus/icons-vue";
import defaultImg from "@/assets/images/profile.jpg";

const userInfo = useUserStore()
const {proxy} = getCurrentInstance();
const {
  sys_activity_status, sys_activity_type, sys_examine_status
} = proxy.useDict('sys_activity_status', 'sys_activity_type', 'sys_examine_status');

const activityList = ref([]);
const volunteerDict = ref([])
const total = ref(0);
const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    userId: userInfo.user.userId,
    userApprovalStatus: null,
    activityName: null,
    organizer: null,
    activityType: null,
  },
});

const state = reactive({
  user: {},
  roleGroup: {},
});

const {queryParams} = toRefs(data);

function init() {
  getUserInfo()
  getActList()
  getOrgList()
}

/*获取用户信息*/
function getUserInfo() {
  const userId = userInfo.user.userId
  getUser(userId).then(res => {
    console.log(res)
    state.user = res.data
  })
}

/*获取活动列表信息*/
function getActList() {
  queryParams.value.userId = userInfo.user.userId
  listRelation(queryParams.value).then(response => {
    activityList.value = response.rows;
    total.value = response.total;
  });
}

/*获取组织信息*/
function getOrgList() {
  listOrganization().then(response => {
    volunteerDict.value = response.rows;
  });
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getActList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

/*加入志愿者*/
function addVolun(row) {
  let data = {
    userId: row.userId,
    name: row.userName,
    sex: row.sex,
    contactPhone: row.phonenumber
  }
  addVolunteer(data).then(res => {
    console.log(res)
    getUserInfo()
  })
}

/*加入活动*/
function handleAdd(row) {
  let data = {
    activityId: row.activityId,
    userId: state.user.userId,
    volunteerId: state.user.volunteerId
  }
  addRelation(data).then(res => {
    ElMessage.success(res.msg)
    return getActList()
  })
}

/*用户退出活动*/
function handleUpdate(row) {
  let data = {...row}
  data.userId = userInfo.user.userId
  data.userApprovalStatus = '4'
  updateRelation(data).then(res => {
    getActList()
    ElMessage.success(res.msg)
  })
}

init()
</script>