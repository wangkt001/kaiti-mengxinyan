import api from "../index";

export const goodsImageApi = {
  listByGoods: (goodsId) => {
    return api.get(`/goods-image/goods/${goodsId}`);
  },
  add: (goodsImage) => {
    return api.post("/goods-image/add", goodsImage);
  },
  delete: (id) => {
    return api.delete(`/goods-image/delete/${id}`);
  },
};
