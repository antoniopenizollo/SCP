/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import controller.Action;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import model.PedidoEstadoRecebido;
import model.Produto;

/**
 *
 * @author anton
 */
public class EfetuarPedidoAction implements Action{

    public EfetuarPedidoAction() {}

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String acao = request.getParameter("acao");
        if (acao.equals("preparar")) {
            try {
                preparar(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(EfetuarPedidoAction.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EfetuarPedidoAction.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(EfetuarPedidoAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (acao.equals("confirmar")) {
                confirmar(request, response);
            }
        }
    }
    
    protected void preparar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, ClassNotFoundException, SQLException {
        try {
            request.setAttribute("clientes", Cliente.obterClientes());
            request.setAttribute("produtos", Produto.obterProdutos());
            RequestDispatcher view = request.getRequestDispatcher("/efetuarPedido.jsp");
            view.forward(request, response);
        } catch (ServletException e) {
            throw e;
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }
    
    public void confirmar(HttpServletRequest request, HttpServletResponse response) throws IOException {
       try {
            Integer codCliente = Integer.parseInt(request.getParameter("cliente"));
            Cliente cliente = Cliente.obterClientes(codCliente);
            String itensProdutos = request.getParameter("itensProdutos");
            String[] produtos = itensProdutos.split(";");
            String itensQuantidades = request.getParameter("itensQuantidades");
            String[] quantidades = itensQuantidades.split(";");
            String itensPrecos = request.getParameter("itensPrecos");
            String[] precos = itensPrecos.split(";");
            Float valorTotal = Float.parseFloat(request.getParameter("valorTotal"));

            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setDataPedido(new Date());
            pedido.setValorTotal(valorTotal);
            //pedido.setPedidoEstado(new PedidoEstadoRecebido());

            for (int i = 0; i < produtos.length; i++) {
                Produto produto = Produto.obterProduto(Integer.parseInt(produtos[i]));
                ItemPedido item = new ItemPedido(produto, Float.parseFloat(quantidades[i]), Float.parseFloat(precos[i]));
                pedido.adicionarItem(item);
            }
            int num_pedido = pedido.gravar();
            response.getWriter().println(num_pedido);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EfetuarPedidoAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EfetuarPedidoAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EfetuarPedidoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
