<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="活动id" prop="activityId">
        <el-input
          v-model="queryParams.activityId"
          placeholder="请输入活动id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评论id" prop="commentId">
        <el-input
          v-model="queryParams.commentId"
          placeholder="请输入评论id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户id" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="志愿者id" prop="volunteerId">
        <el-input
          v-model="queryParams.volunteerId"
          placeholder="请输入志愿者id"
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
          v-hasPermi="['system:relation:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:relation:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:relation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:relation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="relationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="活动评论关系id" align="center" prop="id" />
      <el-table-column label="活动id" align="center" prop="activityId" />
      <el-table-column label="评论id" align="center" prop="commentId" />
      <el-table-column label="用户id" align="center" prop="userId" />
      <el-table-column label="志愿者id" align="center" prop="volunteerId" />
      <el-table-column label="扩展字段" align="center" prop="extra" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:relation:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:relation:remove']">删除</el-button>
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

    <!-- 添加或修改活动评论关系对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="relationRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="活动id" prop="activityId">
          <el-input v-model="form.activityId" placeholder="请输入活动id" />
        </el-form-item>
        <el-form-item label="评论id" prop="commentId">
          <el-input v-model="form.commentId" placeholder="请输入评论id" />
        </el-form-item>
        <el-form-item label="用户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户id" />
        </el-form-item>
        <el-form-item label="志愿者id" prop="volunteerId">
          <el-input v-model="form.volunteerId" placeholder="请输入志愿者id" />
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

<script setup name="Relation">
import { listRelation, getRelation, delRelation, addRelation, updateRelation } from "@/api/system/relation";

const { proxy } = getCurrentInstance();

const relationList = ref([]);
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
    activityId: null,
    commentId: null,
    userId: null,
    volunteerId: null,
    extra: null,
  },
  rules: {
    activityId: [
      { required: true, message: "活动id不能为空", trigger: "blur" }
    ],
    commentId: [
      { required: true, message: "评论id不能为空", trigger: "blur" }
    ],
    userId: [
      { required: true, message: "用户id不能为空", trigger: "blur" }
    ],
    createTime: [
      { required: true, message: "关系创建时间不能为空", trigger: "blur" }
    ],
    updateTime: [
      { required: true, message: "关系更新时间不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询活动评论关系列表 */
function getList() {
  loading.value = true;
  listRelation(queryParams.value).then(response => {
    relationList.value = response.rows;
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
    activityId: null,
    commentId: null,
    userId: null,
    volunteerId: null,
    extra: null,
    createTime: null,
    updateTime: null
  };
  proxy.resetForm("relationRef");
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
  title.value = "添加活动评论关系";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getRelation(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改活动评论关系";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["relationRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateRelation(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addRelation(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除活动评论关系编号为"' + _ids + '"的数据项？').then(function() {
    return delRelation(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/relation/export', {
    ...queryParams.value
  }, `relation_${new Date().getTime()}.xlsx`)
}

getList();
</script>
