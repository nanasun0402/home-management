<template>
    <div class="sidebar" :style="{overflowY: 'auto', display: 'block', float: 'left', width: '200px', height: '100%', backgroundColor: '#324157'}">
        <el-col :span="100" class="sidebar">
            <el-menu 
                :default-active="currentKey" 
                :default-openeds="defaultOpens" 
                :router="true" 
                class="el-menu-vertical-demo" 
                @select="handleSelect" 
                background-color="#324c57" 
                text-color="#fff"
                unique-opened
                >
                <el-menu-item index="member">
                    <i class="fa fa-comments icon1"></i>会员管理</el-menu-item>
                <el-menu-item index="goods">
                    <i class="fa fa-comments icon1"></i>商品管理</el-menu-item>
                <el-menu-item index="order">
                    <i class="fa fa-retweet icon1"></i>订单管理</el-menu-item>
                <el-menu-item index="user">
                    <i class="fa fa-cog icon1"></i>管理员管理</el-menu-item>
            </el-menu>
        </el-col>
    </div>
</template>
<style>
.sidebar {
  background-color: #324c57;
  display: block;
  float: left;
  width: 203px;
  height: 100%;
}

.el-menu-item.is-active {
  color: #42bdcd;
  border-right: 6px solid #49bdcc;
  background-color: #313132;
}
</style>
<script>
import { LoginUser } from "@/api";
export default {
  name: "sidebar",
  data() {
    return {
      defaultOpens: [],
      currentKey: this.currentKey
    };
  },
  mounted() {
    this.updateSidebarStatus();
  },
  watch: {
    $route: "updateSidebarStatus"
  },
  methods: {
    handleSelect(key, keyPath) {},
    isRole(role) {
      return LoginUser.isRole(role);
    },
    updateSidebarStatus() {
      console.log("Sidebar::", this.$route.name);
      this.currentKey = this.$route.name === "" ? "home" : this.$route.name;
    }
  }
};
</script>
