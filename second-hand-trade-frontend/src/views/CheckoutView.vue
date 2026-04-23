<template>
  <div class="checkout-container">
    <el-card class="checkout-card">
      <template #header>
        <div class="card-header">
          <h2>结算</h2>
        </div>
      </template>
      
      <div v-if="cartItems.length === 0" class="empty-cart">
        <el-empty description="购物车为空" />
        <el-button type="primary" @click="goToCart">去购物车</el-button>
      </div>
      
      <div v-else class="checkout-content">
        <!-- 收货地址 -->
        <div class="section">
          <h3>收货地址</h3>
          <div class="address-list">
            <div 
              v-for="(address, index) in addresses" 
              :key="index"
              class="address-item"
              :class="{ active: selectedAddress === index }"
              @click="selectedAddress = index"
            >
              <div class="address-info">
                <div class="address-header">
                  <span class="name">{{ address.name }}</span>
                  <span class="phone">{{ address.phone }}</span>
                  <el-radio v-model="selectedAddress" :label="index" size="large" />
                </div>
                <div class="address-detail">{{ address.detail }}</div>
              </div>
            </div>
            <div class="address-item add-address" @click="showAddressForm = true">
              <el-icon size="24"><Plus /></el-icon>
              <span>添加新地址</span>
            </div>
          </div>
        </div>
        
        <!-- 商品信息 -->
        <div class="section">
          <h3>商品信息</h3>
          <el-table :data="cartItems" style="width: 100%">
            <el-table-column prop="name" label="商品名称" width="300">
              <template #default="{ row }">
                <div class="goods-info">
                  <img 
                    :src="row.images && row.images.length > 0 ? row.images[0].imageData : ''" 
                    alt="商品图片"
                    class="goods-image"
                  />
                  <span>{{ row.name }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="price" label="价格" width="120">
              <template #default="{ row }">
                ¥{{ row.price }}
              </template>
            </el-table-column>
            <el-table-column prop="quantity" label="数量" width="120">
              <template #default="{ row }">
                {{ row.quantity }}
              </template>
            </el-table-column>
            <el-table-column prop="totalPrice" label="小计" width="120">
              <template #default="{ row }">
                ¥{{ (row.price * row.quantity).toFixed(2) }}
              </template>
            </el-table-column>
          </el-table>
        </div>
        
        <!-- 支付方式 -->
        <div class="section">
          <h3>支付方式</h3>
          <div class="payment-methods">
            <div 
              v-for="(method, index) in paymentMethods" 
              :key="index"
              class="payment-item"
              :class="{ active: selectedPayment === index }"
              @click="selectedPayment = index"
            >
              <el-radio v-model="selectedPayment" :label="index" />
              <span>{{ method.name }}</span>
            </div>
          </div>
        </div>
        
        <!-- 订单备注 -->
        <div class="section">
          <h3>订单备注</h3>
          <el-input
            type="textarea"
            v-model="orderRemark"
            placeholder="请输入订单备注"
            :rows="2"
          />
        </div>
        
        <!-- 订单汇总 -->
        <div class="section order-summary">
          <div class="summary-item">
            <span>商品总价</span>
            <span>¥{{ totalPrice.toFixed(2) }}</span>
          </div>
          <div class="summary-item">
            <span>运费</span>
            <span>¥{{ shippingFee.toFixed(2) }}</span>
          </div>
          <div class="summary-item total">
            <span>实付金额</span>
            <span class="price">¥{{ (totalPrice + shippingFee).toFixed(2) }}</span>
          </div>
        </div>
        
        <!-- 提交订单 -->
        <div class="checkout-actions">
          <el-button type="primary" size="large" @click="submitOrder">
            提交订单
          </el-button>
        </div>
      </div>
    </el-card>
    
    <!-- 添加地址弹窗 -->
    <el-dialog
      v-model="showAddressForm"
      title="添加收货地址"
      width="500px"
    >
      <el-form :model="newAddress" label-width="80px">
        <el-form-item label="收货人">
          <el-input v-model="newAddress.name" placeholder="请输入收货人姓名" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="newAddress.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="详细地址">
          <el-input
            type="textarea"
            v-model="newAddress.detail"
            placeholder="请输入详细地址"
            :rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAddressForm = false">取消</el-button>
          <el-button type="primary" @click="addAddress">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { Plus } from "@element-plus/icons-vue";
import { useCartStore, useUserStore } from "../store";
import { orderApi } from "../api/modules/order";

const router = useRouter();
const cartStore = useCartStore();
const userStore = useUserStore();

const cartItems = computed(() => cartStore.cartItems);
const totalPrice = computed(() => {
  return cartItems.value.reduce((total, item) => {
    return total + (item.price * item.quantity);
  }, 0);
});
const shippingFee = ref(0); // 模拟运费

// 收货地址
const addresses = ref([]);
const selectedAddress = ref(-1);
const showAddressForm = ref(false);
const newAddress = ref({
  name: '',
  phone: '',
  detail: ''
});

// 支付方式
const paymentMethods = ref([
  { name: "微信支付" },
  { name: "支付宝" },
  { name: "银行卡支付" }
]);
const selectedPayment = ref(0);

// 订单备注
const orderRemark = ref('');

const goToCart = () => {
  router.push("/cart");
};

const addAddress = () => {
  if (!newAddress.value.name || !newAddress.value.phone || !newAddress.value.detail) {
    ElMessage.error("请填写完整的地址信息");
    return;
  }
  
  addresses.value.push({ ...newAddress.value });
  selectedAddress.value = addresses.value.length - 1;
  showAddressForm.value = false;
  newAddress.value = {
    name: '',
    phone: '',
    detail: ''
  };
  ElMessage.success("地址添加成功");
};

const submitOrder = async () => {
  if (!userStore.isLoggedIn) {
    ElMessage.error("请先登录");
    router.push("/login");
    return;
  }
  
  if (cartItems.value.length === 0) {
    ElMessage.error("购物车为空");
    return;
  }
  
  // 创建订单
  try {
    // 显示加载提示
    const loading = ElMessage({
      message: '正在处理订单...',
      type: 'info',
      duration: 0,
      showClose: false
    });
    
    // 为购物车中的每个商品创建一个订单
    const orderPromises = cartItems.value.map(item => {
      const orderData = {
        goodsId: item.id,
        sellerId: item.userId,
        quantity: item.quantity,
        totalPrice: item.price * item.quantity,
        status: 'pending'
      };
      return orderApi.create(orderData);
    });
    
    // 等待所有订单创建完成
    await Promise.all(orderPromises);
    
    // 关闭加载提示
    loading.close();
    ElMessage.success("订单提交成功！");
    
    // 清空购物车
    cartStore.clearCart();
    
    // 跳转到订单列表
    router.push("/order/list");
    
  } catch (error) {
    console.error("提交订单失败:", error);
    ElMessage.error("提交订单失败，请稍后重试");
  }
};

onMounted(() => {
  // 加载购物车数据
  cartStore.loadCart();
  
  // 如果购物车为空，跳转到购物车页面
  if (cartItems.value.length === 0) {
    router.push("/cart");
  }
});
</script>

<style scoped lang="scss">
.checkout-container {
  padding: 80px 20px 20px;
  max-width: 1200px;
  margin: 0 auto;
  
  .checkout-card {
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      
      h2 {
        font-size: 20px;
        margin: 0;
      }
    }
    
    .empty-cart {
      padding: 60px 0;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 20px;
    }
    
    .checkout-content {
      .section {
        margin-bottom: 30px;
        
        h3 {
          font-size: 16px;
          margin-bottom: 15px;
          color: #333;
        }
      }
      
      .address-list {
        display: flex;
        flex-wrap: wrap;
        gap: 15px;
        
        .address-item {
          width: 300px;
          border: 1px solid #e4e7ed;
          border-radius: 4px;
          padding: 15px;
          cursor: pointer;
          transition: all 0.3s;
          
          &:hover {
            border-color: #409eff;
          }
          
          &.active {
            border-color: #409eff;
            background-color: #ecf5ff;
          }
          
          &.add-address {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 120px;
            border: 1px dashed #d9d9d9;
            
            &:hover {
              border-color: #409eff;
              color: #409eff;
            }
          }
          
          .address-info {
            .address-header {
              display: flex;
              align-items: center;
              margin-bottom: 10px;
              
              .name {
                font-weight: bold;
                margin-right: 15px;
              }
              
              .phone {
                margin-right: auto;
              }
            }
            
            .address-detail {
              color: #666;
              font-size: 14px;
            }
          }
        }
      }
      
      .goods-info {
        display: flex;
        align-items: center;
        gap: 10px;
        
        .goods-image {
          width: 60px;
          height: 60px;
          object-fit: cover;
          border-radius: 4px;
        }
      }
      
      .payment-methods {
        display: flex;
        gap: 20px;
        
        .payment-item {
          display: flex;
          align-items: center;
          gap: 8px;
          padding: 10px 15px;
          border: 1px solid #e4e7ed;
          border-radius: 4px;
          cursor: pointer;
          transition: all 0.3s;
          
          &:hover {
            border-color: #409eff;
          }
          
          &.active {
            border-color: #409eff;
            background-color: #ecf5ff;
          }
        }
      }
      
      .order-summary {
        background-color: #f5f7fa;
        padding: 20px;
        border-radius: 4px;
        
        .summary-item {
          display: flex;
          justify-content: space-between;
          margin-bottom: 10px;
          
          &.total {
            font-weight: bold;
            font-size: 16px;
            margin-top: 10px;
            padding-top: 10px;
            border-top: 1px solid #e4e7ed;
            
            .price {
              color: #f56c6c;
              font-size: 18px;
            }
          }
        }
      }
      
      .checkout-actions {
        display: flex;
        justify-content: flex-end;
        margin-top: 30px;
        
        button {
          width: 200px;
        }
      }
    }
  }
}
</style>
