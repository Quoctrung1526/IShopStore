<template>
    <div>
        <!-- Copyright Start -->
        <div class="container-fluid copyright bg-dark py-4">
            <div class="container">
                <div class="row">
                    <div class="col-md-6 text-center text-md-start mb-3 mb-md-0">
                        <span class="text-light"><a href="javascript:void(0)"><i
                                    class="fas fa-copyright text-light me-2"></i>Your Site Name</a>, All right
                            reserved.</span>
                    </div>
                    <div class="col-md-6 my-auto text-center text-md-end text-white">
                        Designed By <a class="border-bottom" href="">HTML Codex</a>
                        Distributed By <a class="border-bottom" href="">ThemeWagon</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- Copyright End -->

        <!-- Back to Top -->
        <a href="javascript:void(0)" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
                class="fa fa-arrow-up"></i></a>
    </div>
</template>

<script setup>
</script>

<style scoped>
:root {
    --bs-primary: #FFB536;
    --bs-secondary: #45474B;
}

.back-to-top {
    position: fixed;
    right: 30px;
    bottom: 30px;
    display: none;
    width: 45px;
    height: 45px;
    align-items: center;
    justify-content: center;
    transition: 0.5s;
    z-index: 99;
}
</style>