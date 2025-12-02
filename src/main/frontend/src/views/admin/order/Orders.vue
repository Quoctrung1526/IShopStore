<template>
    <div class="admin-container">
        <Navbar />

        <div class="layout-sidenav">
            <Sidebar />

            <div class="content-area">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Quản lý đơn hàng</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item">
                                <router-link to="/admin/dashboard">Trang chủ</router-link>
                            </li>
                            <li class="breadcrumb-item active">Đơn hàng</li>
                        </ol>

                        <div class="card">
                            <div class="card-header">
                                <i class="fas fa-shopping-cart me-1"></i>
                                Danh sách đơn hàng
                            </div>
                            <div class="card-body">
                                <table class="table table-bordered table-striped table-hover">
                                    <thead class="table-dark">
                                        <tr>
                                            <th>Mã đơn</th>
                                            <th>Khách hàng</th>
                                            <th>Tổng tiền</th>
                                            <th>Trạng thái</th>
                                            <th>Ngày đặt</th>
                                            <th>Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr v-for="order in orders" :key="order.id">
                                            <td>{{ order.id }}</td>
                                            <td>{{ order.customer }}</td>
                                            <td>{{ order.total }}</td>
                                            <td>
                                                <span class="badge" :class="getStatusClass(order.status)">
                                                    {{ order.status }}
                                                </span>
                                            </td>
                                            <td>{{ order.date }}</td>
                                            <td>
                                                <button class="btn btn-sm btn-info me-1" title="Xem chi tiết">
                                                    <i class="fas fa-eye"></i>
                                                </button>
                                                <button class="btn btn-sm btn-warning me-1" title="Cập nhật">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                                <button class="btn btn-sm btn-danger" title="Hủy đơn">
                                                    <i class="fas fa-times"></i>
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
    name: 'Orders',
    components: {
        Navbar,
        Sidebar,
        Footer
    },
    data() {
        return {
            orders: [
                { id: '#ORD-001', customer: 'Nguyễn Văn A', total: '25,000,000đ', status: 'Hoàn thành', date: '2024-01-15' },
                { id: '#ORD-002', customer: 'Trần Thị B', total: '30,000,000đ', status: 'Đang xử lý', date: '2024-01-16' },
                { id: '#ORD-003', customer: 'Lê Văn C', total: '22,000,000đ', status: 'Đang giao', date: '2024-01-17' },
                { id: '#ORD-004', customer: 'Phạm Thị D', total: '45,000,000đ', status: 'Hoàn thành', date: '2024-01-18' },
                { id: '#ORD-005', customer: 'Hoàng Văn E', total: '18,000,000đ', status: 'Đã hủy', date: '2024-01-19' }
            ]
        }
    },
    mounted() {
        this.loadExternalScripts()
    },
    methods: {
        getStatusClass(status) {
            const classes = {
                'Hoàn thành': 'bg-success',
                'Đang xử lý': 'bg-warning text-dark',
                'Đang giao': 'bg-info',
                'Đã hủy': 'bg-danger'
            }
            return classes[status] || 'bg-secondary'
        },
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