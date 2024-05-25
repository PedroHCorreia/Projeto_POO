/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.Conexao;
import DAO.ExtratoDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Investidor;
import view.JExtratos;

/**
 *
 * @author moijo
 */
public class ControllerExtrato {
    private JExtratos janela;

    public ControllerExtrato(JExtratos janela) {
        this.janela = janela;
    }
    
    public void consultarExtrato(Investidor inv){
        if(janela.getTxtSenha().getText().equals(inv.getSenha())){
            Conexao conexao = new Conexao();
            
            try{
                Connection conn = conexao.getConnection();
                ExtratoDAO dao = new ExtratoDAO(conn);
                ResultSet res = dao.consultar(inv.getCpf());
                
                String extratos = "";
                while(res.next()){
                    extratos += String.format("%s  %s  %s  %s  CT:  %s  TX:  %s  "
                            + "REAL: %s  BTC: %s  ETH: %s  XRP: %s\n",
        res.getString("datahora"), res.getString("sinal"), res.getString("valor"),
        res.getString("moeda"), res.getString("cota"), res.getString("taxa"),
        res.getString("real"), res.getString("btc"), res.getString("eth"), 
        res.getString("xrp"));
                }
                
                janela.getTxtExtratos().setText(extratos);

            }catch(SQLException e){
                JOptionPane.showMessageDialog(janela, "Falha de conexão ao buscar o extrato!", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                System.out.println(e);
            }
        }else{
            JOptionPane.showMessageDialog(janela, "Senha incorreta", "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
