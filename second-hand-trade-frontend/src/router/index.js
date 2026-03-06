import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/RegisterView.vue')
  },
  {
    path: '/goods/list',
    name: 'goodsList',
    component: () => import('../views/GoodsListView.vue')
  },
  {
    path: '/goods/detail/:id',
    name: 'goodsDetail',
    component: () => import('../views/GoodsDetailView.vue')
  },
  {
    path: '/goods/add',
    name: 'goodsAdd',
    component: () => import('../views/GoodsAddView.vue')
  },
  {
    path: '/user/center',
    name: 'userCenter',
    component: () => import('../views/UserCenterView.vue')
  },
  {
    path: '/order/list',
    name: 'orderList',
    component: () => import('../views/OrderListView.vue')
  },
  {
    path: '/admin',
    name: 'admin',
    component: () => import('../views/AdminView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
