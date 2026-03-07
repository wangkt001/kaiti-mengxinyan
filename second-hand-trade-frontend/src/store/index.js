import { defineStore } from "pinia";

// 定义所有 store
export const useGoodsStore = defineStore("goods", {
  state: () => ({
    goodsList: [],
    currentGoods: null,
  }),
  actions: {
    setGoodsList(goods) {
      this.goodsList = goods;
    },
    setCurrentGoods(goods) {
      this.currentGoods = goods;
    },
  },
});

export const useOrderStore = defineStore("order", {
  state: () => ({
    orderList: [],
  }),
  actions: {
    setOrderList(orders) {
      this.orderList = orders;
    },
  },
});

export const useUserStore = defineStore("user", {
  state: () => ({
    user: null,
    isLoggedIn: false,
  }),
  actions: {
    setUser(user) {
      this.user = user;
      this.isLoggedIn = true;
    },
    logout() {
      this.user = null;
      this.isLoggedIn = false;
      // 清理localStorage中的用户信息
      localStorage.removeItem("user");
      // 清理其他存储中的数据
      const goodsStore = useGoodsStore();
      goodsStore.goodsList = [];
      goodsStore.currentGoods = null;

      const orderStore = useOrderStore();
      orderStore.orderList = [];
    },
  },
});
