package com.example.IShopStore.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

@Controller
public class HomePageController {

    @GetMapping("/")
    public String getHomePage(Model model, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        return "client/homepage/show";
    }

}
