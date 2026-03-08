<template>
  <div class="cart-container">
    <el-card class="cart-card">
      <template #header>
        <div class="card-header">
          <h2>购物车</h2>
        </div>
      </template>
      
      <div v-if="cartItems.length === 0" class="empty-cart">
        <el-empty description="购物车为空" />
        <el-button type="primary" @click="goToGoodsList">去购物</el-button>
      </div>
      
      <div v-else class="cart-items">
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
          <el-table-column prop="quantity" label="数量" width="180">
            <template #default="{ row }">
              <el-input-number 
                v-model="row.quantity" 
                :min="1" 
                @change="updateQuantity(row)"
              />
            </template>
          </el-table-column>
          <el-table-column prop="totalPrice" label="小计" width="120">
            <template #default="{ row }">
              ¥{{ (row.price * row.quantity).toFixed(2) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="100">
            <template #default="{ row }">
              <el-button type="danger" size="small" @click="removeFromCart(row.id)">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        
        <div class="cart-footer">
          <div class="total-price">
            <span>合计：</span>
            <span class="price">¥{{ totalPrice.toFixed(2) }}</span>
          </div>
          <div class="cart-actions">
            <el-button @click="clearCart">清空购物车</el-button>
            <el-button type="primary" @click="checkout">去结算</el-button>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
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

const goToGoodsList = () => {
  router.push("/goods/list");
};

const removeFromCart = (goodsId: number) => {
  cartStore.removeFromCart(goodsId);
  ElMessage.success("已从购物车移除");
};

const updateQuantity = (item: any) => {
  // 保存到localStorage
  localStorage.setItem('cart', JSON.stringify(cartStore.cartItems));
};

const clearCart = () => {
  cartStore.clearCart();
  ElMessage.success("购物车已清空");
};

const checkout = async () => {
  if (!userStore.isLoggedIn) {
    ElMessage.error("请先登录");
    router.push("/login");
    return;
  }
  
  if (cartItems.value.length === 0) {
    ElMessage.error("购物车为空");
    return;
  }
  
  // 跳转到结算页面
  router.push("/checkout");
};

onMounted(() => {
  // 加载购物车数据
  cartStore.loadCart();
});
</script>

<style scoped lang="scss">
.cart-container {
  padding: 80px 20px 20px;
  max-width: 1200px;
  margin: 0 auto;
  
  .cart-card {
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
    
    .cart-items {
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
      
      .cart-footer {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 20px;
        padding-top: 20px;
        border-top: 1px solid #eee;
        
        .total-price {
          font-size: 18px;
          
          .price {
            font-weight: bold;
            color: #f56c6c;
            font-size: 20px;
          }
        }
        
        .cart-actions {
          display: flex;
          gap: 10px;
        }
      }
    }
  }
}
</style>
