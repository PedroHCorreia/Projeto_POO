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
 *
 * @author moijo
 */
public class ExtratoDAO {
    private Connection conn;

    public ExtratoDAO(Connection conn) {
        this.conn = conn;
    }
    
    public void inserirExtrato(String data, String sinal, String valor, String moeda, 
            String cota, String taxa, String real, String btc, String eth, 
            String xrp, String cpf) throws SQLException{
        
        String sql = "insert into extrato (datahora, sinal, valor, moeda, cota,"
                + " taxa, real, btc, eth, xrp, cpf) values ('"+data+"', '"+sinal
                +"', '"+valor+"', '"+moeda+"', '"+cota+"', '"+taxa+"', '"+real
                +"', '"+btc+"', '"+eth+"', '"+xrp+"', '"+cpf+"')";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();
        conn.close();
    }
    
    public ResultSet consultar(String cpf) throws SQLException{
        String sql = "select * from extrato where cpf = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, cpf);
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        return resultado;
    }
    
}
