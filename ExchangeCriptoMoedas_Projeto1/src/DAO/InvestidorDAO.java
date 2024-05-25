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
 *
 * @author moijo
 */
public class InvestidorDAO {
    private Connection conn;
    
    public InvestidorDAO(Connection conn) {
        this.conn = conn;
    }
    
    public ResultSet consultar(Investidor investidor) throws SQLException{
        String sql = "select * from investidor where cpf = ? and senha = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, investidor.getCpf());
        statement.setString(2, investidor.getSenha());
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        return resultado;
    }
    
    public void atualizar(Investidor investidor) throws SQLException{
        String sql = "UPDATE investidor SET \"real\"=?, btc=?, eth=?, xrp=?, ctbtc=?, cteth=?, ctxrp=? WHERE cpf = ?";
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
