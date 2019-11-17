import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api/`,
  // timeout: 1000
});


export default {
  closeCompletedActionEmitter() {
    return AXIOS.delete('sse/completed-action/close');
  }
}
