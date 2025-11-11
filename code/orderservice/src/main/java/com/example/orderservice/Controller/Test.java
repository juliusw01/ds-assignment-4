package com.example.orderservice.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("order/test")
public class Test {

    @GetMapping
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
