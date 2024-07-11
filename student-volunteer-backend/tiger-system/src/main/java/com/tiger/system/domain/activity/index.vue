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
      <el-form-item label="活动描述" prop="describe">
        <el-input
          v-model="queryParams.describe"
          placeholder="请输入活动描述"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动地点" prop="location">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入活动地点"
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
      <el-form-item label="主办方" prop="organizer">
        <el-input
          v-model="queryParams.organizer"
          placeholder="请输入主办方"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择活动状态" clearable>
          <el-option
            v-for="dict in sys_activity_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="联系人" prop="contacts">
        <el-input
          v-model="queryParams.contacts"
          placeholder="请输入联系人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系人电话" prop="contactsPhone">
        <el-input
          v-model="queryParams.contactsPhone"
          placeholder="请输入联系人电话"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动所需物资" prop="substance">
        <el-input
          v-model="queryParams.substance"
          placeholder="请输入活动所需物资"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="志愿者要求" prop="ask">
        <el-input
          v-model="queryParams.ask"
          placeholder="请输入志愿者要求"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动类型" prop="activityType">
        <el-select v-model="queryParams.activityType" placeholder="请选择活动类型" clearable>
          <el-option
            v-for="dict in sys_activity_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="志愿组织id" prop="organizationId">
        <el-input
          v-model="queryParams.organizationId"
          placeholder="请输入志愿组织id"
          clearable
          @keyup.enter="handleQuery"
        />
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
          v-hasPermi="['system:activity:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:activity:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:activity:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:activity:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="activityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="活动ID" align="center" prop="id" />
      <el-table-column label="活动名称" align="center" prop="name" />
      <el-table-column label="活动描述" align="center" prop="describe" />
      <el-table-column label="活动地点" align="center" prop="location" />
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="主办方" align="center" prop="organizer" />
      <el-table-column label="活动状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_activity_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="联系人" align="center" prop="contacts" />
      <el-table-column label="联系人电话" align="center" prop="contactsPhone" />
      <el-table-column label="活动所需物资" align="center" prop="substance" />
      <el-table-column label="志愿者要求" align="center" prop="ask" />
      <el-table-column label="活动图片" align="center" prop="imgs" />
      <el-table-column label="活动类型" align="center" prop="activityType">
        <template #default="scope">
          <dict-tag :options="sys_activity_type" :value="scope.row.activityType"/>
        </template>
      </el-table-column>
      <el-table-column label="志愿组织id" align="center" prop="organizationId" />
      <el-table-column label="扩展字段" align="center" prop="extra" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:activity:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:activity:remove']">删除</el-button>
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
          <el-input v-model="form.name" placeholder="请输入活动名称" />
        </el-form-item>
        <el-form-item label="活动描述" prop="describe">
          <el-input v-model="form.describe" placeholder="请输入活动描述" />
        </el-form-item>
        <el-form-item label="活动地点" prop="location">
          <el-input v-model="form.location" placeholder="请输入活动地点" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable
            v-model="form.startTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable
            v-model="form.endTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="主办方" prop="organizer">
          <el-input v-model="form.organizer" placeholder="请输入主办方" />
        </el-form-item>
        <el-form-item label="活动状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in sys_activity_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="联系人" prop="contacts">
          <el-input v-model="form.contacts" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系人电话" prop="contactsPhone">
          <el-input v-model="form.contactsPhone" placeholder="请输入联系人电话" />
        </el-form-item>
        <el-form-item label="活动所需物资" prop="substance">
          <el-input v-model="form.substance" placeholder="请输入活动所需物资" />
        </el-form-item>
        <el-form-item label="志愿者要求" prop="ask">
          <el-input v-model="form.ask" placeholder="请输入志愿者要求" />
        </el-form-item>
        <el-form-item label="活动图片" prop="imgs">
          <el-input v-model="form.imgs" type="textarea" placeholder="请输入内容" />
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
        <el-form-item label="志愿组织id" prop="organizationId">
          <el-input v-model="form.organizationId" placeholder="请输入志愿组织id" />
        </el-form-item>
        <el-form-item label="扩展字段" prop="extra">
          <el-input v-model="form.extra" type="textarea" placeholder="请输入内容" />
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
import { listActivity, getActivity, delActivity, addActivity, updateActivity } from "@/api/system/activity";

const { proxy } = getCurrentInstance();
const { sys_activity_status, sys_activity_type } = proxy.useDict('sys_activity_status', 'sys_activity_type');

const activityList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

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
      { required: true, message: "活动名称不能为空", trigger: "blur" }
    ],
    describe: [
      { required: true, message: "活动描述不能为空", trigger: "blur" }
    ],
    location: [
      { required: true, message: "活动地点不能为空", trigger: "blur" }
    ],
    startTime: [
      { required: true, message: "开始时间不能为空", trigger: "blur" }
    ],
    endTime: [
      { required: true, message: "结束时间不能为空", trigger: "blur" }
    ],
    organizer: [
      { required: true, message: "主办方不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "活动状态不能为空", trigger: "change" }
    ],
    contacts: [
      { required: true, message: "联系人不能为空", trigger: "blur" }
    ],
    contactsPhone: [
      { required: true, message: "联系人电话不能为空", trigger: "blur" }
    ],
    ask: [
      { required: true, message: "志愿者要求不能为空", trigger: "blur" }
    ],
    createTime: [
      { required: true, message: "创建时间不能为空", trigger: "blur" }
    ],
    updateTime: [
      { required: true, message: "更新时间不能为空", trigger: "blur" }
    ],
    activityType: [
      { required: true, message: "活动类型不能为空", trigger: "change" }
    ],
    organizationId: [
      { required: true, message: "志愿组织id不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询活动信息列表 */
function getList() {
  loading.value = true;
  listActivity(queryParams.value).then(response => {
    activityList.value = response.rows;
    total.value = response.total;
    loading.value = false;
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

/** 提交按钮 */
function submitForm() {
  proxy.$refs["activityRef"].validate(valid => {
    if (valid) {
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
  proxy.$modal.confirm('是否确认删除活动信息编号为"' + _ids + '"的数据项？').then(function() {
    return delActivity(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/activity/export', {
    ...queryParams.value
  }, `activity_${new Date().getTime()}.xlsx`)
}

getList();
</script>
