<template>
  <div
    class="site-wrapper"
    :class="{ 'site-sidebar--fold': sidebarFold }"
    v-loading.fullscreen.lock="loading"
    element-loading-text="拼命加载中">
    <template v-if="!loading">
      <main-navbar/>
      <main-sidebar/>
      <div class="site-content__wrapper" :style="{ 'min-height': documentClientHeight + 'px' }">
        <main-content/>
      </div>
    </template>
    <!-- 自定义右键菜单dom -->
    <div style="z-index: 99999999999" id="right-menu">
      <div  class="menu-item" id="rightTab" @click="newTab()">
        <!-- 在新标签页中打开链接 -->
        <i class="el-icon-circle-plus-outline"></i>
        打开新标签
      </div>
    </div>
  </div>

</template>

<script>
  import MainNavbar from './main-navbar'
  import MainSidebar from './main-sidebar'
  import MainContent from './main-content'

  export default {
    data () {
      return {
        rightTab: 'home',
        loading: true
      }
    },
    components: {
      MainNavbar,
      MainSidebar,
      MainContent
    },
    computed: {
      documentClientHeight: {
        get () {
          return this.$store.state.common.documentClientHeight
        },
        set (val) {
          this.$store.commit('common/updateDocumentClientHeight', val)
        }
      },
      sidebarFold: {
        get () {
          return this.$store.state.common.sidebarFold
        }
      },
      userId: {
        get () {
          return this.$store.state.user.id
        },
        set (val) {
          this.$store.commit('user/updateId', val)
        }
      },
      userName: {
        get () {
          return this.$store.state.user.name
        },
        set (val) {
          this.$store.commit('user/updateName', val)
        }
      }
    },
    created () {
      this.getUserInfo()
    },
    mounted () {
      this.resetDocumentClientHeight()
    },
    methods: {
      newTab () {
        var rightTab = document.getElementById('rightTab')
        var cls = rightTab.getAttribute('name')
        if (cls) {
          window.open(cls, '_blank')
        }
      },
      // 重置窗口可视高度
      resetDocumentClientHeight () {
        this.documentClientHeight = document.documentElement['clientHeight']
        window.onresize = () => {
          this.documentClientHeight = document.documentElement['clientHeight']
        }
      },
      // 获取当前管理员信息
      getUserInfo () {
        this.$http({
          url: this.$http.adornUrl('/sys/user/info'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.loading = false
            this.userId = data.user.userId
            this.userName = data.user.username
          }
        })
      }
    }
  }
</script>
<style type="text/css">
  #right-menu {
    position: absolute;
    left: 0;
    top: 0;
    height: auto;
    border: 1px #ccc solid;
    display: none;
    padding: 10px;
    background-color: rgba(0, 0, 0, .4);
    color: #fff;
    /* box-shadow: 5px 5px 5px #ccc; */
    font-size: 14px;
    cursor: pointer;
  }

  .menu-item {
    margin: 0px;
    padding: 0px;
    font-size: 14px;
    text-align: center;
    list-style-type: none;
  }

  #right-menu:hover {
    background-color: #17B3A3;
  }

  .menu-item-separator {
    border-top: 1px #ccc solid;
    height: 1px;
  }
</style>
