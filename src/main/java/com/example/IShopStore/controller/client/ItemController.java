package com.example.IShopStore.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

@Controller
public class ItemController {

    @GetMapping("/product/{id}")
    public String getProductPage(Model model, @PathVariable Long id) {
        return "client/product/detail";
    }

}
