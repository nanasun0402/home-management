<template>
    <el-dialog title="评论详情" :visible="true" :before-close="closeViewComment" custom-class="el-dialog--small-ex">
        <div v-if="currentComment" style="margin-bottom:40px">
            <div style="width: 100%; height: 80px; text-align: center">
                <img v-if="currentComment.member.headImage" width="80" height="80" style="border-radius: 50%; border: none" :src="currentComment.member.headImage && currentComment.member.headImage.id ? currentComment.member.headImage.fileUrl : ''">
                <!-- <span v-else class="fa fa-user-circle" style="font-size: 80px; color:var(--global-caring-color)"></span> -->
            </div>
            <div style="margin-left: 50px">
                <p>用户名： {{currentComment.member.username}}</p>
                <p>手机号： {{currentComment.member.mobile}}</p>
                <p>评论内容： {{currentComment.description}}</p>
                <p>评论图片： <img v-for="img in currentComment.images" :key="img.id" :src="img.fileUrl" alt="" width="100px" height="100px"></p>
                <!-- <el-button @click="deleteComment">删除该评论</el-button> -->
            </div>
        </div>
    </el-dialog>
</template>
<script>
    import { mapGetters, mapActions } from 'vuex'
    import * as types from '@/store/mutation-types'
    import { Message, MessageBox } from 'element-ui'
    import { api } from '@/api'
    export default {
        name: 'viewComment',
        props: ['beforeClose'],
        computed: mapGetters({
            constants: 'getConstants',
            currentComment: 'getCurrentComment'
        }),
        methods: {
            ...mapActions(['deleteComment']),
            imageUrl(imageId) {
                return api.getImageUrl(imageId)
            },
            deleteComment() {
                MessageBox.confirm('确定删除该评论么？')
                    .then(() => {
                        this.deleteComment(this.currentComment.id)
                        Message({
                            message: '已删除该评论！',
                            type: 'success'
                        })
                    })
                    .catch(() => console.log('cancel deleting'))
            },
            formatter(propItem) {
                if (propItem.formatter) {
                    return propItem.formatter(this.constants, this.myDoctor, propItem)
                }
                let value = this.myDoctor[propItem.prop]
                if (propItem.type && value) {
                    return this.constants[value].label
                } else if (!value) {
                    return '未填'
                }
                return value
            },
            closeViewComment() {
                this.beforeClose()
            }
        },
        mounted() {},
        data() {
            return {}
        }
    }
</script>

