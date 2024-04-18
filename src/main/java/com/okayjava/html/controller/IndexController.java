package com.okayjava.html.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String loja(Model model) {
        List<Map<String, Object>> loja1 = jdbcTemplate.queryForList(
                "SELECT * FROM lojas WHERE id=1"); 
        model.addAttribute("loja", loja1);
        return "index";
    }

    @GetMapping("/screen-main")
    public String main_screen(Model model) {
        List<Map<String, Object>> loja1 = jdbcTemplate.queryForList(
                "SELECT * FROM lojas WHERE id=1"); 
        model.addAttribute("loja", loja1);
        return "screen-main";
    }
}

