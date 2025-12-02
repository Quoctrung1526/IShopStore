package com.example.IShopStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // <-- Cần import
import org.springframework.security.crypto.password.PasswordEncoder; // <-- Cần import
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Cấu hình CSRF: Vô hiệu hóa CSRF cho ứng dụng API RESTful
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Cấu hình CORS: Cho phép truy cập từ Vue.js (5173)
                .cors(Customizer.withDefaults())

                // 3. Phân quyền truy cập (Authorization)
                .authorizeHttpRequests(auth -> auth
                        // Chỉ cho phép ADMIN truy cập các endpoint quản lý người dùng
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // Cho phép tất cả truy cập vào các đường dẫn public, ví dụ: trang chủ Vue.js
                        .requestMatchers("/", "/assets/**", "/index.html", "/favicon.ico").permitAll()

                        // Yêu cầu xác thực (login) cho tất cả các request khác
                        .anyRequest().authenticated())

                // 4. Cấu hình cơ chế xác thực: Dùng HTTP Basic hoặc Form Login (tùy chọn của
                // bạn)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // Bean để cấu hình CORS chi tiết hơn, cần thiết khi tắt CSRF
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // <--- THÊM BEAN PASSWORD ENCODER TẠI ĐÂY --->
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Sử dụng BCryptPasswordEncoder là tiêu chuẩn công nghiệp
        return new BCryptPasswordEncoder();
    }
}