import api from "../index";

export const evaluationApi = {
  listByOrder: (orderId) => {
    return api.get(`/evaluation/order/${orderId}`);
  },
  listByEvaluated: (evaluatedId) => {
    return api.get(`/evaluation/evaluated/${evaluatedId}`);
  },
  add: (evaluation) => {
    return api.post("/evaluation/add", evaluation);
  },
};
