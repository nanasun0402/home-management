<template>
    <el-dialog :title="title" :visible="true" :before-close="() => onCancel()">
        <el-form ref="office-form" label-width="80px" label-position="right">
            <el-form-item label="名称">
                <el-input placeholder="名称" v-model="form.username" style="width:100%;" :disabled="isAdmin()"></el-input>
            </el-form-item>
            <el-form-item label="密码">
                <el-input placeholder="密码" type="password" v-model="form.password" style="width:100%;"></el-input>
            </el-form-item>
            <el-form-item label="权限" v-if="!isAdmin()">
                <el-checkbox-group v-model="form.authorityIds">
                    <el-checkbox v-for="oneRole in roles" :label="oneRole.key" :key="oneRole.key">{{oneRole.label}}</el-checkbox>
                </el-checkbox-group>
            </el-form-item>
            <el-form-item>
                <el-button @click="onCancelDialog">取消</el-button>
                <el-button type="primary" @click="onConfirmDialog">确定</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>
<script>
import * as types from '@/store/mutation-types'
import { LoginUser } from '@/api'
import { Message } from 'element-ui'
export default {
    name: 'AddHospitalDialog',
    props: ['onConfirm', 'onCancel', 'initData', 'roles', 'title'],
    methods: {
        onConfirmDialog() {
            if (this.form.username === types.ADMIN && !LoginUser.isRole(types.ADMIN)) {
                Message.error("无权限修改超级管理员")
                return
            }
            if (this.onConfirm) {
                this.form.authorities = this.form.authorityIds.map(x => { return { key: x } });
                this.onConfirm(this.form);
            }
        },
        onCancelDialog() {
            if (this.onCancel) {
                this.onCancel(this.form);
            }
        },
        isAdmin() {
            return this.form.username === 'admin'
        }
    },
    data() {
        return {
            form: this.initData ?
                {
                    ...this.initData,
                    authorityIds: this.initData.authorities ? this.initData.authorities.map(x => x.key) : []
                } :
                {
                    hospitalId: undefined,
                    authorityIds: []
                }
        };
    }
}
</script>
