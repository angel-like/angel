<template>
  <nav class="site-navbar" :class="'site-navbar--' + navbarLayoutType">
    <div class="site-navbar__header">
      <h1 class="site-navbar__brand" @click="$router.push({ name: 'home' })">
        <a class="site-navbar__brand-lg" href="javascript:;">ZX管端管理系统</a>
        <a class="site-navbar__brand-mini" href="javascript:;">ZX</a>
      </h1>
    </div>
    <div class="site-navbar__body clearfix">
      <el-menu
        class="site-navbar__menu"
        mode="horizontal">
        <el-menu-item class="site-navbar__switch" ref="zd" index="0" @click="sidebarFold = !sidebarFold">
          <icon-svg name="zhedie"></icon-svg>
        </el-menu-item>
      </el-menu>
      <!--start-->
      <el-menu style="padding-left: 25%"
        class="site-navbar__menu" mode="horizontal">
          <div  style="padding-top: 13px" class="mod-home-all" >
            <el-tag>
              在线总人数：{{totalNum}}
            </el-tag>
            <el-tag>
              WEB：{{pcNum}}
            </el-tag>
            <el-tag>
              安卓：{{androidNum}}
            </el-tag>
            <el-tag>
              IOS：{{iphoneNum}}
            </el-tag>
            <el-tag>
              充值：{{sumTodaySuccess}}
            </el-tag>
            <el-tag>
              取款：{{sumTakeMoneyTodaySuccess}}
            </el-tag>
          </div>
      </el-menu>


      <!--end-->
      <el-menu
        class="site-navbar__menu site-navbar__menu--right"
        mode="horizontal">
				<el-menu-item index="1" @click="$router.push({ name: 'message' })">
					<template slot="title">
						<el-badge :value="messageNum" class="item" type="warning">
							<i class="el-icon-message"></i>
							<!-- <icon-svg name="message" class="el-icon-message"></icon-svg> -->
						</el-badge>
					</template>
				</el-menu-item>
        <el-menu-item index="2" @click="$router.push({ name: 'theme' })">
          <template slot="title">
            <el-badge value="new">
              <icon-svg name="shezhi" class="el-icon-setting"></icon-svg>
            </el-badge>
          </template>
        </el-menu-item>
       <!-- <el-menu-item index="2">
          <el-badge value="hot">
            <a href="//www.renren.io/" target="_blank">官方社区</a>
          </el-badge>
        </el-menu-item>
        <el-submenu index="3">
          <template slot="title">Git源码</template>
          <el-menu-item index="2-1"><a href="//github.com/daxiongYang/renren-fast-vue" target="_blank">前端</a></el-menu-item>
          <el-menu-item index="2-2"><a href="//git.oschina.net/renrenio/renren-fast" target="_blank">后台</a></el-menu-item>
          <el-menu-item index="2-3"><a href="//git.oschina.net/renrenio/renren-generator" target="_blank">代码生成器</a></el-menu-item>
        </el-submenu>-->
        <el-menu-item class="site-navbar__avatar" index="3">
          <el-dropdown :show-timeout="0" placement="bottom">
            <span class="el-dropdown-link">
              <img src="~@/assets/img/avatar.png" :alt="userName">{{ userName }}
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="updatePasswordHandle()">修改密码</el-dropdown-item>
              <el-dropdown-item @click.native="logoutHandle()">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-menu-item>
      </el-menu>
    </div>
    <!-- 弹窗, 修改密码 -->
    <update-password v-if="updatePassowrdVisible" ref="updatePassowrd"></update-password>
  </nav>
</template>

<script>
  import UpdatePassword from './main-navbar-update-password'
  import { clearLoginInfo } from '@/utils'
  export default {
    data () {
      return {
        updatePassowrdVisible: false,
        pcNum: 0,
        androidNum: 0,
        iphoneNum: 0,
        totalNum: 0,
        sumTodaySuccess: 0,
        sumTakeMoneyTodaySuccess: 0
      }
    },
    components: {
      UpdatePassword
    },
		created () {
      let that = this
			this.getMessageNum()
      this.getLoginCountByDeviceType()
      setInterval(()=>{
        that.getLoginCountByDeviceType()
      },60000)
		},
    computed: {
			messageNum:{
				get () {return this.$store.state.common.messageNum },
				set (val) { this.$store.commit('common/updateMessageNum', val)}
			},
      navbarLayoutType: {
        get () { return this.$store.state.common.navbarLayoutType }
      },
      sidebarFold: {
        get () {
          if(this.$store.state.common.sidebarFold ){
            this.$refs.zd.$el.classList.remove('is-active')
          }else{
            this.$refs.zd.$el.classList.add('is-active')
          }
          return this.$store.state.common.sidebarFold },
        set (val) { this.$store.commit('common/updateSidebarFold', val) }
      },
      mainTabs: {
        get () { return this.$store.state.common.mainTabs },
        set (val) { this.$store.commit('common/updateMainTabs', val) }
      },
      userName: {
        get () { return this.$store.state.user.name }
      }
    },
    methods: {

      //获取在线人数
      getLoginCountByDeviceType() {
        this.$http({
          url: this.$http.adornUrl('/userstatistics/userstatistics/getLoginCountByDeviceType'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.pcNum = data.data.pcNum;
            this.androidNum = data.data.androidNum;
            this.iphoneNum = data.data.iphoneNum;
            this.totalNum = data.data.pcNum + data.data.androidNum + data.data.iphoneNum;
            this.sumTodaySuccess = data.data.successAmount;
            this.sumTakeMoneyTodaySuccess = data.data.successOrderNum

          } else {
            this.$message.error(data.msg)
          }
        })
      },

			getMessageNum(){
				this.$http({
					url: this.$http.adornUrl('/message/message/getNum'),
					method: 'get',
					params: this.$http.adornParams({})
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.messageNum = data.num
					}
				})
			},
      // 修改密码
      updatePasswordHandle () {
        this.updatePassowrdVisible = true
        this.$nextTick(() => {
          this.$refs.updatePassowrd.init()
        })
      },
      // 退出
      logoutHandle () {
        this.$confirm(`确定进行[退出]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/logout'),
            method: 'post',
            data: this.$http.adornData()
          }).then(({data}) => {
            if (data && data.code === 200) {
              clearLoginInfo()
              this.$router.push({ name: 'login' })
            }
          })
        }).catch(() => {})
      }
    }
  }
</script>
