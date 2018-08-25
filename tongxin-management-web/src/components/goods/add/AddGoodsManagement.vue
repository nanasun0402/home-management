<template>
    <div class="nurse data">
        <el-tabs value="query" type="border-card" :style="{height: '100%'}">
            <el-tab-pane id="table-container" label="商品管理" name="query">
                <el-button type="primary" @click="handleSize">商品规格、颜色</el-button>
                <el-button type="primary" @click="handleImages">商品展示图片</el-button>
                <el-button type="primary" @click="handleDetailImages">商品详情图片</el-button>
                <el-button type="primary" @click="handleSubmit">提交</el-button>
                <div class="form-edit-container">
                    <el-form ref="goods-form" label-width="100px" label-position="right" style="width:500px; padding-bottom: 80px">
                        <el-form-item label="标题" style="margin-top:20px;">
                            <el-input placeholder="标题" v-model="currentGoods.name" style="width:95%;"></el-input>
                        </el-form-item>
                        <el-form-item label="商品描述" style="margin-top:20px;">
                            <el-input placeholder="描述" v-model="currentGoods.description" style="width:95%;"></el-input>
                        </el-form-item>
                        <el-form-item label="商品类别" style="margin-top:20px;">
                            <el-select v-model="currentGoods.type" placeholder="请选择" style="width 50%">
                                <el-option
                                    v-for="type in types"
                                    :key="type.value"
                                    :label="type.label"
                                    :value="type.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="商品标签" style="margin-top:20px;">
                            <el-select v-model="currentGoods.label" placeholder="请选择" style="width 50%">
                                <el-option
                                    v-for="item in labels"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="市场价" style="margin-top:20px;">
                            <el-input placeholder="市场价" v-model="currentGoods.startPrice" style="width:95%;"></el-input>
                        </el-form-item>
                        <el-form-item label="会员价" style="margin-top:20px;">
                            <el-input placeholder="会员价" v-model="currentGoods.salePrice" style="width:95%;"></el-input>
                        </el-form-item>
                        <el-form-item label="材质" style="margin-top:20px;">
                            <el-input placeholder="材质" v-model="currentGoods.material" style="width:95%;"></el-input>
                        </el-form-item>
                        <el-form-item label="库存" style="margin-top:20px;">
                            <el-input placeholder="库存" v-model="currentGoods.goodsCount" style="width:95%;"></el-input>
                        </el-form-item>
                    </el-form>
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
    handleSize() {
      this.$router.push({path: 'goodsSizes', query: {id: this.currentGoods.id}})
    },
    handleDetailImages() {
      this.$router.push({path: 'goodsDetailImages', query: {id: this.currentGoods.id}})
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
