<template>
    <el-form ref="office-form" label-width="100px" label-position="right" style="width:500px;margin-top:20px;">
        <el-form-item label="图片" style="margin-top:20px;">
          <el-upload class="avatar-uploader" action="/api/file/history/image"
                    :show-file-list="false" :on-success="handleAvatarSuccess">
            <img v-if="headImageUrl" :src="headImageUrl" class="avatar" >
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
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
    "image",
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
    handleAvatarSuccess(res) {
      this.image = res;
      this.headImageUrl = res.fileUrl;
      this.currentGoods.images[this.index] = this.image
    },
    handleDelete() {
      if (this.onDelete) {
        this.onDelete(this.image, this.index);
      }
    },
    handleAdd() {
            if (this.onAdd) {
                this.onAdd(this.form, this.index);
            }
        },
    handleDown() {
      if (this.onDown) {
        this.onDown(this.image, this.index);
      }
    },
    handleUp() {
      if (this.onUp) {
        this.onUp(this.image, this.index);
      }
    },
    handleSave() {
      if (this.onSave) {
        if (this.image) {
          this.currentGoods.images = [
            {
              id: this.image.id
            }
          ];
        }

        this.onSave(this.currentGoods);
      }
    }
  },
  data() {
    return {
      headImageUrl:this.image.fileUrl
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
