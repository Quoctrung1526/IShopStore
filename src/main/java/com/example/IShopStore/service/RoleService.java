package com.example.IShopStore.service;

import com.example.IShopStore.domain.Role;
import com.example.IShopStore.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    // Sử dụng Constructor Injection
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Lấy danh sách tất cả các vai trò (được gọi bởi RoleController)
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    // Lấy vai trò theo ID
    public Optional<Role> findRoleById(Long id) {
        return roleRepository.findById(id);
    }

    // Lấy vai trò theo Tên (được gọi bởi logic khởi tạo data)
    // Trả về Optional để xử lý trường hợp không tìm thấy
    public Optional<Role> findRoleByName(String name) {
        // Đảm bảo RoleRepository trả về Optional<Role>
        return roleRepository.findByName(name);
    }

    // Phương thức kiểm tra và tạo Role ban đầu (được gọi khi ứng dụng khởi động)
    public void createDefaultRolesIfNotExist() {
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            roleRepository.save(new Role("ADMIN"));
        }
        if (roleRepository.findByName("USER").isEmpty()) {
            roleRepository.save(new Role("USER"));
        }
    }
}