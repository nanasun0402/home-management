<template>
    <div class="nurse data">
        <el-tabs value="query" type="border-card" :style="{height: '100%'}">
            <el-tab-pane id="table-container" label="会员管理" name="query">
                <el-row id="searchbar">
                    <el-col :span="8" :offset="16">
                        <el-input placeholder="搜索内容" v-model="searchKeyword">
                            <el-select v-model="searchType" slot="prepend" placeholder="搜索类型">
                                <el-option label="用户名" value="username"></el-option>
                                <el-option label="手机号" value="mobile"></el-option>
                            </el-select>
                            <el-button slot="append" icon="el-icon-search" @click="startSearch"></el-button>
                        </el-input>
                    </el-col>
                </el-row>
                <el-row>
                    <table-ex parentContainer="table-container" :columnList="columnList" :subList="['searchbar', 'pagnationbar']" :rowData="members" :formatter="formatter"></table-ex>
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
  import { api } from '@/api'
  import * as types from '@/store/mutation-types'
  import Tablex from '@/components/Tablex.vue'
  import { Message, MessageBox } from 'element-ui'

  export default {
      name: 'user',
      components: {
          'table-ex': Tablex
      },
      computed: mapGetters({
          members: 'getMembers',
          pageSize: 'getMemberPageSize',
          currentPage: 'getCurrerntMemberPage',
          totalPage: 'getMemberTotalPage',
          dataCount: 'getMemberDataCount',
          page: 'getMemberPage'
      }),
      methods: {
          ...mapActions(['getMembersByPage', 'addMember', 'editMember', 'deleteMember']),
          ...mapMutations({
              setCurrentMember: types.SET_CURRENT_MEMBER,
              setPage: types.SET_MEMBER_PAGE,
              setMemberFilter: types.SET_MEMBER_FILTER
          }),
          handleSizeChange(v) {
              this.setPage({ pageSize: v })
              this.getMembersByPage({ page: this.page })
          },
          handleCurrentChange(v) {
              this.setPage({ currentPage: v })
              this.getMembersByPage({ page: this.page })
          },
          // 禁用
          handleForbid(index, row, col) {
              MessageBox.confirm('确定禁用此用户吗？')
                  .then(() => {
                      this.editMember({
                          ...row,
                          forbid: true
                      })
                  })
                  .catch(() => console.log('cancel deleting'))
          },
          handleRemoveForbid(index, row, col) {
              MessageBox.confirm('确定解除此用户的禁用吗？')
                  .then(() => {
                      this.editMember({
                          ...row,
                          forbid: false
                      })
                  })
                  .catch(() => console.log('cancel deleting'))
          },
          formatter(row, column) {
              let key = column.property ? column.property : column.prop
              let value = row[column.property]
              if (key === 'forbid' && row.forbid === true) {
                return '已禁用'
              }
              return value
          },
          startSearch() {
              console.log('searchType::', this.searchType, 'searchKeyword::', this.searchKeyword)
              if (this.searchKeyword) {
                  let filter = {}
                  filter[this.searchType] = this.searchKeyword
                  this.setMemberFilter({ filter })
                  this.getMembersByPage()
              } else {
                  this.setMemberFilter({ filter: {} })
                  this.getMembersByPage()
              }
          }
      },
      mounted() {
          this.getMembersByPage()
      },

      data() {
          return {
              searchType: 'username',
              searchKeyword: '',
              addVisible: false,
              editVisible: false,
              columnList: [
                  {
                      prop: 'id',
                      label: 'id',
                      width: 180
                  },
                  {
                      prop: 'created',
                      label: '创建时间',
                      width: 150
                  },
                  {
                      prop: 'username',
                      label: '用户名',
                      width: 200
                  },
                  {
                      prop: 'mobile',
                      label: '手机号',
                      width: 200
                  },
                  {
                      prop: 'forbid',
                      label: '是否禁用',
                      width: 200
                  },
                  {
                      prop: 'operation',
                      label: '操作',
                      width: 250,
                      operation: [{ name: '[禁用]', do: this.handleForbid }, { name: '[解除]', do: this.handleRemoveForbid }]
                  }
              ]
          }
      }
  }
</script>
