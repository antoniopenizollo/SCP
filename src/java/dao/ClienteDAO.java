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
import model.Cliente;

/**
 *
 * @author anton
 */
public class ClienteDAO extends DAO {

    private static ClienteDAO instancia = new ClienteDAO();

    public static ClienteDAO getInstancia() {
        return instancia;
    }

    private ClienteDAO() {
    }

    public Cliente obterCliente(int codCliente) throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        Statement comando = null;
        Cliente cliente = null;

        try {
            conexao = BD.getInstancia().getConecao();
            comando = conexao.createStatement();
            ResultSet rs = comando.executeQuery("select * from cliente where codigo = " + codCliente);
            while(rs.next()){
                cliente = instanciarCliente(rs);
            }
        } finally {
            fecharConexao(conexao, comando);
        }
        return cliente;
    }

    public List<Cliente> obterCliente() throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        Statement comando = null;
        List<Cliente> clientes = new ArrayList<Cliente>();
        Cliente cliente = null;
        try {
            conexao = BD.getInstancia().getConecao();
            comando = conexao.createStatement();
            ResultSet rs = comando.executeQuery("select * from cliente");
            while(rs.next()){
                cliente = instanciarCliente(rs);
                clientes.add(cliente);
            }
            
        } finally {
            fecharConexao(conexao, comando);
        }
        return clientes;
    }

    public Cliente instanciarCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente(rs.getInt("codigo"),
                rs.getString("nome"), rs.getString("email"));
        return cliente;
    }

    public void gravar(Cliente cliente) throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        try {
            conexao = BD.getInstancia().getConecao();
            comando = conexao.prepareStatement("insert into cliente (codigo,nome,email) values (?,?,?)");
            comando.setInt(1, cliente.getCodigo());
            comando.setString(2, cliente.getNome());
            comando.setString(3, cliente.getEmail());
            comando.executeUpdate();
        } finally {
            fecharConexao(conexao, comando);
        }
    }

    public void alterar(Cliente cliente) throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        Statement comando = null;
        String stringSQL = null;
        try {
            conexao = BD.getInstancia().getConecao();
            comando = conexao.createStatement();
            stringSQL = "update cliente set "
                    + "nome = '" + cliente.getNome() + "', "
                    + "email = '" + cliente.getEmail() + "' "
                    + "where codigo = " + cliente.getCodigo();
            comando.execute(stringSQL);
        } finally {
            fecharConexao(conexao, comando);
        }
    }
    
    public void excluir(Cliente cliente) throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        Statement comando = null;
        String stringSQL = null;
        try {
            conexao = BD.getInstancia().getConecao();
            comando = conexao.createStatement();
            stringSQL = "delete from cliente where codigo = "
                    + cliente.getCodigo();
            comando.execute(stringSQL);
        } finally {
            fecharConexao(conexao, comando);
        }
    }
}
