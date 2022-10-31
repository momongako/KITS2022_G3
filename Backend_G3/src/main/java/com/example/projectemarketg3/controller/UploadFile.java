package com.example.projectemarketg3.controller;

//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//import com.example.projectemarketg3.entity.Image;
//import com.example.projectemarketg3.entity.Product;
import com.example.projectemarketg3.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class UploadFile {
//    @Autowired
//    private Cloudinary cloudinary;

    @Autowired
    private ImageRepository imageRepository;

//    @PostMapping
//    public String addFile(@ModelAttribute Product product) {
//        try {
//            Map r = this.cloudinary.uploader().upload(product.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
//            String img = (String) r.get("secure_url");
//            Image image = new Image();
//            image.setUrl(img);
//            imageRepository.save(image);
//            return img;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
