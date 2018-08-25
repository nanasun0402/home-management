<template>
    <div class="nurse data">
        <el-tabs value="query" type="border-card" :style="{height: '100%'}">
            <el-tab-pane id="table-container" label="管理员管理" name="query">
                <el-row id="searchbar">
                    <el-col :span="16"><el-button icon="el-icon-plus" type="primary" @click="addVisible = true">新增管理员</el-button></el-col>
                    <el-col :span="8">
                        <el-input placeholder="搜索内容" v-model="searchKeyword">
                            <el-select v-model="searchType" slot="prepend" placeholder="搜索类型">
                                <el-option label="用户名" value="username"></el-option>
                            </el-select>
                            <el-button slot="append" icon="el-icon-search" @click="startSearch"></el-button>
                        </el-input>
                    </el-col>
                </el-row>
                <el-row>
                    <table-ex parentContainer="table-container" :columnList="columnList" :subList="['searchbar', 'pagnationbar']" :rowData="users" :formatter="formatter"></table-ex>
                </el-row>
                <user-dialog title="新增管理员" v-if="addVisible" :on-confirm="handleAdd" :on-cancel="handleCancelAdd" :roles="roles"></user-dialog>
                <user-dialog title="编辑管理员" v-if="editVisible" :on-confirm="handleEdit" :init-data="currentItem" :on-cancel="handleCancelEdit" :roles="roles"></user-dialog>
                <el-row id="pagnationbar">
                    <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="dataCount">
                    </el-pagination>
                </el-row>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
import { api } from "@/api";
import * as types from "@/store/mutation-types";
import Tablex from "@/components/Tablex.vue";
import UserDialog from "@/components/user/dialog/UserDialog.vue";
export default {
  name: "user",
  components: {
    "table-ex": Tablex,
    "user-dialog": UserDialog
  },
  computed: mapGetters({
    roles: "getRoles",
    users: "getUsers",
    pageSize: "getUserPageSize",
    currentPage: "getCurrerntUserPage",
    totalPage: "getUserTotalPage",
    dataCount: "getUserDataCount",
    page: "getUserPage",
    isRole: "isRole"
  }),
  methods: {
    ...mapActions([
      "getUsersByPage",
      "addUser",
      "editUser",
      "deleteUser",
      "getRolesByPage"
    ]),
    ...mapMutations({
      setCurrentUser: types.SET_CURRENT_USER,
      setPage: types.SET_USER_PAGE,
      setUserFilter: types.SET_USER_FILTER
    }),
    handleAdd(form) {
      this.addUser(form);
      this.addVisible = false;
    },
    handleCancelAdd() {
      this.addVisible = false;
    },
    handleEdit(form) {
      this.editUser(form);
      this.editVisible = false;
    },
    handleCancelEdit() {
      this.editVisible = false;
    },
    handleSizeChange(v) {
      this.setPage({ pageSize: v });
      this.getUsersByPage({ page: this.page });
    },
    handleCurrentChange(v) {
      this.setPage({ currentPage: v });
      this.getUsersByPage({ page: this.page });
    },
    edit(index, row, col) {
      this.currentItem = row;
      this.editVisible = true;
    },
    delete(index, row, col) {
      this.deleteUser(row.id);
    },
    formatter(row, column) {
      let key = column.property ? column.property : column.prop;
      let value = row[column.property];
      if (value) {
        if (key === "user") {
          return value.name;
        } else if (key === "birth") {
          let birth = new Date(value);
          let current = new Date();
          return current.getFullYear() - birth.getFullYear();
        }
      }
      return value;
    },
    startSearch() {
      console.log(
        "searchType::",
        this.searchType,
        "searchKeyword::",
        this.searchKeyword
      );
      if (this.searchKeyword) {
        let filter = {};
        filter[this.searchType] = this.searchKeyword;
        this.setUserFilter({ filter });
        this.getUsersByPage();
      } else {
        this.setUserFilter({ filter: {} });
        this.getUsersByPage();
      }
    }
  },
  mounted() {
    this.getUsersByPage();
    // this.getRolesByPage();
  },

  data() {
    return {
      searchType: "username",
      searchKeyword: "",
      addVisible: false,
      editVisible: false,
      columnList: [
        {
          prop: "id",
          label: "id",
          width: 180
        },
        {
          prop: "username",
          label: "名称",
          width: 200
        },
        {
          prop: "operation",
          label: "操作",
          width: 250,
          operation: [
            { name: "[编辑]", do: this.edit },
            { name: "[删除]", do: this.delete }
          ]
        }
      ]
    };
  }
};
</script>
