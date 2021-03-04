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
import model.ItemPedido;
import model.Pedido;

/**
 *
 * @author anton
 */
public class PedidoDAO extends DAO{
    private static PedidoDAO instancia = new PedidoDAO();

    public static PedidoDAO getInstancia() {
        return instancia;
    }

    private PedidoDAO() {

    }

    public int gravar(Pedido pedido) throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        int id = 0;
        try {
            conexao = BD.getInstancia().getConecao();
            conexao.setAutoCommit(false);
            comando = conexao.prepareStatement("insert into pedido (cod_cliente, data_venda, valor_total) values (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

             //comando = conexao.prepareStatement("insert into pedido (cod_cliente, data_venda, valor_total, status) values (?,?,?,?)",
            //       Statement.RETURN_GENERATED_KEYS);
            comando.setInt(1, pedido.getCliente().getCodigo());
            comando.setDate(2, new java.sql.Date(pedido.getDataPedido().getTime()));
            comando.setFloat(3, pedido.getValorTotal());
            //comando.setString(4, pedido.getPedidoEstado());
            comando.executeUpdate();

            ResultSet rs = comando.getGeneratedKeys();
            id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            int num_item = 0;
            for (ItemPedido item : pedido.getItensPedido()) {
                comando = conexao.prepareStatement("insert into item_pedido (num_pedido, num_item, cod_produto, quantidade, valor_unitario) values (?,?,?,?,?)");
                num_item = num_item + 1;
                comando.setInt(1, id);
                comando.setInt(2, num_item);
                comando.setInt(3, item.getProduto().getCodigo());
                comando.setFloat(4, item.getQuantidade());
                comando.setFloat(5, item.getPrecoUnitario());
                comando.executeUpdate();
            }

            conexao.commit();
        } catch (SQLException e) {
            conexao.rollback();
        } finally {
            fecharConexao(conexao, comando);
        }

        return id;
    }
}
