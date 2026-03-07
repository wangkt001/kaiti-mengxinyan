<template>
  <el-header height="60px" class="fixed-header">
    <div class="header-content">
      <div class="logo">
        <h1>校园二手交易平台</h1>
      </div>
      <div class="nav">
        <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
        >
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/goods/list">商品列表</el-menu-item>
          <el-menu-item v-if="isLoggedIn" index="/goods/add"
            >发布商品</el-menu-item
          >
          <el-menu-item v-if="isLoggedIn" index="/user/center"
            >个人中心</el-menu-item
          >
          <el-menu-item v-if="isLoggedIn" index="/order/list"
            >订单管理</el-menu-item
          >
          <el-menu-item
            v-if="isLoggedIn && user?.role === 'admin'"
            index="/admin"
            >管理员后台</el-menu-item
          >
          <el-menu-item v-if="!isLoggedIn" index="/login">登录</el-menu-item>
          <el-menu-item v-if="!isLoggedIn" index="/register">注册</el-menu-item>
          <el-menu-item v-if="isLoggedIn" index="/logout" @click="logout"
            >退出登录</el-menu-item
          >
        </el-menu>
      </div>
    </div>
  </el-header>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useUserStore } from "../store/index.js";
import { userApi } from "../api/modules/user.js";

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();
const activeIndex = ref("/");

const isLoggedIn = computed(() => userStore.isLoggedIn);
const user = computed(() => userStore.user);

const handleSelect = (key) => {
  if (key === "/logout") {
    logout();
  } else {
    router.push(key);
  }
};

const logout = async () => {
  try {
    await userApi.logout();
    userStore.logout();
    router.push("/");
  } catch (error) {
    console.error("退出登录失败:", error);
  }
};

const checkLoginStatus = () => {
  // 从localStorage中恢复用户信息
  const user = JSON.parse(localStorage.getItem("user"));
  if (user && user.id) {
    userStore.setUser(user);
  }
};

onMounted(() => {
  activeIndex.value = route.path;
  checkLoginStatus();
});

// 监听路由变化，更新当前激活的菜单项
watch(
  () => route.path,
  (newPath) => {
    activeIndex.value = newPath;
  },
);
</script>

<style scoped>
.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  padding: 0 20px;
  background-color: #409eff;
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

.fixed-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>
