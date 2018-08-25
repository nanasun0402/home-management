
<template>
    <el-form ref="office-form" label-width="120px" label-position="right" style="width:500px;margin-top:20px; border: 1px solid gray">
        <el-form-item label="商品颜色" style="margin-top:20px;">
          <el-row>
            <el-col :span="11"><el-input placeholder="颜色" v-model="color.name"></el-input></el-col>
            <!-- <el-col :span="11" :offset="2"><el-input placeholder="库存" v-model="color.count"></el-input></el-col> -->
          </el-row>
        </el-form-item>
        <el-form-item>
            <el-button @click="handleDelete" icon="delete">删除</el-button>
            <el-button @click="handleUp" icon="arrow-up">上移</el-button>
            <el-button @click="handleDown" icon="arrow-down">下移</el-button>
        </el-form-item>
    </el-form>
</template>
<script>
import * as types from "@/store/mutation-types";

export default {
  name: "RotationTemplate",
  props: [
    "color",
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
  methods: {
    handleDelete() {
      if (this.onDelete) {
        this.onDelete(this.color, this.index);
      }
    },
    handleAdd() {
      if (this.onAdd) {
        this.onAdd(this.form, this.index);
      }
    },
    handleDown() {
      if (this.onDown) {
        this.onDown(this.color, this.index);
      }
    },
    handleUp() {
      if (this.onUp) {
        this.onUp(this.color, this.index);
      }
    },
    handleSave() {
      if (this.onSave) {
        if (this.color) {
          console.log(121212, this.color);
          this.currentGoods.colors = [
            {
              id: this.color.id,
              name: this.color.name,
              count: this.color.count
            }
          ];
        }
        this.onSave(this.currentGoods);
      }
    }
  },
  mounted() {
    this.currentGoods.colors[this.index].name = this.color.name
    this.currentGoods.colors[this.index].count = this.color.count
    console.log(888, this.currentGoods);
  },
  data() {
    return {

    };
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
