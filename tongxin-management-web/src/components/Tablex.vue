<template>
    <span>
        <el-table height="250" ref="multipleTable" tooltip-effect="dark" @selection-change="handleSelectionChange" id="resize-table" class="tablex" :data="rowData" border style="width:100%;" :stripe="true">
            <el-table-column
                type="selection"
                width="55" v-if="selected">
            </el-table-column>
            <template v-for="col in columnList">
                <el-table-column v-if="!col.viewDetail && !col.operation && !col.isImage && !col.isImageBase64" :prop="col.prop" :key="col.prop" :label="col.label" :width="col.width ? col.width:100" :formatter="formatter"></el-table-column>
                <el-table-column v-else-if="col.viewDetail" :prop="col.prop" :key="col.prop" :label="col.label" :width="col.width ? col.width:100">
                    <template slot-scope="scope">
                        <span :style="{cursor: 'pointer'}" @click="col.viewDetail(scope.$index, scope.row, col)">[{{col.constant?col.constant:formatter(scope.row, col)}}]</span>
                    </template>
                </el-table-column>
                <el-table-column v-else-if="col.operation" :prop="col.prop" :key="col.prop" :label="col.label" :width="col.width ? col.width:120">
                    <template slot-scope="scope">
                        <span v-for="oneOperation in col.operation" :style="{cursor: 'pointer'}" :key="oneOperation.id" @click="oneOperation.do(scope.$index, scope.row, col)">{{oneOperation.name}}</span>
                    </template>
                </el-table-column>
                <el-table-column v-else-if="col.isImage" :prop="col.prop" :key="col.prop" :label="col.label" :width="col.width ? col.width:100">
                    <template slot-scope="scope">
                        <img width="32" height="32" :src="col.getImageUrl ? col.getImageUrl(scope.$index, scope.row) : scope.row[col.prop]" class="head-img">
                    </template>
                </el-table-column>
                <el-table-column v-else-if="col.isImageBase64" :prop="col.prop" :key="col.prop" :label="col.label" :width="col.width ? col.width:120">
                    <template slot-scope="scope">
                        <img height="32" :src="'data:image/jpg;base64,' + scope.row[col.prop]" class="medicine-img">
                    </template>
                </el-table-column>
            </template>
        </el-table>
    </span>
</template>

<style>
.medicine-img {
  margin: 10px 10px 10px 10px;
}

.head-img {
  margin-top: 10px;
  border-radius: 50%;
}
</style>

<script>
import * as types from "@/store/mutation-types";
export default {
  name: "tablex",
  props: ["parentContainer", "subList", "columnList", "rowData", "formatter"],
  computed: {
    path() {
      return this.$route.path;
    }
  },
  mounted() {
    let self = this;
    this.onResizeListener = window.onresize = () => {
      return (() => {
        let parentContainer = $("#" + self.parentContainer);
        let subBarH = 0;
        if (self.subList) {
          console.log("subList", self.subList);
          for (let id of self.subList) {
            subBarH += $("#" + id).height();
          }
        }
        let H = 0;
        if (self.parentContainer) {
          H = parentContainer.height();
        }
        let resizeTable = parentContainer.find("#resize-table");
        let tableHeaderH = resizeTable
          .find(".el-table__header-wrapper")
          .height();
        let tableContainerH = H - subBarH - 10;
        let tableBodyH = tableContainerH - tableHeaderH;
        resizeTable
          .css("height", tableContainerH + "px")
          .find(".el-table__body-wrapper")
          .css("height", tableBodyH + "px");
        // Log
        console.log(
          "tableContainerH",
          tableContainerH,
          "tableHeaderH",
          tableHeaderH,
          "tableBodyH",
          tableBodyH
        );
      })();
    };
    setTimeout(() => $(window).trigger("resize"), 200);
  },
  destroyed() {
    window.removeEventListener("resize", this.onResizeListener);
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
      this.$emit("tablex-say", this.multipleSelection);
    }
  },
  data() {
    return {
      multipleSelection: [],
      selected: false
    };
  }
};
</script>
