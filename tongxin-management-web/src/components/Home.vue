<template>
    <div class="home">
        <el-row>
            <div :style="{height: '50px', backgroundColor: '#49bdcc'}">
                <div :style="{display:'block', float:'left', width: '200px', fontSize: '20px', paddingTop: '12px', paddingLeft: '20px', color: 'white'}">创优家居管理平台</div>
                <div :style="{float: 'right', paddingTop: '12px', paddingRight: '20px', color: 'white'}" v-if="authUser">
                    <el-dropdown style="color: white;">
                        <span class="el-dropdown-link">
                            <i class="fa fa-user icon1"></i>{{authUser.username}}
                            <i class="el-icon-caret-bottom el-icon--right"></i>
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>
                                <div @click="() => handleLogout()">注销</div>
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </div>
        </el-row>
        <div :style="{height: '100%', width: '200px', display: 'block', float:'left'}">
            <side-bar></side-bar>
        </div>
        <div id="loadingTarget" :style="{width:'calc(100% - 200px)', height: 'calc(100% - 50px)', float: 'right', marginBottom: '30px'}">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
    import Sidebar from "@/components/Sidebar"
    import { mapGetters } from "vuex"
    import { LoginUser } from "@/api"

    export default {
        name: "home",
        components: {
            "side-bar": Sidebar
        },
        mounted() {
            console.log("home::", this.authUser)
        },
        computed: mapGetters({
            authUser: "getCurrentUser"
        }),
        methods: {
            handleLogout() {
                console.log("handleLogout")
                LoginUser.save(null)
                this.$router.push("/login")
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.home {
    width: 100%;
    height: 100%;
    background-color: #f1f1f1;
}
</style>
