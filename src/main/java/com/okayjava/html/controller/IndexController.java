package com.okayjava.html.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Mapeamento para tela inicial
    @GetMapping("/")
    public String main_screen(Model model) {
        try {
            List<Map<String, Object>> loja1 = jdbcTemplate.queryForList("SELECT * FROM lojas WHERE id=1"); // Busca com query do banco de dados postgresql
            model.addAttribute("loja", loja1);
            model.addAttribute("screen_estoque", "/screen_estoque");
            model.addAttribute("screen_cadastro", "/screen_cadastro");
            model.addAttribute("screen_vendas", "/screen_vendas");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "screen-main";
    }

    // Mapeamento para tela de estoque
    @GetMapping("/screen_estoque")
    public String estoque_screen(Model model) {
        try {
            List<Map<String, Object>> loja1 = jdbcTemplate.queryForList("SELECT * FROM lojas WHERE id=1"); // Busca com query do banco de dados postgresql
            model.addAttribute("loja", loja1);
            List<Map<String, Object>> produto = jdbcTemplate.queryForList("SELECT * FROM produto"); // Busca com query do banco de dados postgresql
            model.addAttribute("produtos", produto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "screen_estoque";
    }

    // Mapeamento para tela de cadastro
    @GetMapping("/screen_cadastro")
    public String screen_cadastro(Model model) {
        try {
            List<Map<String, Object>> loja1 = jdbcTemplate.queryForList("SELECT * FROM lojas WHERE id=1"); // Busca com query do banco de dados postgresql
            model.addAttribute("loja", loja1);
            List<Map<String, Object>> produto = jdbcTemplate.queryForList("SELECT * FROM produto"); // Busca com query do banco de dados postgresql
            model.addAttribute("produtos", produto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "screen_cadastro";
    }

    

    @GetMapping("/screen_vendas")
    public String screen_vendas(Model model) {
        try {
            List<Map<String, Object>> loja1 = jdbcTemplate.queryForList("SELECT * FROM lojas WHERE id=1"); // Busca com query do banco de dados postgresql
            model.addAttribute("loja", loja1);
            List<Map<String, Object>> produto = jdbcTemplate.queryForList("SELECT * FROM produto"); // Busca com query do banco de dados postgresql
            model.addAttribute("produtos", produto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "screen_vendas";
    }

    // Mapeamento para busca de produto
    @GetMapping("/search")
    public String searchByName(@RequestParam("term") String term, Model model) {
        try {
            List<Map<String, Object>> loja1 = jdbcTemplate.queryForList("SELECT * FROM lojas WHERE id=1"); // Busca com query do banco de dados postgresql
            model.addAttribute("loja", loja1);
            List<Map<String, Object>> produtos = jdbcTemplate.queryForList("SELECT * FROM produto WHERE descricao_completa LIKE ?", "%" + term + "%"); // Busca com query do banco de dados postgresql
            model.addAttribute("produtos", produtos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "screen_estoque :: #productTable"; // Retorna apenas a tabela de produtos atualizada
    }

}
