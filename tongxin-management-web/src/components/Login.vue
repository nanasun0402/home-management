
<template>
    <div class="login-view-background login">
        <div class="login-title">
            创优家居管理平台
        </div>
        <div class="login-form">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="用户名">
                    <el-input v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="form.password" type="password"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">登陆</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import { mapMutations } from 'vuex'
import { api, LoginUser } from '@/api'
import * as types from '@/store/mutation-types'
export default {
    name: 'login',
    data() {
        return {
            form: {
                username: '',
                password: ''
            }
        }
    },
    mounted() {
        console.log('In Login mounted')
    },
    methods: {
        ...mapMutations({
            setCurrentUser: types.SET_CURRENT_USER
        }),
        onSubmit() {
            api.login(this.form.username, this.form.password, resp => {
                this.setCurrentUser({ currentUser: LoginUser.get().user })
                console.log(222,LoginUser.get().user)
                this.$router.push('/')
            })
        }
    }
}
</script>
<style scoped>
.login-view-background {
    background-color: #40A2D0;
    height: 100%;
    text-align: center;
}

.login-title {
    padding-top: 100px;
    width: 350px;
    color: white;
    font-size: 30px;
    margin: 0px calc(50% - 100px) 0 calc(50% - 200px);
}

.login-form {
    width: 300px;
    margin: 20px calc(50% - 100px) 0 calc(50% - 200px);
    padding: 30px 40px 0 0;
    border-radius: 10px;
    border: 1px solid #bfcbd9;
    background-color: white;
}
</style>
