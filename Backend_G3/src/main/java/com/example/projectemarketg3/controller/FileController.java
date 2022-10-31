package com.example.projectemarketg3.controller;

import com.example.projectemarketg3.service.ProductService;
import com.example.projectemarketg3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin")
public class FileController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    // Upload file
//    @PostMapping("/users/{id}/upload-file")
//    public String uploadFile(@PathVariable Long id, @ModelAttribute("file") MultipartFile file) {
//        return userService.uploadFile(id, file);
//    }

    @PostMapping("/product/{id}/upload-file")
    public String productUpFile(@PathVariable Long id, @ModelAttribute("file") MultipartFile file) {
        return productService.uploadFile(id, file);
    }

    // Xem file
//    @GetMapping(value = "/users/{id}/files/{fileId}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public byte[] readFile(@PathVariable Long id, @PathVariable String fileId) {
//        return userService.readFile(id, fileId);
//    }

    @GetMapping(value = "/product/{id}/files/{fileId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] readFileProduct(@PathVariable Long id, @PathVariable String fileId) {
        return productService.readFile(id, fileId);
    }

    // Xóa file
//    @DeleteMapping("/users/{id}/files/{fileId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteFile(@PathVariable Long id, @PathVariable String fileId) {
//        userService.deleteFile(id, fileId);
//    }

    @DeleteMapping("/product/{id}/files/{fileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFileProduct(@PathVariable Long id, @PathVariable String fileId) {
        productService.deleteFile(id, fileId);
    }

    // Lấy danh sách file upload
//    @GetMapping("/users/{id}/files")
//    public List<String> getFiles(@PathVariable Long id) {
//        return userService.getFiles(id);
//    }

}

