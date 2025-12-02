<template>
    <div class="container-fluid px-4">
        <h1 class="mt-4">Trang chủ</h1>
        <ol class="breadcrumb mb-4">
            <li class="breadcrumb-item active">Trang chủ</li>
        </ol>

        <div class="row">
            <div class="col-xl-3 col-md-6 mb-4" v-for="card in statsCards" :key="card.title">
                <div class="card" :class="card.bgClass">
                    <div class="card-body">
                        <div class="card-title">{{ card.title }}</div>
                        <div class="display-6">{{ card.value }}</div>
                    </div>
                    <div class="card-footer d-flex align-items-center justify-content-between">
                        <a class="small text-white stretched-link" href="#">Xem chi tiết</a>
                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-xl-6">
                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-chart-area me-1"></i>
                        Biểu đồ doanh thu
                    </div>
                    <div class="card-body">
                        <canvas ref="areaChart" height="150"></canvas>
                    </div>
                </div>
            </div>
            <div class="col-xl-6">
                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-chart-bar me-1"></i>
                        Biểu đồ đơn hàng
                    </div>
                    <div class="card-body">
                        <canvas ref="barChart" height="150"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Dashboard',
    // Đã loại bỏ components: { Navbar, Sidebar, Footer }
    data() {
        return {
            statsCards: [
                { title: 'Người dùng', value: '1,234', bgClass: 'bg-primary text-white' },
                { title: 'Đơn hàng', value: '856', bgClass: 'bg-warning text-white' },
                { title: 'Doanh thu', value: '125M', bgClass: 'bg-success text-white' },
                { title: 'Sản phẩm', value: '432', bgClass: 'bg-danger text-white' }
            ]
        }
    },
    mounted() {
        this.loadExternalScripts()
    },
    methods: {
        loadExternalScripts() {
            // Tải Bootstrap và FontAwesome (Có thể chuyển sang AdminLayout nếu muốn)
            if (!document.querySelector('link[href*="bootstrap"]')) {
                const bootstrap = document.createElement('link')
                bootstrap.rel = 'stylesheet'
                bootstrap.href = 'https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css'
                document.head.appendChild(bootstrap)
            }
            if (!document.querySelector('script[src*="fontawesome"]')) {
                const fontawesome = document.createElement('script')
                fontawesome.src = 'https://use.fontawesome.com/releases/v6.3.0/js/all.js'
                document.head.appendChild(fontawesome)
            }

            // Tải Chart.js
            if (!window.Chart) {
                const chartjs = document.createElement('script')
                chartjs.src = 'https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js'
                // Gọi createCharts sau khi Chart.js đã tải xong
                chartjs.onload = () => this.createCharts()
                document.head.appendChild(chartjs)
            } else {
                this.createCharts()
            }
        },
        createCharts() {
            // Đảm bảo DOM đã cập nhật trước khi cố gắng truy cập $refs
            this.$nextTick(() => {
                // Kiểm tra Chart.js đã load chưa
                if (!window.Chart) return;

                // Biểu đồ Doanh thu (Area Chart)
                if (this.$refs.areaChart) {
                    const ctx1 = this.$refs.areaChart.getContext('2d')
                    new window.Chart(ctx1, { // Dùng window.Chart để đảm bảo truy cập đúng
                        type: 'line',
                        data: {
                            labels: ['T1', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'T8', 'T9', 'T10', 'T11', 'T12'],
                            datasets: [{
                                label: 'Doanh thu (triệu đồng)',
                                data: [120, 190, 150, 250, 220, 300, 280, 320, 270, 350, 400, 450],
                                backgroundColor: 'rgba(2,117,216,0.2)',
                                borderColor: 'rgba(2,117,216,1)',
                                fill: true
                            }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false
                        }
                    })
                }

                // Biểu đồ Đơn hàng (Bar Chart)
                if (this.$refs.barChart) {
                    const ctx2 = this.$refs.barChart.getContext('2d')
                    new window.Chart(ctx2, { // Dùng window.Chart để đảm bảo truy cập đúng
                        type: 'bar',
                        data: {
                            labels: ['Quý 1', 'Quý 2', 'Quý 3', 'Quý 4'],
                            datasets: [{
                                label: 'Số đơn hàng',
                                data: [450, 520, 480, 610],
                                backgroundColor: 'rgba(2,117,216,1)'
                            }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false
                        }
                    })
                }
            })
        }
    }
}
</script>

<style scoped>
.card-title {
    font-size: 0.875rem;
    font-weight: 600;
    margin-bottom: 0.5rem;
}

.display-6 {
    font-size: 2rem;
    font-weight: 300;
}
</style>