import api from '../index'

export const categoryApi = {
  list: () => {
    return api.get('/category/list')
  },
  add: (category) => {
    return api.post('/category/add', category)
  },
  update: (category) => {
    return api.put('/category/update', category)
  },
  delete: (id) => {
    return api.delete(`/category/delete/${id}`)
  }
}
