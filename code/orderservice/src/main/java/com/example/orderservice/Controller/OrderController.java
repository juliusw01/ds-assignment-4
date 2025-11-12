package com.example.orderservice.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("order/api/v1/orders")
public class OrderController {

    @PostMapping("/create/new")
    public ResponseEntity<Object> createOrder(){
        /**
         * Here I want to receive order objects. When I receive an order, the product has already been paid.
         * I therefore only want to process the order (not triggering a payment service etc.)
         * Since the productservice is not waiting for an answer, the order can be added to a message queue and
         * does not have to be processed immediately.
         * Processing an order would include sending out a confirmation email, triggering a shipping service, etc.
         */

    }
}
