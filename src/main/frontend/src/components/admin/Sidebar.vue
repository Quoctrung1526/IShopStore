<template>
    <div id="layoutSidenav_nav">
        <nav class="sb-sidenav accordion sb-sidenav-dark">
            <div class="sb-sidenav-menu">
                <div class="nav">
                    <div class="sb-sidenav-menu-heading">BẢNG ĐIỀU KHIỂN</div>

                    <router-link to="/admin/dashboard" class="nav-link"
                        :class="{ active: $route.path === '/admin/dashboard' }">
                        <div class="sb-nav-link-icon">
                            <i class="fas fa-tachometer-alt"></i>
                        </div>
                        Trang chủ
                    </router-link>

                    <router-link to="/admin/users" class="nav-link" :class="{ active: $route.path === '/admin/users' }">
                        <div class="sb-nav-link-icon">
                            <i class="fas fa-box"></i>
                        </div>
                        Người dùng
                    </router-link>

                    <router-link to="/admin/products" class="nav-link"
                        :class="{ active: $route.path === '/admin/products' }">
                        <div class="sb-nav-link-icon">
                            <i class="fas fa-box"></i>
                        </div>
                        Sản phẩm
                    </router-link>

                    <router-link to="/admin/orders" class="nav-link"
                        :class="{ active: $route.path === '/admin/orders' }">
                        <div class="sb-nav-link-icon">
                            <i class="fas fa-shopping-cart"></i>
                        </div>
                        Đơn hàng
                    </router-link>
                </div>
            </div>
        </nav>

        <div class="sb-sidenav-footer">
            <div class="small">Đăng nhập bởi:</div>
            Admin User
        </div>
    </div>
</template>

<script>
export default {
    name: 'Sidebar'
}
</script>

<style scoped>
#layoutSidenav_nav {
    display: flex;
    flex-direction: column;
    flex-shrink: 0;
    grid-area: sidenav;
    width: 225px;
    background-color: #212529;
    min-height: calc(100vh - 56px);
}

.sb-sidenav {
    display: flex;
    flex-direction: column;
    height: 100%;
}

.sb-sidenav-dark {
    background-color: #212529;
    color: rgba(255, 255, 255, 0.5);
}

.sb-sidenav-menu {
    flex: 1;
    overflow-y: auto;
    overflow-x: hidden;
    padding-bottom: 60px;
}

.sb-sidenav-menu .nav {
    flex-direction: column;
}

.sb-sidenav-menu-heading {
    padding: 1.75rem 1rem 0.75rem;
    font-size: 0.75rem;
    font-weight: bold;
    text-transform: uppercase;
    color: rgba(255, 255, 255, 0.4);
}

.nav-link {
    display: flex;
    align-items: center;
    padding: 0.75rem 1rem;
    color: rgba(255, 255, 255, 0.5);
    text-decoration: none;
    cursor: pointer;
    transition: all 0.2s;
}

.nav-link:hover {
    color: #fff;
    background-color: rgba(255, 255, 255, 0.075);
}

.nav-link.active {
    color: #fff;
    background-color: rgba(255, 255, 255, 0.1);
    border-left: 3px solid #0d6efd;
}

.sb-nav-link-icon {
    margin-right: 0.5rem;
    width: 20px;
    text-align: center;
}

.sb-sidenav-footer {
    padding: 0.75rem 1rem;
    background-color: #343a40;
    color: rgba(255, 255, 255, 0.5);
    position: absolute;
    bottom: 0;
    left: 0;
    width: 225px;
}
</style>