
package com.okayjava.html.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


@Entity
public class Produto implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String descricao_completa;
    private int estoque;
    private float custo_produto;
    private float preco_venda;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao_completa() {
        return descricao_completa;
    }

    public void setDescricao_completa(String descricao_completa) {
        this.descricao_completa = descricao_completa;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public float getCusto_produto() {
        return custo_produto;
    }

    public void setCusto_produto(float custo_produto) {
        this.custo_produto = custo_produto;
    }

    public float getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(float preco_venda) {
        this.preco_venda = preco_venda;
    }

    public Produto(int id, int estoque, float custoProduto, String descricaoCompleta, float precoVenda) {
        this.estoque = estoque;
        this.custo_produto = custoProduto;
        this.descricao_completa = descricaoCompleta;
        this.preco_venda = precoVenda;

        this.id = id;
    }

  
}
