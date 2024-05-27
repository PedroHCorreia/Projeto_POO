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
import view.JSaque;

/**
 * Classe para controlar a janela JSaque
 *
 * @author moijo
 */
public class ControllerSaque {

    private JSaque janela;

    /**
     * Construtor
     *
     * @param janela obj JSaque
     */
    public ControllerSaque(JSaque janela) {
        this.janela = janela;
    }

    /**
     * Metodo para retirar um valor do saldo de Real do investidor
     *
     * @param inv obj Investidor
     */
    public void sacar(Investidor inv) {
        Double valor = Double.parseDouble(janela.getTxtSaque().getText());
        //Verificando se o investidor possui saldo o suficiente
        if (valor <= inv.getCarteira().getMoedas().get(0).getSaldo()) {
            //Retirando o valor do saldo de Real do investidor
            inv.getCarteira().getMoedas().get(0).setSaldo(
                    inv.getCarteira().getMoedas().get(0).getSaldo() - valor);

            //Atualizando os dados do investidor no banco de dados
            Conexao conexao = new Conexao();
            try {
                Connection conn = conexao.getConnection();
                InvestidorDAO daoInv = new InvestidorDAO(conn);
                daoInv.atualizar(inv);
                String saldos = saldosFormatados(inv);
                gerarExtrato(inv, valor);
                JOptionPane.showMessageDialog(janela, saldos, "Sucesso!", JOptionPane.PLAIN_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(janela, "Falha de conexão ao atualizar investidor!", "Erro", JOptionPane.ERROR_MESSAGE);
                System.out.println(e);
            }

        } else {
            JOptionPane.showMessageDialog(janela, "Saldo Insuficiente", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo para gerar extratos
     *
     * @param inv objeto Investidor
     * @param valor Double do valor da transacao
     */
    public void gerarExtrato(Investidor inv, Double valor) {
        String stValor, stReal, stBtc, stEth, stXrp, stCota, stTx, moeda;

        // Recebendo os valores e convertendo para String
        stValor = valor.toString();
        stReal = inv.getCarteira().getMoedas().get(0).getSaldo().toString();
        stBtc = inv.getCarteira().getMoedas().get(1).getSaldo().toString();
        stEth = inv.getCarteira().getMoedas().get(2).getSaldo().toString();
        stXrp = inv.getCarteira().getMoedas().get(3).getSaldo().toString();
        stCota = inv.getCarteira().getMoedas().get(0).getCota().toString();
        stTx = inv.getCarteira().getMoedas().get(0).getTxCompra().toString();
        moeda = inv.getCarteira().getMoedas().get(0).getNome();

        //Pegando a data formatada
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        //Inserindo o extrato no banco de dados
        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConnection();
            ExtratoDAO daoext = new ExtratoDAO(conn);
            daoext.inserirExtrato(dtf.format(now), "-", stValor, moeda, stCota,
                    stTx, stReal, stBtc, stEth, stXrp, inv.getCpf());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(janela, "Falha de conexão ao gerar extrato!", "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }

    /**
     * Metodo para formatar os saldos em uma String
     *
     * @param investidor obj Investidor
     * @return saldos String com os saldos formatados
     */
    public String saldosFormatados(Investidor investidor) {
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
