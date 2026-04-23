<template>
  <div class="order-list">
    <el-card class="order-card">
      <template #header>
        <div class="order-header">
          <h2>订单管理</h2>
        </div>
      </template>
      <div class="order-content">
        <el-tabs v-model="activeTab">
          <el-tab-pane label="我购买的" name="buyer">
            <div class="orders">
              <el-card
                v-for="order in buyerOrders"
                :key="order.id"
                class="order-item"
              >
                <div class="order-info">
                  <img
                    :src="order.goodsImage || ''"
                    alt=""
                    class="order-goods-image"
                  />
                  <div class="order-detail">
                    <h4>订单号: {{ order.orderNumber }}</h4>
                    <p>商品: {{ order.goodsName }}</p>
                    <p>价格: ¥{{ order.totalPrice }}</p>
                    <p v-if="order.address">收货地址: {{ order.address }}</p>
                    <p>
                      状态:
                      <el-tag :type="getStatusTagType(order.status)">{{
                        getOrderStatusText(order.status)
                      }}</el-tag>
                    </p>
                    <p>下单时间: {{ order.createdAt }}</p>
                    <div class="order-actions">
                      <el-button
                        v-if="order.status === 'shipped'"
                        type="primary"
                        @click="confirmReceive(order.id)"
                        >确认收货</el-button
                      >
                      <el-button
                        v-if="order.status === 'received' && !order.buyerEvaluated"
                        type="success"
                        @click="openEvaluateDialog(order)"
                        >评价</el-button
                      >
                      <el-button
                        v-if="
                          order.status === 'received' && order.buyerEvaluated
                        "
                        type="info"
                        disabled
                        >已评价</el-button
                      >
                    </div>
                  </div>
                </div>
              </el-card>
            </div>
          </el-tab-pane>
          <el-tab-pane label="我卖出的" name="seller">
            <div class="orders">
              <el-card
                v-for="order in sellerOrders"
                :key="order.id"
                class="order-item"
              >
                <div class="order-info">
                  <img
                    :src="order.goodsImage || ''"
                    alt=""
                    class="order-goods-image"
                  />
                  <div class="order-detail">
                    <h4>订单号: {{ order.orderNumber }}</h4>
                    <p>商品: {{ order.goodsName }}</p>
                    <p>价格: ¥{{ order.totalPrice }}</p>
                    <p v-if="order.address">收货地址: {{ order.address }}</p>
                    <p>
                      状态:
                      <el-tag :type="getStatusTagType(order.status)">{{
                        getOrderStatusText(order.status)
                      }}</el-tag>
                    </p>
                    <p>下单时间: {{ order.createdAt }}</p>
                    <div class="order-actions">
                      <el-button
                        v-if="order.status === 'pending'"
                        type="primary"
                        @click="shipOrder(order.id)"
                        >发货</el-button
                      >
                      <el-button
                        v-if="
                          order.status === 'shipped' ||
                          order.status === 'received'
                        "
                        type="success"
                        disabled
                        >{{
                          order.status === "shipped" ? "运输中" : "已完成"
                        }}</el-button
                      >
                    </div>
                  </div>
                </div>
              </el-card>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-card>

    <el-dialog v-model="evaluateDialogVisible" title="评价订单" width="500px">
      <el-form :model="evaluateForm" label-width="80px">
        <el-form-item label="评分">
          <el-rate v-model="evaluateForm.rating" :max="5" />
        </el-form-item>
        <el-form-item label="评价内容">
          <el-input
            v-model="evaluateForm.comment"
            type="textarea"
            rows="4"
            placeholder="请输入评价内容"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="evaluateDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEvaluate">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { orderApi } from "../api/modules/order";
import { evaluationApi } from "../api/modules/evaluation";
import { useUserStore } from "../store";

const router = useRouter();

const userStore = useUserStore();
const activeTab = ref("buyer");
const buyerOrders = ref([]);
const sellerOrders = ref([]);
const evaluateDialogVisible = ref(false);
const evaluateForm = ref({
  orderId: 0,
  evaluatedId: 0,
  rating: 5,
  comment: "",
});

const getOrderStatusText = (status: string) => {
  const statusMap: Record<string, string> = {
    pending: "待发货",
    shipped: "运输中",
    received: "已收货",
  };
  return statusMap[status] || status;
};

const getStatusTagType = (status: string) => {
  const typeMap: Record<string, string> = {
    pending: "warning",
    shipped: "primary",
    received: "success",
  };
  return typeMap[status] || "info";
};

const shipOrder = async (orderId: number) => {
  try {
    await orderApi.updateStatus(orderId, "shipped");
    ElMessage.success("发货成功");
    fetchSellerOrders();
  } catch (error) {
    console.error("发货失败:", error);
    ElMessage.error("发货失败，请稍后重试");
  }
};

const confirmReceive = async (orderId: number) => {
  try {
    await orderApi.updateStatus(orderId, "received");
    ElMessage.success("确认收货成功");
    fetchBuyerOrders();
  } catch (error) {
    console.error("确认收货失败:", error);
    ElMessage.error("确认收货失败，请稍后重试");
  }
};

const openEvaluateDialog = async (order: any) => {
  evaluateForm.value = {
    orderId: order.id,
    evaluatedId: order.sellerId,
    rating: 5,
    comment: "",
  };
  evaluateDialogVisible.value = true;
};

const submitEvaluate = async () => {
  try {
    await evaluationApi.add({
      orderId: evaluateForm.value.orderId,
      evaluatedId: evaluateForm.value.evaluatedId,
      rating: evaluateForm.value.rating,
      comment: evaluateForm.value.comment,
    });
    ElMessage.success("评价成功");
    evaluateDialogVisible.value = false;
    fetchBuyerOrders();
  } catch (error: any) {
    console.error("评价失败:", error);
    ElMessage.error(error.response?.data?.message || "评价失败，请稍后重试");
  }
};

const fetchBuyerOrders = async () => {
  try {
    const res = await orderApi.listByBuyer();
    buyerOrders.value = res;
  } catch (error) {
    console.error("获取购买的订单失败:", error);
  }
};

const fetchSellerOrders = async () => {
  try {
    const res = await orderApi.listBySeller();
    sellerOrders.value = res;
  } catch (error) {
    console.error("获取卖出的订单失败:", error);
  }
};

onMounted(async () => {
  if (!userStore.isLoggedIn) {
    ElMessage.error("请先登录");
    router.push("/login");
    return;
  }

  await fetchBuyerOrders();
  await fetchSellerOrders();
});
</script>

<style scoped lang="scss">
.order-list {
  padding: 20px;

  .order-card {
    .order-header {
      text-align: center;

      h2 {
        font-size: 24px;
        color: #333;
      }
    }

    .order-content {
      .orders {
        .order-item {
          margin-bottom: 15px;

          .order-info {
            display: flex;
            align-items: flex-start;
            gap: 15px;

            .order-goods-image {
              width: 80px;
              height: 80px;
              object-fit: cover;
              border-radius: 6px;
              flex-shrink: 0;
            }

            .order-detail {
              flex: 1;

              p {
                margin-bottom: 5px;
                color: #666;
              }

              .order-actions {
                margin-top: 15px;

                .el-button {
                  margin-right: 10px;
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
