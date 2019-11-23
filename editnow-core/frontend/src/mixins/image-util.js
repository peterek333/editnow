const BASE_64_CONST = ';base64,';
const DATA_INPUT_IMAGE_CONST = 'data:image/';

export default {

  getImageStructureFromFullImageBase64(fullImageBase64) {
    const indexOfBase64Const = fullImageBase64.indexOf(BASE_64_CONST);
    const base64 = fullImageBase64.substring(indexOfBase64Const + BASE_64_CONST.length);
    const type = fullImageBase64.substring(
      fullImageBase64.indexOf(DATA_INPUT_IMAGE_CONST) + DATA_INPUT_IMAGE_CONST.length,
      indexOfBase64Const);

    return {
      fullImageBase64: fullImageBase64,
      base64: base64,
      type: type
    }
  },
  getImageStructureFromResponse(response) {
    return {
      fullImageBase64: DATA_INPUT_IMAGE_CONST + response.data.type +  BASE_64_CONST + response.data.base64,
      base64: response.data.base64,
      type: response.data.type
    }
  },
  getEmptyImageObject() {
    return {
      fullImageBase64: null,
      base64: null,
      type: null
    }
  }

}