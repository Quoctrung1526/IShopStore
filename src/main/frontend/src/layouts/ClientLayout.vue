<template>
    <div>

        <Navbar />

        <main class="client-main-content">
            <router-view></router-view>
        </main>
        <Footer />

        <Copyright />
    </div>
</template>

<script setup>
import Navbar from '@/components/client/Navbar.vue';
import Footer from '@/components/client/Footer.vue';
import Copyright from '@/components/client/Copyright.vue';
</script>

<style scoped>
.client-main-content {

    padding-top: 150px;
}
</style>