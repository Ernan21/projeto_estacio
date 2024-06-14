package com.okayjava.html.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


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
            model.addAttribute("screen_relatorio_vendas", "/screen_relatorio_vendas");
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
            List<Map<String, Object>> produto = jdbcTemplate.queryForList("SELECT * FROM produto ORDER BY id ASC"); // Busca com query do banco de dados postgresql
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
            List<Map<String, Object>> loja1 = jdbcTemplate.queryForList("SELECT * FROM lojas WHERE id=1");
            model.addAttribute("loja", loja1);
            List<Map<String, Object>> produto = jdbcTemplate.queryForList("SELECT * FROM produto ORDER BY id ASC");
            model.addAttribute("produtos", produto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "screen_cadastro";
    }
    
    // Verificação para saber se o produto a ser cadastro já tem uma descrição parecida

    @PostMapping("/check-description")
    @ResponseBody
    public Map<String, Boolean> checkDescription(@RequestBody Map<String, String> requestBody) {
        String descricao = requestBody.get("descricao");
        boolean exists = isDescricaoCadastrada(descricao);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
            return response;
}

    private boolean isDescricaoCadastrada(String descricao) {
        try {
            List<Map<String, Object>> produtos = jdbcTemplate.queryForList("SELECT * FROM produto WHERE LOWER(descricao_completa) = LOWER(?)", descricao);
            return !produtos.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
    }
}
    // Tela de Cadastro

    @PostMapping("/screen_cadastro")
    public String salvarProduto(@RequestParam("descricao_completa") String descricao_completa,
                            @RequestParam("estoque") int estoque,
                            @RequestParam("custo_produto") float custo_produto,
                            @RequestParam("preco_venda") float preco_venda) {
    try {
        Integer ultimoId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM produto", Integer.class);
        int novoId = (ultimoId != null) ? ultimoId + 1 : 1;

        jdbcTemplate.update("INSERT INTO produto (id, descricao_completa, estoque, custo_produto, preco_venda) VALUES (?, ?, ?, ?, ?)",
                            novoId, descricao_completa, estoque, custo_produto, preco_venda);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "redirect:/screen_cadastro";
}

    @GetMapping("/screen_vendas")
    public String screen_vendas(Model model) {
        try {
            List<Map<String, Object>> loja1 = jdbcTemplate.queryForList("SELECT * FROM lojas WHERE id=1"); // Busca com query do banco de dados postgresql
            model.addAttribute("loja", loja1);
            List<Map<String, Object>> produto = jdbcTemplate.queryForList("SELECT * FROM produto ORDER BY id ASC"); // Busca com query do banco de dados postgresql
            model.addAttribute("produtos", produto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "screen_vendas";
    }

    @GetMapping("/produtos")
    @ResponseBody
    public List<Map<String, Object>> getProdutos(
        @RequestParam(value = "codigo", required = false) String codigo,
        @RequestParam(value = "descricao", required = false) String descricao
    ) {
        String query = "SELECT * FROM produto WHERE 1 = 1";
    
        if (codigo != null && !codigo.isEmpty()) {
            query += " AND id = '" + codigo + "'";
        }
    
        if (descricao != null && !descricao.isEmpty()) {
            query += " AND descricao_completa LIKE '%" + descricao + "%'";
        }
    
        return jdbcTemplate.queryForList(query);
    }

    // Tela de Vendas - Finalizando uma Venda

    @PostMapping("/finalizar_venda")
    public String finalizarVenda(@RequestParam("id_produtos") String[] idsProdutos,
                                 @RequestParam("quantidades") int[] quantidades,
                                 @RequestParam("valores_unitarios") float[] valoresUnitarios,
                                 @RequestParam("desconto") float desconto) {
        try {
            Integer ultimoIdVenda = jdbcTemplate.queryForObject("SELECT MAX(id_venda) FROM vendas", Integer.class);
            int novoIdVenda = (ultimoIdVenda != null) ? ultimoIdVenda + 1 : 1;
    
            float descontoPorProduto = desconto / idsProdutos.length;
    
            for (int i = 0; i < idsProdutos.length; i++) {
                int idProduto = Integer.parseInt(idsProdutos[i]);
                int quantidadeVendida = quantidades[i];
    
                jdbcTemplate.update("INSERT INTO vendas (id_venda, id_produto, quantidade_vendida, valor_unitario, desconto, data_venda) VALUES (?, ?, ?, ?, ?, CURRENT_DATE)",
                                    novoIdVenda, idProduto, quantidadeVendida, valoresUnitarios[i], descontoPorProduto);
    
                jdbcTemplate.update("UPDATE produto SET estoque = estoque - ? WHERE id = ?", quantidadeVendida, idProduto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/screen_vendas";
    }    
    
    // Estoque - Funcionalidades da Barra de Pesquisa na Maneira de Organizar os Dados

    @GetMapping("/search")
    public String searchByName(@RequestParam(value = "term", required = false) String term, Model model) {
        try {
            if (term == null || term.isEmpty()) {
                return "screen_estoque :: #productTable"; 
            }
            
            int id = 0;
            try {
                id = Integer.parseInt(term);
            } catch (NumberFormatException ex) {
            }
            
            List<Map<String, Object>> loja1 = jdbcTemplate.queryForList("SELECT * FROM lojas WHERE id=1");
            model.addAttribute("loja", loja1);
            
            List<Map<String, Object>> produtos;
            if (id != 0) {
                produtos = jdbcTemplate.queryForList("SELECT * FROM produto WHERE id = ?", id);
            } else {
                produtos = jdbcTemplate.queryForList("SELECT * FROM produto WHERE descricao_completa LIKE ? ORDER BY CASE WHEN descricao_completa ILIKE ? THEN 0 ELSE 1 END, descricao_completa", "%" + term + "%", term + "%");
            }
            
            if (produtos.isEmpty()) {
                model.addAttribute("mensagem", term);
            } else {
                model.addAttribute("produtos", produtos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "screen_estoque :: #productTable"; 
    }

    // Tela de Edição do Produto (Pós Clicado no Botão Editar no Estoque)

    @GetMapping("/screen_editar")
    public String editarProduto(@RequestParam("id") int id,
                            @RequestParam("descricao_completa") String descricao_completa, 
                            @RequestParam("estoque") int estoque, 
                            Model model) {
    try {
        model.addAttribute("id", id);
        model.addAttribute("descricao_completa", descricao_completa);
        model.addAttribute("estoque", estoque);
        
        List<Map<String, Object>> loja1 = jdbcTemplate.queryForList("SELECT * FROM lojas WHERE id=1");
        model.addAttribute("loja", loja1);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "screen_editar";
}


// Tela de Edição - Ação após o produto editado

@PostMapping("/update_product")
public String atualizarProduto(@RequestParam("id") int id,
                               @RequestParam("descricao_completa") String descricao_completa,
                               @RequestParam("estoque") int estoque) {
    try {
        jdbcTemplate.update("UPDATE produto SET descricao_completa = ?, estoque = ? WHERE id = ?",
                            descricao_completa, estoque, id);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "redirect:/screen_estoque";
}

// Tela com o relatorio de vendas

@GetMapping("/screen_relatorio_vendas")
public String relatorioVendasScreen(Model model) {
    return "screen_relatorio_vendas";
}

@PostMapping("/search_relatorio_vendas")
public String searchRelatorioVendas(
        @RequestParam("data_inicio") String dataInicio,
        @RequestParam("data_fim") String dataFim,
        Model model) {
    try {
        Date dataInicioSql = Date.valueOf(dataInicio);
        Date dataFimSql = Date.valueOf(dataFim);

        String query = "SELECT * FROM VendasResumo WHERE data_venda BETWEEN ? AND ?";
        
        List<Map<String, Object>> vendas = jdbcTemplate.queryForList(query, dataInicioSql, dataFimSql);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Map<String, Object> venda : vendas) {
            Date dataVenda = (Date) venda.get("data_venda");
            String dataVendaFormatada = sdf.format(dataVenda);
            venda.put("data_venda", dataVendaFormatada);
        }

        model.addAttribute("vendas", vendas);

    } catch (Exception e) {
        e.printStackTrace();
    }
    return "screen_relatorio_vendas";
    }
}