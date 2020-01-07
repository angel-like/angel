<template>
  <el-card class="box-card-component" style="margin-left:8px;">
    <!-- <div slot="header" class="box-card-header">
      <img src="https://wpimg.wallstcn.com/e7d23d71-cf19-4b90-a1cc-f56af8c0903d.png">
    </div> -->
    <div style="text-align:center; font-size:24px; line-height:40px; box-shadow:0 5px 5px #ccc; color:#008acd;">
      统计人数
    </div>
    <div style="position:relative;">
      <div style="padding-top:35px;" class="progress-item">
        <div class="icon-i"><icon-svg name="pople1"></icon-svg></div>
        <span>会员数量(人)</span>123456
        <!-- <el-progress :percentage="2457"/> -->
      </div>
      <div class="progress-item">
        <div class="icon-i"><icon-svg name="pople2"></icon-svg></div>
        <span>有效会员数量(人)</span>123456
      </div>
      <div class="progress-item">
        <div class="icon-i"><icon-svg name="pople3"></icon-svg></div>
        <span>PC端在线人数(人)</span>123456
      </div>
      <div class="progress-item">
        <div class="icon-i"><icon-svg name="pople4"></icon-svg></div>
        <span>Wap端在线人数(人)</span>123456
      </div>
      <div class="progress-item">
        <div class="icon-i"><icon-svg name="pople5"></icon-svg></div>
        <span>苹果端在线人数(人)</span>123456
      </div>
      <div class="progress-item">
        <div class="icon-i"><icon-svg name="pople6"></icon-svg></div>
        <span>安卓端在线人数(人)</span>123456
      </div>
    </div>
  </el-card>
</template>

<script>
import { mapGetters } from 'vuex'
import PanThumb from '@/components/PanThumb'
import Mallki from '@/components/TextHoverEffect/Mallki'

export default {
  components: { PanThumb, Mallki },

  filters: {
    statusFilter (status) {
      const statusMap = {
        success: 'success',
        pending: 'danger'
      }
      return statusMap[status]
    }
  },
  data () {
    return {
      statisticsData: {
        article_count: 1024,
        pageviews_count: 1024
      }
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'avatar',
      'roles'
    ])
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" >
.box-card-component{
  height: 768px;
  padding-top: 20px;
  border-radius: 15px;
  .el-card__header {
    padding: 0px!important;
  }
}
</style>
<style rel="stylesheet/scss" lang="scss" scoped>
.box-card-component {
  .box-card-header {
    position: relative;
    height: 220px;
    img {
      width: 100%;
      height: 100%;
      transition: all 0.2s linear;
      &:hover {
        transform: scale(1.1, 1.1);
        filter: contrast(130%);
      }
    }
  }
  .mallki-text {
    position: absolute;
    top: 0px;
    right: 0px;
    font-size: 20px;
    font-weight: bold;
  }
  .panThumb {
    z-index: 100;
    height: 70px!important;
    width: 70px!important;
    position: absolute!important;
    top: -45px;
    left: 0px;
    border: 5px solid #ffffff;
    background-color: #fff;
    margin: auto;
    box-shadow: none!important;
    /deep/ .pan-info {
      box-shadow: none!important;
    }
  }
  .progress-item {
    line-height: 40px;
    padding-top: 10px;
    font-size: 14px;
    border-bottom:1px solid #e4e4e4;
    text-align:right;
    .icon-i{
      text-align:center;
      font-size:50px;
      color:#1296db;
    }
    span{
      float: left;
      display: inline-block;
      font-size:15px;
      font-weight: 600;
    }
  }
  @media only screen and (max-width: 1510px){
    .mallki-text{
      display: none;
    }
  }
}
</style>
