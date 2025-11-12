package com.example.productservice.Service;

import com.example.productservice.DTO.BuyProductDTO;
import com.example.productservice.DTO.ProductRequestDTO;
import com.example.productservice.DTO.ProductResponseDTO;
import com.example.productservice.Model.Product;
import com.example.productservice.RabbitMQ.OrderMessageProducer;
import com.example.productservice.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderMessageProducer orders;

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
    @Transactional
    public synchronized ResponseEntity<Object> buyProduct(BuyProductDTO productDTO){
        Optional<Product> product = productRepository.findById(productDTO.getId());
        if (product.isEmpty() || product.get().getQuantity() <= 0){
            return new ResponseEntity<>("Product not available", HttpStatus.NOT_FOUND);
        }
        int quantity = product.get().getQuantity();
        if (quantity < productDTO.getQuantity()){
            return new ResponseEntity<>("Only " + quantity + " items left", HttpStatus.BAD_REQUEST);
        }
        //Payment logic here --> I will pretend the customer has paid and I can proceed with the order
        product.get().setQuantity(quantity-productDTO.getQuantity());
        productRepository.deleteById(product.get().getId());
        productRepository.save(product.get());
        // call orderservice here
        orders.sendOrderMessage(quantity + " " + product.get().getName() + "s have been ordered");
        return new ResponseEntity<>("Order created", HttpStatus.OK);
    }
}
