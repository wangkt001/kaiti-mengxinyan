<template>
  <div class="goods-list">
    <div class="search-bar">
      <el-input
        v-model="searchKeyword"
        placeholder="请输入商品名称"
        style="width: 300px; margin-right: 10px"
      >
        <template #append>
          <el-button @click="search">搜索</el-button>
        </template>
      </el-input>
      <el-select
        v-model="selectedCategory"
        placeholder="选择分类"
        style="width: 200px; margin-right: 10px"
      >
        <el-option label="全部分类" value="0"></el-option>
        <el-option
          v-for="category in categories"
          :key="category.id"
          :label="category.name"
          :value="category.id"
        ></el-option>
      </el-select>
      <el-select v-model="sortBy" placeholder="排序方式" style="width: 150px">
        <el-option label="默认" value="default"></el-option>
        <el-option label="价格从低到高" value="price_asc"></el-option>
        <el-option label="价格从高到低" value="price_desc"></el-option>
        <el-option label="最新发布" value="newest"></el-option>
      </el-select>
    </div>

    <div class="goods-grid">
      <el-card v-for="goods in goodsList" :key="goods.id" class="goods-card">
        <img
          :src="goods.images[0]?.imagePath || ''"
          alt=""
          class="goods-image"
        />
        <div class="goods-info">
          <h3>{{ goods.name }}</h3>
          <p class="goods-price">¥{{ goods.price }}</p>
          <el-button type="primary" size="small" @click="goToDetail(goods.id)"
            >查看详情</el-button
          >
        </div>
      </el-card>
    </div>

    <div class="pagination">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 30, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { goodsApi } from "../api/modules/goods";
import { categoryApi } from "../api/modules/category";

const router = useRouter();
const route = useRoute();
const searchKeyword = ref("");
const selectedCategory = ref("0");
const sortBy = ref("default");
const goodsList = ref([]);
const categories = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

const search = async () => {
  await fetchGoods();
};

const goToDetail = (goodsId: number) => {
  router.push(`/goods/detail/${goodsId}`);
};

const fetchGoods = async () => {
  try {
    let res;
    if (selectedCategory.value === "0") {
      if (searchKeyword.value) {
        res = await goodsApi.search(searchKeyword.value);
      } else {
        res = await goodsApi.list();
      }
    } else {
      res = await goodsApi.listByCategory(Number(selectedCategory.value));
    }
    goodsList.value = res;
    total.value = res.length;
  } catch (error) {
    console.error("获取商品失败:", error);
  }
};

const handleSizeChange = (size: number) => {
  pageSize.value = size;
  fetchGoods();
};

const handleCurrentChange = (current: number) => {
  currentPage.value = current;
  fetchGoods();
};

onMounted(async () => {
  // 获取分类列表
  try {
    const res = await categoryApi.list();
    categories.value = res;
  } catch (error) {
    console.error("获取分类失败:", error);
  }

  // 检查路由参数中的分类
  const categoryParam = route.query.category;
  if (categoryParam) {
    selectedCategory.value = categoryParam as string;
  }

  // 获取商品列表
  await fetchGoods();
});
</script>

<style scoped lang="scss">
.goods-list {
  padding: 20px;

  .search-bar {
    margin-bottom: 20px;
    display: flex;
    align-items: center;
  }

  .goods-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
    margin-bottom: 20px;
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

  .pagination {
    display: flex;
    justify-content: center;
    margin-top: 20px;
  }
}
</style>
