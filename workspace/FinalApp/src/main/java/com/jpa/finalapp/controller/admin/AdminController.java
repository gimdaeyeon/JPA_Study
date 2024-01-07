package com.jpa.finalapp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
    @GetMapping("/product/register")
    public String produceRegister(){
        return "admin/product_register";
    }
}
