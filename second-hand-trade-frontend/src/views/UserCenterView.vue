<template>
  <div class="user-center">
    <el-card class="user-card">
      <template #header>
        <div class="user-header">
          <h2>个人中心</h2>
        </div>
      </template>
      <div class="user-content">
        <div class="user-info">
          <div class="user-avatar">
            <img
              :src="user?.avatar || 'https://picsum.photos/200/200?random=1'"
              alt=""
              class="avatar"
            />
          </div>
          <div class="user-details">
            <p>
              {{ 
                user?.role === "student" 
                  ? "学生"
                  : user?.role === "teacher"
                  ? "教师"
                  : "管理员"
              }}
            </p>
            <p>{{ user?.idNumber }}</p>
            <p>{{ user?.email }}</p>
            <p>{{ user?.phone }}</p>
            <el-button type="primary" @click="editInfo">编辑信息</el-button>
          </div>
        </div>

        <div class="user-tabs">
          <el-tabs v-model="activeTab">
            <el-tab-pane label="发布的商品" name="published">
              <div class="goods-list">
                <el-card
                  v-for="goods in publishedGoods"
                  :key="goods.id"
                  class="goods-card"
                >
                  <img
                    :src="goods.images[0]?.imagePath || ''"
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
                    <el-button
                      type="primary"
                      size="small"
                      @click="editGoods(goods.id)"
                      >编辑</el-button
                    >
                    <el-button
                      type="danger"
                      size="small"
                      @click="deleteGoods(goods.id)"
                      >删除</el-button
                    >
                  </div>
                </el-card>
              </div>
            </el-tab-pane>
            <el-tab-pane label="购买的商品" name="purchased">
              <div class="order-list">
                <el-card
                  v-for="order in purchasedOrders"
                  :key="order.id"
                  class="order-card"
                >
                  <div class="order-info">
                    <h4>订单号: {{ order.orderNumber }}</h4>
                    <p>商品: {{ order.goodsName }}</p>
                    <p>价格: ¥{{ order.totalPrice }}</p>
                    <p>状态: {{ getOrderStatusText(order.status) }}</p>
                    <p>下单时间: {{ order.createdAt }}</p>
                  </div>
                </el-card>
              </div>
            </el-tab-pane>
            <el-tab-pane label="评价记录" name="evaluations">
              <div class="evaluation-list">
                <el-card
                  v-for="evaluation in evaluations"
                  :key="evaluation.id"
                  class="evaluation-card"
                >
                  <div class="evaluation-info">
                    <p>评价对象: {{ evaluation.evaluatedName }}</p>
                    <p>评分: {{ evaluation.rating }}星</p>
                    <p>评价内容: {{ evaluation.comment }}</p>
                    <p>评价时间: {{ evaluation.createdAt }}</p>
                  </div>
                </el-card>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { userApi } from "../api/modules/user";
import { goodsApi } from "../api/modules/goods";
import { orderApi } from "../api/modules/order";
import { useUserStore } from "../store";

const router = useRouter();
const userStore = useUserStore();
const user = ref(null);
const activeTab = ref("published");
const publishedGoods = ref([]);
const purchasedOrders = ref([]);
const evaluations = ref([]);

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

const editInfo = () => {
  // 编辑个人信息逻辑
};

const editGoods = (goodsId: number) => {
  router.push(`/goods/edit/${goodsId}`);
};

const deleteGoods = async (goodsId: number) => {
  try {
    await goodsApi.delete(goodsId);
    ElMessage.success("商品删除成功");
    fetchPublishedGoods();
  } catch (error) {
    console.error("删除商品失败:", error);
    ElMessage.error("删除商品失败，请稍后重试");
  }
};

const fetchUserInfo = async () => {
  try {
    const res = await userApi.getInfo();
    user.value = res;
  } catch (error) {
    console.error("获取用户信息失败:", error);
  }
};

const fetchPublishedGoods = async () => {
  if (user.value) {
    try {
      const res = await goodsApi.listByUser(user.value.id);
      publishedGoods.value = res;
    } catch (error) {
      console.error("获取发布的商品失败:", error);
    }
  }
};

const fetchPurchasedOrders = async () => {
  try {
    const res = await orderApi.listByBuyer();
    purchasedOrders.value = res;
  } catch (error) {
    console.error("获取购买的商品失败:", error);
  }
};

const fetchEvaluations = async () => {
  // 这里需要实现获取评价记录的API
};

onMounted(async () => {
  if (!userStore.isLoggedIn) {
    ElMessage.error("请先登录");
    router.push("/login");
    return;
  }

  await fetchUserInfo();
  await fetchPublishedGoods();
  await fetchPurchasedOrders();
  await fetchEvaluations();
});
</script>

<style scoped lang="scss">
.user-center {
  padding: 20px;

  .user-card {
    .user-header {
      text-align: center;

      h2 {
        font-size: 24px;
        color: #333;
      }
    }

    .user-content {
      .user-info {
        display: flex;
        align-items: center;
        margin-bottom: 30px;
        padding-bottom: 20px;
        border-bottom: 1px solid #eee;

        .user-avatar {
          margin-right: 30px;

          .avatar {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            object-fit: cover;
          }
        }

        .user-details {
          h3 {
            font-size: 20px;
            margin-bottom: 10px;
            color: #333;
          }

          p {
            margin-bottom: 5px;
            color: #666;
          }

          button {
            margin-top: 15px;
          }
        }
      }

      .user-tabs {
        .goods-list {
          display: grid;
          grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
          gap: 20px;

          .goods-card {
            height: 250px;
            display: flex;
            flex-direction: column;

            .goods-image {
              width: 100%;
              height: 150px;
              object-fit: cover;
              margin-bottom: 10px;
            }

            .goods-info {
              flex: 1;
              display: flex;
              flex-direction: column;
              justify-content: space-between;

              h4 {
                font-size: 14px;
                margin-bottom: 5px;
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
              }

              .goods-price {
                font-size: 16px;
                color: #f56c6c;
                font-weight: bold;
                margin-bottom: 5px;
              }

              .goods-status {
                font-size: 12px;
                color: #999;
                margin-bottom: 10px;
              }

              .el-button {
                margin-right: 5px;
              }
            }
          }
        }

        .order-list {
          .order-card {
            margin-bottom: 15px;

            .order-info {
              p {
                margin-bottom: 5px;
                color: #666;
              }
            }
          }
        }

        .evaluation-list {
          .evaluation-card {
            margin-bottom: 15px;

            .evaluation-info {
              p {
                margin-bottom: 5px;
                color: #666;
              }
            }
          }
        }
      }
    }
  }
}
</style>
