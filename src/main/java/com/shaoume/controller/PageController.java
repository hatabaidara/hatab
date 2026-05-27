package com.shaoume.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/pages/categories")
    public String categoriesPage() {
        return "forward:/pages/categories.html";
    }
}
