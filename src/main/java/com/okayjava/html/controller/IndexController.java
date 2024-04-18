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
    public String main_screen(Model model) {
        try {
            List<Map<String, Object>> loja1 = jdbcTemplate.queryForList(
                    "SELECT * FROM lojas WHERE id=1");
            model.addAttribute("loja", loja1);
            model.addAttribute("screen_estoque", "/screen-estoque");
        } catch (Exception e) {
            // Tratar a exceção aqui, como registrar ou retornar uma página de erro
            e.printStackTrace(); // apenas para depuração, você pode substituir isso pelo tratamento adequado
        }
        return "screen-main";
    }

    @GetMapping("/screen-estoque")
    public String estoque_screen(Model model) {
        try {
            List<Map<String, Object>> produto = jdbcTemplate.queryForList(
                    "SELECT * FROM produto");
            model.addAttribute("produtos", produto);
        } catch (Exception e) {
            // Tratar a exceção aqui, como registrar ou retornar uma página de erro
            e.printStackTrace(); // apenas para depuração, você pode substituir isso pelo tratamento adequado
        }
        return "screen-estoque";
    }
}
