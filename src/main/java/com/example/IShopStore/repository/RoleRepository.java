package com.example.IShopStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IShopStore.domain.Role;

import java.util.Optional; // Cần import

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Đã sửa để trả về Optional<Role>
    Optional<Role> findByName(String name);

}