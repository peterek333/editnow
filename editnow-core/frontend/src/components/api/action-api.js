import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api/action`,
  // timeout: 1000
});


export default {
  sendActionRequest(actionRequest) {
    return AXIOS.post('', actionRequest);
  },
  getOutputImageByActionId(actionId) {
    return AXIOS.get(`/${actionId}/output`);
  },
  newActionChain() {
    return AXIOS.post('/chain/new');
  },
}
