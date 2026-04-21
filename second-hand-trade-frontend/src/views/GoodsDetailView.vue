<template>
  <div class="goods-detail">
    <el-card v-if="goods" class="goods-card">
      <div class="goods-content">
        <div class="goods-images">
          <el-carousel :interval="5000" type="card" height="400px">
            <el-carousel-item v-for="image in goods.images" :key="image.id">
              <img
                :src="image.imageData"
                alt=""
                style="width: 100%; height: 100%; object-fit: cover"
              />
            </el-carousel-item>
          </el-carousel>
        </div>
        <div class="goods-info">
          <h2>{{ goods.name }}</h2>
          <p class="goods-price">¥{{ goods.price }}</p>
          <p class="goods-stock">库存: {{ goods.stock }}件</p>
          <p class="goods-category">分类: {{ goods.categoryName }}</p>
          <p class="goods-description">{{ goods.description }}</p>
          <div class="goods-actions">
            <el-button type="primary" size="large" @click="addToCart"
              >加入购物车</el-button
            >
            <el-button type="success" size="large" @click="buyNow"
              >立即购买</el-button
            >
          </div>
        </div>
      </div>
      <div class="seller-info">
        <h3>卖家信息</h3>
        <div class="seller-details">
          <img :src="seller?.avatar || 'https://picsum.photos/80/80?random=1'" alt="" class="seller-avatar" />
          <div class="seller-info-text">
            <p>{{ seller?.role === "student" ? "学生" : "教师" }}</p>
            <p>{{ seller?.phone }}</p>
            <p>{{ seller?.idNumber }}</p>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { goodsApi } from "../api/modules/goods";
import { userApi } from "../api/modules/user";
import { orderApi } from "../api/modules/order";
import { useUserStore, useCartStore } from "../store";

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();
const cartStore = useCartStore();
const goods = ref(null);
const seller = ref(null);

const fetchGoodsDetail = async () => {
  const goodsId = route.params.id;
  try {
    const res = await goodsApi.detail(Number(goodsId));
    console.log("商品详情数据:", res);
    goods.value = res;
    // 获取卖家信息
    if (res.userId) {
      console.log("获取卖家信息，用户ID:", res.userId);
      await fetchSellerInfo(res.userId);
    } else {
      console.log("商品没有关联用户ID");
    }
  } catch (error) {
    console.error("获取商品详情失败:", error);
  }
};

const fetchSellerInfo = async (userId: number) => {
  try {
    console.log("开始获取卖家信息，用户ID:", userId);
    const res = await userApi.getUserInfo(userId);
    console.log("卖家信息:", res);
    seller.value = res;
  } catch (error) {
    console.error("获取卖家信息失败:", error);
  }
};

const addToCart = () => {
  if (!goods.value) return;

  cartStore.addToCart(goods.value);
  ElMessage.success("已加入购物车");
};

const buyNow = async () => {
  if (!userStore.isLoggedIn) {
    ElMessage.error("请先登录");
    router.push("/login");
    return;
  }

  if (goods.value) {
    try {
      const order = {
        goodsId: goods.value.id,
        sellerId: goods.value.userId,
        quantity: 1,
        totalPrice: goods.value.price,
      };
      await orderApi.create(order);
      ElMessage.success("订单创建成功");
      router.push("/order/list");
    } catch (error) {
      console.error("创建订单失败:", error);
      ElMessage.error("创建订单失败，请稍后重试");
    }
  }
};

onMounted(() => {
  fetchGoodsDetail();
});
</script>

<style scoped lang="scss">
.goods-detail {
  padding: 20px;

  .goods-card {
    .goods-content {
      display: flex;
      margin-bottom: 30px;

      .goods-images {
        width: 50%;
        margin-right: 30px;

        .el-carousel {
          border-radius: 8px;
          overflow: hidden;
        }
      }

      .goods-info {
        flex: 1;

        h2 {
          font-size: 24px;
          margin-bottom: 20px;
          color: #333;
        }

        .goods-price {
          font-size: 28px;
          color: #f56c6c;
          font-weight: bold;
          margin-bottom: 15px;
        }

        .goods-stock {
          margin-bottom: 10px;
          color: #666;
        }

        .goods-category {
          margin-bottom: 20px;
          color: #666;
        }

        .goods-description {
          margin-bottom: 30px;
          line-height: 1.6;
          color: #333;
        }

        .goods-actions {
          display: flex;
          gap: 20px;

          button {
            width: 150px;
          }
        }
      }
    }

    .seller-info {
      border-top: 1px solid #eee;
      padding-top: 20px;

      h3 {
        margin-bottom: 15px;
        font-size: 18px;
        color: #333;
      }

      .seller-details {
        display: flex;
        align-items: center;

        .seller-avatar {
          width: 80px;
          height: 80px;
          border-radius: 50%;
          margin-right: 20px;
          object-fit: cover;
        }

        .seller-info-text {
          p {
            margin-bottom: 5px;
            color: #666;
          }
        }
      }
    }
  }
}
</style>
