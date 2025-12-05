<template>
    <div>
        <!-- Spinner Start -->
        <div id="spinner"
            class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
            <div class="spinner-grow text-primary" role="status"></div>
        </div>
        <!-- Spinner End -->

        <!-- Navbar start -->
        <div class="container-fluid fixed-top">
            <div class="container topbar bg-primary d-none d-lg-block">
                <div class="d-flex justify-content-between">
                    <div class="top-info ps-2">
                        <small class="me-3"><i class="fas fa-map-marker-alt me-2 text-secondary"></i>
                            <a href="" class="text-white">123 Lê Trọng Tấn, Hồ Chí Minh</a></small>
                        <small class="me-3"><i class="fas fa-envelope me-2 text-secondary"></i><a href=""
                                class="text-white">cuahangdienmay@gmail.com</a></small>
                    </div>
                </div>
            </div>
            <div class="container px-0">
                <nav class="navbar navbar-light bg-white navbar-expand-xl">
                    <router-link to="/" class="navbar-brand">
                        <h1 class="text-primary display-6">Cửa Hàng Điện Máy</h1>
                    </router-link>
                    <button class="navbar-toggler py-2 px-3" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarCollapse">
                        <span class="fa fa-bars text-primary"></span>
                    </button>
                    <div class="collapse navbar-collapse bg-white" id="navbarCollapse">
                        <div class="navbar-nav mx-auto">
                            <router-link to="/" class="nav-item nav-link active">Home</router-link>
                            <a href="" class="nav-item nav-link">Shop</a>
                            <a href="" class="nav-item nav-link">Shop Detail</a>
                            <div class="nav-item dropdown">
                                <a href="" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Pages</a>
                                <div class="dropdown-menu m-0 bg-secondary rounded-0">
                                    <a href="" class="dropdown-item">Cart</a>
                                    <a href="" class="dropdown-item">Chackout</a>
                                    <a href="" class="dropdown-item">Testimonial</a>
                                    <a href="" class="dropdown-item">404 Page</a>
                                </div>
                            </div>
                            <a href="" class="nav-item nav-link">Contact</a>
                        </div>
                        <div class="d-flex m-3 me-0">
                            <button
                                class="btn-search btn border border-secondary btn-md-square rounded-circle bg-white me-4"
                                data-bs-toggle="modal" data-bs-target="#searchModal"><i
                                    class="fas fa-search text-primary"></i></button>
                            <a href="" class="position-relative me-4 my-auto">
                                <i class="fa fa-shopping-bag fa-2x"></i>
                                <span
                                    class="position-absolute bg-secondary rounded-circle d-flex align-items-center justify-content-center text-dark px-1"
                                    style="top: -5px; left: 15px; height: 20px; min-width: 20px;">3</span>
                            </a>
                            <a href="" class="my-auto">
                                <i class="fas fa-user fa-2x"></i>
                            </a>
                        </div>
                    </div>
                </nav>
            </div>
        </div>
        <!-- Navbar End -->

        <!-- Modal Search Start -->
        <div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-fullscreen">
                <div class="modal-content rounded-0">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Search by keyword</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body d-flex align-items-center">
                        <div class="input-group w-75 mx-auto d-flex">
                            <input type="search" class="form-control p-3" placeholder="keywords"
                                aria-describedby="search-icon-1">
                            <span id="search-icon-1" class="input-group-text p-3"><i class="fa fa-search"></i></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal Search End -->
    </div>
</template>

<script setup>
import { onMounted } from 'vue';

const loadSpinner = () => {
    // Khởi tạo Spinner
    if (document.getElementById('spinner')) {
        setTimeout(() => {
            document.getElementById('spinner').classList.remove('show');
        }, 1);
    }
};

onMounted(() => {
    loadSpinner();
});
</script>

<style scoped>
/* Định nghĩa màu sắc cơ bản */
:root {
    --bs-primary: #FFB536;
    --bs-secondary: #45474B;
    --bs-light: #F4F6F8;
    --bs-white: #FFFFFF;
    --bs-dark: #000000;
}

/* Spinner Styles */
#spinner {
    opacity: 0;
    visibility: hidden;
    transition: opacity .8s ease-out, visibility 0s linear .5s;
    z-index: 99999;
}

#spinner.show {
    transition: opacity .8s ease-out, visibility 0s linear .0s;
    visibility: visible;
    opacity: 1;
}

.spinner-grow {
    width: 3rem;
    height: 3rem;
    vertical-align: -0.125em;
    border-radius: 50%;
    animation: 0.75s linear infinite spinner-grow;
}

@keyframes spinner-grow {
    0% {
        transform: scale(0);
    }

    50% {
        opacity: 1;
        transform: none;
    }
}


/* Navbar Styling */
.topbar {
    padding: 20px;
    border-radius: 230px 100px;
}

.topbar .top-info {
    font-size: 15px;
    line-height: 0;
    letter-spacing: 1px;
    display: flex;
    align-items: center;
}

.topbar .top-link {
    font-size: 15px;
    line-height: 0;
    letter-spacing: 1px;
    display: flex;
    align-items: center;
}

.topbar .top-link a small:hover {
    color: var(--bs-secondary) !important;
    transition: 0.5s;
}

.topbar .top-link a small:hover i {
    color: var(--bs-primary) !important;
}

.fixed-top {
    transition: 0.5s;
    background: var(--bs-white);
    border: 0;
}

.navbar {
    height: 100px;
    border-bottom: 1px solid rgba(255, 255, 255, .1);
}

.navbar .navbar-nav .nav-link {
    padding: 10px 15px;
    font-size: 16px;
    transition: .5s;
}

.navbar .navbar-nav .nav-link:hover,
.navbar .navbar-nav .nav-link.active,
.fixed-top.bg-white .navbar .navbar-nav .nav-link:hover,
.fixed-top.bg-white .navbar .navbar-nav .nav-link.active {
    color: var(--bs-primary);
}

.navbar .dropdown-toggle::after {
    border: none;
    content: "\f107";
    font-family: "Font Awesome 5 Free";
    font-weight: 700;
    vertical-align: middle;
    margin-left: 8px;
}

@media (min-width: 1200px) {
    .navbar .nav-item .dropdown-menu {
        display: block;
        visibility: hidden;
        top: 100%;
        transform: rotateX(-75deg);
        transform-origin: 0% 0%;
        border: 0;
        transition: .5s;
        opacity: 0;
    }
}

.dropdown .dropdown-menu a:hover {
    background: var(--bs-secondary);
    color: var(--bs-primary);
}

.navbar .nav-item:hover .dropdown-menu {
    transform: rotateX(0deg);
    visibility: visible;
    background: var(--bs-light) !important;
    border-radius: 10px !important;
    transition: .5s;
    opacity: 1;
}

#searchModal .modal-content {
    background: rgba(255, 255, 255, .8);
}
</style>