<template>
  <div class="admin">
    <el-card class="admin-card">
      <template #header>
        <div class="admin-header">
          <h2>管理员后台</h2>
        </div>
      </template>
      <div class="admin-content">
        <el-tabs v-model="activeTab">
          <el-tab-pane label="用户管理" name="users">
            <div class="users-list">
              <el-card v-for="user in users" :key="user.id" class="user-item">
                <div class="user-info">
                  <img
                    :src="user.avatar || 'https://picsum.photos/60/60?random=1'"
                    alt=""
                    class="user-avatar"
                  />
                  <div class="user-detail">
                    <h4>{{ user.username }}</h4>
                    <p>
                      角色:
                      {{
                        user.role === "student"
                          ? "学生"
                          : user.role === "teacher"
                          ? "教师"
                          : "管理员"
                      }}
                    </p>
                    <p>学号/工号: {{ user.idNumber }}</p>
                    <p>邮箱: {{ user.email }}</p>
                    <p>手机号: {{ user.phone }}</p>
                  </div>
                  <div class="user-actions">
                    <el-button type="primary" @click="openEditDialog(user)"
                      >编辑</el-button
                    >
                    <el-button type="danger" @click="deleteUser(user.id)"
                      >删除</el-button
                    >
                  </div>
                </div>
              </el-card>
            </div>
          </el-tab-pane>
          <el-tab-pane label="商品管理" name="goods">
            <div class="goods-list">
              <el-card
                v-for="goods in goodsList"
                :key="goods.id"
                class="goods-item"
              >
                <img
                  :src="
                    goods.images && goods.images.length > 0
                      ? goods.images[0].imageData
                      : ''
                  "
                  alt=""
                  class="goods-image"
                />
                <div class="goods-info">
                  <h4>{{ goods.name }}</h4>
                  <p class="goods-price">¥{{ goods.price }}</p>
                  <p class="goods-status">
                    {{
                      goods.status === "active"
                        ? "在售"
                        : goods.status === "sold"
                        ? "已售出"
                        : "已过期"
                    }}
                  </p>
                  <div class="goods-actions">
                    <el-button type="primary" @click="editGoods(goods.id)"
                      >编辑</el-button
                    >
                    <el-button type="danger" @click="deleteGoods(goods.id)"
                      >删除</el-button
                    >
                  </div>
                </div>
              </el-card>
            </div>
          </el-tab-pane>
          <el-tab-pane label="订单管理" name="orders">
            <div class="orders-list">
              <el-card
                v-for="order in orders"
                :key="order.id"
                class="order-item"
              >
                <div class="order-info">
                  <h4>订单号: {{ order.orderNumber }}</h4>
                  <p>商品: {{ order.goodsName }}</p>
                  <p>买家: {{ order.buyerName }}</p>
                  <p>卖家: {{ order.sellerName }}</p>
                  <p>价格: ¥{{ order.totalPrice }}</p>
                  <p>状态: {{ getOrderStatusText(order.status) }}</p>
                  <p>下单时间: {{ order.createdAt }}</p>
                </div>
              </el-card>
            </div>
          </el-tab-pane>
          <el-tab-pane label="纠纷管理" name="disputes">
            <div class="disputes-list">
              <el-card
                v-for="dispute in disputes"
                :key="dispute.id"
                class="dispute-item"
              >
                <div class="dispute-info">
                  <h4>{{ dispute.title }}</h4>
                  <p>订单号: {{ dispute.orderNumber }}</p>
                  <p>提交用户: {{ dispute.userName }}</p>
                  <p>
                    状态:
                    {{
                      dispute.status === "pending"
                        ? "待处理"
                        : dispute.status === "processing"
                        ? "处理中"
                        : "已解决"
                    }}
                  </p>
                  <p>描述: {{ dispute.description }}</p>
                  <div class="dispute-actions">
                    <el-button
                      v-if="dispute.status === 'pending'"
                      type="primary"
                      @click="handleDispute(dispute.id)"
                      >处理</el-button
                    >
                    <el-button
                      v-if="dispute.status === 'processing'"
                      type="success"
                      @click="resolveDispute(dispute.id)"
                      >标记为已解决</el-button
                    >
                  </div>
                </div>
              </el-card>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-card>

    <!-- 用户编辑弹窗 -->
    <el-dialog v-model="editDialogVisible" title="编辑用户" width="500px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.username" disabled />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="editForm.role">
            <el-option label="学生" value="student" />
            <el-option label="教师" value="teacher" />
            <el-option label="管理员" value="admin" />
          </el-select>
        </el-form-item>
        <el-form-item label="学号/工号">
          <el-input v-model="editForm.idNumber" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editForm.email" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="editForm.phone" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEdit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { ElMessage, ElConfirm } from "element-plus";
import { useUserStore } from "../store";
import api from "../api/index";

const router = useRouter();

const userStore = useUserStore();
const activeTab = ref("users");
const users = ref([]);
const goodsList = ref([]);
const orders = ref([]);
const disputes = ref([]);
const editDialogVisible = ref(false);
const editForm = ref({
  id: null,
  username: "",
  role: "",
  idNumber: "",
  email: "",
  phone: "",
});

const getOrderStatusText = (status: string) => {
  const statusMap = {
    pending: "待支付",
    payed: "已支付",
    shipped: "已发货",
    received: "已收货",
    completed: "已完成",
    cancelled: "已取消",
  };
  return statusMap[status] || status;
};

const openEditDialog = (user) => {
  editForm.value = {
    id: user.id,
    username: user.username,
    role: user.role,
    idNumber: user.idNumber,
    email: user.email,
    phone: user.phone,
  };
  editDialogVisible.value = true;
};

const submitEdit = async () => {
  try {
    await api.put(`/admin/users/${editForm.value.id}`, editForm.value);
    ElMessage.success("修改成功");
    editDialogVisible.value = false;
    await fetchUsers();
  } catch (error) {
    console.error("编辑用户失败:", error);
    ElMessage.error("编辑失败，请稍后重试");
  }
};

const deleteUser = async (userId: number) => {
  ElConfirm({
    title: "确认删除",
    message: "确定要删除这个用户吗？",
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
    async confirm() {
      try {
        await api.delete(`/admin/users/${userId}`);
        ElMessage.success("删除成功");
        await fetchUsers();
      } catch (error) {
        console.error("删除用户失败:", error);
        ElMessage.error("删除失败，请稍后重试");
      }
    },
  });
};

const editGoods = (goodsId: number) => {
  // 编辑商品逻辑
  ElMessage.info("编辑商品功能待实现");
};

const deleteGoods = async (goodsId: number) => {
  ElConfirm({
    title: "确认删除",
    message: "确定要删除这个商品吗？",
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
    async confirm() {
      try {
        await api.delete(`/admin/goods/${goodsId}`);
        ElMessage.success("删除成功");
        await fetchGoods();
      } catch (error) {
        console.error("删除商品失败:", error);
        ElMessage.error("删除失败，请稍后重试");
      }
    },
  });
};

const handleDispute = async (disputeId: number) => {
  try {
    await api.put(`/admin/disputes/${disputeId}/status`, {
      status: "processing",
    });
    ElMessage.success("开始处理纠纷");
    await fetchDisputes();
  } catch (error) {
    console.error("处理纠纷失败:", error);
    ElMessage.error("处理失败，请稍后重试");
  }
};

const resolveDispute = async (disputeId: number) => {
  try {
    await api.put(`/admin/disputes/${disputeId}/status`, {
      status: "resolved",
    });
    ElMessage.success("纠纷已解决");
    await fetchDisputes();
  } catch (error) {
    console.error("解决纠纷失败:", error);
    ElMessage.error("操作失败，请稍后重试");
  }
};

const fetchUsers = async () => {
  try {
    const res = await api.get("/admin/users");
    users.value = res;
  } catch (error) {
    console.error("获取用户列表失败:", error);
    ElMessage.error("获取用户列表失败");
  }
};

const fetchGoods = async () => {
  try {
    const res = await api.get("/admin/goods");
    goodsList.value = res;
  } catch (error) {
    console.error("获取商品列表失败:", error);
    ElMessage.error("获取商品列表失败");
  }
};

const fetchOrders = async () => {
  try {
    const res = await api.get("/admin/orders");
    orders.value = res;
  } catch (error) {
    console.error("获取订单列表失败:", error);
    ElMessage.error("获取订单列表失败");
  }
};

const fetchDisputes = async () => {
  try {
    const res = await api.get("/admin/disputes");
    disputes.value = res;
  } catch (error) {
    console.error("获取纠纷列表失败:", error);
    ElMessage.error("获取纠纷列表失败");
  }
};

onMounted(async () => {
  if (!userStore.isLoggedIn || userStore.user?.role !== "admin") {
    ElMessage.error("权限不足");
    router.push("/");
    return;
  }

  await fetchUsers();
  await fetchGoods();
  await fetchOrders();
  await fetchDisputes();
});
</script>

<style scoped lang="scss">
.admin {
  padding: 20px;

  .admin-card {
    .admin-header {
      text-align: center;

      h2 {
        font-size: 24px;
        color: #333;
      }
    }

    .admin-content {
      .users-list {
        .user-item {
          margin-bottom: 15px;

          .user-info {
            display: flex;
            align-items: center;

            .user-avatar {
              width: 60px;
              height: 60px;
              border-radius: 50%;
              object-fit: cover;
              margin-right: 20px;
              flex-shrink: 0;
            }

            .user-detail {
              flex: 1;

              p {
                margin-bottom: 5px;
                color: #666;
              }
            }

            .user-actions {
              margin-left: 20px;
              flex-shrink: 0;

              .el-button {
                margin-right: 10px;
              }
            }
          }
        }
      }

      .goods-list {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
        gap: 20px;

        .goods-item {
          display: flex;
          padding: 10px;

          .goods-image {
            width: 100px;
            height: 100px;
            object-fit: cover;
            margin-right: 15px;
          }

          .goods-info {
            flex: 1;

            h4 {
              font-size: 16px;
              margin-bottom: 10px;
              overflow: hidden;
              text-overflow: ellipsis;
              display: -webkit-box;
              -webkit-line-clamp: 2;
              -webkit-box-orient: vertical;
            }

            .goods-price {
              font-size: 18px;
              color: #f56c6c;
              font-weight: bold;
              margin-bottom: 5px;
            }

            .goods-status {
              font-size: 14px;
              color: #999;
              margin-bottom: 10px;
            }

            .goods-actions {
              .el-button {
                margin-right: 10px;
              }
            }
          }
        }
      }

      .orders-list {
        .order-item {
          margin-bottom: 15px;

          .order-info {
            p {
              margin-bottom: 5px;
              color: #666;
            }
          }
        }
      }

      .disputes-list {
        .dispute-item {
          margin-bottom: 15px;

          .dispute-info {
            p {
              margin-bottom: 5px;
              color: #666;
            }

            .dispute-actions {
              margin-top: 15px;
            }
          }
        }
      }
    }
  }
}
</style>
