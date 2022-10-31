package com.example.projectemarketg3.controller;

import com.example.projectemarketg3.entity.Product;
import com.example.projectemarketg3.exception.NotFoundException;
import com.example.projectemarketg3.repository.OrderDetailRepository;
import com.example.projectemarketg3.repository.ProductRepository;
import com.example.projectemarketg3.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductService productService;


    // Get all products rest api
    @GetMapping("/search")
    public List<Product> getAllProduct(@RequestParam Optional<String> name,
                                       @RequestParam Optional<Long> origin,
                                       @RequestParam Optional<String> category,
                                       @RequestParam Optional<Long> start,
                                       @RequestParam Optional<Long> end
    ) {
//        return productService.searchProduct(name.get(), category.get(), start.get(), end.get());

        //        name
        if (name.isPresent() && category.isEmpty() && start.isEmpty() && end.isEmpty()) {
            return productService.caseSearch(1, name.get(), null, null, null);
        }
//        category
        else if (category.isPresent() && name.isEmpty() && start.isEmpty() && end.isEmpty()) {
            return productService.caseSearch(2, null, category.get(), null, null);
        }
//        price
        else if (category.isEmpty() && name.isEmpty() && start.isPresent() && end.isPresent()) {
            return productService.caseSearch(3, null, null, start.get(), end.get());
        }
//        name category
        else if (category.isPresent() && name.isPresent() && start.isEmpty() && end.isEmpty()) {
            return productService.caseSearch(4, name.get(), category.get(), null, null);
        }
//        name category price
        else if (category.isPresent() && name.isPresent() && start.isPresent() && end.isPresent()) {
            return productService.caseSearch(5, name.get(), category.get(), start.get(), end.get());
        }
//        name price
        else if (category.isEmpty() && name.isPresent() && start.isPresent() && end.isPresent()) {
            return productService.caseSearch(6, name.get(), null, start.get(), end.get());
        }
//        category price
        else if (category.isPresent() && name.isEmpty() && start.isPresent() && end.isPresent()) {
            return productService.caseSearch(7, null, category.get(), start.get(), end.get());
        } else {
            return productService.caseSearch(0, null, null, null, null);
        }
    }

    @GetMapping
    public List<Product> searchProductByKeyword(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) String origin,
                                                @RequestParam(required = false) String category,
                                                @RequestParam(required = false) Long start,
                                                @RequestParam(required = false) Long end) {
        if(start==null){
            start = 0L;
        }
        if (end== null){
            List<Product> products = productRepository.findAll();
            end = 0L;
            for (Product p : products
                 ) {
                if(end < p.getPrice()){
                    end = p.getPrice();
                }
            }
        }
        return productRepository.searchProductByKeyword(name, origin, category, start, end);
    }

    @GetMapping("/product-star/{star}")
    public List<Product> findproductByStar(@PathVariable Double star) {
        return productRepository.findByAvgRatingBetweenOrderByAvgRatingDesc(star, 5.0);
    }


    // get product by ID rest api
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("product not exist with id :" + id));
        return ResponseEntity.ok(product);
    }

    // update product rest api
//    @PutMapping("/{id}")
//    public  ResponseEntity <products> updateProduct(@PathVariable Long id, @RequestBody products productDetails){
//        products product = productRepository.findById(id)
//                .orElseThrow (()->new NotFoundException
//                        ("product not exist with id :" + id));
//
//        product.set(productDetails.get());
//        product.set(productDetails.get());
//        product.set(productDetails.get());
//
//        products updatedProduct = productRepository.save(product);
//
//        return  ResponseEntity.ok(updatedProduct);
//    }


    //    HOT PRODUCT
    @GetMapping("/hot")
    public List<Product> getProductTopSold() {
        return productRepository.findTop6AllByOrderBySoldDesc();
    }

    //SEARCH BY NAME -> http://localhost:8080/api/v1/products/search-name?name=Lesley Rohan
    @GetMapping("/search-name")
    public List<Product> findByName(@RequestParam String name) {
        return productRepository.getByNameStartsWithIgnoreCaseOrderByNameAsc(name);
    }

    //    SEARCH BY PRICE
//    http://localhost:8080/api/v1/products/search-price?sellPriceStart=20&sellPriceEnd=50
    @GetMapping("/search-price")
    public List<Product> findByPrice(@RequestParam Long sellPriceStart, @RequestParam Long sellPriceEnd) {
        return productRepository.findByPriceBetweenOrderByPriceAsc(sellPriceStart, sellPriceEnd);
    }

    @GetMapping("/category/{name}")
    public List<Product> getProductByCategory(@PathVariable String name) {
        return productRepository.findByCategory_Name(name);
    }


}
