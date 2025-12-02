package com.example.IShopStore.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public User handleSaveUser(User user) {
        user = this.userRepository.save(user);
        System.out.println("Saved user: " + user);
        return user;
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

}
