package com.example.productservice.Controller;

import com.example.productservice.DTO.BuyProductDTO;
import com.example.productservice.DTO.ProductRequestDTO;
import com.example.productservice.DTO.ProductResponseDTO;
import com.example.productservice.Model.Product;
import com.example.productservice.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("product/api/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable UUID id){
        return productService.findProductById(id);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO product){
        return productService.saveProduct(product);
    }

    @PostMapping("/{id}/buy")
    public ResponseEntity<Object> buyProduct(@RequestBody BuyProductDTO productDTO){
        return productService.buyProduct(productDTO);
    }
}
