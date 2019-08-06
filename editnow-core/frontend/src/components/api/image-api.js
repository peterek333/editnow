import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api`,
  // timeout: 1000
});


export default {
  getTestImageSki() {
    return AXIOS.get(`/test/skis`);
  },
  transformToGrayscale(imageDetails) {
    return AXIOS.post(`/transformation/grayscale`, {
      imageBase64: imageDetails.base64,
      imageType: imageDetails.type
    });
  },
  getImage(imageName) {
    return AXIOS.get(`/image/${imageName}`);
  },
  testDatabase() {
    return AXIOS.get('/test/database');
  }
}
