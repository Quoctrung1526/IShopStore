package com.example.IShopStore.repository;

import java.util.List;
import java.util.Optional; // Cần import Optional

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.IShopStore.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // findById(Long id), findAll(), save(), deleteById(Long id), existsById(Long
    // id)
    // đã được kế thừa từ JpaRepository

    List<User> findByEmail(String email);

    Optional<User> findOneByEmail(String email);

}