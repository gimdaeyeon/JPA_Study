package com.jpa.finalapp.controller.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store/*")
public class StoreController {

    @GetMapping("/main")
    public String storeMain(){
        return "store/main";
    }
    @GetMapping("/detail")
    public String storeDetail(){
        return "store/detail";
    }

    @GetMapping("/list")
    public String storeList(){
        return "store/list";
    }
    @GetMapping("/cart")
    public String storeCart(){
        return "store/cart";
    }


}








