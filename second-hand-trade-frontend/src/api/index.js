import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
  withCredentials: true
})

// 请求拦截器，添加用户信息到请求头
api.interceptors.request.use(
  config => {
    const user = JSON.parse(localStorage.getItem('user'))
    if (user && user.id) {
      config.headers['X-User-Id'] = user.id
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

api.interceptors.response.use(
  response => response.data,
  error => {
    console.error('API Error:', error)
    return Promise.reject(error)
  }
)

export default api
