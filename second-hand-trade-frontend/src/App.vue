<template>
  <div id="app">
    <el-container>
      <NavBar />
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
import { defineComponent } from "vue";
import NavBar from "./components/NavBar.vue";

export default defineComponent({
  name: "App",
  components: {
    NavBar,
  },
});
</script>

<style scoped>
#app {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.el-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding-top: 60px;
}

.el-main {
  flex: 1;
  padding-top: 20px;
  overflow-y: auto;
}

.el-footer {
  height: 60px !important;
  flex-shrink: 0;
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
