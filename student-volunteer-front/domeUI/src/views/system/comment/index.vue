<template>
  <div class="app-container">
    <!--活动信息-->
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
              {{ activeInfo.organizer }}
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

    <el-card class="mt10" shadow="never">
      <!--刷新按钮-->
      <el-row :gutter="10">
        <el-col :span="20">
          <el-input v-model="form.commentContent" placeholder="请输入评论内容"></el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="danger" @click="addComm">提交</el-button>
        </el-col>
        <el-col :span="2">
          <el-button type="text" @click="getCommList">刷新评论</el-button>
        </el-col>
      </el-row>
      <!--评论列表-->
      <div class="mt10 text-center" v-if="total<1">暂无评论</div>
      <div class="mt10" v-else>
        <el-collapse>
          <el-collapse-item v-for="item in commentList" :name="item.id">
            <template #title>
              <el-avatar :src="item.avatar||defaultImg"/>
              <span class="ml10 text-warning">{{ item.extra }}</span>
              <span class="ml20 text-warning">{{ item.createTime }}</span>
            </template>
            <div class="ml20 comCard">
              <div class="text-danger">@{{ item.extra }}:</div>
              <div>{{ item.commentContent }}</div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
      <!--分页-->
      <pagination
          :total="total"
          v-model:page="queryParams.pageNum"
          v-model:limit="queryParams.pageSize"
          @pagination="getCommList"
      />
    </el-card>
  </div>
</template>

<script setup name="Comment">
import {listComment, getComment, delComment, addComment, updateComment} from "@/api/system/comment";
import {useRoute} from "vue-router";
import {getActivity} from "@/api/system/activity.js";
import {ElMessage} from "element-plus";
import useUserStore from "@/store/modules/user.js";
import defaultImg from '@/assets/images/profile.jpg'

const {proxy} = getCurrentInstance();

let activeId = null;
const route = useRoute()

const {
  sys_activity_status,
  sys_activity_type,
  sys_examine_status
} = proxy.useDict('sys_activity_status', 'sys_activity_type', 'sys_examine_status');

const userInfo = useUserStore()
const activeInfo = ref({})
const commentList = ref([])
const total = ref(0)

const form = ref({
  activittyId: activeInfo.value.id,
  userId: userInfo.user.userId,
  commentContent: ""
})

const queryParams = ref({
  pageSize: 10,
  pageNum: 1,
})

function init() {
  activeId = route.params.id
  getAciInfo(activeId)
  getCommList()
}

/*获取活动信息*/
async function getAciInfo(activeId) {
//活动信息
  const resAct = await getActivity(activeId)
  activeInfo.value = resAct.data
}

/*获取评论列表*/
function getCommList() {
  listComment({activittyId: activeInfo.value.id, ...queryParams.value}).then(res => {
    commentList.value = res.rows
    total.value = res.total;
  })
}

/*发布*/
function addComm() {
  form.value.activittyId = activeInfo.value.id
  form.value.userId = userInfo.user.userId
  form.value.extra = userInfo.user.userName
  if (form.value.commentContent.length < 1) {
    ElMessage.warning("评论信息不可以为空")
  } else {
    addComment(form.value).then(res => {
      ElMessage.success(res.msg)
      form.value.commentContent = ""
      return getCommList()
    })
  }
}

init();
</script>
<style scoped lang="scss">
:deep(.el-form-item) {
  margin-bottom: 0 !important;
}

.comCard {
  border: 1px solid #eeeeee;
  padding: 10px 20px;
  border-radius: 8px;
}
</style>