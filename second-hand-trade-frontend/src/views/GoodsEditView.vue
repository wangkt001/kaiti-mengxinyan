<template>
  <div class="goods-edit">
    <el-card class="goods-edit-card">
      <template #header>
        <div class="goods-edit-header">
          <h2>编辑商品</h2>
        </div>
      </template>
      <div class="goods-edit-body">
        <el-form
          :model="goodsForm"
          :rules="goodsRules"
          ref="goodsFormRef"
          label-width="100px"
        >
          <el-form-item label="商品名称" prop="name">
            <el-input
              v-model="goodsForm.name"
              placeholder="请输入商品名称"
            ></el-input>
          </el-form-item>
          <el-form-item label="商品分类" prop="categoryId">
            <el-select
              v-model="goodsForm.categoryId"
              placeholder="请选择商品分类"
            >
              <el-option
                v-for="category in categories"
                :key="category.id"
                :label="category.name"
                :value="category.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="商品价格" prop="price">
            <el-input
              v-model="goodsForm.price"
              type="number"
              placeholder="请输入商品价格"
            ></el-input>
          </el-form-item>
          <el-form-item label="库存数量" prop="stock">
            <el-input
              v-model="goodsForm.stock"
              type="number"
              placeholder="请输入库存数量"
            ></el-input>
          </el-form-item>
          <el-form-item label="商品描述" prop="description">
            <el-input
              v-model="goodsForm.description"
              type="textarea"
              rows="4"
              placeholder="请输入商品描述"
            ></el-input>
          </el-form-item>
          <el-form-item label="商品图片">
            <el-upload
              class="upload-demo"
              action=""
              :on-change="handleImageChange"
              :on-remove="handleRemove"
              :auto-upload="false"
              list-type="picture-card"
              :limit="5"
              :file-list="existingImages"
            >
              <el-icon><Plus /></el-icon>
              <template #file="{ file }">
                <div>
                  <img
                    :src="file.url || file.imageData"
                    class="el-upload-list__item-thumbnail"
                  />
                  <span class="el-upload-list__item-actions">
                    <span
                      class="el-upload-list__item-preview"
                      @click="handlePictureCardPreview(file)"
                    >
                      <el-icon><ZoomIn /></el-icon>
                    </span>
                    <span
                      class="el-upload-list__item-delete"
                      @click="handleRemove(file)"
                    >
                      <el-icon><Delete /></el-icon>
                    </span>
                  </span>
                </div>
              </template>
            </el-upload>
            <el-dialog v-model="dialogVisible">
              <img w-full :src="dialogImageUrl" alt="" />
            </el-dialog>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm">保存修改</el-button>
            <el-button @click="router.back()">返回</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { ElMessage } from "element-plus";
import { Plus, ZoomIn, Delete } from "@element-plus/icons-vue";
import api from "../api/index";
import { goodsApi } from "../api/modules/goods";
import { categoryApi } from "../api/modules/category";
import { goodsImageApi } from "../api/modules/goodsImage";

const router = useRouter();
const route = useRoute();
const goodsId = Number(route.params.id);
const goodsFormRef = ref();
const goodsForm = ref({
  id: goodsId,
  name: "",
  categoryId: "",
  price: 0,
  stock: 1,
  description: "",
});
const goodsRules = ref({
  name: [{ required: true, message: "请输入商品名称", trigger: "blur" }],
  categoryId: [{ required: true, message: "请选择商品分类", trigger: "blur" }],
  price: [
    { required: true, message: "请输入商品价格", trigger: "blur" },
    {
      pattern: /^\d+(\.\d+)?$/,
      message: "请输入正确的价格",
      trigger: "blur",
    },
  ],
  stock: [
    { required: true, message: "请输入库存数量", trigger: "blur" },
    { pattern: /^\d+$/, message: "请输入正确的库存数量", trigger: "blur" },
  ],
  description: [{ required: true, message: "请输入商品描述", trigger: "blur" }],
});
const categories = ref([]);
const dialogVisible = ref(false);
const dialogImageUrl = ref("");
const imageList = ref([]);
const existingImages = ref([]);

const handleImageChange = (file: any, fileList: any[]) => {
  imageList.value = fileList;
};

const handlePictureCardPreview = (file: any) => {
  dialogImageUrl.value = file.url || file.imageData;
  dialogVisible.value = true;
};

const handleRemove = (file: any, fileList: any[]) => {
  imageList.value = fileList;
};

const submitForm = async () => {
  if (goodsFormRef.value) {
    await goodsFormRef.value.validate(async (valid: boolean) => {
      if (valid) {
        try {
          const res = await goodsApi.update(goodsForm.value);

          if (imageList.value.length > 0) {
            for (const file of imageList.value) {
              if (!file.raw && file.imageData) continue;

              const formData = new FormData();
              formData.append("file", file.raw);
              formData.append("goodsId", goodsId);

              try {
                await api.post("/goods-image/upload", formData, {
                  headers: { "Content-Type": "multipart/form-data" },
                });
              } catch (error) {
                console.error("图片上传失败:", error);
              }
            }
          }

          ElMessage.success("修改成功");
          router.push("/user/center");
        } catch (error) {
          console.error("修改失败:", error);
          ElMessage.error("修改失败，请稍后重试");
        }
      }
    });
  }
};

const fetchGoodsDetail = async () => {
  try {
    const res = await goodsApi.detail(goodsId);
    goodsForm.value = {
      id: res.id,
      name: res.name,
      categoryId: res.categoryId,
      price: res.price,
      stock: res.stock,
      description: res.description,
    };

    if (res.images && res.images.length > 0) {
      existingImages.value = res.images.map((img: any) => ({
        ...img,
        url: img.imageData || img.imagePath,
        name: `image-${img.id}`,
      }));
    }
  } catch (error) {
    console.error("获取商品详情失败:", error);
    ElMessage.error("获取商品信息失败");
  }
};

onMounted(async () => {
  try {
    const res = await categoryApi.list();
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
  }

  await fetchGoodsDetail();
});
</script>

<style scoped lang="scss">
.goods-edit {
  padding: 20px;

  .goods-edit-card {
    width: 800px;
    max-width: 100%;

    .goods-edit-header {
      text-align: center;

      h2 {
        font-size: 24px;
        color: #333;
      }
    }

    .goods-edit-body {
      padding: 20px 0;
    }

    .upload-demo {
      margin-top: 10px;
    }
  }
}
</style>
