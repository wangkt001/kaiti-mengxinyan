import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    user: null,
    isLoggedIn: false
  }),
  actions: {
    setUser(user) {
      this.user = user
      this.isLoggedIn = true
    },
    logout() {
      this.user = null
      this.isLoggedIn = false
    }
  }
})

export const useGoodsStore = defineStore('goods', {
  state: () => ({
    goodsList: [],
    currentGoods: null
  }),
  actions: {
    setGoodsList(goods) {
      this.goodsList = goods
    },
    setCurrentGoods(goods) {
      this.currentGoods = goods
    }
  }
})

export const useOrderStore = defineStore('order', {
  state: () => ({
    orderList: []
  }),
  actions: {
    setOrderList(orders) {
      this.orderList = orders
    }
  }
})
