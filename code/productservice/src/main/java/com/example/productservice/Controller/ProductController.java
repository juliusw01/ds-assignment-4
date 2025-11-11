package com.example.productservice.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("product/api/v1/products")
public class ProductController {

    @GetMapping
    public ResponseEntity<List<String>> getAllProducts() {
        List<String> products = new ArrayList<>();
        products.add("Tisch");
        products.add("Stuhl");
        products.add("Lampe");

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
