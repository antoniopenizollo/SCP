/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author anton
 */
public class BD {
    private static BD instancia = new BD();
    public static BD getInstancia(){
        return instancia;
    }
    private BD(){}
    
    public Connection getConecao() throws ClassNotFoundException, SQLException{
        Connection conexao = null;
        Class.forName("org.postgresql.Driver");
        conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/scp","postgres","admin");
        return conexao;
    }
}
