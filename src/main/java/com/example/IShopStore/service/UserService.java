package com.example.IShopStore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.example.IShopStore.domain.Role;
import com.example.IShopStore.domain.User;
import com.example.IShopStore.repository.RoleRepository;
import com.example.IShopStore.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findAll();
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public void deleteUserById(long id) {
        if (!this.userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng để xóa.");
        }
        this.userRepository.deleteById(id);
    }

    public User handleSaveUser(User user) {

        // 1. LẤY ID TỪ ĐỐI TƯỢNG ROLE TRANSIENT
        if (user.getRole() != null && user.getRole().getId() != 0) {
            Long roleId = user.getRole().getId();

            // 2. TẢI ĐỐI TƯỢNG ROLE PERSISTENT (Đã lưu) TỪ DATABASE
            Role existingRole = this.roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Role ID không hợp lệ: " + roleId));

            // 3. GÁN ĐỐI TƯỢNG PERSISTENT VÀO USER
            user.setRole(existingRole);
        } else {
            // Xử lý trường hợp role bị thiếu hoặc ID bằng 0
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role ID bị thiếu.");
        }

        // 4. LƯU USER
        user = this.userRepository.save(user);
        System.out.println("Saved user: " + user);
        return user;
    }

    public Optional<Role> getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }
}
