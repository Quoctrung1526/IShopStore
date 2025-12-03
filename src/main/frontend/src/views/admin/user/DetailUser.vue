<template>
    <div class="container-fluid px-4">
        <h1 class="mt-4">Chi tiết Người dùng</h1>
        <ol class="breadcrumb mb-4">
            <li class="breadcrumb-item">
                <router-link to="/admin/dashboard">Trang chủ</router-link>
            </li>
            <li class="breadcrumb-item">
                <router-link to="/admin/users">Người dùng</router-link>
            </li>
            <li class="breadcrumb-item active">Chi tiết</li>
        </ol>

        <div v-if="loading" class="text-center p-5">
            <i class="fas fa-spinner fa-spin fa-2x text-primary"></i>
            <p class="mt-2 text-muted">Đang tải thông tin người dùng...</p>
        </div>

        <div v-else-if="error" class="alert alert-danger" role="alert">
            <h4 class="alert-heading">Lỗi!</h4>
            <p>{{ error }}</p>
            <button @click="fetchUser" class="btn btn-sm btn-outline-danger">
                <i class="fas fa-redo me-1"></i> Tải lại
            </button>
        </div>

        <div v-else class="card mb-4 shadow-sm">
            <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                <span><i class="fas fa-user me-1"></i> Thông tin chi tiết Người dùng #{{ user.id }}</span>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-4 text-center mb-4 border-end">
                        <img :src="getAvatarUrl(user.avatarUrl) || 'https://placehold.co/150x150/007bff/FFFFFF/png?text=AVATAR'"
                            alt="Avatar" class="img-thumbnail rounded-circle mb-3"
                            style="width: 150px; height: 150px; object-fit: cover;">
                        <h3 class="fw-bold text-dark">{{ user.fullName }}</h3>

                        <span v-if="user.role && user.role.name" class="badge fs-6 py-2 px-3"
                            :class="user.role.name === 'ADMIN' ? 'bg-danger' : 'bg-secondary'">
                            {{ user.role.name }}
                        </span>
                        <span v-else class="badge fs-6 py-2 px-3 bg-warning">Chưa xác định</span>
                    </div>

                    <div class="col-md-8">
                        <h5 class="mb-3 text-primary border-bottom pb-1">Chi tiết Tài khoản</h5>
                        <table class="table table-sm table-hover">
                            <tbody>
                                <tr>
                                    <th style="width: 180px;">ID Người dùng:</th>
                                    <td>{{ user.id }}</td>
                                </tr>
                                <tr>
                                    <th>Địa chỉ Email:</th>
                                    <td>{{ user.email }}</td>
                                </tr>
                                <tr>
                                    <th>Số điện thoại:</th>
                                    <td>{{ user.phone || 'Chưa cập nhật' }}</td>
                                </tr>
                                <tr>
                                    <th>Địa chỉ:</th>
                                    <td>{{ user.address || 'Chưa cập nhật' }}</td>
                                </tr>
                                <tr>
                                    <th>Ngày tạo:</th>
                                    <td>{{ formatDate(user.createdAt) }}</td>
                                </tr>
                                <tr>
                                    <th>Cập nhật lần cuối:</th>
                                    <td>{{ formatDate(user.updatedAt) }}</td>
                                </tr>
                            </tbody>
                        </table>

                        <div class="mt-4 pt-3 border-top">
                            <button @click="goToEditUser" class="btn btn-warning text-white me-2">
                                <i class="fas fa-edit me-1"></i> Chỉnh sửa
                            </button>

                            <button @click="goToDeleteUser" class="btn btn-danger me-2">
                                <i class="fas fa-trash me-1"></i> Xóa
                            </button>

                            <button @click="goBack" class="btn btn-secondary">
                                <i class="fas fa-arrow-left me-1"></i> Trở về
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '';

// Định nghĩa Props nhận ID từ Router
const props = defineProps({
    id: {
        type: [String, Number],
        required: true
    }
});

// State
const user = ref({});
const loading = ref(false);
const error = ref(null);

const getAvatarUrl = (avatarPath) => {
    if (!avatarPath) {
        return null;
    }

    if (avatarPath.startsWith('http')) {
        return avatarPath;
    }

    const backendBase = "http://localhost:9090";
    const cleanPath = avatarPath.startsWith('/') ? avatarPath.substring(1) : avatarPath;

    // Giả định path từ DB là /upload-images/ten_file.jpg
    return `${backendBase}/${cleanPath}`;
};


const goBack = () => {
    router.push('/admin/users');
};

const goToEditUser = () => {
    router.push({ name: 'UpdateUser', params: { id: props.id } });
};

const goToDeleteUser = () => {
    router.push({ name: 'DeleteUser', params: { id: props.id } });
};

const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    if (isNaN(date)) return dateString;

    const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
    return date.toLocaleDateString('vi-VN', options);
};

// Hàm tải dữ liệu người dùng
const fetchUser = async () => {
    loading.value = true;
    error.value = null;
    try {
        const response = await axios.get(`${API_BASE_URL}/api/users/${props.id}`);
        user.value = response.data;
    } catch (err) {
        if (err.response && err.response.status === 404) {
            error.value = `Không tìm thấy người dùng với ID: ${props.id}.`;
        } else {
            error.value = 'Lỗi khi tải dữ liệu. Vui lòng kiểm tra kết nối API Back-end.';
            console.error(err);
        }
    } finally {
        loading.value = false;
    }
};

onMounted(() => {
    fetchUser();
});

watch(() => props.id, () => {
    fetchUser();
});

</script>