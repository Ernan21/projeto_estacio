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

    // @GetMapping("/produtos");
    // public String produto(Model model) {
        // List<Map<String, Object>> produto = jdbcTemplate.queryForList(
            // "SELECT * FROM produto"); 
        // model.addAttribute("produtos", produto);
        // return "produtos";
    // }
}

