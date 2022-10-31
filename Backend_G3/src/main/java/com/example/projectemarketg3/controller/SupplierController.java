package com.example.projectemarketg3.controller;


import com.example.projectemarketg3.entity.Supplier;
import com.example.projectemarketg3.exception.NotFoundException;
import com.example.projectemarketg3.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    @GetMapping
    public List<Supplier> getAllSup(){
        return supplierRepository.findAll();
    }

    // create a new supplier rest api


    // get supplier by ID rest api
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("supplier not exist with id :" + id));
        return ResponseEntity.ok(supplier);
    }




}
