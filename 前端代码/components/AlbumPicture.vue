<template>
  <div>
    <div v-if="JSON.stringify(AlbumPicture) == '{}'">对象为空</div>
    <div v-else class="a">
      <img :src="'http://192.168.66.1/img/' + AlbumPicture.default_img" />
      <br />
      <el-button
        type="primary"
        icon="el-icon-delete"
        @click="deleteAlbum"
        class="btn1"
        >删除相册</el-button
      >
      <el-button type="primary" @click="goto">进入相册</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "AlbumPicture",
  props: {
    AlbumPicture: {
      type: Object,
      default: () => ({}),
    },
  },
  methods: {
    //删除相册
    deleteAlbum() {
      var that = this;
      this.$axios({
        method: "get",
        url:
          "http://192.168.66.1/Album/delete?id=" +
          encodeURI(encodeURI(this.AlbumPicture.id)),
      }).then((res) => {
        that.$emit("fun");
      });
    },
    goto() {
      this.$emit("pic");
    },
  },
};
</script>
<style scoped>
img {
  height: 700px;
  width: 1100px;
}
.btn1 {
  margin-right: 280px;
}
.a {
  line-height: 160px;
}
</style>