<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="组织名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入组织名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="组织介绍" prop="introduce">
        <el-input
          v-model="queryParams.introduce"
          placeholder="请输入组织介绍"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="扩展字段" prop="extra">
        <el-input
          v-model="queryParams.extra"
          placeholder="请输入扩展字段"
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
          v-hasPermi="['system:organization:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:organization:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:organization:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:organization:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="organizationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="组织id" align="center" prop="id" />
      <el-table-column label="组织名" align="center" prop="name" />
      <el-table-column label="组织介绍" align="center" prop="introduce" />
      <el-table-column label="扩展字段" align="center" prop="extra" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:organization:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:organization:remove']">删除</el-button>
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

    <!-- 添加或修改志愿组织信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="organizationRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="组织名" prop="name">
          <el-input v-model="form.name" placeholder="请输入组织名" />
        </el-form-item>
        <el-form-item label="组织介绍" prop="introduce">
          <el-input v-model="form.introduce" placeholder="请输入组织介绍" />
        </el-form-item>
        <el-form-item label="扩展字段" prop="extra">
          <el-input v-model="form.extra" placeholder="请输入扩展字段" />
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

<script setup name="Organization">
import { listOrganization, getOrganization, delOrganization, addOrganization, updateOrganization } from "@/api/system/organization";

const { proxy } = getCurrentInstance();

const organizationList = ref([]);
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
    introduce: null,
    extra: null,
  },
  rules: {
    name: [
      { required: true, message: "组织名不能为空", trigger: "blur" }
    ],
    introduce: [
      { required: true, message: "组织介绍不能为空", trigger: "blur" }
    ],
    createTime: [
      { required: true, message: "组织创建时间不能为空", trigger: "blur" }
    ],
    updateTime: [
      { required: true, message: "更新时间不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询志愿组织信息列表 */
function getList() {
  loading.value = true;
  listOrganization(queryParams.value).then(response => {
    organizationList.value = response.rows;
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
    introduce: null,
    extra: null,
    createTime: null,
    updateTime: null
  };
  proxy.resetForm("organizationRef");
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
  title.value = "添加志愿组织信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getOrganization(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改志愿组织信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["organizationRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateOrganization(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addOrganization(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除志愿组织信息编号为"' + _ids + '"的数据项？').then(function() {
    return delOrganization(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/organization/export', {
    ...queryParams.value
  }, `organization_${new Date().getTime()}.xlsx`)
}

getList();
</script>
