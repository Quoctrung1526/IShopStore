<template>
    <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
        <a class="navbar-brand ps-3" href="/admin/dashboard" @click.prevent="$emit('navigate', 'dashboard')">
            Cửa Hàng Điện Máy
        </a>

        <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" @click="$emit('toggle-sidebar')">
            <i class="fas fa-bars"></i>
        </button>

        <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0" @submit.prevent>
            <div class="input-group">
                <input class="form-control" type="text" placeholder="Search for..." v-model="searchText" />
                <button class="btn btn-primary" type="button">
                    <i class="fas fa-search"></i>
                </button>
            </div>
        </form>

        <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" @click.prevent="toggleMenu" role="button">
                    <i class="fas fa-user fa-fw"></i>
                </a>
                <ul class="dropdown-menu dropdown-menu-end" :class="{ show: showMenu }">
                    <li><a class="dropdown-item" href="#" @click.prevent="handleSettings">Cài đặt</a></li>
                    <li><a class="dropdown-item" href="#" @click.prevent="handleActivityLog">Nhật kí hoạt động</a></li>
                    <li>
                        <hr class="dropdown-divider" />
                    </li>
                    <li><a class="dropdown-item" href="#" @click.prevent="handleLogout">Đăng xuất</a></li>
                </ul>
            </li>
        </ul>
    </nav>
</template>

<script>
export default {
    name: 'Navbar',
    data() {
        return {
            searchText: '',
            showMenu: false
        }
    },
    mounted() {
        document.addEventListener('click', this.handleClickOutside)
    },
    beforeUnmount() {
        document.removeEventListener('click', this.handleClickOutside)
    },
    methods: {
        toggleMenu() {
            this.showMenu = !this.showMenu
        },
        handleClickOutside(event) {
            const dropdown = event.target.closest('.nav-item.dropdown')
            if (!dropdown && this.showMenu) {
                this.showMenu = false
            }
        },
        handleSettings() {
            alert('Cài đặt - Coming soon!')
            this.showMenu = false
        },
        handleActivityLog() {
            alert('Nhật kí hoạt động - Coming soon!')
            this.showMenu = false
        },
        handleLogout() {
            if (confirm('Bạn có chắc muốn đăng xuất?')) {
                alert('Đăng xuất thành công!')
            }
            this.showMenu = false
        }
    }
}
</script>

<style scoped>
.sb-topnav {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    height: 56px;
    z-index: 1039;
}

.dropdown-menu {
    position: absolute;
    top: 100%;
    right: 0;
    z-index: 1000;
    display: none;
    min-width: 10rem;
    padding: 0.5rem 0;
    margin: 0;
    font-size: 1rem;
    color: #212529;
    background-color: #fff;
    border: 1px solid rgba(0, 0, 0, 0.15);
    border-radius: 0.25rem;
}

.dropdown-menu.show {
    display: block;
}

.dropdown-item {
    display: block;
    width: 100%;
    padding: 0.25rem 1rem;
    color: #212529;
    text-decoration: none;
    cursor: pointer;
}

.dropdown-item:hover {
    background-color: #e9ecef;
}

.dropdown-divider {
    height: 0;
    margin: 0.5rem 0;
    border-top: 1px solid rgba(0, 0, 0, 0.15);
}
</style>