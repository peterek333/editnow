import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api`,
  // timeout: 1000
});


export default {
  getTestImageSki() {
    return AXIOS.get(`/test/skis`);
  },
  transformToGrayscale(image) {
    return
  }
}
