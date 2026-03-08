<template>
  <div class="goods-add">
    <el-card class="goods-add-card">
      <template #header>
        <div class="goods-add-header">
          <h2>发布商品</h2>
        </div>
      </template>
      <div class="goods-add-body">
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
            >
              <el-icon><Plus /></el-icon>
              <template #file="{ file }">
                <div>
                  <img :src="file.url" class="el-upload-list__item-thumbnail" />
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
            <el-button type="primary" @click="submitForm">发布商品</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { Plus, ZoomIn, Delete } from "@element-plus/icons-vue";
import api from "../api/index";
import { goodsApi } from "../api/modules/goods";
import { categoryApi } from "../api/modules/category";
import { goodsImageApi } from "../api/modules/goodsImage";
import { useUserStore } from "../store";

const router = useRouter();
const userStore = useUserStore();
const goodsFormRef = ref();
const goodsForm = ref({
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
    { pattern: /^\d+(\.\d+)?$/, message: "请输入正确的价格", trigger: "blur" },
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

const handleImageChange = (file: any, fileList: any[]) => {
  imageList.value = fileList;
};

const handlePictureCardPreview = (file: any) => {
  dialogImageUrl.value = file.url;
  dialogVisible.value = true;
};

const handleRemove = (file: any, fileList: any[]) => {
  imageList.value = fileList;
};

const submitForm = async () => {
  if (goodsFormRef.value) {
    await goodsFormRef.value.validate(async (valid: boolean) => {
      if (valid) {
        if (!userStore.isLoggedIn) {
          ElMessage.error("请先登录");
          router.push("/login");
          return;
        }

        try {
          // 获取当前登录用户的ID
          const user = JSON.parse(localStorage.getItem("user"));
          if (!user || !user.id) {
            ElMessage.error("请先登录");
            router.push("/login");
            return;
          }

          // 添加用户ID到商品表单
          const goodsData = {
            ...goodsForm.value,
            userId: user.id,
          };

          const res = await goodsApi.add(goodsData);
          if (res) {
            // 上传图片逻辑
            if (imageList.value.length > 0) {
              const imageIds = [];
              for (const file of imageList.value) {
                // 创建FormData对象
                const formData = new FormData();
                formData.append("file", file.raw);
                formData.append("goodsId", res.id);

                // 上传图片
                console.log("上传图片:", file.raw);
                console.log("商品ID:", res.id);
                console.log("FormData内容:", formData);

                try {
                  const imageRes = await api.post(
                    "/goods-image/upload",
                    formData,
                    {
                      headers: {
                        "Content-Type": "multipart/form-data",
                      },
                    },
                  );
                  console.log("图片上传成功:", imageRes);
                  imageIds.push(imageRes.id);
                } catch (error) {
                  console.error("图片上传失败:", error);
                  throw error;
                }
              }
              console.log("上传的图片ID:", imageIds);
            }
            ElMessage.success("商品发布成功");
            router.push("/");
          } else {
            ElMessage.error("商品发布失败，请稍后重试");
          }
        } catch (error) {
          console.error("商品发布失败:", error);
          ElMessage.error("商品发布失败，请稍后重试");
        }
      }
    });
  }
};

const resetForm = () => {
  if (goodsFormRef.value) {
    goodsFormRef.value.resetFields();
    imageList.value = [];
  }
};

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
  }
});
</script>

<style scoped lang="scss">
.goods-add {
  padding: 20px;

  .goods-add-card {
    width: 800px;
    max-width: 100%;

    .goods-add-header {
      text-align: center;

      h2 {
        font-size: 24px;
        color: #333;
      }
    }

    .goods-add-body {
      padding: 20px 0;
    }

    .upload-demo {
      margin-top: 10px;
    }
  }
}
</style>
