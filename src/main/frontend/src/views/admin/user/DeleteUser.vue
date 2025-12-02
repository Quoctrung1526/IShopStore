<template>
    <div class="container-fluid px-4">
        <h1 class="mt-4">Xác nhận Xóa Người dùng</h1>
        <ol class="breadcrumb mb-4">
            <li class="breadcrumb-item">
                <router-link to="/admin/dashboard">Trang chủ</router-link>
            </li>
            <li class="breadcrumb-item">
                <router-link to="/admin/users">Người dùng</router-link>
            </li>
            <li class="breadcrumb-item active">Xóa (ID: {{ id }})</li>
        </ol>

        <!-- Trạng thái Loading -->
        <div v-if="loadingInitial" class="text-center p-5">
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

        <!-- Thông báo Alert -->
        <div v-if="alert.message" :class="`alert alert-${alert.type}`" role="alert">
            {{ alert.message }}
        </div>

        <!-- Nội dung xác nhận xóa -->
        <div v-else class="card mb-4 border-danger shadow-sm">
            <div class="card-header bg-danger text-white">
                <i class="fas fa-trash-alt me-1"></i>
                CẢNH BÁO: Thao tác này không thể hoàn tác
            </div>
            <div class="card-body">
                <h4 class="card-title text-danger mb-4">Bạn có chắc chắn muốn xóa người dùng này?</h4>

                <p>Thông tin người dùng sẽ bị xóa vĩnh viễn:</p>

                <div class="row">
                    <div class="col-md-6">
                        <table class="table table-bordered table-sm">
                            <tr>
                                <th>ID:</th>
                                <td>{{ user.id }}</td>
                            </tr>
                            <tr>
                                <th>Họ tên:</th>
                                <td>{{ user.fullName }}</td>
                            </tr>
                            <tr>
                                <th>Email:</th>
                                <td>{{ user.email }}</td>
                            </tr>
                            <tr>
                                <th>Vai trò:</th>
                                <td><span class="badge" :class="user.role === 'ADMIN' ? 'bg-danger' : 'bg-secondary'">{{
                                    user.role }}</span></td>
                            </tr>
                        </table>
                    </div>
                    <div class="col-md-6 text-center">
                        <img :src="user.avatarUrl ? user.avatarUrl : 'https://placehold.co/150x150/dc3545/FFFFFF/png?text=DELETE'"
                            alt="Avatar" class="img-thumbnail rounded-circle"
                            style="width: 150px; height: 150px; object-fit: cover;">
                        <p class="mt-2 text-danger">Người dùng sẽ bị xóa!</p>
                    </div>
                </div>

                <hr>

                <button @click="deleteUser" class="btn btn-danger me-2" :disabled="loadingSubmit">
                    <i v-if="loadingSubmit" class="fas fa-spinner fa-spin me-1"></i>
                    <i v-else class="fas fa-check me-1"></i>
                    {{ loadingSubmit ? 'Đang xóa...' : 'XÁC NHẬN XÓA' }}
                </button>
                <button @click="goBack" class="btn btn-secondary">
                    <i class="fas fa-times me-1"></i> Hủy
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '';

// Nhận ID từ Route
const props = defineProps({
    id: { type: [String, Number], required: true }
});

// State
const user = ref({});
const loadingInitial = ref(true);
const loadingSubmit = ref(false);
const error = ref(null);
const alert = reactive({ type: '', message: '' });

// Methods
const showAlert = (type, message) => {
    alert.type = type;
    alert.message = message;
    setTimeout(() => { alert.type = ''; alert.message = ''; }, 3500);
};

const goBack = () => {
    router.push(`/admin/users/${props.id}`); // Quay lại trang chi tiết hoặc danh sách
};

const fetchUser = async () => {
    loadingInitial.value = true;
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
        loadingInitial.value = false;
    }
};

const deleteUser = async () => {
    loadingSubmit.value = true;
    try {
        // Gửi Request DELETE
        await axios.delete(`${API_BASE_URL}/api/users/${props.id}`);

        // Xử lý thành công
        showAlert('success', `Đã xóa người dùng ID #${props.id} thành công. Đang chuyển hướng...`);

        // Chuyển hướng về trang danh sách người dùng sau khi xóa thành công
        setTimeout(() => {
            router.push('/admin/users');
        }, 1500);

    } catch (err) {
        // Xử lý lỗi API
        const msg = err?.response?.data?.message || err?.message || 'Có lỗi xảy ra khi xóa, vui lòng thử lại.';
        showAlert('danger', msg);
        loadingSubmit.value = false;
    }
};


// Lifecycle Hooks
onMounted(() => {
    fetchUser();
});
</script>