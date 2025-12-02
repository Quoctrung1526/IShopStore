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

        <!-- Trạng thái Loading -->
        <div v-if="loading" class="text-center p-5">
            <i class="fas fa-spinner fa-spin fa-2x text-primary"></i>
            <p class="mt-2 text-muted">Đang tải thông tin người dùng...</p>
        </div>

        <!-- Trạng thái Lỗi -->
        <div v-else-if="error" class="alert alert-danger" role="alert">
            <h4 class="alert-heading">Lỗi!</h4>
            <p>{{ error }}</p>
            <button @click="fetchUser" class="btn btn-sm btn-outline-danger">
                <i class="fas fa-redo me-1"></i> Tải lại
            </button>
        </div>

        <!-- Hiển thị Chi tiết Người dùng -->
        <div v-else class="card mb-4 shadow-sm">
            <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                <span><i class="fas fa-user me-1"></i> Thông tin chi tiết Người dùng #{{ user.id }}</span>
            </div>
            <div class="card-body">
                <div class="row">
                    <!-- Cột Ảnh Avatar và Tên -->
                    <div class="col-md-4 text-center mb-4 border-end">
                        <img :src="user.avatarUrl ? user.avatarUrl : 'https://placehold.co/150x150/007bff/FFFFFF/png?text=AVATAR'"
                            alt="Avatar" class="img-thumbnail rounded-circle mb-3"
                            style="width: 150px; height: 150px; object-fit: cover;">
                        <h3 class="fw-bold text-dark">{{ user.fullName }}</h3>

                        <!-- ĐÃ SỬA: SỬ DỤNG LOGIC DƯỚI ĐÂY -->
                        <span v-if="user.role && user.role.name" class="badge fs-6 py-2 px-3"
                            :class="user.role.name === 'ADMIN' ? 'bg-danger' : 'bg-secondary'">
                            {{ user.role.name }}
                        </span>
                        <span v-else class="badge fs-6 py-2 px-3 bg-warning">Chưa xác định</span>
                        <!-- KẾT THÚC SỬA LỖI -->

                    </div>

                    <!-- Cột Thông tin chi tiết -->
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
                            <!-- Nút Sửa: Link tới trang chỉnh sửa (Cần định nghĩa route EditUser) -->
                            <button @click="goToEditUser" class="btn btn-warning text-white me-2">
                                <i class="fas fa-edit me-1"></i> Chỉnh sửa
                            </button>

                            <!-- Nút Trở về -->
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

// Lấy API Base URL từ .env file
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '';

// Định nghĩa Props nhận ID từ Router (Đã cấu hình props: true trong router/index.js)
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

// Methods
const goBack = () => {
    router.push('/admin/users');
};

const goToEditUser = () => {
    // Điều hướng đến Route chỉnh sửa (ví dụ: /admin/users/123/edit)
    router.push(`/admin/users/${props.id}/edit`);
};

const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    // Thêm trường hợp nếu backend trả về timestamp
    const date = new Date(dateString);
    if (isNaN(date)) return dateString; // Nếu không phải ngày hợp lệ, trả về chuỗi gốc

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

// Lifecycle Hooks: Tải dữ liệu khi component được mount
onMounted(() => {
    fetchUser();
});

// Watch: Tải lại dữ liệu nếu ID trong URL thay đổi
watch(() => props.id, () => {
    fetchUser();
});

</script>