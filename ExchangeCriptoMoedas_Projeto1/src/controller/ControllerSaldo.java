/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.JOptionPane;
import view.JSaldo;
import model.Investidor;

/**
 *
 * @author moijo
 */
public class ControllerSaldo {
    private JSaldo janela;

    public ControllerSaldo(JSaldo janela) {
        this.janela = janela;
    }
    
    public void consultar(Investidor investidor){
        if(janela.getTxtSenha().getText().equals(investidor.getSenha())){
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
            
             janela.getTxtSaldos().setText(saldos);
        }
        else{
            JOptionPane.showMessageDialog(janela, "Senha incorreta", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
