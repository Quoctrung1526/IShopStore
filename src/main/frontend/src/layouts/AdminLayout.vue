<template>
    <div>
        <Navbar />

        <div id="layoutSidenav">
            <Sidebar />

            <div id="layoutSidenav_content">
                <main>
                    <router-view></router-view>
                </main>
                <Footer />

            </div>
        </div>
    </div>
</template>

<script>
import Sidebar from '@/components/admin/Sidebar.vue'
import Navbar from '@/components/admin/Navbar.vue'
import Footer from '@/components/admin/Footer.vue'

export default {
    components: { Sidebar, Navbar, Footer }
}
</script>

<style>
#layoutSidenav {
    padding-top: 56px;
    display: flex;
    position: relative;
}

#layoutSidenav_nav {
    width: 225px;
    flex-shrink: 0;
}

#layoutSidenav_content {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    min-width: 0;
    min-height: 100vh;
    padding-top: 1.5rem !important;
}

#layoutSidenav_content main {
    flex: 1 0 auto;
}
</style>