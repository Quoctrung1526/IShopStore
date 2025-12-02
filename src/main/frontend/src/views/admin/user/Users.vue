<template>
    <div class="container-fluid px-4">
        <h1 class="mt-4">Quản lý người dùng</h1>
        <ol class="breadcrumb mb-4">
            <li class="breadcrumb-item">
                <router-link to="/admin/dashboard">Trang chủ</router-link>
            </li>
            <li class="breadcrumb-item active">Người dùng</li>
        </ol>

        <div class="card">
            <div class="card-header">
                <i class="fas fa-users me-1"></i>
                Danh sách người dùng
            </div>
            <div class="card-body">
                <button class="btn btn-primary mb-3" @click="goToAddUser">
                    <i class="fas fa-plus"></i> Thêm người dùng
                </button>
                <table class="table table-bordered table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Tên</th>
                            <th>Email</th>
                            <th>Vai trò</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="user in users" :key="user.id">
                            <td>{{ user.id }}</td>
                            <td>{{ user.name }}</td>
                            <td>{{ user.email }}</td>
                            <td>
                                <span class="badge" :class="user.role === 'Admin' ? 'bg-danger' : 'bg-secondary'">
                                    {{ user.role }}
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
</template>

<script>
export default {
    name: 'Users',
    data() {
        return {
            users: [
                { id: 1, name: 'Nguyễn Văn A', email: 'nva@example.com', role: 'Admin' },
                { id: 2, name: 'Trần Thị B', email: 'ttb@example.com', role: 'User' },
                { id: 3, name: 'Lê Văn C', email: 'lvc@example.com', role: 'User' },
                { id: 4, name: 'Phạm Thị D', email: 'ptd@example.com', role: 'User' },
                { id: 5, name: 'Hoàng Văn E', email: 'hve@example.com', role: 'User' }
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
        },
        goToAddUser() {
            this.$router.push('/admin/users/createuser');
        }
    }
}
</script>