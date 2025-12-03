package com.example.IShopStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class InMemoryUserConfig {

        @Bean
        public UserDetailsService userDetailsService(
                        org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {

                // Mật khẩu sẽ được mã hóa: "123456"
                UserDetails admin = User.builder()
                                .username("admin")
                                .password(passwordEncoder.encode("123456"))
                                .roles("ADMIN")
                                .build();

                // Người dùng phụ để test
                UserDetails user = User.builder()
                                .username("user")
                                .password(passwordEncoder.encode("123456"))
                                .roles("USER")
                                .build();

                return new InMemoryUserDetailsManager(admin, user);
        }
}