<template>
    <div class="nurse data">
        <el-tabs value="query" type="border-card" :style="{height: '100%'}">
            <el-tab-pane id="table-container" label="默认商品管理" name="query">
                <el-row id="searchbar">
                    <el-col :span="16"><el-button icon="el-icon-plus" type="primary" @click="add">新增商品</el-button></el-col>
                    <el-col :span="8">
                        <el-input placeholder="搜索内容" v-model="searchKeyword">
                            <el-select v-model="searchType" slot="prepend" placeholder="搜索类型">
                                <el-option label="标题" value="name"></el-option>
                            </el-select>
                            <el-button slot="append" icon="el-icon-search" @click="startSearch"></el-button>
                        </el-input>
                    </el-col>
                </el-row>
                <el-row id="navbar">
                  <el-col :span="20" :offset="4">
                        <el-radio-group size='small' v-model="currentStatus" @change="handleStatus" :style="{marginBottom: '10px'}">
                          <el-radio-button label="全部用品">全部用品</el-radio-button>
                          <el-radio-button label="生活用品">生活用品</el-radio-button>
                          <el-radio-button label="厨房用品">厨房用品</el-radio-button>
                          <el-radio-button label="卧室用品">卧室用品</el-radio-button>
                          <el-radio-button label="客厅用品">客厅用品</el-radio-button>
                          <el-radio-button label="浴室用品">浴室用品</el-radio-button>
                          <el-radio-button label="今日新品">今日新品</el-radio-button>
                          <el-radio-button label="本周热销">本周热销</el-radio-button>
                          <el-radio-button label="优品特惠">优品特惠</el-radio-button>
                        </el-radio-group>
                  </el-col>
                </el-row>
                <el-row>
                    <table-ex parentContainer="table-container" :columnList="columnList" :subList="['searchbar', 'navbar', 'pagnationbar']" :rowData="goods"></table-ex>
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
import { mapGetters, mapActions, mapMutations } from "vuex";
import { api,LoginUser } from "@/api";
import * as types from "@/store/mutation-types";
import Tablex from "@/components/Tablex.vue";

export default {
  name: "office",
  components: {
    "table-ex": Tablex,
  },
  computed: mapGetters({
    goods: "getGoods",
    currentGoods: 'getCurrentGoods',
    pageSize: "getGoodsPageSize",
    currentPage: "getCurrerntGoodsPage",
    totalPage: "getGoodsTotalPage",
    dataCount: "getGoodsDataCount",
    page: "getGoodsPage"
  }),
  methods: {
    ...mapActions([
      "getGoodsByPage",
      "addGoods",
      "editGoods",
      "deleteGoods",
      "getGoodsById"
    ]),
    ...mapMutations({
      setCurrentGoods: types.SET_CURRENT_GOODS,
      setPage: types.SET_GOODS_PAGE,
      setGoodsFilter: types.SET_GOODS_FILTER
    }),
    handleAdd(form) {
      this.addOffice(form);
      this.addVisible = false;
    },
    handleCancelAdd() {
      this.addVisible = false;
    },
    handleEdit(form) {
      this.editRotation(form);
      this.editVisible = false;
    },
    handleCancelEdit() {
      this.editVisible = false;
    },
    handleSizeChange(v) {
      this.setPage({ pageSize: v });
      this.getGoodsByPage(this.page);
    },
    handleCurrentChange(v) {
      this.setPage({ currentPage: v });
      this.getGoodsByPage(this.page);
    },
    delete(index, row, col) {
      this.deleteGoods(row.id);
    },
    // 新增轮播
    add(form){
        this.addGoods(form)
    },
    edit(index, row, col) {
      this.setGoodsFilter({ filter: { id: this.$route.query.id } })
      this.setCurrentGoods({ currentGoods: row });
      this.$router.push({ name: "addgoods", query: { id: row.id } })
      this.getGoodsById(row.id)
    },
    handleStatus(value) {
            if (value === '全部用品') {
                this.setGoodsFilter({ filter: {} })
            } else if(value === '今日新品' || value === '本周热销' || value === '优品特惠') {
                this.setGoodsFilter({ filter: { label: value } })
            } else {
                this.setGoodsFilter({ filter: { type: value } })
            }
            this.getGoodsByPage()
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
        this.setGoodsFilter({ filter });
        this.getGoodsByPage();
      } else {
        this.setGoodsFilter({ filter: {} });
        this.getGoodsByPage();
      }
    }
  },
  mounted() {
    this.setGoodsFilter({ filter: {} });
    this.getGoodsByPage();
    console.log(111,this.goods)
  },

  data() {
    return {
      searchType: "name",
      searchKeyword: "",
      currentStatus: '全部用品',
      columnList: [
        {
          prop: "id",
          label: "id",
          width: 100
        },
        {
          prop: "name",
          label: "标题",
          width: 300
        },
        {
          prop: "description",
          label: "描述",
          width: 200
        },
        {
          prop: "type",
          label: "类别",
          width: 200
        },
        {
          prop: "label",
          label: "标签",
          width: 200
        },
         {
          prop: "startPrice",
          label: "市场价",
          width: 100
        },
         {
          prop: "salePrice",
          label: "会员价",
          width: 100
        },
        {
          prop: "material",
          label: "材质",
          width: 100
        },
        {
          prop: "goodsCount",
          label: "库存",
          width: 100
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
