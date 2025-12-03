package com.example.IShopStore.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UploadService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * * @param file Tệp MultipartFile được tải lên.
     * 
     * @param subDirectory Thư mục con để lưu tệp (ví dụ: "avatar" hoặc "product").
     * @return Đường dẫn URL tương đối (ví dụ: "/upload-images/ten_file.jpg").
     */
    public String handleSaveUploadFile(MultipartFile file, String subDirectory) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            Path directory = Paths.get(uploadDir, subDirectory);
            // TẠO THƯ MỤC NẾU CHƯA TỒN TẠI (QUAN TRỌNG)
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            // Tạo tên tệp duy nhất
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + extension;

            Path filePath = directory.resolve(uniqueFilename);

            // Ghi tệp
            Files.copy(file.getInputStream(), filePath);
            return "/upload-images/" + subDirectory + "/" + uniqueFilename;

        } catch (IOException e) {
            System.err.println("LỖI GHI FILE: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Không thể lưu tệp đã tải lên. Lỗi hệ thống: " + e.getMessage());
        }
    }
}