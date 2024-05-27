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
 * Classe para controlar a janela JComprarMoeda
 *
 * @author moijo
 */
public class ControllerComprarMoeda {

    JComprarMoeda janela;

    /**
     * Construtor
     *
     * @param janela objeto JComprarMoeda
     */
    public ControllerComprarMoeda(JComprarMoeda janela) {
        this.janela = janela;
    }

    /**
     * Metodo para confirmar a senha digitada
     *
     * @param inv objeto Investidor
     */
    public void ConfirmaSenha(Investidor inv) {
        if (janela.getTxtSenha().getText().equals(inv.getSenha())) {
            janela.getPnCripto().setVisible(true);
            atualizaValores(inv);
        } else {
            JOptionPane.showMessageDialog(janela, "Senha incorreta", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo para atualizar os saldos do investidor
     *
     * @param inv objeto Investidor
     */
    public void atualizaValores(Investidor inv) {
        String real, ctBtc, ctEth, ctXrp;
        ctBtc = inv.getCarteira().getMoedas().get(1).getCota().toString();
        ctEth = inv.getCarteira().getMoedas().get(2).getCota().toString();
        ctXrp = inv.getCarteira().getMoedas().get(3).getCota().toString();
        real = inv.getCarteira().getMoedas().get(0).getSaldo().toString();
        janela.getLblBtc().setText("Bitcoin: " + ctBtc);
        janela.getLblEth().setText("Ethereum: " + ctEth);
        janela.getLblXrp().setText("Ripple: " + ctXrp);
        janela.getLblSaldo().setText("Saldo: " + real);
    }

    /**
     * Metodo para comprar criptomoedas
     *
     * @param inv objeto Investidor
     */
    public void ComprarMoeda(Investidor inv) {
        Double valor = Double.parseDouble(janela.getTxtValor().getText());

        //Recebendo o index da moeda escolhida (somando 1 porque o real nao eh um opcao)
        int moeda = 1 + janela.getCbxMoedas().getSelectedIndex();

        Double saldoMoeda, valorComCotacao, valorReal;
        //Convertendo o valor para a criptomoeda desejada
        valorComCotacao = valor / inv.getCarteira().getMoedas().get(moeda).getCota();
        //Aplicando a taxa de compra sobre o valor a ser descontado
        valorReal = inv.getCarteira().getMoedas().get(moeda).tarifaCompra(valor);

        //Verificando se o investidor possui saldo o suficiente
        if (valorReal <= inv.getCarteira().getMoedas().get(0).getSaldo()) {
            //Caso sim, atualiza os valores de saldo e gera um extrato

            inv.getCarteira().getMoedas().get(0).setSaldo(
                    inv.getCarteira().getMoedas().get(0).getSaldo() - valorReal);

            saldoMoeda = inv.getCarteira().getMoedas().get(moeda).getSaldo();
            inv.getCarteira().getMoedas().get(moeda).setSaldo(saldoMoeda + valorComCotacao);

            gerarExtrato(inv, valor, moeda);
            atualizaValores(inv);

            //Atualizando os dados do investidor no banco de dados
            Conexao conexao = new Conexao();
            try {
                Connection conn = conexao.getConnection();
                InvestidorDAO daoInv = new InvestidorDAO(conn);
                daoInv.atualizar(inv);
                String saldos = saldosFormatados(inv);
                JOptionPane.showMessageDialog(janela, saldos, "Sucesso!", JOptionPane.PLAIN_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(janela, "Falha de conexão ao atualizar investidor!", "Erro", JOptionPane.ERROR_MESSAGE);
                System.out.println(e);
            }
            //Caso o saldo nao seja suficiente
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
     * @param indexMoeda int do index da moeda
     */
    public void gerarExtrato(Investidor inv, Double valor, int indexMoeda) {
        String stValor, stReal, stBtc, stEth, stXrp, stCota, stTx, moeda;

        // Recebendo os valores e convertendo para String
        stValor = valor.toString();
        stReal = inv.getCarteira().getMoedas().get(0).getSaldo().toString();
        stBtc = inv.getCarteira().getMoedas().get(1).getSaldo().toString();
        stEth = inv.getCarteira().getMoedas().get(2).getSaldo().toString();
        stXrp = inv.getCarteira().getMoedas().get(3).getSaldo().toString();
        stCota = inv.getCarteira().getMoedas().get(indexMoeda).getCota().toString();
        stTx = inv.getCarteira().getMoedas().get(indexMoeda).getTxCompra().toString();
        moeda = inv.getCarteira().getMoedas().get(indexMoeda).getNome();

        //Pegando a data formatada
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        //Inserindo o extrato no banco de dados
        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConnection();
            ExtratoDAO daoext = new ExtratoDAO(conn);
            daoext.inserirExtrato(dtf.format(now), "+", stValor, moeda, stCota,
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
