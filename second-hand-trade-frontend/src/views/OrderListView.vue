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
              <el-card v-for="order in buyerOrders" :key="order.id" class="order-item">
                <div class="order-info">
                  <h4>订单号: {{ order.orderNumber }}</h4>
                  <p>商品: {{ order.goodsName }}</p>
                  <p>价格: ¥{{ order.totalPrice }}</p>
                  <p>状态: {{ getOrderStatusText(order.status) }}</p>
                  <p>下单时间: {{ order.createdAt }}</p>
                  <div class="order-actions">
                    <el-button v-if="order.status === 'pending'" type="primary" @click="payOrder(order.id)">支付</el-button>
                    <el-button v-else-if="order.status === 'shipped'" type="primary" @click="confirmReceive(order.id)">确认收货</el-button>
                    <el-button v-else-if="order.status === 'received'" type="primary" @click="evaluateOrder(order.id)">评价</el-button>
                  </div>
                </div>
              </el-card>
            </div>
          </el-tab-pane>
          <el-tab-pane label="我卖出的" name="seller">
            <div class="orders">
              <el-card v-for="order in sellerOrders" :key="order.id" class="order-item">
                <div class="order-info">
                  <h4>订单号: {{ order.orderNumber }}</h4>
                  <p>商品: {{ order.goodsName }}</p>
                  <p>价格: ¥{{ order.totalPrice }}</p>
                  <p>状态: {{ getOrderStatusText(order.status) }}</p>
                  <p>下单时间: {{ order.createdAt }}</p>
                  <div class="order-actions">
                    <el-button v-if="order.status === 'payed'" type="primary" @click="shipOrder(order.id)">发货</el-button>
                    <el-button v-if="order.status === 'received'" type="primary" @click="evaluateOrder(order.id)">评价</el-button>
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
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { orderApi } from '../api/modules/order'
import { useUserStore } from '../store'

const router = useRouter()

const userStore = useUserStore()
const activeTab = ref('buyer')
const buyerOrders = ref([])
const sellerOrders = ref([])

const getOrderStatusText = (status: string) => {
  const statusMap = {
    'pending': '待支付',
    'payed': '已支付',
    'shipped': '已发货',
    'received': '已收货',
    'completed': '已完成',
    'cancelled': '已取消'
  }
  return statusMap[status] || status
}

const payOrder = async (orderId: number) => {
  try {
    await orderApi.updateStatus(orderId, 'payed')
    ElMessage.success('支付成功')
    fetchBuyerOrders()
  } catch (error) {
    console.error('支付失败:', error)
    ElMessage.error('支付失败，请稍后重试')
  }
}

const shipOrder = async (orderId: number) => {
  try {
    await orderApi.updateStatus(orderId, 'shipped')
    ElMessage.success('发货成功')
    fetchSellerOrders()
  } catch (error) {
    console.error('发货失败:', error)
    ElMessage.error('发货失败，请稍后重试')
  }
}

const confirmReceive = async (orderId: number) => {
  try {
    await orderApi.updateStatus(orderId, 'received')
    ElMessage.success('确认收货成功')
    fetchBuyerOrders()
  } catch (error) {
    console.error('确认收货失败:', error)
    ElMessage.error('确认收货失败，请稍后重试')
  }
}

const evaluateOrder = (orderId: number) => {
  // 评价订单逻辑
}

const fetchBuyerOrders = async () => {
  try {
    const res = await orderApi.listByBuyer()
    buyerOrders.value = res
  } catch (error) {
    console.error('获取购买的订单失败:', error)
  }
}

const fetchSellerOrders = async () => {
  try {
    const res = await orderApi.listBySeller()
    sellerOrders.value = res
  } catch (error) {
    console.error('获取卖出的订单失败:', error)
  }
}

onMounted(async () => {
  if (!userStore.isLoggedIn) {
    ElMessage.error('请先登录')
    router.push('/login')
    return
  }

  await fetchBuyerOrders()
  await fetchSellerOrders()
})
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
</style>
