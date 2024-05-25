/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import model.Carteira;
import model.Moeda;
import model.Real;

/**
 *
 * @author tioli
 */
public class ControllerCarteira {
    Carteira carteira;
    private ArrayList<Moeda> moedas;

    public ControllerCarteira(Carteira carteira) {
        this.carteira = carteira;
        moedas = carteira.getMoedas();
    }
    
    public boolean permitirCompra(Double valor){
        return moedas.get(0).getSaldo() >= valor;
    }
    public boolean permitirVenda(Double valor, String nome){
        for (Moeda m: moedas){
            if(m.getNome().equals(nome)){
                if (m.getSaldo() >= valor){
                    return true;
                }
            }
        }
        return false;
    }
    
    public Double saldoReal(){
        return moedas.get(0).getSaldo();
    }
    public String saldoBtc(){
        BigDecimal btc = new BigDecimal(moedas.get(1).getSaldo());
        return btc.stripTrailingZeros().toPlainString();
    }
    public String saldoEth(){
        BigDecimal eth = new BigDecimal(moedas.get(2).getSaldo());
        return eth.stripTrailingZeros().toPlainString();
    }
    public String saldoXrp(){
        BigDecimal xrp = new BigDecimal(moedas.get(3).getSaldo());
        return xrp.stripTrailingZeros().toPlainString();
    }
    
    public void deposito(Double valor){
        moedas.get(0).setSaldo(valor);
    }
    public boolean saque(Double valor){
        if (permitirVenda(valor, moedas.get(0).getNome())){
            moedas.get(0).setSaldo(((Real)moedas.get(0)).tarifaCompra(valor));
            return true;
        }
        return false;
    }
    
    public void comprarMoeda(){
        
    }
    
    //Atualizar as cotas
    public void setAllCotas(Double ctBtc, Double ctEth, Double ctXrp){
        moedas.get(1).setCota(ctBtc);
        moedas.get(2).setCota(ctEth);
        moedas.get(3).setCota(ctXrp);
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
    public void atualizaValorCotas(){
        Double ctAtBtc = moedas.get(1).getCota();
        Double ctAtEth = moedas.get(2).getCota();
        Double ctAtXrp = moedas.get(3).getCota();
        
        Double ctNvBtc = cotaAle(ctAtBtc);
        Double ctNvEth = cotaAle(ctAtEth);
        Double ctNvXrp = cotaAle(ctAtXrp);
        
        setAllCotas(ctNvBtc, ctNvEth, ctNvXrp);   
    }
  
    
}
