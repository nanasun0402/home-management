<template>
    <div class="nurse data">
        <el-tabs value="query" type="border-card" :style="{height: '100%'}">
            <el-tab-pane id="table-container" label="商品管理" name="query">
                <el-button icon="el-icon-plus" type="primary" @click="handleAddSize">新增商品规格</el-button>
                <el-button icon="el-icon-plus" type="primary" @click="handleAddColor">新增商品颜色</el-button>
                <el-button type="primary" @click="handleSubmit">提交</el-button>
                <div class="form-edit-container">
                    <el-row>
                      <el-col :span="11">
                        <size-template style="marginBottom:50px" v-for="(size,index) in goodsSizes"
                                :currentGoods="currentGoods"
                                :size="size"
                                :index="index"
                                :key="size.id"
                                :on-delete="handleDelete"
                                :on-up="handleUp"
                                :on-down="handleDown"
                                :on-save="handleSave" />
                      </el-col>
                      <el-col :span="11" :offset="2">
                        <color-template style="marginBottom:50px" v-for="(color,index) in goodsColors"
                                :currentGoods="currentGoods"
                                :color="color"
                                :index="index"
                                :key="color.id"
                                :on-delete="handleDeleteColor"
                                :on-up="handleUpColor"
                                :on-down="handleDownColor"
                                :on-save="handleSaveColor" />
                      </el-col>
                    </el-row>
                </div>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
import * as types from "@/store/mutation-types";
import GoodsSizeTemplate from "@/components/goods/size/GoodsSizeTemplate.vue";
import ColorTemplate from "@/components/goods/size/ColorTemplate.vue";

export default {
  name: "RotationTemplate",
  components: {
    "size-template": GoodsSizeTemplate,
    "color-template": ColorTemplate
  },
  computed: {
    ...mapGetters({
      goods: "getGoods",
      users: "getUsers",
      pageSize: "getUserPageSize",
      currentGoods: "getCurrentGoods",
      detailImages: "getGoodsDetailImages",
      goodsSizes: "getGoodsSizes",
      goodsColors: "getGoodsColors"
    })
  },
  methods: {
    ...mapActions(["editGoods"]),
    ...mapMutations({
      setCurrentGoods: types.SET_CURRENT_GOODS
    }),
    handleAddSize() {
      //新增
      let goodsSizes = this.goodsSizes;
      console.log(1212, goodsSizes);
      goodsSizes.push({});
      this.currentGoods.sizes = [...goodsSizes];
      this.setCurrentGoods({ ...this.currentGoods });
    },
    handleAddColor() {
      //新增
      let goodsColors = this.goodsColors;
      console.log(1212, goodsColors);
      goodsColors.push({});
      this.currentGoods.colors = [...goodsColors];
      this.setCurrentGoods({ ...this.currentGoods });
    },
    handleDelete(item, index) {
      this.currentGoods.sizes.splice(index, 1);
    },
    handleUp(item, index) {
      if (index > 0) {
        let goodsSizes = this.goodsSizes;
        let currentItem = goodsSizes[index];
        goodsSizes[index] = goodsSizes[index - 1];
        goodsSizes[index - 1] = currentItem;
        this.currentGoods.sizes = [...goodsSizes];
      }
    },
    handleDown(item, index) {
      if (this.currentGoods.sizes.length > index + 1) {
        let goodsSizes = this.goodsSizes;
        let currentItem = goodsSizes[index];
        goodsSizes[index] = goodsSizes[index + 1];
        goodsSizes[index + 1] = currentItem;
        this.currentGoods.sizes = [...goodsSizes];
      }
    },
    handleSave(currentGoods) {
      console.log(111, currentGoods);
      this.editGoods(this.currentGoods);
    },
    handleSubmit() {
      this.editGoods(this.currentGoods);
    },
    handleDeleteColor(item, index) {
      this.currentGoods.colors.splice(index, 1);
    },
    handleUpColor(item, index) {
      if (index > 0) {
        let goodsColors = this.goodsColors;
        let currentItem = goodsColors[index];
        goodsColors[index] = goodsColors[index - 1];
        goodsColors[index - 1] = currentItem;
        this.currentGoods.colors = [...goodsColors];
      }
    },
    handleDownColor(item, index) {
      if (this.currentGoods.colors.length > index + 1) {
        let goodsColors = this.goodsColors;
        let currentItem = goodsColors[index];
        goodsColors[index] = goodsColors[index + 1];
        goodsColors[index + 1] = currentItem;
        this.currentGoods.colors = [...goodsColors];
      }
    },
    handleSaveColor(currentGoods) {
      console.log(111, currentGoods);
      this.editGoods(this.currentGoods);
    }
  },
  data() {
    return {};
  }
};
</script>

<style scoped>
.avatar-uploader {
  width: 380px;
  height: 220px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader:hover {
  border-color: #20a0ff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 380px;
  height: 220px;
  line-height: 200px;
  text-align: center;
}
.avatar {
  width: 380px;
  height: 220px;
  display: block;
}
</style>