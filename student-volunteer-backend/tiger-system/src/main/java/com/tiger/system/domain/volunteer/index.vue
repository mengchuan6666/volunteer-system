<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户id" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="志愿者名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入志愿者名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系方式" prop="contactPhone">
        <el-input
          v-model="queryParams.contactPhone"
          placeholder="请输入联系方式"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="加入志愿者时间" prop="registerTime">
        <el-date-picker clearable
          v-model="queryParams.registerTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择加入志愿者时间">
        </el-date-picker>
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
          v-hasPermi="['system:volunteer:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:volunteer:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:volunteer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:volunteer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="volunteerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="${comment}" align="center" prop="id" />
      <el-table-column label="用户id" align="center" prop="userId" />
      <el-table-column label="志愿者名称" align="center" prop="name" />
      <el-table-column label="志愿者性别" align="center" prop="sex" />
      <el-table-column label="联系方式" align="center" prop="contactPhone" />
      <el-table-column label="扩展字段" align="center" prop="extra" />
      <el-table-column label="加入志愿者时间" align="center" prop="registerTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.registerTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:volunteer:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:volunteer:remove']">删除</el-button>
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

    <!-- 添加或修改志愿者信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="volunteerRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户id" />
        </el-form-item>
        <el-form-item label="志愿者名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入志愿者名称" />
        </el-form-item>
        <el-form-item label="联系方式" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="扩展字段" prop="extra">
          <el-input v-model="form.extra" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="加入志愿者时间" prop="registerTime">
          <el-date-picker clearable
            v-model="form.registerTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择加入志愿者时间">
          </el-date-picker>
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

<script setup name="Volunteer">
import { listVolunteer, getVolunteer, delVolunteer, addVolunteer, updateVolunteer } from "@/api/system/volunteer";

const { proxy } = getCurrentInstance();

const volunteerList = ref([]);
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
    userId: null,
    name: null,
    sex: null,
    contactPhone: null,
    extra: null,
    registerTime: null,
  },
  rules: {
    userId: [
      { required: true, message: "用户id不能为空", trigger: "blur" }
    ],
    name: [
      { required: true, message: "志愿者名称不能为空", trigger: "blur" }
    ],
    registerTime: [
      { required: true, message: "加入志愿者时间不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询志愿者信息列表 */
function getList() {
  loading.value = true;
  listVolunteer(queryParams.value).then(response => {
    volunteerList.value = response.rows;
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
    userId: null,
    name: null,
    sex: null,
    contactPhone: null,
    extra: null,
    registerTime: null,
    updateTime: null
  };
  proxy.resetForm("volunteerRef");
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
  title.value = "添加志愿者信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getVolunteer(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改志愿者信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["volunteerRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateVolunteer(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addVolunteer(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除志愿者信息编号为"' + _ids + '"的数据项？').then(function() {
    return delVolunteer(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/volunteer/export', {
    ...queryParams.value
  }, `volunteer_${new Date().getTime()}.xlsx`)
}

getList();
</script>
