package org.example.tltravel.controller;


import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
    private final ResourceLoader resourceLoader;

    public TestController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/index")
    public ModelAndView homePage(Model model){

        return new ModelAndView ("index");        // resolves to /templates/index.html
    }

    @GetMapping("/about")
    public String about() {

        return "about";
    }

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/elements")
    public String elements() {
        return "elements";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/offers")
    public String offers() {
        return "offers";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/single_listing")
    public String singleListing() {
        return "single_listing";
    }

    @GetMapping("/test")
    public ResponseEntity<Test> test1(){
        Test t1= new Test();
        t1.setStatus("OK");

        //return json

        return ResponseEntity.ok(t1);
    }
}
