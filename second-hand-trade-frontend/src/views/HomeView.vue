<template>
  <div class="home">
    <div class="banner">
      <el-carousel :interval="5000" type="card" height="400px">
        <el-carousel-item v-for="item in bannerList" :key="item.id">
          <img :src="item.image" alt="" style="width: 100%; height: 100%; object-fit: cover;" />
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="categories">
      <h2>商品分类</h2>
      <div class="category-list">
        <el-card v-for="category in categories" :key="category.id" class="category-card">
          <template #header>
            <div class="category-header">
              <span>{{ category.name }}</span>
            </div>
          </template>
          <div class="category-body">
            <el-button type="primary" @click="goToCategory(category.id)">查看商品</el-button>
          </div>
        </el-card>
      </div>
    </div>

    <div class="hot-goods">
      <h2>热门商品</h2>
      <div class="goods-list">
        <el-card v-for="goods in hotGoods" :key="goods.id" class="goods-card">
          <img :src="goods.images[0]?.imagePath || ''" alt="" class="goods-image" />
          <div class="goods-info">
            <h3>{{ goods.name }}</h3>
            <p class="goods-price">¥{{ goods.price }}</p>
            <el-button type="primary" size="small" @click="goToDetail(goods.id)">查看详情</el-button>
          </div>
        </el-card>
      </div>
    </div>

    <div class="new-goods">
      <h2>最新发布</h2>
      <div class="goods-list">
        <el-card v-for="goods in newGoods" :key="goods.id" class="goods-card">
          <img :src="goods.images[0]?.imagePath || ''" alt="" class="goods-image" />
          <div class="goods-info">
            <h3>{{ goods.name }}</h3>
            <p class="goods-price">¥{{ goods.price }}</p>
            <el-button type="primary" size="small" @click="goToDetail(goods.id)">查看详情</el-button>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { goodsApi } from '../api/modules/goods'
import { categoryApi } from '../api/modules/category'

const router = useRouter()
const bannerList = ref([
  { id: 1, image: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=campus%20second%20hand%20trade%20platform%20banner&image_size=landscape_16_9' },
  { id: 2, image: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=second%20hand%20books%20and%20electronics&image_size=landscape_16_9' },
  { id: 3, image: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=campus%20life%20second%20hand%20market&image_size=landscape_16_9' }
])
const categories = ref([])
const hotGoods = ref([])
const newGoods = ref([])

const goToCategory = (categoryId: number) => {
  router.push(`/goods/list?category=${categoryId}`)
}

const goToDetail = (goodsId: number) => {
  router.push(`/goods/detail/${goodsId}`)
}

onMounted(async () => {
  // 获取分类列表
  try {
    const res = await categoryApi.list()
    categories.value = res
  } catch (error) {
    console.error('获取分类失败:', error)
  }

  // 获取热门商品
  try {
    const res = await goodsApi.list()
    hotGoods.value = res.slice(0, 6)
    newGoods.value = res.slice(0, 6)
  } catch (error) {
    console.error('获取商品失败:', error)
  }
})
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
    height: 120px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  .category-header {
    font-size: 18px;
    font-weight: bold;
  }

  .category-body {
    display: flex;
    justify-content: center;
  }
}

.hot-goods,
.new-goods {
  margin-bottom: 30px;

  h2 {
    margin-bottom: 20px;
    font-size: 24px;
    color: #333;
  }

  .goods-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
  }

  .goods-card {
    height: 300px;
    display: flex;
    flex-direction: column;

    .goods-image {
      width: 100%;
      height: 200px;
      object-fit: cover;
      margin-bottom: 10px;
    }

    .goods-info {
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: space-between;

      h3 {
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
        margin-bottom: 10px;
      }
    }
  }
}
</style>
