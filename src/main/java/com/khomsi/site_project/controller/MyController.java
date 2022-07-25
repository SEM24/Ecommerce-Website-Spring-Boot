package com.khomsi.site_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
    @GetMapping({"/", ""})
    public String index() {
        return "index";
    }

}
