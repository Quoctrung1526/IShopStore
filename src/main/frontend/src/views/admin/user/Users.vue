<template>
    <div class="container-fluid px-4">
        <h1 class="mt-4">Quản lý người dùng</h1>
        <ol class="breadcrumb mb-4">
            <li class="breadcrumb-item">
                <router-link to="/admin/dashboard">Trang chủ</router-link>
            </li>
            <li class="breadcrumb-item active">Người dùng</li>
        </ol>

        <div v-if="loading" class="text-center p-5">
            <i class="fas fa-spinner fa-spin fa-2x text-primary"></i>
            <p class="mt-2 text-muted">Đang tải danh sách người dùng...</p>
        </div>

        <div v-else-if="error" class="alert alert-danger" role="alert">
            <h4 class="alert-heading">Lỗi tải dữ liệu!</h4>
            <p>{{ error }}</p>
            <button @click="fetchUsers" class="btn btn-sm btn-outline-danger">
                <i class="fas fa-redo me-1"></i> Tải lại
            </button>
        </div>

        <div v-else class="card mb-4 shadow-sm">
            <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">
                <span><i class="fas fa-users me-1"></i> Danh sách người dùng ({{ users.length }} người)</span>
                <button class="btn btn-sm btn-primary" @click="goToAddUser">
                    <i class="fas fa-plus"></i> Thêm người dùng
                </button>
            </div>

            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Tên</th>
                                <th>Email</th>
                                <th>Vai trò</th>
                                <th>Avatar</th>
                                <th style="width: 150px;">Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="user in users" :key="user.id">
                                <td>{{ user.id }}</td>
                                <td>{{ user.fullName }}</td>
                                <td>{{ user.email }}</td>
                                <td>
                                    <span v-if="user.role" class="badge"
                                        :class="user.role.name === 'ADMIN' ? 'bg-danger' : 'bg-secondary'">
                                        {{ user.role.name }}
                                    </span>
                                    <span v-else class="badge bg-warning">N/A</span>
                                </td>
                                <td>

                                    <img :src="getAvatarUrl(user.avatar) ? getAvatarUrl(user.avatar) : 'https://placehold.co/30x30/CCCCCC/FFFFFF/png?text=A'"
                                        alt="Avatar" class="rounded-circle"
                                        style="width: 30px; height: 30px; object-fit: cover;">
                                </td>
                                <td>
                                    <router-link :to="{ name: 'DetailUser', params: { id: user.id } }"
                                        class="btn btn-sm btn-info me-1" title="Xem chi tiết">
                                        <i class="fas fa-eye"></i>
                                    </router-link>

                                    <router-link :to="{ name: 'UpdateUser', params: { id: user.id } }"
                                        class="btn btn-sm btn-warning me-1" title="Sửa thông tin">
                                        <i class="fas fa-edit"></i>
                                    </router-link>

                                    <router-link :to="{ name: 'DeleteUser', params: { id: user.id } }"
                                        class="btn btn-sm btn-danger" title="Xóa người dùng">
                                        <i class="fas fa-trash"></i>
                                    </router-link>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '';

const users = ref([]);
const loading = ref(true);
const error = ref(null);

const getAvatarUrl = (avatarPath) => {
    if (!avatarPath) {
        return null;
    }

    if (avatarPath.startsWith('http')) {
        return avatarPath;
    }

    const backendBase = "http://localhost:9090";

    // Loại bỏ dấu '/' ở đầu avatarPath nếu có để tránh lỗi đường dẫn đôi
    const cleanPath = avatarPath.startsWith('/') ? avatarPath.substring(1) : avatarPath;

    return `${backendBase}/${cleanPath}`;
};


const fetchUsers = async () => {
    loading.value = true;
    error.value = null;
    try {
        const response = await axios.get(`${API_BASE_URL}/api/users`);
        users.value = response.data;
    } catch (err) {
        const errorMessage =
            err.response?.data?.message ||
            `Lỗi HTTP: ${err.response?.status} - ${err.message}` ||
            'Không thể kết nối đến API Back-end. Vui lòng kiểm tra server.';

        error.value = errorMessage;
        console.error('Error fetching users:', err);
    } finally {
        loading.value = false;
    }
};

const goToAddUser = () => {
    router.push({ name: 'CreateUser' });
};

onMounted(() => {
    fetchUsers();
});

</script>