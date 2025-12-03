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

    public User getUserById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Không tìm thấy người dùng với ID: " + id));
    }

    public void deleteUserById(Long id) {
        if (!this.userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng để xóa.");
        }
        this.userRepository.deleteById(id);
    }

    public User handleSaveUser(User user) {

        Role roleFromPayload = user.getRole();
        if (roleFromPayload != null && roleFromPayload.getId() != null && roleFromPayload.getId() > 0) {
            Long roleId = roleFromPayload.getId();
            // TẢI ĐỐI TƯỢNG ROLE PERSISTENT TỪ DATABASE
            Role existingRole = this.roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Role ID không hợp lệ: " + roleId));
            user.setRole(existingRole);
        } else if (user.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role ID bị thiếu khi tạo người dùng mới.");
        }

        // LƯU USER
        user = this.userRepository.save(user);
        System.out.println("Saved user: " + user);
        return user;
    }

    public Optional<Role> getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }
}