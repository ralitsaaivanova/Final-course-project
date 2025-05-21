package org.example.tltravel.controller;


import org.example.tltravel.view.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public ResponseEntity<String> test(){
        String response = "{\"status\":\"ok\"}";

        //return text/plain
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public ResponseEntity<Test> test1(){
        Test t1= new Test();
        t1.setStatus("OK");

        //return json

        return ResponseEntity.ok(t1);
    }
}
