package com.example.projectemarketg3.service;

import com.example.projectemarketg3.entity.Product;
import com.example.projectemarketg3.exception.NotFoundException;
import com.example.projectemarketg3.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private FileService fileService;
    @Autowired
    private ProductRepository productRepository;


    //    Upload file
    public String uploadFile(Long id, MultipartFile file) {
        if (productRepository.findById(id).isEmpty()) {
            throw new NotFoundException("khong ton tai san pham co id = " + id);
        }
        return fileService.uploadFile(id, file);
    }

    //    xem file
    public byte[] readFile(Long id, String fileId) {
        return fileService.readFile(id, fileId);
    }

    //    Xoa file
    public String deleteFile(Long id, String fileId) {
        fileService.deleteFile(id, fileId);
        return "xoa thanh cong";
    }

    //    lay danh sach file
    public List<String> getFile(Long id) {
        if (productRepository.findById(id).isEmpty()) {
            throw new NotFoundException("khong ton tai product co id = " + id);
        }
        return fileService.getFiles(id);
    }

    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    public List<Product> caseSearch(int num, String name, String category, Long start, Long end) {
        switch (num) {
            case 1: //name
                return getProduct()
                        .stream()
                        .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase())
                                || s.getSupplier().getName().toLowerCase().contains(name.toLowerCase())
                                || s.getOrigin().toLowerCase().contains(name.toLowerCase())
                                || s.getDescription().toLowerCase().contains(name.toLowerCase()))
                        .toList();

            case 2: //category
                return getProduct()
                        .stream()
                        .filter(s -> s.getCategory().getName().equals(category))
                        .collect(Collectors.toList());

            case 3: //price
                return getProduct()
                        .stream()
                        .filter(s -> end >= s.getPrice() && s.getPrice() >= start)
                        .collect(Collectors.toList());

            case 4: //name category
                List<Product> p = getProduct().stream()
                        .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase())
                                || s.getSupplier().getName().toLowerCase().contains(name.toLowerCase())
                                || s.getOrigin().toLowerCase().contains(name.toLowerCase())
                                || s.getDescription().toLowerCase().contains(name.toLowerCase()))
                        .toList();
                return p.stream()
                        .filter(s -> s.getCategory().getName().equals(category))
                        .collect(Collectors.toList());

            case 5: //name category price
                List<Product> nameProducts = getProduct().stream()
                        .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase())
                                || s.getSupplier().getName().toLowerCase().contains(name.toLowerCase())
                                || s.getOrigin().toLowerCase().contains(name.toLowerCase())
                                || s.getDescription().toLowerCase().contains(name.toLowerCase()))
                        .toList();
                List<Product> categoryProducts = nameProducts.stream().filter(s -> s.getCategory().getName().equals(category)).toList();
                return categoryProducts.stream()
                        .filter(s -> end >= s.getPrice() && s.getPrice() >= start)
                        .collect(Collectors.toList());

            case 6: //name price
                List<Product> nameProduct = getProduct().stream()
                        .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase())
                                || s.getSupplier().getName().toLowerCase().contains(name.toLowerCase())
                                || s.getOrigin().toLowerCase().contains(name.toLowerCase())
                                || s.getDescription().toLowerCase().contains(name.toLowerCase()))
                        .toList();
                return nameProduct.stream()
                        .filter(s -> end >= s.getPrice() && s.getPrice() >= start)
                        .collect(Collectors.toList());

            case 7: //category price
                List<Product> categoryProduct2 = getProduct().stream()
                        .filter(s -> s.getCategory().getName().equals(category)).toList();
                return categoryProduct2.stream()
                        .filter(s -> end >= s.getPrice() && s.getPrice() >= start)
                        .collect(Collectors.toList());

            default:
                return getProduct();
        }
    }


}
