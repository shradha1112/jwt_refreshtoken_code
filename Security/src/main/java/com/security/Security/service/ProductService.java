package com.security.Security.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.Security.dto.Product;
import com.security.Security.entity.UserInfo;
import com.security.Security.repository.UserInfoRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ProductService {

    List<Product> productList = null;

//    @Autowired
//    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadProductsFromDB() {
    	productList = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> new Product(i, "product " + i, new Random().nextInt(10), new Random().nextInt(5000)))
                .collect(Collectors.toList());
    }


    public List<Product> getProducts() {
        return productList;
    }

    public List<Product> getProductupto25() {
        return (List<Product>) productList.stream()
                .filter(product -> product.getProductId()<= 25)
                .findAny()
                .orElseThrow(() -> new RuntimeException("product not found"));
    }
    
    public Product getProduct(int id) {
        return productList.stream()
                .filter(product -> product.getProductId() == id)
                .findAny()
                .orElseThrow(() -> new RuntimeException("product " + id + " not found"));
    }


//    public String addUser(UserInfo userInfo) {
//        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
//        repository.save(userInfo);
//        return "user added to system ";
//    }
}