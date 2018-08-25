<template>
  <div class="order data">
      <el-tabs value="query" type="border-card" :style="{height: '100%'}">
            <el-tab-pane id="table-container" label="订单管理" name="query">
                <el-row id="searchbar">
                    <el-col :span="8" :offset="16">
                        <el-input placeholder="搜索内容" v-model="searchKeyword">
                            <el-select v-model="searchType" slot="prepend" placeholder="搜索类型">
                                <el-option label="名称" value="name"></el-option>
                            </el-select>
                            <el-button slot="append" icon="el-icon-search" @click="startSearch"></el-button>
                        </el-input>
                    </el-col>
                </el-row>
                <el-row>
                  <view-comment v-if="commentVisible" :before-close="() => commentVisible=false"/>
                    <table-ex parentContainer="table-container" :columnList="columnList" :subList="['searchbar', 'pagnationbar']" :rowData="orders" :formatter="formatter"></table-ex>
                </el-row>
                <el-row id="pagnationbar">
                    <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="dataCount">
                    </el-pagination>
                </el-row>
            </el-tab-pane>
        </el-tabs>
  </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import * as types from "@/store/mutation-types";
    import Tablex from '@/components/Tablex.vue'
    import { Message, MessageBox } from 'element-ui'
    import ViewComment from '@/components/order/ViewComment.vue'
    import { api } from '@/api'
    export default {
        name: 'order',
        components: {
            'table-ex': Tablex,
            'view-comment': ViewComment
        },
        computed: {
            ...mapGetters({
                orders: 'getOrders',
                constants: 'getConstants',
                pageSize: 'getOrderPageSize',
                currentPage: 'getCurrerntOrderPage',
                totalPage: 'getOrderTotalPage',
                dataCount: 'getOrderDataCount',
                page: 'getOrderPage'
            })
        },
        methods: {
            ...mapActions(['getOrdersByPage', 'editOrder', 'deleteOrder', 'getCommentByOrderIdAndMemberId']),
            ...mapMutations({
                setPage: types.SET_ORDER_PAGE
            }),
            handleSizeChange(v) {
                this.setPage({ pageSize: v })
                this.getOrdersByPage()
            },
            handleCurrentChange(v) {
                this.setPage({ currentPage: v })
                this.getOrdersByPage()
            },
            handleDelivery(index, row, col) {
                MessageBox.confirm('确定发货吗？')
                    .then(() => {
                        this.editOrder({
                            ...row,
                            delivery: '已发货'
                        })
                    })
                    .catch(() => console.log('cancel deleting'))
            },
            delete(index, row, col) {
                this.deleteOrder(row.id)
            },
            viewDetail(index, row, col) {
                if (col.prop === 'delivery') {
                    if (row.delivery === '已评论') {
                        this.getCommentByOrderIdAndMemberId({ orderId: row.id, memberId: row.member.id })
                        this.commentVisible = true
                        console.log(1212, row)
                    }
                }
            },
            formatter(row, column) {
                let key = column.property ? column.property : column.prop
                let value = row[column.property]
                if (key === 'name') {
                    if (row.member && row.member.username) {
                        return row.member.username
                    }
                }
                if (key === 'delivery') {
                    if (row.delivery === '已发货') {
                        return '已发货'
                    }
                    if (row.delivery === '未发货') {
                        return '未发货'
                    }
                    if (row.delivery === '已取消') {
                        return '已取消'
                    }
                    if (row.delivery === '已收货') {
                        return '已收货'
                    }
                    if (row.delivery === '已评论') {
                        return '已评论'
                    }
                }
                if (key === 'goodsName') {
                    if (row.goods && row.goods.name) {
                        return row.goods.name
                    }
                }
                if (key === 'address') {
                    if (row.address && row.address.city && row.address.address) {
                        return row.address.city + row.address.address
                    }
                }
                if (key === 'standard') {
                    let standard
                    if (row.color && row.size) {
                        standard = row.size + ' | ' + row.color
                    } else {
                        if (row.color) {
                            standard = row.color
                        } else if (row.size) {
                            standard = row.size
                        }
                    }
                    return standard
                }
                if (value) {
                }
                return value
            }
        },
        mounted() {
            this.getOrdersByPage()
        },
        data() {
            return {
                commentVisible: false,
                columnList: [
                    {
                        prop: 'id',
                        label: 'id',
                        width: 100
                    },
                    {
                        prop: 'created',
                        label: '创建时间',
                        width: 150
                    },
                    {
                        prop: 'name',
                        label: '用户名',
                        width: 200
                    },
                    {
                        prop: 'goodsName',
                        label: '商品名称',
                        width: 200
                    },
                    {
                        prop: 'count',
                        label: '数量',
                        width: 200
                    },
                    {
                        prop: 'standard',
                        label: '规格',
                        width: 200
                    },
                    {
                        prop: 'address',
                        label: '地址',
                        width: 300
                    },
                    {
                        prop: 'delivery',
                        label: '状态',
                        width: 200,
                        viewDetail: this.viewDetail
                    },
                    {
                        prop: 'operation',
                        label: '操作',
                        width: 100,
                        operation: [{ name: '[发货]', do: this.handleDelivery }, { name: '[删除]', do: this.delete }]
                    }
                ]
            }
        }
    }
</script>

