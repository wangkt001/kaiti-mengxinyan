<template>
  <div class="home">
    <div class="banner">
      <el-carousel :interval="5000" type="card" height="400px">
        <el-carousel-item v-for="item in bannerList" :key="item.id">
          <img
            :src="item.image"
            alt=""
            style="width: 100%; height: 100%; object-fit: cover"
          />
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="categories">
      <h2>商品分类</h2>
      <div class="category-list">
        <el-card
          v-for="category in categories"
          :key="category.id"
          class="category-card"
        >
          <div class="category-image">
            <img
              :src="getCategoryImage(category.name)"
              alt="{{ category.name }}"
            />
          </div>
          <template #header>
            <div class="category-header">
              <span>{{ category.name }}</span>
            </div>
          </template>
          <div class="category-body">
            <el-button type="primary" @click="goToCategory(category.id)"
              >查看商品</el-button
            >
          </div>
        </el-card>
      </div>
    </div>

    <div class="hot-goods">
      <h2>热门商品</h2>
      <div class="goods-list">
        <el-card v-for="goods in hotGoods" :key="goods.id" class="goods-card">
          <img
            :src="goods.images[0]?.imagePath || ''"
            alt=""
            class="goods-image"
          />
          <div class="goods-info">
            <h3>{{ goods.name }}</h3>
            <p class="goods-price">¥{{ goods.price }}</p>
            <div class="goods-meta">
              <span>浏览 {{ goods.views }}</span>
              <span>{{ goods.createdAt }}</span>
            </div>
            <el-button type="primary" size="small" @click="goToDetail(goods.id)"
              >查看详情</el-button
            >
          </div>
        </el-card>
      </div>
    </div>

    <div class="new-goods">
      <h2>最新发布</h2>
      <div class="goods-list">
        <el-card v-for="goods in newGoods" :key="goods.id" class="goods-card">
          <img
            :src="goods.images[0]?.imagePath || ''"
            alt=""
            class="goods-image"
          />
          <div class="goods-info">
            <h3>{{ goods.name }}</h3>
            <p class="goods-price">¥{{ goods.price }}</p>
            <div class="goods-meta">
              <span>浏览 {{ goods.views }}</span>
              <span>{{ goods.createdAt }}</span>
            </div>
            <el-button type="primary" size="small" @click="goToDetail(goods.id)"
              >查看详情</el-button
            >
          </div>
        </el-card>
      </div>
    </div>

    <!-- 商品详情弹框 -->
    <el-dialog
      v-model="showDetailDialog"
      :title="currentGoods?.name || '商品详情'"
      width="80%"
      :before-close="closeDetailDialog"
    >
      <div v-if="currentGoods" class="goods-detail">
        <div class="goods-detail-image">
          <img :src="currentGoods.images[0]?.imagePath || ''" alt="" />
        </div>
        <div class="goods-detail-info">
          <h3>{{ currentGoods.name }}</h3>
          <p class="goods-detail-price">¥{{ currentGoods.price }}</p>
          <div class="goods-detail-meta">
            <span>浏览 {{ currentGoods.views }}</span>
            <span>发布时间: {{ currentGoods.createdAt }}</span>
          </div>
          <div class="goods-detail-desc">
            <h4>商品描述</h4>
            <p>
              这是一件优质的二手商品，状态良好，价格实惠。欢迎感兴趣的同学联系购买。
            </p>
          </div>
        </div>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeDetailDialog">关闭</el-button>
          <el-button type="primary" @click="buyNow">立即购买</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { goodsApi } from "../api/modules/goods";
import { categoryApi } from "../api/modules/category";

const router = useRouter();
const bannerList = ref([
  {
    id: 1,
    image: require("../assets/images/lunbo1.png"),
  },
  {
    id: 2,
    image: require("../assets/images/lunbo2.png"),
  },
  {
    id: 3,
    image: require("../assets/images/lunbo3.png"),
  },
]);
const categories = ref([]);
const hotGoods = ref([]);
const newGoods = ref([]);

// 商品详情弹框
const showDetailDialog = ref(false);
const currentGoods = ref(null);

const goToCategory = (categoryId: number) => {
  router.push(`/goods/list?category=${categoryId}`);
};

const goToDetail = (goodsId: number) => {
  // 查找商品信息
  const goods = [...hotGoods.value, ...newGoods.value].find(
    (item) => item.id === goodsId,
  );
  if (goods) {
    currentGoods.value = goods;
    showDetailDialog.value = true;
  }
};

const closeDetailDialog = () => {
  showDetailDialog.value = false;
  currentGoods.value = null;
};

const buyNow = () => {
  // 立即购买逻辑
  console.log("立即购买:", currentGoods.value);
  // 可以添加购买逻辑，如跳转到支付页面等
  closeDetailDialog();
};

const getCategoryImage = (name: string) => {
  const imageMap: Record<string, string> = {
    数码产品: require("../assets/images/digital.png"),
    服装鞋包: require("../assets/images/clothing.png"),
    图书文具: require("../assets/images/books.png"),
    运动户外: require("../assets/images/sports.png"),
    其他: require("../assets/images/other.png"),
  };
  return imageMap[name] || require("../assets/images/other.png");
};

// 模拟热门商品数据
const mockHotGoods = [
  {
    id: 1,
    name: "iPhone 13 Pro 256GB 远峰蓝色",
    price: 5999,
    images: [
      {
        imagePath: "https://picsum.photos/400/300?random=1",
      },
    ],
    categoryId: 1,
    views: 1234,
    createdAt: "2024-03-01",
  },
  {
    id: 2,
    name: "AirPods Pro 二代 全新未拆封",
    price: 1299,
    images: [
      {
        imagePath: "https://picsum.photos/400/300?random=2",
      },
    ],
    categoryId: 1,
    views: 987,
    createdAt: "2024-03-02",
  },
  {
    id: 3,
    name: "MacBook Pro 14英寸 M1 Pro",
    price: 8999,
    images: [
      {
        imagePath: "https://picsum.photos/400/300?random=3",
      },
    ],
    categoryId: 1,
    views: 765,
    createdAt: "2024-03-03",
  },
  {
    id: 4,
    name: "Adidas Ultraboost 22 运动鞋",
    price: 499,
    images: [
      {
        imagePath:
          "https://img.freepik.com/free-photo/sport-shoes-white-background_114579-822.jpg",
      },
    ],
    categoryId: 2,
    views: 654,
    createdAt: "2024-03-04",
  },
  {
    id: 5,
    name: "《算法导论》第三版 全新",
    price: 50,
    images: [
      {
        imagePath:
          "https://img.freepik.com/free-photo/stack-books-with-copy-space_23-2148857559.jpg",
      },
    ],
    categoryId: 3,
    views: 543,
    createdAt: "2024-03-05",
  },
  {
    id: 6,
    name: "Nike 运动背包 大容量",
    price: 199,
    images: [
      {
        imagePath:
          "https://img.freepik.com/free-photo/sport-bag-with-copy-space_23-2148857557.jpg",
      },
    ],
    categoryId: 4,
    views: 432,
    createdAt: "2024-03-06",
  },
];

// 模拟最新发布数据
const mockNewGoods = [
  {
    id: 7,
    name: "iPad Air 5 256GB WiFi版",
    price: 3999,
    images: [
      {
        imagePath: "https://picsum.photos/400/300?random=4",
      },
    ],
    categoryId: 1,
    views: 123,
    createdAt: "2024-03-07",
  },
  {
    id: 8,
    name: "优衣库 男士休闲外套",
    price: 199,
    images: [
      {
        imagePath:
          "https://img.freepik.com/free-photo/men-s-jacket-isolated-white-background_18591-64713.jpg",
      },
    ],
    categoryId: 2,
    views: 98,
    createdAt: "2024-03-07",
  },
  {
    id: 9,
    name: "《深入理解计算机系统》",
    price: 60,
    images: [
      {
        imagePath:
          "https://img.freepik.com/free-photo/books-arrangement_23-2148857558.jpg",
      },
    ],
    categoryId: 3,
    views: 87,
    createdAt: "2024-03-07",
  },
  {
    id: 10,
    name: "瑜伽垫 加厚加宽",
    price: 80,
    images: [
      {
        imagePath:
          "https://img.freepik.com/free-photo/yoga-mat-roll_23-2148766880.jpg",
      },
    ],
    categoryId: 4,
    views: 76,
    createdAt: "2024-03-06",
  },
  {
    id: 11,
    name: "小米手环 7 Pro",
    price: 299,
    images: [
      {
        imagePath:
          "https://img.freepik.com/free-photo/smartwatch-isolated-white-background_18591-64712.jpg",
      },
    ],
    categoryId: 1,
    views: 65,
    createdAt: "2024-03-06",
  },
  {
    id: 12,
    name: "收纳盒 桌面整理",
    price: 30,
    images: [
      {
        imagePath:
          "https://img.freepik.com/free-photo/desk-organizer-with-copy-space_23-2148857556.jpg",
      },
    ],
    categoryId: 5,
    views: 54,
    createdAt: "2024-03-06",
  },
];

onMounted(async () => {
  // 获取分类列表
  try {
    const res = await categoryApi.list();
    // 去重处理
    const uniqueCategories = [];
    const categoryNames = new Set();

    res.forEach((category) => {
      if (!categoryNames.has(category.name)) {
        categoryNames.add(category.name);
        uniqueCategories.push(category);
      }
    });

    categories.value = uniqueCategories;
  } catch (error) {
    console.error("获取分类失败:", error);
    // 使用默认分类
    categories.value = [
      { id: 1, name: "数码产品" },
      { id: 2, name: "服装鞋包" },
      { id: 3, name: "图书文具" },
      { id: 4, name: "运动户外" },
      { id: 5, name: "其他" },
    ];
  }

  // 使用模拟数据
  hotGoods.value = mockHotGoods;
  newGoods.value = mockNewGoods;
});
</script>

<style scoped lang="scss">
.home {
  padding: 20px;
}

.banner {
  margin-bottom: 30px;
}

.categories {
  margin-bottom: 30px;

  h2 {
    margin-bottom: 20px;
    font-size: 24px;
    color: #333;
  }

  .category-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 20px;
  }

  .category-card {
    min-height: 220px;
    display: flex;
    flex-direction: column;
    text-align: center;
  }

  .category-image {
    margin-bottom: 15px;
    height: 100px;
    display: flex;
    align-items: center;
    justify-content: center;

    img {
      width: 80px;
      height: 80px;
      object-fit: cover;
      border-radius: 8px;
    }
  }

  .category-header {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 15px;
  }

  .category-body {
    display: flex;
    justify-content: center;
    margin-top: 10px;
  }
}

.hot-goods,
.new-goods {
  margin-bottom: 40px;

  h2 {
    margin-bottom: 20px;
    font-size: 24px;
    color: #333;
    font-weight: bold;
    position: relative;
    padding-left: 10px;
    &::before {
      content: "";
      position: absolute;
      left: 0;
      top: 50%;
      transform: translateY(-50%);
      width: 4px;
      height: 20px;
      background-color: #409eff;
      border-radius: 2px;
    }
  }

  .goods-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 20px;
  }

  .goods-card {
    min-height: 220px;
    display: flex;
    flex-direction: column;
    border-radius: 8px;
    overflow: hidden;
    transition: all 0.3s ease;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    &:hover {
      transform: translateY(-4px);
      box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.15);
    }

    .goods-image {
      width: 100%;
      height: 100px;
      object-fit: cover;
      margin-bottom: 10px;
      transition: transform 0.3s ease;
      &:hover {
        transform: scale(1.05);
      }
    }

    .goods-info {
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      padding: 10px;
      background-color: #fff;
      overflow: hidden;

      h3 {
        font-size: 14px;
        margin-bottom: 8px;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        color: #333;
        font-weight: 500;
      }

      .goods-price {
        font-size: 16px;
        color: #f56c6c;
        font-weight: bold;
        margin-bottom: 8px;
      }

      .goods-meta {
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 10px;
        color: #909399;
        margin-bottom: 8px;
      }
    }
  }
}

/* 商品详情弹框样式 */
.goods-detail {
  display: flex;
  gap: 20px;
  padding: 20px 0;

  .goods-detail-image {
    flex: 0 0 40%;
    max-width: 400px;
    img {
      width: 100%;
      height: auto;
      max-height: 400px;
      object-fit: cover;
      border-radius: 8px;
    }
  }

  .goods-detail-info {
    flex: 1;

    h3 {
      font-size: 20px;
      font-weight: bold;
      margin-bottom: 15px;
      color: #333;
    }

    .goods-detail-price {
      font-size: 24px;
      font-weight: bold;
      color: #f56c6c;
      margin-bottom: 20px;
    }

    .goods-detail-meta {
      display: flex;
      gap: 30px;
      margin-bottom: 20px;
      font-size: 14px;
      color: #909399;
    }

    .goods-detail-desc {
      margin-top: 20px;

      h4 {
        font-size: 16px;
        font-weight: bold;
        margin-bottom: 10px;
        color: #333;
      }

      p {
        line-height: 1.6;
        color: #606266;
      }
    }
  }
}

/* 响应式调整 */
@media (max-width: 768px) {
  .goods-detail {
    flex-direction: column;

    .goods-detail-image {
      flex: 0 0 100%;
      max-width: 100%;
    }
  }
}
</style>
