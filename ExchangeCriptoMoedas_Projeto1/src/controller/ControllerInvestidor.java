/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.Conexao;
import DAO.InvestidorDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Random;
import javax.swing.JOptionPane;
import model.Investidor;
import view.JInvestidor;
import view.JLogin;

/**
 *
 * @author tioli
 */
public class ControllerInvestidor {
    private JInvestidor janela;

    public ControllerInvestidor(JInvestidor janela) {
        this.janela = janela;
        colocarNome(janela.getInvestidor());
    }
    
    public void colocarNome(Investidor investidor){
        janela.getLblNome().setText("Bem Vindo: "+investidor.getNome());
    }

    //Atualizar as cotas
    public void setAllCotas(Investidor inv, Double ctBtc, Double ctEth, Double ctXrp){
        inv.getCarteira().getMoedas().get(1).setCota(ctBtc);
        inv.getCarteira().getMoedas().get(2).setCota(ctEth);
        inv.getCarteira().getMoedas().get(3).setCota(ctXrp);
    }
    public boolean posOuNeg(){
        Random r = new Random();
        return r.nextBoolean();
    }  
    public Double numAle(){
        Random r = new Random();
        return r.nextDouble();
    }   
    public Double cotaAle(Double cotaAtual){
        Double ale = numAle();
        if(posOuNeg()){
            return (cotaAtual+(ale*5*(cotaAtual/100)));
        }
        else{
            return (cotaAtual-(ale*5*(cotaAtual/100)));
        }
    }
    public void atualizaValorCotas(Investidor inv){      
        DecimalFormat df = new DecimalFormat("+#.#;-#");
        
        Double ctAtBtc = inv.getCarteira().getMoedas().get(1).getCota();
        Double ctAtEth = inv.getCarteira().getMoedas().get(2).getCota();
        Double ctAtXrp = inv.getCarteira().getMoedas().get(3).getCota();
        
        Double ctNvBtc = cotaAle(ctAtBtc);
        Double ctNvEth = cotaAle(ctAtEth);
        Double ctNvXrp = cotaAle(ctAtXrp);
        
        String porcBtc = df.format(verificaPorcentagem(ctAtBtc, ctNvBtc));
        String porcEth = df.format(verificaPorcentagem(ctAtEth, ctNvEth));
        String porcXrp = df.format(verificaPorcentagem(ctAtXrp, ctNvXrp));
        
        setAllCotas(inv, ctNvBtc, ctNvEth, ctNvXrp);
        
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            InvestidorDAO daoInv = new InvestidorDAO(conn);
            daoInv.atualizar(inv);
            String cotas = cotasFormatados(inv, porcBtc, porcEth, porcXrp);
            JOptionPane.showMessageDialog(janela, cotas, "Sucesso!", JOptionPane.PLAIN_MESSAGE);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(janela, "Falha de conexão ao atualizar investidor!", "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }
    
    public String cotasFormatados(Investidor investidor, String porcBtc, 
            String porcEth, String porcXrp){
     
        String cotas = String.format("""
             Cotas atualizadas:
                Bitcoin: %s%%  Cotação: R$:%.2f
                Ethereum: %s%%  Cotação: R$:%.2f
                Ripple: %s%%  Cotação: R$:%.2f
             """,
              porcBtc, investidor.getCarteira().getMoedas().get(1).getCota(),
              porcEth, investidor.getCarteira().getMoedas().get(2).getCota(),
             porcXrp, investidor.getCarteira().getMoedas().get(3).getCota());
        
        return cotas;
    }
    
    public Double verificaPorcentagem(Double valor1, Double valor2){
        return (valor1-valor2)*(-100)/valor1;
    }
    
    public void Sair(){
        janela.setVisible(false);
        new JLogin().setVisible(true);
    }
    
}
