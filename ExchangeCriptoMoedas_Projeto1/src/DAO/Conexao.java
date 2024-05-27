/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe para se conectar ao banco de dados
 *
 * @author moijo
 */
public class Conexao {

    /**
     * Método para se conectar ao banco de dados
     *
     * @return conexao um objeto Connection
     * @throws SQLException para erros de conexão com o banco de dados
     */
    public Connection getConnection() throws SQLException {
        Connection conexao = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/exchangecriptomoedas",
                "postgres", "root");
        return conexao;
    }

}
