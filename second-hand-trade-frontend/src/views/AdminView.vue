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
                  <div class="user-actions">
                    <el-button type="primary" @click="editUser(user.id)"
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
                  </div>
                </div>
              </el-card>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-card>
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

const editUser = (userId: number) => {
  // 编辑用户逻辑
  ElMessage.info("编辑用户功能待实现");
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
            p {
              margin-bottom: 5px;
              color: #666;
            }

            .user-actions {
              margin-top: 15px;

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
