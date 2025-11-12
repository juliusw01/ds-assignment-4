package com.example.productservice.Service;

import com.example.productservice.DTO.ProductRequestDTO;
import com.example.productservice.DTO.ProductResponseDTO;
import com.example.productservice.Model.Product;
import com.example.productservice.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        List<ProductResponseDTO> products = productRepository.findAll()
                .stream().map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()
                )).collect(Collectors.toList());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    public ResponseEntity<ProductResponseDTO> findProductById(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ProductResponseDTO response = new ProductResponseDTO(product.get().getId(),
                product.get().getName(),
                product.get().getDescription(),
                product.get().getPrice());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ProductResponseDTO> saveProduct(ProductRequestDTO product){
        Product saved = productRepository.save(new Product(product.getName(), product.getDescription(), product.getPrice(), product.getQuantity()));
        ProductResponseDTO response = new ProductResponseDTO(saved.getId(), saved.getName(), saved.getDescription(), saved.getPrice());
         return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Object> buyProduct(UUID id){
        return null;
    }
}
