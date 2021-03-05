/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.ClienteDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
//import util.Email;

/**
 *
 * @author anton
 */
public class Cliente implements Observer{
    
    private Integer codigo;
    private String nome;
    private String email;

    public Cliente(Integer codigo, String nome, String email) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
    }
    
    public static List<Cliente> obterClientes() throws ClassNotFoundException, SQLException {
        return ClienteDAO.getInstancia().obterCliente();
    }
    
    public static Cliente obterClientes(int codCliente) throws ClassNotFoundException, SQLException {
        return ClienteDAO.getInstancia().obterCliente(codCliente);
    }
    
    public void gravar() throws ClassNotFoundException, SQLException{
        ClienteDAO.getInstancia().gravar(this);
    }
    
    public void alterar() throws ClassNotFoundException, SQLException{
        ClienteDAO.getInstancia().alterar(this);
    }
    
    public void excluir() throws ClassNotFoundException, SQLException{
        ClienteDAO.getInstancia().excluir(this);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public void update(Observable pedidoObservado, Object arg1) {
        if (pedidoObservado instanceof Pedido) {
            Pedido pedido = (Pedido)pedidoObservado;
//            Email.enviarEmail(
//                    "Estado alterado de seu pedido!",
//                    "Olá " + pedido.getCliente().getNome() + "! Seu pedido " + pedido.getNumero() + " mudou para o estado " + pedido.getNomeEstado() + ". Agradecemos sua preferência!",
//                    pedido.getCliente().getEmail());
            //System.err.println("Olá " + pedido.getCliente() + "! Seu pedido " + pedido.getNumero() + " mudou para o estado " + pedido.getNomeEstado() + ". Agradecemos sua preferência!");
        }
    }
}
