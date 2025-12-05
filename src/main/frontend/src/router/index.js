import { createRouter, createWebHashHistory } from 'vue-router'

// Import Layouts
import ClientLayout from '@/layouts/ClientLayout.vue'
import AdminLayout from '@/layouts/AdminLayout.vue'

import HomeClient from '@/views/client/homeclient/HomeClient.vue' // Trang chủ mới (Điện Máy)

// Import Views Admin
import Dashboard from '@/views/admin/dashboard/Dashboard.vue'
import Users from '@/views/admin/user/Users.vue'
import CreateUser from '@/views/admin/user/CreateUser.vue'
import DetailUser from '@/views/admin/user/DetailUser.vue'
import UpdateUser from '@/views/admin/user/UpdateUser.vue'
import DeleteUser from '@/views/admin/user/DeleteUser.vue'
import Products from '@/views/admin/product/Products.vue'
import Orders from '@/views/admin/order/Orders.vue'


const routes = [
    {
        path: '/',
        component: ClientLayout,
        children: [
            {
                path: '', // Đường dẫn: /
                redirect: '/client/homeclient'
            },
            {
                path: 'client', // Đường dẫn: /client
                redirect: '/client/homeclient'
            },

            {
                path: 'client/homeclient',
                name: 'HomeClient',
                component: HomeClient,
                meta: { title: 'Trang chủ | Cửa Hàng Điện Máy' }
            },

        ]
    },

    {
        path: '/admin',
        component: AdminLayout,
        children: [
            {
                path: '', // Đường dẫn: /admin
                redirect: 'dashboard' // Redirect /admin về /admin/dashboard
            },

            {
                path: 'dashboard',
                name: 'Dashboard',
                component: Dashboard,
                meta: { title: 'Trang chủ | Cửa Hàng Điện Máy' }
            },

            {
                path: 'users',
                name: 'UserList',
                component: Users,
                meta: { title: 'Quản lý Người dùng' },
            },
            {
                path: 'users/createuser',
                name: 'CreateUser',
                component: CreateUser,
                meta: { title: 'Tạo Người dùng mới' }
            },
            {
                path: 'users/:id',
                name: 'DetailUser',
                component: DetailUser,
                props: true,
                meta: { title: 'Chi tiết Người dùng' }
            },
            {
                path: 'users/:id/edit',
                name: 'UpdateUser',
                component: UpdateUser,
                props: true,
                meta: { title: 'Cập nhật Người dùng' }
            },
            {
                path: 'users/:id/delete',
                name: 'DeleteUser',
                component: DeleteUser,
                props: true,
                meta: { title: 'Xác nhận xóa Người dùng' }
            },
            {
                path: 'products',
                name: 'Products',
                component: Products,
                meta: { title: 'Sản phẩm' }
            },
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