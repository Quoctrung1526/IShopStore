<template>
    <div class="admin-container">
        <Navbar />

        <div class="layout-sidenav">
            <Sidebar />

            <div class="content-area">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Quản lý sản phẩm</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item">
                                <router-link to="/admin/dashboard">Trang chủ</router-link>
                            </li>
                            <li class="breadcrumb-item active">Sản phẩm</li>
                        </ol>

                        <div class="card">
                            <div class="card-header">
                                <i class="fas fa-box me-1"></i>
                                Danh sách sản phẩm
                            </div>
                            <div class="card-body">
                                <button class="btn btn-primary mb-3">
                                    <i class="fas fa-plus"></i> Thêm sản phẩm
                                </button>

                                <table class="table table-bordered table-striped table-hover">
                                    <thead class="table-dark">
                                        <tr>
                                            <th>ID</th>
                                            <th>Tên sản phẩm</th>
                                            <th>Giá</th>
                                            <th>Số lượng</th>
                                            <th>Trạng thái</th>
                                            <th>Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr v-for="product in products" :key="product.id">
                                            <td>{{ product.id }}</td>
                                            <td>{{ product.name }}</td>
                                            <td>{{ product.price }}</td>
                                            <td>{{ product.quantity }}</td>
                                            <td>
                                                <span class="badge"
                                                    :class="product.quantity > 0 ? 'bg-success' : 'bg-danger'">
                                                    {{ product.quantity > 0 ? 'Còn hàng' : 'Hết hàng' }}
                                                </span>
                                            </td>
                                            <td>
                                                <button class="btn btn-sm btn-info me-1" title="Xem">
                                                    <i class="fas fa-eye"></i>
                                                </button>
                                                <button class="btn btn-sm btn-warning me-1" title="Sửa">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                                <button class="btn btn-sm btn-danger" title="Xóa">
                                                    <i class="fas fa-trash"></i>
                                                </button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </main>

                <Footer />
            </div>
        </div>
    </div>
</template>

<script>
import Navbar from '@/components/admin/Navbar.vue'
import Sidebar from '@/components/admin/Sidebar.vue'
import Footer from '@/components/admin/Footer.vue'

export default {
    name: 'Products',
    components: {
        Navbar,
        Sidebar,
        Footer
    },
    data() {
        return {
            products: [
                { id: 1, name: 'Laptop Dell XPS 13', price: '25,000,000đ', quantity: 15 },
                { id: 2, name: 'iPhone 15 Pro Max', price: '30,000,000đ', quantity: 25 },
                { id: 3, name: 'Samsung Galaxy S24', price: '22,000,000đ', quantity: 30 },
                { id: 4, name: 'MacBook Pro M3', price: '45,000,000đ', quantity: 10 },
                { id: 5, name: 'iPad Air', price: '18,000,000đ', quantity: 0 }
            ]
        }
    },
    mounted() {
        this.loadExternalScripts()
    },
    methods: {
        loadExternalScripts() {
            if (!document.querySelector('link[href*="bootstrap"]')) {
                const bootstrap = document.createElement('link')
                bootstrap.rel = 'stylesheet'
                bootstrap.href = 'https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css'
                document.head.appendChild(bootstrap)
            }
            if (!document.querySelector('script[src*="fontawesome"]')) {
                const fontawesome = document.createElement('script')
                fontawesome.src = 'https://use.fontawesome.com/releases/v6.3.0/js/all.js'
                document.head.appendChild(fontawesome)
            }
        }
    }
}
</script>

<style scoped>
.admin-container {
    min-height: 100vh;
}

.layout-sidenav {
    display: flex;
    margin-top: 56px;
}

.content-area {
    flex: 1;
    display: flex;
    flex-direction: column;
    min-height: calc(100vh - 56px);
    background-color: #f5f5f5;
}

main {
    flex: 1;
    padding-bottom: 2rem;
}
</style>