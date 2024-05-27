/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe para interagir com a tabela extrato do banco de dados
 *
 * @author moijo
 */
public class ExtratoDAO {

    private Connection conn;

    /**
     * Construtor
     *
     * @param conn objeto Connection
     */
    public ExtratoDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Método para inserir dados do extrato ao banco de dados
     *
     * @param data Data formatada da transação
     * @param sinal + ou -
     * @param valor Valor da transação
     * @param moeda Moeda utilizada na transação
     * @param cota Cotação da moeda utilizada
     * @param taxa Taxa da moeda na transação executada
     * @param real Saldo em reais
     * @param btc Saldo em bitcoins
     * @param eth Saldo em ethereum
     * @param xrp Saldo em ripple
     * @param cpf cpf do investidor
     * @throws SQLException para erros de conexão com o banco de dados
     */
    public void inserirExtrato(String data, String sinal, String valor, String moeda,
            String cota, String taxa, String real, String btc, String eth,
            String xrp, String cpf) throws SQLException {

        String sql = "insert into extrato (datahora, sinal, valor, moeda, cota,"
                + " taxa, real, btc, eth, xrp, cpf) values ('" + data + "', '" + sinal
                + "', '" + valor + "', '" + moeda + "', '" + cota + "', '" + taxa + "', '" + real
                + "', '" + btc + "', '" + eth + "', '" + xrp + "', '" + cpf + "')";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();
        conn.close();
    }

    /**
     * Método para receber as informações de extrato do investidor
     *
     * @param cpf cpf do investidor
     * @return resultado um ResultSet contendo os extratos
     * @throws SQLException para erros de conexão com o banco de dados
     */
    public ResultSet consultar(String cpf) throws SQLException {
        String sql = "select * from extrato where cpf = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, cpf);
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        return resultado;
    }

}
