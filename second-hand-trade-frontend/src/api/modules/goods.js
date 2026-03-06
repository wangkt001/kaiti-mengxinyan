import api from '../index'

export const goodsApi = {
  list: () => {
    return api.get('/goods/list')
  },
  listByCategory: (categoryId) => {
    return api.get(`/goods/category/${categoryId}`)
  },
  listByUser: (userId) => {
    return api.get(`/goods/user/${userId}`)
  },
  search: (keyword) => {
    return api.get(`/goods/search?keyword=${keyword}`)
  },
  detail: (id) => {
    return api.get(`/goods/detail/${id}`)
  },
  add: (goods) => {
    return api.post('/goods/add', goods)
  },
  update: (goods) => {
    return api.put('/goods/update', goods)
  },
  delete: (id) => {
    return api.delete(`/goods/delete/${id}`)
  }
}
