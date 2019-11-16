import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api/image`,
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
    return AXIOS.get(`/${imageName}`);
  },
  testDatabase() {
    return AXIOS.get('/test/database');
  }
}
