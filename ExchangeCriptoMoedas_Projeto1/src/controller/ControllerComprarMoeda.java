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
import view.JComprarMoeda;

/**
 *
 * @author moijo
 */
public class ControllerComprarMoeda {
    JComprarMoeda janela;

    public ControllerComprarMoeda(JComprarMoeda janela) {
        this.janela = janela;
    }
    
    public void ConfirmaSenha(Investidor inv){
        if(janela.getTxtSenha().getText().equals(inv.getSenha())){
            janela.getPnCripto().setVisible(true);
            atualizaValores(inv);
        }else{
            JOptionPane.showMessageDialog(janela, "Senha incorreta", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void atualizaValores(Investidor inv){
        String real, ctBtc, ctEth, ctXrp;
        ctBtc = inv.getCarteira().getMoedas().get(1).getCota().toString();
        ctEth = inv.getCarteira().getMoedas().get(2).getCota().toString();
        ctXrp = inv.getCarteira().getMoedas().get(3).getCota().toString();
        real = inv.getCarteira().getMoedas().get(0).getSaldo().toString();
        janela.getLblBtc().setText("Bitcoin: "+ctBtc);
        janela.getLblEth().setText("Ethereum: "+ctEth);
        janela.getLblXrp().setText("Ripple: "+ctXrp);
        janela.getLblSaldo().setText("Saldo: "+real);
    }
    
    public void ComprarMoeda(Investidor inv){
        Double valor = Double.parseDouble(janela.getTxtValor().getText());
        if(valor <= inv.getCarteira().getMoedas().get(0).getSaldo()){
            int moeda = 1+janela.getCbxMoedas().getSelectedIndex();
            System.out.println(moeda);
            Double valor2,valorFinal;
            valor2 = valor / inv.getCarteira().getMoedas().get(moeda).getCota();
            valorFinal = inv.getCarteira().getMoedas().get(moeda).tarifaCompra(valor);
            
            inv.getCarteira().getMoedas().get(0).setSaldo(
                inv.getCarteira().getMoedas().get(0).getSaldo()-valorFinal);
            
            inv.getCarteira().getMoedas().get(moeda).setSaldo(valor2);
            
            gerarExtrato(inv, valor, moeda);
            atualizaValores(inv);
            
            Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            InvestidorDAO daoInv = new InvestidorDAO(conn);
            daoInv.atualizar(inv);
            String saldos = saldosFormatados(inv);
            JOptionPane.showMessageDialog(janela, saldos, "Sucesso!", JOptionPane.PLAIN_MESSAGE);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(janela, "Falha de conexão ao atualizar investidor!", "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
            
        } else {
            JOptionPane.showMessageDialog(janela, "Saldo Insuficiente", "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        }    
    }
    
    public void gerarExtrato(Investidor inv, Double valor, int indexMoeda){
        String stValor,stReal,stBtc,stEth,stXrp,stCota,stTx, moeda;
        
        stValor = valor.toString();
        stReal = inv.getCarteira().getMoedas().get(0).getSaldo().toString();
        stBtc = inv.getCarteira().getMoedas().get(1).getSaldo().toString();
        stEth = inv.getCarteira().getMoedas().get(2).getSaldo().toString();
        stXrp = inv.getCarteira().getMoedas().get(3).getSaldo().toString();
        stCota = inv.getCarteira().getMoedas().get(indexMoeda).getCota().toString();
        stTx = inv.getCarteira().getMoedas().get(indexMoeda).getTxCompra().toString();
        moeda = inv.getCarteira().getMoedas().get(indexMoeda).getNome();
        
        
        
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
