/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.Conexao;
import DAO.ExtratoDAO;
import DAO.InvestidorDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import model.Investidor;
import view.JDeposito;

/**
 *
 * @author moijo
 */
public class ControllerDeposito {
    private JDeposito janela;

    public ControllerDeposito(JDeposito janela) {
        this.janela = janela;
    }
    
    public void depositar(Investidor investidor){
        Double valor = Double.parseDouble(janela.getTxtDeposito().getText());
        investidor.getCarteira().getMoedas().get(0).setSaldo(
                investidor.getCarteira().getMoedas().get(0).getSaldo()+valor);
        
        
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            InvestidorDAO daoInv = new InvestidorDAO(conn);
            daoInv.atualizar(investidor);
            String saldos = saldosFormatados(investidor);
            gerarExtrato(investidor, valor);
            JOptionPane.showMessageDialog(janela, saldos, "Sucesso!", JOptionPane.PLAIN_MESSAGE);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(janela, "Falha de conexão ao atualizar investidor!", "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }       
    }
    
    public void gerarExtrato(Investidor inv, Double valor){
        String stValor,stReal,stBtc,stEth,stXrp,stCota,stTx, moeda;
        
        stValor = valor.toString();
        stReal = inv.getCarteira().getMoedas().get(0).getSaldo().toString();
        stBtc = inv.getCarteira().getMoedas().get(1).getSaldo().toString();
        stEth = inv.getCarteira().getMoedas().get(2).getSaldo().toString();
        stXrp = inv.getCarteira().getMoedas().get(3).getSaldo().toString();
        stCota = inv.getCarteira().getMoedas().get(0).getCota().toString();
        stTx = inv.getCarteira().getMoedas().get(0).getTxCompra().toString();
        moeda = inv.getCarteira().getMoedas().get(0).getNome();
        
        
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            ExtratoDAO daoext = new ExtratoDAO(conn);
            daoext.inserirExtrato(dtf.format(now), "+", stValor, moeda, stCota, 
                    stTx, stReal, stBtc, stEth, stXrp, inv.getCpf());
        }catch(SQLException e){
            JOptionPane.showMessageDialog(janela, "Falha de conexão ao gerar extrato!", "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }
    
    public String saldosFormatados(Investidor investidor){
        String saldos = String.format("""
             Nome: %s
             CPF: %s

             Real: %.2f
             Bitcoin: %s
             Ethereum: %s
             Ripple: %s
             """, investidor.getNome(), investidor.getCpf(),
             investidor.getCarteira().getMoedas().get(0).getSaldo(),
             investidor.getCarteira().getMoedas().get(1).getSaldo().toString(),
             investidor.getCarteira().getMoedas().get(2).getSaldo().toString(),
             investidor.getCarteira().getMoedas().get(3).getSaldo().toString());
        
        return saldos;
    }
            
}
