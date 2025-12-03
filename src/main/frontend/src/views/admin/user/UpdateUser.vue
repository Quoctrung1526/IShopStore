<template>
    <div class="container-fluid px-4">
        <h1 class="mt-4">Cập nhật Người dùng: {{ form.fullName }}</h1>
        <ol class="breadcrumb mb-4">
            <li class="breadcrumb-item">
                <router-link to="/admin/dashboard">Trang chủ</router-link>
            </li>
            <li class="breadcrumb-item">
                <router-link to="/admin/users">Người dùng</router-link>
            </li>
            <li class="breadcrumb-item active">Cập nhật (ID: {{ id }})</li>
        </ol>

        <div v-if="loadingInitial" class="text-center p-5">
            <i class="fas fa-spinner fa-spin fa-2x text-primary"></i>
            <p class="mt-2 text-muted">Đang tải thông tin người dùng...</p>
        </div>

        <div v-if="alert.message" :class="`alert alert-${alert.type}`" role="alert">
            {{ alert.message }}
        </div>

        <div v-else class="card mb-4">
            <div class="card-header bg-warning text-white">
                <i class="fas fa-edit me-1"></i>
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
                            <label for="inputEmail" class="form-label">Địa chỉ Email (Không thể thay đổi)</label>
                            <input v-model="form.email" type="email" class="form-control bg-light" id="inputEmail"
                                readonly>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label for="inputPassword" class="form-label">Mật khẩu (Để trống nếu không muốn thay
                                đổi)</label>
                            <input v-model="form.password" type="password" class="form-control" id="inputPassword"
                                placeholder="Nhập mật khẩu mới (Tối thiểu 6 ký tự)">
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

                    <div class="row mb-4">
                        <div class="col-md-6">
                            <label for="inputAvatar" class="form-label">Cập nhật Ảnh đại diện</label>
                            <input @change="previewAvatar" type="file" class="form-control" id="inputAvatar"
                                accept="image/*">
                        </div>
                        <div class="col-md-6 d-flex align-items-end">
                            <div v-if="avatarPreview || form.avatarUrl">
                                <span class="d-block mb-1 text-muted small">Ảnh hiện tại/Xem trước:</span>

                                <img :src="avatarPreview || getAvatarUrl(form.avatarUrl)" alt="Avatar Preview"
                                    class="img-thumbnail"
                                    style="width: 100px; height: 100px; object-fit: cover; border-radius: 50%;">

                            </div>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-warning text-white" :disabled="loadingSubmit">
                        <i v-if="loadingSubmit" class="fas fa-spinner fa-spin me-1"></i>
                        <i v-else class="fas fa-save me-1"></i>
                        {{ loadingSubmit ? 'Đang cập nhật...' : 'Lưu thay đổi' }}
                    </button>
                    <button type="button" @click="goBack" class="btn btn-secondary ms-2" :disabled="loadingSubmit">
                        <i class="fas fa-times me-1"></i> Hủy
                    </button>
                </form>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '';

// Nhận ID từ Route
const props = defineProps({
    id: { type: [String, Number], required: true }
});

// State
const form = reactive({
    id: null,
    fullName: '',
    email: '',
    password: '', // Chỉ gửi khi có giá trị
    role: 'USER', // Khởi tạo thành chuỗi để khớp với <select>
    phone: '',
    address: '',
    avatarUrl: '' // URL ảnh cũ
});
const avatarFile = ref(null);
const avatarPreview = ref(null);
const loadingInitial = ref(true); // Loading khi tải dữ liệu ban đầu
const loadingSubmit = ref(false); // Loading khi submit form
const alert = reactive({ type: '', message: '' });

const getAvatarUrl = (avatarPath) => {
    if (!avatarPath) {
        return 'https://placehold.co/100x100/007bff/FFFFFF/png?text=AVATAR';
    }

    if (avatarPath.startsWith('http')) {
        return avatarPath;
    }

    const backendBase = "http://localhost:9090";
    const cleanPath = avatarPath.startsWith('/') ? avatarPath.substring(1) : avatarPath;

    // Ví dụ: http://localhost:9090/upload-images/ten_file.jpg
    return `${backendBase}/${cleanPath}`;
};

// Methods
const showAlert = (type, message) => {
    alert.type = type;
    alert.message = message;
    setTimeout(() => { alert.type = ''; alert.message = ''; }, 3500);
};

const isValidEmail = (val) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(val);

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

const goBack = () => {
    router.push(`/admin/users/${props.id}`);
};

const fetchUser = async (userId) => {
    loadingInitial.value = true;
    try {
        const response = await axios.get(`${API_BASE_URL}/api/users/${userId}`);
        const data = response.data;

        form.id = data.id;
        form.fullName = data.fullName;
        form.email = data.email;

        //Gán chuỗi tên vai trò để khớp với <select>
        form.role = data.role ? data.role.name : 'USER';

        form.phone = data.phone || '';
        form.address = data.address || '';
        form.avatarUrl = data.avatarUrl;
        form.password = '';

    } catch (err) {
        showAlert('danger', `Lỗi tải dữ liệu: ${err.response?.data?.message || err.message}`);
    } finally {
        loadingInitial.value = false;
    }
};

const submitForm = async () => {
    //Mật khẩu chỉ cần validate nếu người dùng có nhập
    if (form.password && form.password.length < 6) {
        showAlert('warning', 'Mật khẩu mới tối thiểu 6 ký tự.');
        return;
    }

    loadingSubmit.value = true;
    try {
        const payload = {
            id: form.id,
            fullName: form.fullName,
            // KHÔNG GỬI EMAIL VÌ NÓ LÀ READONLY
            password: form.password || undefined, // Chỉ gửi password nếu có giá trị

            // Vì Form đang dùng chuỗi 'ADMIN'/'USER', chúng ta phải chuyển nó thành ID
            role: {
                id: form.role === 'ADMIN' ? 1 : 2
            },

            phone: form.phone,
            address: form.address,
        };

        const fd = new FormData();
        const userBlob = new Blob([JSON.stringify(payload)], { type: 'application/json' });
        fd.append('user', userBlob);

        // Thêm file avatar mới (nếu có)
        if (avatarFile.value) {
            fd.append('avatar', avatarFile.value);
        }

        //Gửi Request PUT/PATCH (Giả sử Back-end dùng PUT)
        await axios.put(`${API_BASE_URL}/api/users/${form.id}`, fd, {
        });

        showAlert('success', `Cập nhật người dùng ID #${form.id} thành công.`);

        // Quay lại trang chi tiết
        setTimeout(() => goBack(), 1000);

    } catch (err) {
        //Xử lý lỗi API
        const msg = err?.response?.data?.message || err?.message || 'Có lỗi xảy ra, vui lòng thử lại.';
        showAlert('danger', msg);
    } finally {
        loadingSubmit.value = false;
    }
};


onMounted(() => {
    fetchUser(props.id);
});

watch(() => props.id, (newId) => {
    fetchUser(newId);
});

</script>