<template>
  <div class="login">
    <el-card class="login-card">
      <template #header>
        <div class="login-header">
          <h2>用户登录</h2>
        </div>
      </template>
      <div class="login-body">
        <el-form
          :model="loginForm"
          :rules="loginRules"
          ref="loginFormRef"
          label-width="80px"
        >
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model="loginForm.username"
              placeholder="请输入用户名"
            ></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="login" style="width: 100%"
              >登录</el-button
            >
          </el-form-item>
          <el-form-item>
            <span>还没有账号？</span>
            <el-button type="text" @click="goToRegister">立即注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { userApi } from "../api/modules/user";
import { useUserStore } from "../store";

const router = useRouter();
const userStore = useUserStore();
const loginFormRef = ref();
const loginForm = ref({
  username: "",
  password: "",
});
const loginRules = ref({
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
});

const login = async () => {
  if (loginFormRef.value) {
    await loginFormRef.value.validate(async (valid: boolean) => {
      if (valid) {
        try {
          const res = await userApi.login(
            loginForm.value.username,
            loginForm.value.password,
          );
          if (res) {
            userStore.setUser(res);
            // 存储用户信息到localStorage
            localStorage.setItem("user", JSON.stringify(res));
            ElMessage.success("登录成功");
            router.push("/");
          } else {
            ElMessage.error("用户名或密码错误");
          }
        } catch (error) {
          console.error("登录失败:", error);
          ElMessage.error("登录失败，请稍后重试");
        }
      }
    });
  }
};

const goToRegister = () => {
  router.push("/register");
};
</script>

<style scoped lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh;
  padding: 20px;

  .login-card {
    width: 400px;
    max-width: 100%;

    .login-header {
      text-align: center;

      h2 {
        font-size: 24px;
        color: #333;
      }
    }

    .login-body {
      padding: 20px 0;
    }
  }
}
</style>
