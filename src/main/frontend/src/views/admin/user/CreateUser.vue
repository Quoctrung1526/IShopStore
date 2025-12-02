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

        <div v-if="alert.message" :class="`alert alert-${alert.type}`" role="alert">
            {{ alert.message }}
        </div>

        <div class="card mb-4">
            <div class="card-header">
                <i class="fas fa-user-plus me-1"></i>
                Thông tin người dùng
            </div>
            <div class="card-body">
                <form @submit.prevent="submitForm">
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label for="inputName" class="form-label">Tên đầy đủ</label>
                            <input v-model="form.fullName" type="text" class="form-control" id="inputName" required>
                        </div>
                        <div class="col-md-6">
                            <label for="inputEmail" class="form-label">Địa chỉ Email</label>
                            <input v-model="form.email" type="email" class="form-control" id="inputEmail" required>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label for="inputPassword" class="form-label">Mật khẩu</label>
                            <input v-model="form.password" type="password" class="form-control" id="inputPassword"
                                required>
                        </div>
                        <div class="col-md-6">
                            <label for="inputRole" class="form-label">Vai trò</label>
                            <select v-model="form.role" class="form-select" id="inputRole" required>
                                <option value="USER">User</option>
                                <option value="ADMIN">Admin</option>
                            </select>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label for="inputPhone" class="form-label">Số điện thoại</label>
                            <input v-model="form.phone" type="text" class="form-control" id="inputPhone">
                        </div>
                        <div class="col-md-6">
                            <label for="inputAddress" class="form-label">Địa chỉ</label>
                            <input v-model="form.address" type="text" class="form-control" id="inputAddress">
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary" :disabled="loading">
                        <i v-if="loading" class="fas fa-spinner fa-spin me-1"></i>
                        <i v-else class="fas fa-save me-1"></i>
                        {{ loading ? 'Đang xử lý...' : 'Lưu người dùng' }}
                    </button>
                    <button type="button" @click="goBack" class="btn btn-secondary ms-2" :disabled="loading">
                        <i class="fas fa-times me-1"></i> Trở về
                    </button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:9090';

export default {
    name: 'CreateUser',
    data() {
        return {
            // Đã đổi tên `user` thành `form` để khớp với template VUE hiện tại
            form: {
                fullName: '',
                email: '',
                password: '',
                role: 'USER',
                phone: '',
                address: '',
            },
            avatarFile: null,
            avatarPreview: null,
            loading: false, // submit loading
            alert: { type: '', message: '' },
        };
    },
    methods: {
        // ... (Giữ nguyên showAlert, isValidEmail, previewAvatar, resetForm, goBack)
        showAlert(type, message) {
            this.alert = { type, message };
            setTimeout(() => (this.alert = { type: '', message: '' }), 3500);
        },

        isValidEmail(val) {
            return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(val);
        },

        // ... (Bạn nên định nghĩa lại previewAvatar và resetForm ở đây)

        resetForm() {
            this.form = {
                fullName: '',
                email: '',
                password: '',
                role: 'USER',
                phone: '',
                address: '',
            };
            if (this.avatarPreview) URL.revokeObjectURL(this.avatarPreview);
            this.avatarPreview = null;
            this.avatarFile = null;
        },

        goBack() {
            this.$router.push('/admin/users');
        },

        // ====== CHỨC NĂNG REFTECH ẨN ======
        async triggerUserListRefetch() {
            try {
                // Gửi yêu cầu GET tới API danh sách người dùng (Refetch)
                // Mục đích là để kích hoạt cache hoặc cập nhật data ở nơi khác
                await axios.get(`${API_BASE_URL}/api/users`);
                console.log('Đã kích hoạt refetch danh sách người dùng.');
            } catch (e) {
                console.error('Lỗi khi kích hoạt refetch:', e);
            }
        },

        // ====== SUBMIT: LƯU DB + REFETCH ======
        async submitForm() {
            // Validate tối thiểu
            if (!this.form.fullName || !this.form.email || !this.form.password || !this.form.role) {
                this.showAlert('warning', 'Vui lòng điền đầy đủ Họ tên, Email, Mật khẩu, Role.');
                return;
            }
            if (!this.isValidEmail(this.form.email)) {
                this.showAlert('warning', 'Email không hợp lệ.');
                return;
            }
            if (this.form.password.length < 6) {
                this.showAlert('warning', 'Mật khẩu tối thiểu 6 ký tự.');
                return;
            }

            this.loading = true;
            try {
                // JSON phần "user" (username dùng email)
                const payload = {
                    fullName: this.form.fullName,
                    email: this.form.email,
                    username: this.form.email,
                    password: this.form.password,
                    role: this.form.role,
                    phone: this.form.phone,
                    address: this.form.address,
                };

                const fd = new FormData();
                const userBlob = new Blob([JSON.stringify(payload)], { type: 'application/json' });
                fd.append('user', userBlob);
                if (this.avatarFile) fd.append('avatar', this.avatarFile);

                await axios.post(`${API_BASE_URL}/api/users`, fd, {
                    headers: { 'Content-Type': 'multipart/form-data' },
                });

                this.showAlert('success', `Tạo người dùng thành công.`);

                // Kích hoạt Refetch ẩn
                await this.triggerUserListRefetch();

                // Reset form
                this.resetForm();
            } catch (err) {
                const msg =
                    err?.response?.data?.message ||
                    (err?.response?.status === 409
                        ? 'Email hoặc tên đăng nhập đã tồn tại.'
                        : err?.message || 'Có lỗi xảy ra, vui lòng thử lại.');
                this.showAlert('danger', msg);
            } finally {
                this.loading = false;
            }
        },
    },
};
</script>