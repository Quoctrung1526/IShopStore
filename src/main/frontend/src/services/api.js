import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

const apiClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
})

// Interceptor để xử lý lỗi
apiClient.interceptors.response.use(
    response => response,
    error => {
        console.error('API Error:', error)
        return Promise.reject(error)
    }
)

export default {
    // Products API
    products: {
        getAll(page = 0, size = 10, sortBy = 'id', sortDir = 'desc') {
            return apiClient.get('/products', {
                params: { page, size, sortBy, sortDir }
            })
        },
        getById(id) {
            return apiClient.get(`/products/${id}`)
        },
        create(product) {
            return apiClient.post('/products', product)
        },
        update(id, product) {
            return apiClient.put(`/products/${id}`, product)
        },
        delete(id) {
            return apiClient.delete(`/products/${id}`)
        },
        search(keyword) {
            return apiClient.get('/products/search', { params: { keyword } })
        },
        getByCategory(categoryId) {
            return apiClient.get(`/products/category/${categoryId}`)
        },
        getByBrand(brand) {
            return apiClient.get(`/products/brand/${brand}`)
        },
        getAvailable() {
            return apiClient.get('/products/available')
        },
        updateStock(id, quantity) {
            return apiClient.patch(`/products/${id}/stock`, null, {
                params: { quantity }
            })
        },
        getStatistics() {
            return apiClient.get('/products/statistics')
        }
    },

    // Categories API
    categories: {
        getAll() {
            return apiClient.get('/categories')
        },
        getById(id) {
            return apiClient.get(`/categories/${id}`)
        },
        create(category) {
            return apiClient.post('/categories', category)
        },
        update(id, category) {
            return apiClient.put(`/categories/${id}`, category)
        },
        delete(id) {
            return apiClient.delete(`/categories/${id}`)
        }
    },

    // Orders API (sẽ implement sau)
    orders: {
        getAll(page = 0, size = 10) {
            return apiClient.get('/orders', { params: { page, size } })
        },
        getById(id) {
            return apiClient.get(`/orders/${id}`)
        },
        create(order) {
            return apiClient.post('/orders', order)
        },
        updateStatus(id, status) {
            return apiClient.patch(`/orders/${id}/status`, null, {
                params: { status }
            })
        }
    },

    // Customers API (sẽ implement sau)
    customers: {
        getAll() {
            return apiClient.get('/customers')
        },
        getById(id) {
            return apiClient.get(`/customers/${id}`)
        },
        create(customer) {
            return apiClient.post('/customers', customer)
        },
        update(id, customer) {
            return apiClient.put(`/customers/${id}`, customer)
        }
    }
}