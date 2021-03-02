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

    public Produto obterProduto(int codProduto) throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        Statement comando = null;
        Produto produto = null;

        try {
            conexao = BD.getInstancia().getConecao();
            comando = conexao.createStatement();
            ResultSet rs = comando.executeQuery("select * from produto where codigo = " + codProduto);
             while(rs.next()){
                produto = instanciarProduto(rs);
            }

        } finally {
            fecharConexao(conexao, comando);
        }
        return produto;
    }

    public List<Produto> obterProduto() throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        Statement comando = null;
        List<Produto> produtos = new ArrayList<Produto>();
        Produto produto = null;

        try {
            conexao = BD.getInstancia().getConecao();
            comando = conexao.createStatement();
            ResultSet rs = comando.executeQuery("select * from produto");
            while(rs.next()){
                produto = instanciarProduto(rs);
                produtos.add(produto);
            }
        } finally {
            fecharConexao(conexao, comando);
        }
        return produtos;
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
            comando = conexao.prepareStatement("insert into produto (codigo,nome,preco) values (?,?,?)");
            comando.setInt(1, produto.getCodigo());
            comando.setString(2,produto.getNome());
            comando.setFloat(3,produto.getPreco());
            comando.executeUpdate();
        }finally{
            fecharConexao(conexao, comando);
        }
    }
     
     public void alterar(Produto produto) throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        Statement comando = null;
        String stringSQL = null;
        try {
            conexao = BD.getInstancia().getConecao();
            stringSQL = "update produto set "
                    + "nome = '" + produto.getNome() + "', "
                    + "preco = " + produto.getPreco()+ "', "
                    + "where codigo = " + produto.getCodigo();
            comando.execute(stringSQL);
        } finally {
            fecharConexao(conexao, comando);
        }
    }
    
    public void excluir(Produto produto) throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        Statement comando = null;
        String stringSQL = null;
        try {
            conexao = BD.getInstancia().getConecao();
            stringSQL = "delete from produto where codigo = "
                    + produto.getCodigo();
            comando.execute(stringSQL);
        } finally {
            fecharConexao(conexao, comando);
        }
    }
}
