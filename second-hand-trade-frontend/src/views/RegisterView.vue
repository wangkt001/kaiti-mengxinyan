<template>
  <div class="register">
    <el-card class="register-card">
      <template #header>
        <div class="register-header">
          <h2>用户注册</h2>
        </div>
      </template>
      <div class="register-body">
        <el-form
          :model="registerForm"
          :rules="registerRules"
          ref="registerFormRef"
          label-width="100px"
        >
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model="registerForm.username"
              placeholder="请输入用户名（只能使用英文字母）"
            ></el-input>
          </el-form-item>
          <el-form-item label="真实姓名" prop="realName">
            <el-input
              v-model="registerForm.realName"
              placeholder="请输入真实姓名"
            ></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="registerForm.password"
              type="password"
              placeholder="请输入密码"
            ></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input
              v-model="registerForm.confirmPassword"
              type="password"
              placeholder="请确认密码"
            ></el-input>
          </el-form-item>
          <el-form-item label="学号/工号" prop="idNumber">
            <el-input
              v-model="registerForm.idNumber"
              placeholder="请输入学号/工号"
            ></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input
              v-model="registerForm.email"
              placeholder="请输入邮箱"
            ></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input
              v-model="registerForm.phone"
              placeholder="请输入手机号"
            ></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="role">
            <el-select v-model="registerForm.role" placeholder="请选择角色">
              <el-option label="学生" value="student"></el-option>
              <el-option label="教师" value="teacher"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="register" style="width: 100%"
              >注册</el-button
            >
          </el-form-item>
          <el-form-item>
            <span>已有账号？</span>
            <el-button type="text" @click="goToLogin">立即登录</el-button>
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

const router = useRouter();
const registerFormRef = ref();
const registerForm = ref({
  username: "",
  realName: "",
  password: "",
  confirmPassword: "",
  idNumber: "",
  email: "",
  phone: "",
  role: "student",
});
const registerRules = ref({
  username: [
    { required: true, message: "请输入用户名", trigger: "blur" },
    {
      pattern: /^[a-zA-Z]+$/,
      message: "用户名只能使用英文字母",
      trigger: "blur",
    },
  ],
  realName: [{ required: true, message: "请输入真实姓名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
  confirmPassword: [
    { required: true, message: "请确认密码", trigger: "blur" },
    {
      validator: (rule: any, value: string, callback: any) => {
        if (value !== registerForm.value.password) {
          callback(new Error("两次输入的密码不一致"));
        } else {
          callback();
        }
      },
      trigger: "blur",
    },
  ],

  idNumber: [{ required: true, message: "请输入学号/工号", trigger: "blur" }],
  email: [
    { required: true, message: "请输入邮箱", trigger: "blur" },
    { type: "email", message: "请输入正确的邮箱格式", trigger: "blur" },
  ],
  phone: [{ required: true, message: "请输入手机号", trigger: "blur" }],
  role: [{ required: true, message: "请选择角色", trigger: "blur" }],
});

const register = async () => {
  if (registerFormRef.value) {
    await registerFormRef.value.validate(async (valid: boolean) => {
      if (valid) {
        try {
          const res = await userApi.register(registerForm.value);
          if (res) {
            ElMessage.success("注册成功");
            router.push("/login");
          } else {
            ElMessage.error("注册失败，请稍后重试");
          }
        } catch (error) {
          console.error("注册失败:", error);
          ElMessage.error(
            error.response?.data?.message ||
              error.response?.data?.detail ||
              error.response?.data?.error ||
              "注册失败，请稍后重试",
          );
        }
      }
    });
  }
};

const goToLogin = () => {
  router.push("/login");
};
</script>

<style scoped lang="scss">
.register {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh;
  padding: 20px;

  .register-card {
    width: 500px;
    max-width: 100%;

    .register-header {
      text-align: center;

      h2 {
        font-size: 24px;
        color: #333;
      }
    }

    .register-body {
      padding: 20px 0;
    }
  }
}
</style>
