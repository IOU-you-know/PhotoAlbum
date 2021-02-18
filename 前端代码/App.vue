<template>
  <div id="app">
    <el-container class="content">
      <el-header style="height: 138px">自定义相册</el-header>
      <el-container>
        <el-aside width="400px">
          <Album @getPicture="getAlbumPicture" ref="child1Container" />
        </el-aside>
        <el-main>
          <AlbumPicture
            v-if="visible"
            :AlbumPicture="Albums"
            @fun="fun"
            @pic="goto"
          />
          <Picture v-if="!visible" :img_id="img_id" />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Album from "./components/Album";
import AlbumPicture from "./components/AlbumPicture";
import Picture from "./components/Picture";
export default {
  name: "App",
  data: function () {
    return {
      Albums: {}, //当前点击的相册
      Picture: [
        //相册封面的图片信息
        {
          id: 0,
          name: "",
          album_id: 0,
          p_date: 0,
          note: "",
        },
      ],
      visible: true,
      img_id: 0,
    };
  },
  components: {
    Album,
    AlbumPicture,
    Picture,
  },
  methods: {
    getAlbumPicture(Albums) {
      this.visible = true;
      this.Albums = Albums;
      this.img_id = Albums.id;
    },
    fun() {
      this.Albums = {};
      console.log(this.$refs.child1Container);
      this.$refs.child1Container.onloadAlbum();
    },
    goto() {
      this.visible = false;
    },
  },
};
</script>

<style>
.el-header {
  background-color: #409eff;
  color: #fffaf0;
  text-align: center;
  line-height: 138px;
  font-size: 88px;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  line-height: 120px;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 2px;
  height: 1055px;
}
.content {
  height: 100%;
  width: 100%;
}
</style>
