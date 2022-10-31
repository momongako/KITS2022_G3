import axios from 'axios';

const PRODUCT_API_BASE_URL = 'http://localhost:8080/api/v1/products';

class ProductService {

    getProduct(name, origin, category, start, end) {
        let data = axios.get(PRODUCT_API_BASE_URL + '?name=' + name + '&origin=' + origin + '&category=' + category + '&start=' + start + '&end=' + end);
        return data;
    }

    getProductRating(rating) {
        let data = axios.get(PRODUCT_API_BASE_URL + '/product-star/' + rating);
        return data;
    }

    getProductCategoriesName(categoryName, start, end, origin) {
        let dataCategories = axios.get(PRODUCT_API_BASE_URL + '?category=' + categoryName + '&start=' + start + '&end=' + end + '&name=' + origin);
        return dataCategories;
    }

    getProductPriceSlider(start, end) {
        let dataCategories = axios.get(PRODUCT_API_BASE_URL + '?start=' + start + '&end=' + end);
        return dataCategories;
    }

    SearchProduct(product, start, end) {
        return axios.get(PRODUCT_API_BASE_URL + '?name=' + product + '&start=' + start + '&end=' + end);
    }


    //chua dung
    createProduct(product) {
        return axios.post(PRODUCT_API_BASE_URL, product);
    }

    getProductById(productId) {
        let dataid = axios.get(PRODUCT_API_BASE_URL + '/' + productId);
        return dataid;
    }

    getProductByDepartmentID(departmentID) {
        return axios.get(PRODUCT_API_BASE_URL + '/departmentID/' + departmentID);
    }

    getProductByDepartmentNameBirthDate(DepartmentName, BirthDate) {
        return axios.get(PRODUCT_API_BASE_URL + '/departmentName/' + DepartmentName + '/' + BirthDate);
    }


    updateProduct(product, productId) {
        return axios.put(PRODUCT_API_BASE_URL + '/' + productId, product);
    }

    deleteProduct(productId) {
        return axios.delete(PRODUCT_API_BASE_URL + '/' + productId);
    }


}

export default new ProductService()