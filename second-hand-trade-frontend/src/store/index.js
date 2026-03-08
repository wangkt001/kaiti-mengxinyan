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

export const useCartStore = defineStore("cart", {
  state: () => ({
    cartItems: [],
  }),
  getters: {
    cartCount: (state) => {
      return state.cartItems.length;
    },
  },
  actions: {
    addToCart(goods) {
      // 检查商品是否已在购物车中
      const existingItem = this.cartItems.find((item) => item.id === goods.id);
      if (existingItem) {
        // 如果已存在，增加数量
        existingItem.quantity++;
      } else {
        // 如果不存在，添加到购物车
        this.cartItems.push({
          ...goods,
          quantity: 1,
        });
      }
      // 保存到localStorage
      localStorage.setItem("cart", JSON.stringify(this.cartItems));
    },
    removeFromCart(goodsId) {
      this.cartItems = this.cartItems.filter((item) => item.id !== goodsId);
      localStorage.setItem("cart", JSON.stringify(this.cartItems));
    },
    clearCart() {
      this.cartItems = [];
      localStorage.removeItem("cart");
    },
    loadCart() {
      const cart = localStorage.getItem("cart");
      if (cart) {
        this.cartItems = JSON.parse(cart);
      }
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

      const cartStore = useCartStore();
      cartStore.clearCart();
    },
  },
});
