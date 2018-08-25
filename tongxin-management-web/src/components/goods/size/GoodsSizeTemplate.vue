
<template>
    <el-form ref="office-form" label-width="120px" label-position="right" style="width:500px;margin-top:20px; border: 1px solid gray">
        <el-form-item label="商品规格" style="margin-top:20px;">
          <el-row>
            <el-col :span="11"><el-input placeholder="尺寸" v-model="size.name"></el-input></el-col>
            <!-- <el-col :span="11" :offset="2"><el-input placeholder="库存" v-model="size.count"></el-input></el-col> -->
          </el-row>
        </el-form-item>
        <!-- <el-form-item>
          <el-button icon="el-icon-plus" type="primary" @click="handleAddColor">新增商品颜色</el-button>
          <el-button type="primary" @click="handleSubmit">提交</el-button>
        </el-form-item> -->
        <!-- <el-form-item>
          <color-template style="marginBottom:50px" v-for="(color,index) in sizeColors"
                                :currentGoods="currentGoods"
                                :color="color"
                                :index="index"
                                :key="color.id"
                                :on-delete="handleDeleteColor"
                                :on-up="handleUpColor"
                                :on-down="handleDownColor"
                                :on-save="handleSaveColor" />
        </el-form-item> -->
        <el-form-item>
            <el-button @click="handleDelete" icon="delete">删除</el-button>
            <el-button @click="handleUp" icon="arrow-up">上移</el-button>
            <el-button @click="handleDown" icon="arrow-down">下移</el-button>
        </el-form-item>
    </el-form>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
import * as types from "@/store/mutation-types";
// import SizeColorTemplate from "@/components/goods/size/SizeColorTemplate.vue";

export default {
  name: "RotationTemplate",
  props: [
    "size",
    "index",
    "onDelete",
    "onUp",
    "onDown",
    "onConfig",
    "onSave",
    "groups",
    "onAdd",
    "currentGoods"
  ],
  components: {
    // "color-template": SizeColorTemplate
  },
  computed: {
    ...mapGetters({
      goods: "getGoods",
      users: "getUsers",
      pageSize: "getUserPageSize",
      // currentGoods: "getCurrentGoods",
      detailImages: "getGoodsDetailImages",
      sizeColors: "getSizeColors"
    })
  },
  methods: {
    ...mapActions(["editGoods"]),
    ...mapMutations({
      setCurrentGoods: types.SET_CURRENT_GOODS
    }),
    handleDelete() {
      if (this.onDelete) {
        this.onDelete(this.size, this.index);
      }
    },
    handleAdd() {
      if (this.onAdd) {
        this.onAdd(this.form, this.index);
      }
    },
    handleDown() {
      if (this.onDown) {
        this.onDown(this.size, this.index);
      }
    },
    handleUp() {
      if (this.onUp) {
        this.onUp(this.size, this.index);
      }
    },
    handleAddColor() {
      let sizeColors = this.sizeColors;
      sizeColors.push({});
      console.log(1212, sizeColors, this.index);
      this.currentGoods.sizes[this.index].colors = [...sizeColors];
      this.setCurrentGoods({ ...this.currentGoods });
    },
    handleSave() {
      if (this.onSave) {
        if (this.size) {
          console.log(121212, this.size);
          this.currentGoods.sizes = [
            {
              id: this.size.id,
              name: this.size.name,
              count: this.size.count
            }
          ];
        }
        this.onSave(this.currentGoods);
      }
    },
    handleDeleteColor(item, index) {
      this.currentGoods.sizes.colors.splice(index, 1);
    },
    handleUpColor(item, index) {
      if (index > 0) {
        let sizeColors = this.sizeColors;
        let currentItem = sizeColors[index];
        sizeColors[index] = sizeColors[index - 1];
        sizeColors[index - 1] = currentItem;
        this.currentGoods.sizes.colors = [...sizeColors];
      }
    },
    handleDownColor(item, index) {
      if (this.currentGoods.sizes.colors.length > index + 1) {
        let sizeColors = this.sizeColors;
        let currentItem = sizeColors[index];
        sizeColors[index] = sizeColors[index + 1];
        sizeColors[index + 1] = currentItem;
        this.currentGoods.sizes.colors = [...sizeColors];
      }
    },
    handleSaveColor(currentGoods) {
      this.editGoods(this.currentGoods);
    },
    handleSubmit() {
      this.editGoods(this.currentGoods);
    }
  },
  mounted() {
    this.currentGoods.sizes[this.index].name = this.size.name;
    this.currentGoods.sizes[this.index].count = this.size.count;
    console.log(888, this.currentGoods);
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
