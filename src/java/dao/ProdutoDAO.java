/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Produto;

/**
 *
 * @author anton
 */
public class ProdutoDAO extends DAO {

    private static ProdutoDAO instancia = new ProdutoDAO();

    public static ProdutoDAO getInstancia() {
        return instancia;
    }

    private ProdutoDAO() {
    }

    public Produto obterCliente(int codCliente) throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        Statement comando = null;
        Produto produto = null;

        try {
            conexao = BD.getInstancia().getConecao();
            comando = conexao.createStatement();
            ResultSet rs = comando.executeQuery("select * from produto where codigo" + codCliente);
            rs.first();
            produto = instanciarProduto(rs);
        } finally {
            fecharConexao(conexao, comando);
        }
        return produto;
    }

    public List<Produto> obterCliente() throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        Statement comando = null;
        List<Produto> clientes = new ArrayList<Produto>();
        Produto produto = null;

        try {
            conexao = BD.getInstancia().getConecao();
            comando = conexao.createStatement();
            ResultSet rs = comando.executeQuery("select * from produto");
            rs.first();
            produto = instanciarProduto(rs);
        } finally {
            fecharConexao(conexao, comando);
        }
        return clientes;
    }

    public Produto instanciarProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto(rs.getInt("codigo"),
                rs.getString("nome"), rs.getFloat("preco"));
        return produto;
    }
    
     public void gravar(Produto produto) throws ClassNotFoundException, SQLException{
        Connection conexao = null;
        PreparedStatement comando = null;
        try{
            conexao = BD.getInstancia().getConecao();
            comando = conexao.prepareStatement("insert into cliente (codigo,nome,preco) values (?,?,?)");
            comando.setInt(1, produto.getCodigo());
            comando.setString(2,produto.getNome());
            comando.setFloat(3,produto.getPreco());
            comando.executeUpdate();
        }finally{
            fecharConexao(conexao, comando);
        }
    }
}
