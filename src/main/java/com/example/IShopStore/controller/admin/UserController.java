package com.example.IShopStore.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.IShopStore.domain.User;
import com.example.IShopStore.service.UserService;
import com.example.IShopStore.service.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// Đổi từ @Controller sang @RestController để trả về JSON
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    public UserController(UploadService uploadService,
            UserService userService,
            PasswordEncoder passwordEncoder,
            ObjectMapper objectMapper) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserDetail(@PathVariable Long id) {
        User user = this.userService.getUserById(id); // Giả định service ném 404 nếu không tìm thấy
        return ResponseEntity.ok(user);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> createUser(
            @RequestPart("user") String userJson,
            @RequestPart(value = "avatar", required = false) MultipartFile file) {
        try {
            User user = objectMapper.readValue(userJson, User.class);

            // Xử lý File Upload
            if (file != null && !file.isEmpty()) {
                String avatarUrl = this.uploadService.handleSaveUploadFile(file, "avatar");
                user.setAvatar(avatarUrl);
            }

            // Mã hóa mật khẩu
            String hashPassword = this.passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPassword);
            User savedUser = this.userService.handleSaveUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED); // Trả về 201 Created
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lỗi khi tạo người dùng: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestPart("user") String userJson,
            @RequestPart(value = "avatar", required = false) MultipartFile file) {
        try {
            User userUpdate = objectMapper.readValue(userJson, User.class);

            if (!id.equals(userUpdate.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID người dùng không khớp với đường dẫn.");
            }

            User currentUser = this.userService.getUserById(id);

            currentUser.setfullName(userUpdate.getfullName());
            currentUser.setPhone(userUpdate.getPhone());
            currentUser.setAddress(userUpdate.getAddress());

            if (userUpdate.getRole() != null && userUpdate.getRole().getId() != null
                    && userUpdate.getRole().getId() > 0) {
                currentUser.setRole(userUpdate.getRole());
            }

            if (userUpdate.getPassword() != null && !userUpdate.getPassword().isEmpty()) {
                String hashPassword = this.passwordEncoder.encode(userUpdate.getPassword());
                currentUser.setPassword(hashPassword);
            }

            if (file != null && !file.isEmpty()) {
                String avatarUrl = this.uploadService.handleSaveUploadFile(file, "avatar");
                currentUser.setAvatar(avatarUrl);
            }

            User updatedUser = this.userService.handleSaveUser(currentUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lỗi khi cập nhật người dùng: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        this.userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
