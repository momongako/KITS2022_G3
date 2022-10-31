import axios from 'axios';

const AUTH_API_BASE_URL = 'http://localhost:8080/api/auth';

class AuthService {
    login(credentials) {
        return axios.post(AUTH_API_BASE_URL + '/login', credentials)
            .then((response) => {
                if (response.data) {
                    localStorage.setItem('user', JSON.stringify(response.data))
                    localStorage.setItem('id', JSON.stringify(response.data.id))
                    localStorage.setItem('email', JSON.stringify(response.data.email))
                }
                return response.data
            })
    }

    logout() {
        return axios.get(AUTH_API_BASE_URL + '/logout');
    }

    register(credentials) {
        return axios.post(AUTH_API_BASE_URL + '/register',credentials)
    }

    getCurrentUser() {
        JSON.parse(localStorage.getItem('user'))
    }
}

export default new AuthService()