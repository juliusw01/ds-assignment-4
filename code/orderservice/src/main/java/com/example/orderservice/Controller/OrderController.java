package com.example.orderservice.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("order/api/v1/orders")
public class OrderController {

    @GetMapping
    public ResponseEntity<List<String>> getAllOrders(){
        List<String> orders = new ArrayList<>();
        orders.add("Great Order");
        orders.add("Big Order");
        orders.add("Expensive Order");

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
