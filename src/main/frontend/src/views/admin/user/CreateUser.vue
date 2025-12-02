<template>
    <div class="container-fluid px-4">
        <h1 class="mt-4">Thêm người dùng mới</h1>
        <ol class="breadcrumb mb-4">
            <li class="breadcrumb-item">
                <router-link to="/admin/dashboard">Trang chủ</router-link>
            </li>
            <li class="breadcrumb-item">
                <router-link to="/admin/users">Người dùng</router-link>
            </li>
            <li class="breadcrumb-item active">Thêm mới</li>
        </ol>

        <div v-if="loading" class="text-center p-5">
            <i class="fas fa-spinner fa-spin fa-2x text-primary"></i>
            <p class="mt-2 text-muted">Đang tải form...</p>
        </div>

        <div v-else-if="error" class="alert alert-danger">{{ error }}</div>

        <div v-else class="card shadow-sm mb-4">
            <div class="card-header bg-dark text-white">
                <i class="fas fa-user-plus me-1"></i> Thông tin người dùng
            </div>
            <div class="card-body">
                <form @submit.prevent="createUser">

                    <!-- Tên đầy đủ -->
                    <div class="mb-3">
                        <label for="fullName" class="form-label">Tên đầy đủ</label>
                        <input type="text" class="form-control" id="fullName" v-model="user.fullName" required>
                    </div>

                    <!-- Địa chỉ Email -->
                    <div class="mb-3">
                        <label for="email" class="form-label">Địa chỉ Email</label>
                        <input type="email" class="form-control" id="email" v-model="user.email" required>
                    </div>

                    <!-- Mật khẩu -->
                    <div class="mb-3">
                        <label for="password" class="form-label">Mật khẩu</label>
                        <input type="password" class="form-control" id="password" v-model="user.password" required>
                    </div>

                    <!-- Vai trò (Role) -->
                    <div class="mb-3">
                        <label for="role" class="form-label">Vai trò</label>
                        <!-- Gán giá trị vào selectedRoleId, là ID của Role -->
                        <select class="form-select" id="role" v-model="selectedRoleId" required>
                            <!-- Giá trị của option là ID (1, 2, ...) -->
                            <option v-for="role in availableRoles" :key="role.id" :value="role.id">
                                {{ role.name }}
                            </option>
                        </select>
                    </div>

                    <!-- Số điện thoại (Tùy chọn) -->
                    <div class="mb-3">
                        <label for="phone" class="form-label">Số điện thoại</label>
                        <input type="text" class="form-control" id="phone" v-model="user.phone">
                    </div>

                    <!-- Địa chỉ (Tùy chọn) -->
                    <div class="mb-3">
                        <label for="address" class="form-label">Địa chỉ</label>
                        <input type="text" class="form-control" id="address" v-model="user.address">
                    </div>

                    <!-- Avatar (File upload và Preview) -->
                    <div class="row mb-4">
                        <div class="col-md-6">
                            <label for="avatar" class="form-label">Ảnh đại diện</label>
                            <input type="file" class="form-control" id="avatar" @change="previewAvatar"
                                accept="image/*">
                        </div>
                        <div class="col-md-6 d-flex align-items-center">
                            <div v-if="avatarPreview">
                                <span class="d-block mb-1 text-muted small">Xem trước:</span>
                                <img :src="avatarPreview" alt="Avatar Preview" class="img-thumbnail rounded-circle"
                                    style="width: 50px; height: 50px; object-fit: cover;">
                            </div>
                        </div>
                    </div>


                    <button type="submit" class="btn btn-primary me-2">
                        <i class="fas fa-save me-1"></i> Lưu người dùng
                    </button>
                    <button type="button" class="btn btn-secondary" @click="goBack">
                        <i class="fas fa-arrow-left me-1"></i> Trở về
                    </button>
                </form>
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

// Dữ liệu người dùng mới
const user = ref({
    fullName: '',
    email: '',
    password: '',
    phone: '',
    address: '',
    // role sẽ được set khi submit
    role: null,
    // avatarUrl: null // Không cần avatarUrl cho người dùng mới
});

// NEW STATE for Avatar
const avatarFile = ref(null);
const avatarPreview = ref(null);

// State cho việc chọn Role ID từ dropdown
const selectedRoleId = ref(null); // Bắt đầu là null

// Danh sách các vai trò có sẵn (Tải từ API)
const availableRoles = ref([]);

const loading = ref(true); // Đặt loading là true ban đầu để chờ tải roles
const error = ref(null);

// NEW METHOD: Preview Avatar
const previewAvatar = (event) => {
    const file = event.target.files[0];
    avatarFile.value = file;
    if (file) {
        if (avatarPreview.value) URL.revokeObjectURL(avatarPreview.value);
        avatarPreview.value = URL.createObjectURL(file);
    } else {
        avatarPreview.value = null;
    }
};

// NEW METHOD: Tải danh sách Roles từ Back-end
const fetchAvailableRoles = async () => {
    loading.value = true;
    error.value = null;
    try {
        // Giả định bạn có API để lấy danh sách Roles
        // Cần phải tạo endpoint này trong Spring Boot (ví dụ: /api/roles)
        const response = await axios.get(`${API_BASE_URL}/api/roles`);
        availableRoles.value = response.data;

        // Thiết lập ID Role mặc định là ID đầu tiên hoặc ID của USER
        if (availableRoles.value.length > 0) {
            // Giả sử ID=2 là USER, nếu không có, lấy ID đầu tiên
            const defaultUserRole = availableRoles.value.find(r => r.name === 'USER');
            selectedRoleId.value = defaultUserRole ? defaultUserRole.id : availableRoles.value[0].id;
        }

    } catch (err) {
        error.value = 'Lỗi tải danh sách vai trò. Vui lòng kiểm tra API /api/roles.';
        console.error('Error fetching roles:', err);
    } finally {
        loading.value = false;
    }
};

// Xử lý khi người dùng ấn nút Lưu
const createUser = async () => {
    error.value = null;
    // Basic validation
    if (!user.value.fullName || !user.value.email || !user.value.password || !selectedRoleId.value) {
        alert('Vui lòng điền đầy đủ thông tin bắt buộc và chọn vai trò.');
        return;
    }

    loading.value = true;

    try {
        // 1. Prepare JSON Payload
        const payload = {
            fullName: user.value.fullName,
            email: user.value.email,
            password: user.value.password,
            phone: user.value.phone,
            address: user.value.address,
            // Gửi Role dưới dạng đối tượng { id: ... } để Jackson/JPA hiểu
            role: { id: selectedRoleId.value },
        };

        // 2. Prepare FormData for Multipart request
        const fd = new FormData();
        // Append user JSON data as a Blob
        const userBlob = new Blob([JSON.stringify(payload)], { type: 'application/json' });
        fd.append('user', userBlob);

        // Append avatar file if selected
        if (avatarFile.value) {
            fd.append('avatar', avatarFile.value);
        }

        // 3. Send Request
        await axios.post(`${API_BASE_URL}/api/users`, fd);

        // Thông báo thành công và chuyển hướng
        alert('Thêm người dùng mới thành công!');
        router.push('/admin/users');

    } catch (err) {
        const errorMessage =
            err.response?.data?.message ||
            `Lỗi HTTP: ${err.response?.status} - ${err.message}` ||
            'Lỗi khi thêm người dùng mới.';

        error.value = errorMessage;
        console.error('Error creating user:', err);
    } finally {
        loading.value = false;
    }
};

const goBack = () => {
    router.push('/admin/users');
};

// Lifecycle Hook
onMounted(() => {
    fetchAvailableRoles();
});

</script>
