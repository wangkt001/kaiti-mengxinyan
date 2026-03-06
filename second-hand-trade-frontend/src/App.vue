<template>
  <div id="app">
    <el-container>
      <el-header height="60px">
        <div class="header-content">
          <div class="logo">
            <h1>校园二手交易平台</h1>
          </div>
          <div class="nav">
            <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
              <el-menu-item index="/">首页</el-menu-item>
              <el-menu-item index="/goods/list">商品列表</el-menu-item>
              <el-menu-item index="/goods/add">发布商品</el-menu-item>
              <el-menu-item v-if="isLoggedIn" index="/user/center">个人中心</el-menu-item>
              <el-menu-item v-if="isLoggedIn" index="/order/list">订单管理</el-menu-item>
              <el-menu-item v-if="isLoggedIn && user.role === 'admin'" index="/admin">管理员后台</el-menu-item>
              <template v-else>
                <el-menu-item index="/login">登录</el-menu-item>
                <el-menu-item index="/register">注册</el-menu-item>
              </template>
              <el-menu-item v-if="isLoggedIn" index="/logout" @click="logout">退出登录</el-menu-item>
            </el-menu>
          </div>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
      <el-footer height="60px">
        <div class="footer-content">
          <p>© 2026 校园二手交易平台</p>
        </div>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from './store/index.js'
import { userApi } from './api/modules/user.js'

export default {
  name: 'App',
  setup() {
    const router = useRouter()
    const route = useRoute()
    const userStore = useUserStore()
    const activeIndex = ref('/')

    const isLoggedIn = computed(() => userStore.isLoggedIn)
    const user = computed(() => userStore.user)

    const handleSelect = (key) => {
      if (key === '/logout') {
        logout()
      } else {
        router.push(key)
      }
    }

    const logout = async () => {
      try {
        await userApi.logout()
        userStore.logout()
        router.push('/')
      } catch (error) {
        console.error('退出登录失败:', error)
      }
    }

    const checkLoginStatus = async () => {
      try {
        const res = await userApi.getInfo()
        if (res) {
          userStore.setUser(res)
        }
      } catch (error) {
        console.error('获取用户信息失败:', error)
      }
    }

    onMounted(() => {
      activeIndex.value = route.path
      checkLoginStatus()
    })

    return {
      activeIndex,
      isLoggedIn,
      user,
      handleSelect,
      logout
    }
  }
}
</script>

<style scoped>
#app {
  height: 100vh;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  padding: 0 20px;
  background-color: #409EFF;
  color: white;
}

.logo h1 {
  font-size: 20px;
  margin: 0;
}

.nav {
  flex: 1;
  margin-left: 50px;
}

.el-menu-demo {
  background-color: transparent;
  border-bottom: none;
}

.el-menu-item {
  color: white;
}

.el-menu-item:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.el-menu-item.is-active {
  background-color: rgba(255, 255, 255, 0.2);
  color: white;
}

.footer-content {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-color: #f5f7fa;
  color: #606266;
}

.footer-content p {
  margin: 0;
}
</style>
