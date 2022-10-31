package com.example.projectemarketg3.controller;

import com.example.projectemarketg3.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class fileUser {
    @Autowired
    private ProductService productService;

    @GetMapping("api/v1/product/{id}/files")
    public List<String> getFileProduct(@PathVariable Long id) {
        return productService.getFile(id);
    }

}
