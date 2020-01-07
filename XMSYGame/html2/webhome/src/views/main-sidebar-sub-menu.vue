<template>
  <el-submenu
    v-if="menu.list && menu.list.length >= 1"
    :index="menu.menuId + ''"
    :popper-class="'site-sidebar--' + sidebarLayoutSkin + '-popper'">
    <template slot="title">
      <icon-svg :name="menu.icon || ''" class="site-sidebar__menu-icon"></icon-svg>
      <span>{{ menu.name }}</span>
    </template>
    <sub-menu
      v-for="item in menu.list"
      :key="item.menuId"
      :menu="item"
      :dynamicMenuRoutes="dynamicMenuRoutes">
    </sub-menu>
  </el-submenu>
  <el-menu-item v-else :index="menu.menuId + ''" @contextmenu.prevent.native="openMenu(menu)"
                @click="gotoRouteHandle(menu)">
    <icon-svg :name="menu.icon || ''" class="site-sidebar__menu-icon"></icon-svg>
    <span>{{ menu.name }}</span>
  </el-menu-item>
</template>

<script>

  import SubMenu from './main-sidebar-sub-menu'

  export default {
    name: 'sub-menu',
    rightTab: '',
    props: {
      menu: {
        type: Object,
        required: true
      },
      dynamicMenuRoutes: {
        type: Array,
        required: true
      }
    },
    components: {
      SubMenu
    },
    computed: {
      sidebarLayoutSkin: {
        get() {
          return this.$store.state.common.sidebarLayoutSkin
        }
      }
    },

    methods: {
      openMenu(menu) {
        window.oncontextmenu = function (e) {
          e.preventDefault(); //阻止浏览器自带的右键菜单显示
          var menu = document.getElementById("right-menu");
          menu.style.display = "block"; //将自定义的“右键菜单”显示出来
          menu.style.left = e.clientX + "px";  //设置位置，跟随鼠标
          menu.style.top = e.clientY + "px";
        }
        window.onclick = function (e) { //点击窗口，右键菜单隐藏
          var menu = document.getElementById("right-menu");
          menu.style.display = "none";
        }
        var route = this.dynamicMenuRoutes.filter(item => item.meta.menuId === menu.menuId)
        if (route.length >= 1) {
          this.rightTab = route[0].name
          console.log(this.rightTab)
          var rightTab = document.getElementById("rightTab")
          rightTab.setAttribute("name",this.rightTab)
        }
      },
      // 通过menuId与动态(菜单)路由进行匹配跳转至指定路由
      gotoRouteHandle(menu) {
        var route = this.dynamicMenuRoutes.filter(item => item.meta.menuId === menu.menuId)
        if (route.length >= 1) {
          var rightTab = document.getElementById("rightTab")
          rightTab.setAttribute("name",route[0].name)
          this.$router.push({name: route[0].name})
        }
      }
    }
  }
</script>
