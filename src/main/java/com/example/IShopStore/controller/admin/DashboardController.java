package com.example.IShopStore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/admin")
    public String getDashboard() {
        return "/frontend/src/views/admin/dashboard/Dashboard.vue";
    }

}
