<template>
    <div id="app">
        <router-view v-if="constants">
        </router-view>
    </div>
</template>

<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
import * as types from "@/store/mutation-types";
import { LoginUser } from "@/api";
export default {
  name: "app",
  // created() {
  //   this.getConstants();
  // },
  mounted() {
    let userInfo = LoginUser.get();
    console.log("App::", userInfo);
    this.setCurrentUser({ currentUser: userInfo ? userInfo.user : null });
  },
  methods: {
    ...mapActions(["getConstants"]),
    ...mapMutations({
      setCurrentUser: types.SET_CURRENT_USER
    })
  },
  computed: {
    ...mapGetters({
      constants: "getConstants"
    })
  }
};
</script>

<style>
* {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

html,
body {
  margin: 0;
  height: 100%;
  width: 100%;
  overflow-y: hidden;
  overflow-x: auto;
  padding: 0;
  min-height: 680px;
  min-width: 800px;
}

#app {
  height: 100%;
  margin: 0;
  padding: 0;
}

.data {
  height: calc(100% - 35px);
  padding: 10px;
  overflow: hidden;
}

.el-tabs__content {
  height: calc(100% - 60px);
}

.el-tab-pane {
  overflow: hidden;
}

.el-table {
  font-size: 12px !important;
  overflow-x: auto;
}

.el-cascader {
  width: 100%;
}

.el-form-item__label,
.el-form-item__content,
.el-input__inner {
  font-size: 14px !important;
}

.el-dialog--small-ex {
  width: 500px !important;
}

.el-dialog__body {
  max-height: 600px;
  overflow-y: auto;
}

.el-dialog__header {
  padding: 10px 20px 10px 20px !important;
  background-color: #49bdcc;
}

.el-dialog__body {
  padding: 20px 20px 0px 20px !important;
}

.el-dialog__title {
  color: white;
}

.el-form-item {
  margin-bottom: 15px !important;
}

.el-dialog__headerbtn .el-dialog__close {
  color: white !important;
  padding-top: 5px;
}

.el-popover__title {
  font-size: 16px;
  margin: 10px 0px 20px 20px;
}
.fa-ellipsis-h {
  color: #49bdcc;
  margin-left: 10px;
}

#table-container {
  height: calc(100% - 10px);
}

.icon0 {
  padding-right: 10px;
  font-size: 20px;
}

.icon1 {
  padding-right: 10px;
  font-size: 15px;
}
.tongxin .el-select > .el-input {
  width: 100%;
}
.el-select .el-input {
  width: 110px;
}
.icon3 {
  color: #6f6f6f;
}

.icon-right {
  padding-right: 10px;
}

#searchbar {
  height: 46px;
}

#pagnationbar {
  margin-top: 10px;
}

.form-edit-container {
  display: block;
  height: 100%;
  overflow-y: auto;
}

.form-sub-edit-container {
  display: block;
  height: 50%;
  overflow-y: auto;
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>
