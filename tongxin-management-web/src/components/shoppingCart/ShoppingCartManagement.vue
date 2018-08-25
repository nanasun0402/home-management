<template>
  <div class="shoppingCart data">
      <el-tabs value="query" type="border-card" :style="{height: '100%'}">
            <el-tab-pane id="table-container" label="购物车管理" name="query">
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
                    <table-ex parentContainer="table-container" :columnList="columnList" :subList="['searchbar', 'pagnationbar']" :rowData="shoppingCarts" :formatter="formatter"></table-ex>
                </el-row>
                <group-dialog v-if="addGroupVisible" :on-confirm="handleAddGroup" :on-cancel="handleCancelAddGroup" :constants="constants"></group-dialog>
                <group-dialog v-if="editGroupVisible" :on-confirm="handleEditGroup" :on-cancel="handleCancelEditGroup" :init-data="currentGroup" :doctors="doctors" :constants="constants"></group-dialog>
                <el-row id="pagnationbar">
                    <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="dataCount">
                    </el-pagination>
                </el-row>
            </el-tab-pane>
        </el-tabs>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import Tablex from "@/components/Tablex.vue";
export default {
  name: "shoppingCart",
  components: {
    "table-ex": Tablex
  },
  computed: {
    ...mapGetters({
      shoppingCarts: "getShoppingCarts",
      constants: "getConstants",
      pageSize: "getShoppingCartPageSize",
      currentPage: "getCurrerntShoppingCartPage",
      totalPage: "getShoppingCartTotalPage",
      dataCount: "getShoppingCartDataCount",
      page: "getShoppingCartPage"
    })
  },
  methods: {
    ...mapActions(["getShoppingCartsByPage"]),
    handleSizeChange(v) {
      this.setPage({ pageSize: v });
      this.getShoppingCartsByPage();
    },
    handleCurrentChange(v) {
      this.setPage({ currentPage: v });
      this.getShoppingCartsByPage();
    },
    formatter(row, column) {
      let key = column.property ? column.property : column.prop;
      let value = row[column.property];
      if(key === "name") {
          if(row.member && row.member.username) {
              return row.member.username
          }
      }
      if(key === "goodsName") {
          if(row.goods && row.goods.name) {
              return row.goods.name
          }
      }
      if(key === "standard") {
          let standard
          if(row.color && row.size) {
              standard = row.size + ' | ' + row.color
          } else {
              if(row.color) {
                  standard = row.color 
              } else if(row.size) {
                  standard = row.size
              }
          }
          return standard
      }
      if (value) {
       
      }
      return value;
    }
  },
  mounted() {
    this.getShoppingCartsByPage();
    console.log(111111111111111, this.shoppingCarts);
  },
  data() {
    return {
      columnList: [
        {
          prop: "id",
          label: "id",
          width: 100
        },
        {
          prop: "created",
          label: "创建时间",
          width: 100
        },
        {
          prop: "name",
          label: "用户名",
          width: 200
        },
        {
          prop: "goodsName",
          label: "商品名称",
          width: 200
        },
        {
          prop: "count",
          label: "数量",
          width: 200
        },
        {
          prop: "standard",
          label: "规格",
          width: 200
        },
        {
          prop: "operation",
          label: "操作",
          width: 400,
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

