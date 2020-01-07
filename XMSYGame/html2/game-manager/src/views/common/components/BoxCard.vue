<template>
  <el-card class="box-card-component" style="margin-left:8px; margin-top:33px;">
    <!-- <div slot="header" class="box-card-header">
      <img src="https://wpimg.wallstcn.com/e7d23d71-cf19-4b90-a1cc-f56af8c0903d.png">
    </div> -->
    <div class="boxcard-line" style="position:relative;" >
      <div class="" style="line-height:35px;"><span> - 线上存款金额:{{data.list.onlineAmount}}</span></div>
			<div style="line-height:35px;"><span>- 扫码存款:{{data.list.qrcodeAmount}}</span></div>
			<div style="line-height:35px;"><span>- 存款总额:{{data.list.rechargeAmount}}</span></div>
			<div style="line-height:35px;"><span>- 会员总人数:{{data.list.userNum}}</span></div>


    </div>
  </el-card>
</template>

<script>
import { mapGetters } from 'vuex'
import PanThumb from '@/components/PanThumb'
import Mallki from '@/components/TextHoverEffect/Mallki'

export default {
  components: { PanThumb, Mallki },
  props: ['data'],
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
  .el-card__header {
    padding: 0px!important;
  }
}
</style>
<style rel="stylesheet/scss" lang="scss" scoped>
.box-card-component {
  .boxcard-line span{
    padding: 5px 10px;
    border:1px solid #ccc;
  }
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
    margin-bottom: 10px;
    font-size: 14px;
  }
  @media only screen and (max-width: 1510px){
    .mallki-text{
      display: none;
    }
  }
}
</style>
