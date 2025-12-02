package com.example.IShopStore.controller.admin;

import com.example.IShopStore.domain.Role;
import com.example.IShopStore.service.RoleService; // Giả định service này tồn tại
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // GET /api/roles: Trả về danh sách tất cả các Roles
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        // Giả định RoleService có phương thức để lấy tất cả các vai trò
        List<Role> roles = roleService.findAllRoles();
        return ResponseEntity.ok(roles);
    }
}