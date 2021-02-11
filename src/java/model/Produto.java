/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.ProdutoDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author anton
 */
public class Produto {
    private Integer codigo;
    private String nome;
    private Float preco;

    public Produto(Integer codigo, String nome, Float preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }
    
    public static List<Produto> obterProdutos() throws ClassNotFoundException, SQLException {
        return ProdutoDAO.getInstancia().obterProduto();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }
    
    
}
