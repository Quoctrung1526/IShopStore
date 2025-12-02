package com.example.IShopStore.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.Map;

// Đổi sang @RestController và dùng đường dẫn API
@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    // GET /api/admin/dashboard
    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        // Trả về dữ liệu Dashboard dưới dạng JSON
        Map<String, Object> data = Map.of(
                "totalUsers", 150,
                "totalProducts", 500,
                "salesToday", 125000000);
        return ResponseEntity.ok(data);
    }
}