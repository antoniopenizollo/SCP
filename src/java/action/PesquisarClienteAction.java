/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import controller.Action;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cliente;

/**
 *
 * @author anton
 */
public class PesquisarClienteAction implements Action{
    public PesquisarClienteAction() {
        
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            request.setAttribute("clientes", Cliente.obterClientes());
            RequestDispatcher view =
                    request.getRequestDispatcher("/pesquisaCliente.jsp");
            view.forward(request, response);
        }catch (ClassNotFoundException | SQLException e) {
            try {
                throw new ServletException(e);
            } catch (ServletException ex) {
                Logger.getLogger(PesquisarClienteAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ServletException ex) {
            Logger.getLogger(PesquisarClienteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
