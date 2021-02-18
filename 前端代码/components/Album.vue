<template>
  <div>
    <div>
      <el-button class="box1" @click="dialogVisible" style="font-size: 51px"
        >新建相册</el-button
      >
    </div>
    <div id="test">
      <el-button
        id="asdi"
        v-for="(Album, index) in Albums"
        :key="index"
        class="box1"
        @click="sendAlbumPicture(index)"
        >{{ Albums[index].name }}</el-button
      >
    </div>
  </div>
</template>

<script>
export default {
  name: "Album",
  methods: {
    sendAlbumPicture(index) {
      this.onloadAlbum();
      this.$emit("getPicture", this.Albums[index]);
    },
    //刷新相册列表
    onloadAlbum() {
      this.$axios({
        method: "get",
        url: "http://192.168.66.1/Album/findAll",
      }).then((res) => {
        this.Albums = res.data.data;
      });
    },
    //新建相册
    dialogVisible() {
      var text = prompt("请输入相片名字");
      var that = this;
      if (text.length === 0) {
        alert("相片名字不能为空不能为空");
      } else {
        this.$axios({
          method: "get",
          url: "http://192.168.66.1/Album/add?name=" + text,
        }).then((res) => {
          that.onloadAlbum();
        });
      }
    },
  },
  data: function () {
    return {
      Albums: [],
    };
  },
  created() {
    this.onloadAlbum();
  },
};
</script>

<style scoped>
.box1 {
  height: 181px;
  width: 400px;
  padding: 0px;
  font-size: 100px;
  margin: 0px;
  border: 0px;
}
#test {
  min-height: 250px;
  overflow-y: auto;
  max-height: 853px;
}
#asdi {
  border-top: 3px solid #ccc;
}
</style>