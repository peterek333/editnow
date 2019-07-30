import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api/user`,
  // timeout: 1000
});


export default {
  generateUUID() {
    return AXIOS.get('/uuid')
  }
}
