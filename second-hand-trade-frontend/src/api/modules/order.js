import api from '../index'

export const orderApi = {
  listByBuyer: () => {
    return api.get('/order/buyer')
  },
  listBySeller: () => {
    return api.get('/order/seller')
  },
  detail: (id) => {
    return api.get(`/order/detail/${id}`)
  },
  create: (order) => {
    return api.post('/order/create', order)
  },
  updateStatus: (orderId, status) => {
    return api.put('/order/update-status', null, { params: { orderId, status } })
  }
}
