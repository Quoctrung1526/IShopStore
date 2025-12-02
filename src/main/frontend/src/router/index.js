import { createRouter, createWebHashHistory } from 'vue-router'
import AdminLayout from '@/layouts/AdminLayout.vue'
import Dashboard from '@/views/admin/dashboard/Dashboard.vue'
import Users from '@/views/admin/user/Users.vue'
import Products from '@/views/admin/product/Products.vue'
import Orders from '@/views/admin/order/Orders.vue'
import CreateUser from '@/views/admin/user/CreateUser.vue'
import DetailUser from '@/views/admin/user/DetailUser.vue'
import UpdateUser from '@/views/admin/user/UpdateUser.vue'
import DeleteUser from '@/views/admin/user/DeleteUser.vue'

const routes = [

    // Định tuyến mặc định (Redirect)
    {
        path: '/',
        redirect: '/admin/dashboard'
    },
    {
        path: '/admin',
        redirect: '/admin/dashboard'
    },
    {
        path: '/admin',
        component: AdminLayout,
        children: [
            // Trang chủ (Dashboard)
            {
                path: 'dashboard',
                name: 'Dashboard',
                component: Dashboard,
                meta: { title: 'Trang chủ' }
            },

            // QUẢN LÝ NGƯỜI DÙNG

            // 2.1. Danh sách Người dùng
            {
                path: 'users',
                name: 'User',
                component: Users,
                meta: { title: 'Quản lý Người dùng' },
            },

            // 2.2. Thêm mới Người dùng
            {
                path: 'users/createuser',
                name: 'CreateUser',
                component: CreateUser,
                meta: { title: 'Tạo Người dùng mới' }
            },

            // 2.3. Xem Chi tiết Người dùng (Route động)
            {
                path: 'users/:id',
                name: 'DetailUser',
                component: DetailUser,
                props: true, // Cho phép truyền params 'id' vào props
                meta: { title: 'Chi tiết Người dùng' }
            },

            // 2.4. Cập nhật Người dùng (Route động)
            {
                path: 'users/:id/edit',
                name: 'UpdateUser',
                component: UpdateUser,
                props: true,
                meta: { title: 'Cập nhật Người dùng' }
            },

            // 2.5. Xóa Người dùng (Route động)
            {
                path: 'users/:id/delete',
                name: 'DeleteUser',
                component: DeleteUser,
                props: true,
                meta: { title: 'Xác nhận xóa Người dùng' }
            },

            // QUẢN LÝ SẢN PHẨM
            {
                path: 'products',
                name: 'Products',
                component: Products,
                meta: { title: 'Sản phẩm' }
            },

            // QUẢN LÝ ĐƠN HÀNG
            {
                path: 'orders',
                name: 'Orders',
                component: Orders,
                meta: { title: 'Đơn hàng' }
            }
        ]
    },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router