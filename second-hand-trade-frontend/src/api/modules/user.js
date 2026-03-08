import api from "../index";

export const userApi = {
  login: (username, password) => {
    return api.post("/user/login", { username, password });
  },
  register: (user) => {
    return api.post("/user/register", user);
  },
  logout: () => {
    return api.post("/user/logout");
  },
  getInfo: () => {
    return api.get("/user/info");
  },
  getUserInfo: (userId) => {
    return api.get(`/user/getById/${userId}`);
  },
  update: (user) => {
    return api.put("/user/update", user);
  },
};
