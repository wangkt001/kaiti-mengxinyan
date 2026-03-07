import api from "../index";

export const disputeApi = {
  listByUser: () => {
    return api.get("/dispute/user");
  },
  listByOrder: (orderId) => {
    return api.get(`/dispute/order/${orderId}`);
  },
  add: (dispute) => {
    return api.post("/dispute/add", dispute);
  },
  resolve: (disputeId, resolution) => {
    return api.put("/dispute/resolve", { disputeId, resolution });
  },
};
