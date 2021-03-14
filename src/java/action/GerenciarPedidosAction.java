/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import controller.Action;
import controller.EfetuarPedidoController;
import controller.GerenciarPedidosController;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pedido;

/**
 *
 * @author anton
 */
public class GerenciarPedidosAction implements Action{

    public GerenciarPedidosAction() {}
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String acao = request.getParameter("acao");
        if (acao.equals("listar")) {
            try {
                listar(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(GerenciarPedidosAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (acao.equals("mudarEstado")) {
                try {
                    mudarEstado(request, response);
                } catch (ServletException ex) {
                    Logger.getLogger(GerenciarPedidosAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            request.setAttribute("pedidos", Pedido.obterPedidos());
            RequestDispatcher view = request.getRequestDispatcher("/gerenciarPedidos.jsp");
            view.forward(request, response);
        } catch (ServletException e) {
                throw e;
            } catch (IOException e) {
                throw new ServletException(e);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EfetuarPedidoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(EfetuarPedidoController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void mudarEstado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer numero = Integer.parseInt(request.getParameter("numero"));
            String estado = request.getParameter("estado");
            Pedido pedido = Pedido.obterPedido(numero);
            
            boolean resultado = false;
            System.out.println("Numero: " + numero + " Estado: " + estado + " Pedido: " + pedido.getNumero());
            Class classe = Class.forName("model.Pedido");
            Method metodo = classe.getMethod(estado);
            resultado = (Boolean) metodo.invoke(pedido);
            
            String mensagem = "";
            if (resultado) {
                mensagem = "Estado alterado com sucesso";
            } else {
                mensagem = "Não foi possível alterar estado do pedido";
            }
            response.getWriter().println(mensagem);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GerenciarPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GerenciarPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(GerenciarPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(GerenciarPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GerenciarPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(GerenciarPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(GerenciarPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
