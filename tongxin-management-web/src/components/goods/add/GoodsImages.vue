<template>
    <div class="nurse data">
        <el-tabs value="query" type="border-card" :style="{height: '100%'}">
            <el-tab-pane id="table-container" label="商品管理" name="query">
                <el-button icon="el-icon-plus" type="primary" @click="handleAddGoods">新增商品图片</el-button>
                <el-button type="primary" @click="handleSubmit">提交</el-button>
                <div class="form-edit-container">
                              <addgoods-template style="marginBottom:80px" v-for="(image,index) in images"
                                           :currentGoods="currentGoods"
                                           :image="image"
                                           :index="index"
                                           :key="image.id"
                                           :on-delete="handleDelete"
                                           :on-up="handleUp"
                                           :on-down="handleDown"
                                           :on-save="handleSave" />
                           
                </div>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
import { api } from "@/api";
import * as types from "@/store/mutation-types";
import Tablex from "@/components/Tablex.vue";
import AddGoodsTemplate from "@/components/goods/add/AddGoodsTemplate.vue";
import AddDetailImageTemplate from "@/components/goods/add/AddDetailImageTemplate.vue";

export default {
  name: "goods",
  components: {
    "table-ex": Tablex,
    "addgoods-template": AddGoodsTemplate,
    "detailimages-template": AddDetailImageTemplate
  },
  computed: {
    ...mapGetters({
      goods: "getGoods",
      users: "getUsers",
      pageSize: "getUserPageSize",
      currentGoods: "getCurrentGoods",
      images: "getGoodsImages",
      detailImages: "getGoodsDetailImages"
    })
  },
  methods: {
    ...mapActions([
      "getRotationsByPage",
      "getGoodsById",
      "getCurrentRotationById",
      "addRotation",
      "editGoods",
      "deleteRotation",
      "getRolesByPage"
    ]),
    ...mapMutations({
      setGoodsFilter: types.SET_GOODS_FILTER,
      setCurrentGoods: types.SET_CURRENT_GOODS
    }),
    handleDelete(item, index) {
      this.currentGoods.images.splice(index, 1)
    },
    handleImages() {
      this.$router.push({path: 'goodsImages', query: {id: this.currentGoods.id}})
    },
    handleUp(item, index) {
      if (index > 0) {
        let images = this.images
        let currentItem = images[index]
        images[index] = images[index - 1]
        images[index - 1] = currentItem
        this.currentGoods.images = [...images]
      }
    },
    handleDown(item, index) {
      if (this.currentGoods.images.length > index + 1) {
        let images = this.images
        let currentItem = images[index]
        images[index] = images[index + 1]
        images[index + 1] = currentItem
        this.currentGoods.images = [...images]
      }
    },
    handleAddGoods() {
      //新增轮播图片
      let images = this.images
        images.push({})
        this.currentGoods.images = [...images]
        this.setCurrentGoods({ ...this.currentGoods })
    },
    handleSave(currentGoods) {
      this.editGoods(currentGoods)
    },
    handleSubmit() {
      this.editGoods(this.currentGoods)
    }
  },
  mounted() {
    this.setGoodsFilter({ filter: { id: this.$route.query.id } })
    this.getGoodsById()
  },
  data() {
    return {
      types: [{
          value: '生活用品',
          label: '生活用品'
        }, {
          value: '厨房用品',
          label: '厨房用品'
        }, {
          value: '卧室用品',
          label: '卧室用品'
        }, {
          value: '客厅用品',
          label: '客厅用品'
        }, {
          value: '浴室用品',
          label: '浴室用品'
        }],
        labels: [{
          value: '',
          label: ''
        }, {
          value: '今日新品',
          label: '今日新品'
        }, {
          value: '本周热销',
          label: '本周热销'
        }, {
          value: '优品特惠',
          label: '优品特惠'
        }]
    };
  }
};
</script>
