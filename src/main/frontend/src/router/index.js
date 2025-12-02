import { createRouter, createWebHistory } from 'vue-router'
import AdminLayout from '@/layouts/AdminLayout.vue'
import Dashboard from '@/views/admin/dashboard/Dashboard.vue'
import Users from '@/views/admin/user/Users.vue'
import Products from '@/views/admin/product/Products.vue'
import Orders from '@/views/admin/order/Orders.vue'
import CreateUser from '@/views/admin/user/CreateUser.vue'

const routes = [
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
            {
                path: 'dashboard',// /admin/dashboard
                name: 'Dashboard',
                component: Dashboard,
                meta: { title: 'Trang chủ' }
            },
            {
                path: 'users', // /admin/users
                name: 'Users',
                component: Users,
                meta: { title: 'Người dùng' },
                children: [
                    {
                        path: 'createuser', // /admin/users/createuser
                        name: 'CreateUser',
                        component: CreateUser,
                        meta: {
                            // Callback để xử lý sau khi tạo user
                            onUserCreated: (userData) => {
                                // Có thể gọi API, cập nhật store, redirect, etc.
                                console.log('User created:', userData);
                                // Redirect về danh sách
                                router.push('/admin/users');
                            }
                        },
                        props: (route) => ({
                            onSuccess: route.meta.onUserCreated,
                            mode: 'createuser', // Thêm props khác
                            title: 'Tạo người dùng mới'
                        })
                    },
                ]
            },
            {
                path: 'products', // /admin/products
                name: 'Products',
                component: Products,
                meta: { title: 'Sản phẩm' }
            },
            {
                path: 'orders',// /admin/orders
                name: 'Orders',
                component: Orders,
                meta: { title: 'Đơn hàng' }
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router