import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api/configuration`,
  // timeout: 1000
});


export default {
  getActionTools() {
    return AXIOS.get('/action-tools')
  }
}
