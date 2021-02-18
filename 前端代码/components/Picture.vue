<template>
  <div>
    <canvas
      id="canvas"
      @mousedown="onmousedown"
      @mousemove="onmousemove"
      @mouseup="onmouseup"
    ></canvas>
    <div id="fu">
      <div v-if="display" class="cup">
        <img :src="'http://192.168.66.1/img/' + Pictures[index].name" />
        <div id="barcon">
          <ul>
            <li>一共有{{ Pictures.length }}图片,当前在第{{ index + 1 }}张</li>
            <li>
              <a href="JavaScript:void(0)" @click="first">首页</a>
            </li>
            <li>
              <a href="JavaScript:void(0)" @click="indexSub">上一页</a>
            </li>
            <li>
              <a href="JavaScript:void(0)" @click="indexPlus">下一页</a>
            </li>
            <li>
              <a href="JavaScript:void(0)" @click="last">尾页</a>
            </li>
            <li><input id="in" type="text" v-model="tempNum" /></li>
            <li>
              <a href="JavaScript:void(0)" @click="jump">确定</a>
            </li>
          </ul>
        </div>
        <div class="caidan">
          <div class="btn" id="huabi" @click="hua">铅笔</div>
          <div class="btn xpc" @click="xiangpi">橡皮擦</div>
          <div class="btn" @click="clearAll">清屏</div>
          <div class="btn" @click="keep">保存</div>
          <div class="btn2">
            <input
              type="color"
              name="color"
              id="color"
              value="#000000"
              @change="colorInput"
            />
          </div>
          <div @click="deletePic" class="btn">删除图片</div>
          <div class="btn" style="color: black">
            上传图片
            <input
              id="upload"
              style="opacity: 0"
              name="file"
              type="file"
              accept="image/png,image/gif,image/jpeg"
              @change="update"
            />
          </div>
          <div class="btn" id="size">
            <div id="size1" @click="sizePlus">+</div>
            <div id="size2">{{ size }}</div>
            <div id="size3" @click="sizeSub">-</div>
          </div>
        </div>
      </div>
      <div v-if="!display">
        <h1>没有图片 请先添加图片</h1>
        <div id="caidan2" class="btn" style="color: black">
          上传图片
          <input
            id="upload2"
            style="opacity: 0"
            name="file"
            type="file"
            accept="image/png,image/gif,image/jpeg"
            @change="update"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Picture",
  props: {
    img_id: 0, //相册id
  },
  data: function () {
    return {
      temp: "#000", //临时颜色
      tempNum: 0, //临时序号
      index: 0, //当前图片在相册中的序号
      ctx: null, //canvas Element节点
      canvas: null, //canvas 2D对象
      size: 6, //临时画笔粗细
      huaban: {
        //画板对象
        type: "none", //当前画笔状态 画笔 OR 橡皮
        isDraw: false, //画板是否可用
        beginX: 0, //开始坐标
        beginY: 0,
        lineWidth: 6, //画笔粗细
        color: "#000", //画笔颜色
      },
      note: [], //坐标存放数组
      line: [], //每个线段坐标带你存放数组
      display: false, //是否有图片 有:true显示图片 没有不显示 提示上传图片
      Pictures: {
        //所有图片信息
        type: Array,
        default: () => [
          {
            id: 0,
            name: "",
            album_id: 0,
            p_date: 0,
            note: "",
          },
        ],
      },
    };
  },
  mounted() {
    console.log(this.img_id);
    this.onloadPicture();
    this.canvas = document.querySelector("#canvas");
    this.ctx = this.canvas.getContext("2d");
    this.canvas.setAttribute("width", canvas.offsetWidth);
    this.canvas.setAttribute("height", canvas.offsetHeight);
  },
  methods: {
    //上传图片
    update(e) {
      var that = this;
      let file = e.target.files[0];
      let param = new FormData(); //创建form对象
      param.append("file", file); //通过append向form对象添加数据
      console.log(param.get("file")); //FormData私有类对象，访问不到，通过get判断值是否传进去
      //设置请求头
      let config = {
        headers: { "Content-Type": "multipart/form-data" },
      };
      this.$axios
        .post(
          "http://192.168.66.1/Picture/add?id=" + this.img_id,
          param,
          config
        )
        .then((response) => {
          that.onloadPicture();
          that.index = 0;
        });
    },
    //下一张图片
    indexPlus() {
      this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
      let temp = this.index;
      temp++;
      if (temp == this.Pictures.length) {
        this.index = 0;
      } else {
        this.index = temp;
      }
      this.clearAll();
      this.note = [];
      this.recurrence();
    },
    //上一张图片
    indexSub() {
      this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
      let temp = this.index;
      temp--;
      if (temp < 0) {
        this.index = this.Pictures.length - 1;
      } else {
        this.index = temp;
      }
      this.clearAll();
      this.note = [];
      this.recurrence();
    },
    //第一张图片
    first() {
      this.index = 0;
      this.clearAll();
      this.note = [];
      this.recurrence();
    },
    //最后一张图片
    last() {
      this.index = this.Pictures.length - 1;
      this.clearAll();
      this.note = [];
      this.recurrence();
    },
    //跳转到指定位置
    jump() {
      var temp = this.tempNum;
      if (temp > this.Pictures.length || temp <= 0) {
        alert("请输入范围内的数字");
      } else {
        this.index = this.tempNum - 1;
      }
      this.clearAll();
      this.note = [];
      this.recurrence();
    },
    //画笔大小增加
    sizePlus() {
      let temp = this.size;
      temp++;
      if (temp == 37) {
        this.size = 6;
        this.huaban.lineWidth = this.size;
      } else {
        this.size = temp;
        this.huaban.lineWidth = this.size;
      }
    },
    //画笔大小减小
    sizeSub() {
      let temp = this.size;
      temp--;
      if (temp == 5) {
        this.size = 36;
        this.huaban.lineWidth = this.size;
      } else {
        this.size = temp;
        this.huaban.lineWidth = this.size;
      }
    },
    //删除图片
    deletePic() {
      var that = this;
      this.$axios({
        method: "get",
        url:
          "http://192.168.66.1/Picture/delete?id=" +
          this.Pictures[this.index].id +
          "&name=" +
          this.Pictures[this.index].name,
      }).then((res) => {
        that.onloadPicture();
      });
    },
    //鼠标点击事件
    onmousedown(e) {
      if (this.huaban.type == "huabi") {
        this.line = [];
        this.huaban.isDraw = true;
        var x = e.pageX - this.canvas.offsetLeft;
        var y = e.pageY - this.canvas.offsetTop;
        this.huaban.beginX = x;
        this.huaban.beginY = y;
        this.ctx.beginPath();
        this.ctx.moveTo(x, y);
      }
    },
    //鼠标移动事件
    onmousemove(e) {
      if (this.huaban.isDraw) {
        this.huabiFn(e);
      }
    },
    //鼠标抬起事件
    onmouseup(e) {
      this.huaban.isDraw = false;
      if (this.huaban.type == "huabi") {
        this.note.push(this.line);
        this.ctx.closePath();
      }
    },
    //画笔事件
    huabiFn(e) {
      var x = e.pageX - this.canvas.offsetLeft;
      var y = e.pageY - this.canvas.offsetTop;
      console.log(x + " " + y);
      this.ctx.lineTo(x, y);
      var point = {
        x: x,
        y: y,
        color: this.huaban.color,
        lineWidth: this.huaban.lineWidth,
      };
      this.line.push(point);
      this.ctx.strokeStyle = this.huaban.color;
      this.ctx.lineWidth = this.huaban.lineWidth;
      this.ctx.lineCap = "round";
      this.ctx.lineJoin = "round";
      this.ctx.stroke();
    },
    //画笔改为橡皮
    xiangpi() {
      this.ctx.strokeStyle = "rgb(255, 255, 255)";
      this.huaban.color = "#ffffff";
      this.ctx.globalCompositeOperation = "destination-out";
    },
    //颜色选择
    colorInput() {
      var color = document.querySelector("#color");
      console.log(color.value);
      this.temp = color.value;
      this.huaban.color = color.value;
    },
    //擦除整个画板
    clearAll() {
      this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
      this.note = [];
    },
    //启用画笔
    hua() {
      this.huaban.color = this.temp;
      this.huaban.type = "huabi";
      this.ctx.globalCompositeOperation = "source-over";
    },
    //上传笔记
    keep() {
      var jsonArr = JSON.stringify(this.note);
      console.log(jsonArr);
      var id = this.Pictures[this.index].id;
      var that = this;
      this.$axios({
        url: "http://192.168.66.1/Picture/edit?id=" + id,
        method: "post",
        data: {
          note: jsonArr,
        },
        headers: {
          "Content-Type": "application/json",
        },
      }).then((res) => {
        console.log(res.data);
      });
    },
    //刷新图片列表
    onloadPicture() {
      var that = this;
      this.$axios({
        method: "get",
        url: "http://192.168.66.1/Picture/findAll?id=" + this.img_id,
      }).then((res) => {
        if (res.data.data.length == 0) {
          that.display = false;
        } else {
          that.display = true;
          that.Pictures = res.data.data;
          console.log(that.Pictures);
          that.recurrence();
        }
      });
    },
    //刷新当前图片涂鸦
    recurrence() {
      var that = this;
      this.$axios({
        method: "get",
        url:
          "http://192.168.66.1/Picture/findNote?id=" +
          that.Pictures[that.index].id,
      }).then((res) => {
        that.note = JSON.parse(res.data.data);
        console.log(that.note);
        if (that.note.length != 0) {
          for (let i = 0; i < that.note.length; i++) {
            console.log(that.note[i]);
            if (that.note[i][0].color == "#ffffff") {
              that.ctx.globalCompositeOperation = "destination-out";
            } else {
              that.ctx.globalCompositeOperation = "source-over";
            }
            that.ctx.beginPath();
            that.ctx.moveTo(that.note[i][0].x, that.note[i][0].y);
            for (let j = 0; j < that.note[i].length; j++) {
              that.ctx.lineTo(that.note[i][j].x, that.note[i][j].y);
              that.ctx.strokeStyle = that.note[i][j].color;
              that.ctx.lineWidth = that.note[i][j].lineWidth;
              that.ctx.lineCap = "round";
              that.ctx.lineJoin = "round";
              that.ctx.stroke();
            }
            that.ctx.closePath();
          }
        } else {
          console.log(that.note.length);
        }
      });
    },
  },
};
</script>

<style scoped>
img {
  height: 800px;
  width: 1400px;
}
canvas {
  position: absolute;
  top: 165px;
  left: 687px;
  z-index: 1;
  height: 800px;
  width: 1400px;
}
.caidan {
  height: 100px;
  width: 100x;
  display: flex;
  margin-left: 410px;
}
.btn,
.btn2 {
  width: 150px;
  height: 50px;
  border: 1px solid #ccc;
  border-radius: 20px;
  text-align: center;
  line-height: 50px;
  color: #999;
  background-size: auto 100%;
  background-position: center;
  background-repeat: no-repeat;
}
.btn.active {
  box-shadow: 0 0 20px deepskyblue;
  border: 1px solid deepskyblue;
}
.line {
  display: flex;
  justify-content: center;
  align-items: center;
}
#id {
  left: 100px;
}
#upload {
  position: absolute;
  top: 1017px;
  left: 1753px;
  z-index: 1;
  width: 150px;
  height: 50px;
}
#upload2 {
  position: absolute;
  top: 205px;
  left: 1315px;
  z-index: 1;
  width: 150px;
  height: 50px;
}
#size {
  display: flex;
  justify-content: space-between; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  user-select: none;
}
#size1 {
  margin-left: 20px;
  font-size: 40px;
  margin-top: -8px;
}
#size2 {
  font-size: 30px;
}
#size3 {
  margin-right: 20px;
  font-size: 60px;
  margin-top: -10px;
}
li {
  display: inline;
}
#barcon {
  margin-left: 20px;
  width: 1700px;
  height: 35px;
  font-size: 25px;
}
a {
  text-decoration: none;
  color: #000;
}
#in {
  width: 20px;
  height: 20px;
}
#caidan2 {
  margin: auto;
}
</style>