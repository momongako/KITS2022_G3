import axios from 'axios';

const CATEGORIES_API_BASE_URL = 'http://localhost:8080/api/v1/categories';

class CategoriesService {

    getCategories() {
        let data = axios.get(CATEGORIES_API_BASE_URL);
        return data;
    }


    createCategories(categories) {
        return axios.post(CATEGORIES_API_BASE_URL, categories);
    }

    getCategoriesById(categoriesId) {
        let dataid = axios.get(CATEGORIES_API_BASE_URL + '/' + categoriesId);
        return dataid;
    }

    getCategoriesByDepartmentID(departmentID) {
        return axios.get(CATEGORIES_API_BASE_URL + '/departmentID/' + departmentID);
    }

    getCategoriesByDepartmentNameBirthDate(DepartmentName, BirthDate) {
        return axios.get(CATEGORIES_API_BASE_URL + '/departmentName/' + DepartmentName + '/' + BirthDate);
    }


    updateCategories(product, productId) {
        return axios.put(CATEGORIES_API_BASE_URL + '/' + productId, product);
    }

    deleteCategories(productId) {
        return axios.delete(CATEGORIES_API_BASE_URL + '/' + productId);
    }
}

export default new CategoriesService()