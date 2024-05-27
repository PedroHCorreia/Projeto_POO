/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Investidor;
import java.sql.ResultSet;

/**
 * Classe para interagir com a tabela investidor do banco de dados
 *
 * @author moijo
 */
public class InvestidorDAO {

    private Connection conn;

    /**
     * Construtor
     *
     * @param conn objeto Connection
     */
    public InvestidorDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Método para recuperar as informações do investidor
     *
     * @param investidor objeto Investidor
     * @return resultado um ResultSet contendo os dados do investidor
     * @throws SQLException para erros de conexão com o banco de dados
     */
    public ResultSet consultar(Investidor investidor) throws SQLException {
        String sql = "select * from investidor where cpf = ? and senha = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, investidor.getCpf());
        statement.setString(2, investidor.getSenha());
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        return resultado;
    }

    /**
     * Método para atualizar as informações do investidor
     *
     * @param investidor objeto Investidor
     * @throws SQLException para erros de conexão com o banco de dados
     */
    public void atualizar(Investidor investidor) throws SQLException {
        String sql = "UPDATE investidor SET \"real\"=?, btc=?, eth=?, xrp=?, "
                + "ctbtc=?, cteth=?, ctxrp=? WHERE cpf = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setDouble(1, investidor.getCarteira().getMoedas().get(0).getSaldo());
        statement.setDouble(2, investidor.getCarteira().getMoedas().get(1).getSaldo());
        statement.setDouble(3, investidor.getCarteira().getMoedas().get(2).getSaldo());
        statement.setDouble(4, investidor.getCarteira().getMoedas().get(3).getSaldo());
        statement.setDouble(5, investidor.getCarteira().getMoedas().get(1).getCota());
        statement.setDouble(6, investidor.getCarteira().getMoedas().get(2).getCota());
        statement.setDouble(7, investidor.getCarteira().getMoedas().get(3).getCota());
        statement.setString(8, investidor.getCpf());
        statement.execute();
        conn.close();
    }

}
