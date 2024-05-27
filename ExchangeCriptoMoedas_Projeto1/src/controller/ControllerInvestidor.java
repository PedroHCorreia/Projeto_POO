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
 * Classe para controlar a janela JInvestidor
 *
 * @author tioli
 */
public class ControllerInvestidor {

    private JInvestidor janela;

    /**
     * Construtor
     *
     * @param janela obj JInvestidor
     */
    public ControllerInvestidor(JInvestidor janela) {
        this.janela = janela;
        //Adicionando o nome do investidor a parte de cima da janela
        colocarNome(janela.getInvestidor());
    }

    /**
     * Metodo para colocar o nome do investidor a parte de cima da janela
     *
     * @param investidor obj Investidor
     */
    public void colocarNome(Investidor investidor) {
        janela.getLblNome().setText("Bem Vindo: " + investidor.getNome());
    }

    //Atualizar as cotas
    /**
     * Metodo para atualizar as cotas no obj Investidor
     *
     * @param inv obj Investidor
     * @param ctBtc Double com a cotacao do bitcoin
     * @param ctEth Double com a cotacao do ethereum
     * @param ctXrp Double com a cotacao do ripple
     */
    public void setAllCotas(Investidor inv, Double ctBtc, Double ctEth, Double ctXrp) {
        inv.getCarteira().getMoedas().get(1).setCota(ctBtc);
        inv.getCarteira().getMoedas().get(2).setCota(ctEth);
        inv.getCarteira().getMoedas().get(3).setCota(ctXrp);
    }

    /**
     * Metodo para decidir se o valor de cotacao aumenta ou diminui
     *
     * @return boolean para informar se o valor de cotacao aumenta ou diminui
     */
    public boolean posOuNeg() {
        Random r = new Random();
        return r.nextBoolean();
    }

    /**
     * Metodo para gerar um numero aleatorio
     *
     * @return Double aleatorio
     */
    public Double numAle() {
        Random r = new Random();
        return r.nextDouble();
    }

    /**
     * Metodo para retornar o valor de cotacao atualizado
     *
     * @param cotaAtual Double do valor atual da cotacao
     * @return Double com o valor atualizado da cotacao
     */
    public Double cotaAle(Double cotaAtual) {
        Double ale = numAle();
        //Aumentando ou diminuindo o valor de forma aleatoria
        if (posOuNeg()) {
            return (cotaAtual + (ale * 5 * (cotaAtual / 100)));
        } else {
            return (cotaAtual - (ale * 5 * (cotaAtual / 100)));
        }
    }

    /**
     * Metodo para atualizar os valores de cotacao de forma aleatoria
     *
     * @param inv obj Investidor
     */
    public void atualizaValorCotas(Investidor inv) {
        //Criando um formato de Double para as porcentagens em que o sinal esta presente
        DecimalFormat df = new DecimalFormat("+#.#;-#");

        //Recebendo as cotas atuais
        Double ctAtBtc = inv.getCarteira().getMoedas().get(1).getCota();
        Double ctAtEth = inv.getCarteira().getMoedas().get(2).getCota();
        Double ctAtXrp = inv.getCarteira().getMoedas().get(3).getCota();

        //Recebendo os novos valores para as cotas
        Double ctNvBtc = cotaAle(ctAtBtc);
        Double ctNvEth = cotaAle(ctAtEth);
        Double ctNvXrp = cotaAle(ctAtXrp);

        //Recebendo os valores da porcentagem de alteracao das moedas
        String porcBtc = df.format(verificaPorcentagem(ctAtBtc, ctNvBtc));
        String porcEth = df.format(verificaPorcentagem(ctAtEth, ctNvEth));
        String porcXrp = df.format(verificaPorcentagem(ctAtXrp, ctNvXrp));

        //Atualizando os valores das cotas no obj Investidor
        setAllCotas(inv, ctNvBtc, ctNvEth, ctNvXrp);

        //Atualizando o obj Investidor no banco de dados
        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConnection();
            InvestidorDAO daoInv = new InvestidorDAO(conn);
            daoInv.atualizar(inv);
            String cotas = cotasFormatados(inv, porcBtc, porcEth, porcXrp);
            JOptionPane.showMessageDialog(janela, cotas, "Sucesso!", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(janela, "Falha de conexão ao atualizar investidor!", "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }

    /**
     * Metodo para mostar o valor atualizado das cotacoes
     *
     * @param investidor obj Investidor
     * @param porcBtc String com a porcentagem de alteracao da cotacao do
     * bitcoin
     * @param porcEth String com a porcentagem de alteracao da cotacao do
     * ethereum
     * @param porcXrp String com a porcentagem de alteracao da cotacao do ripple
     * @return cotas String para ser apresentada na janela
     */
    public String cotasFormatados(Investidor investidor, String porcBtc,
            String porcEth, String porcXrp) {

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

    /**
     * Metodo para verificar a porcentagem de mudanca entre dois valores
     *
     * @param valor1 Double com o primeiro valor
     * @param valor2 Double com o segundo valor
     * @return Double com o valor de alteracao em porcentagem
     */
    public Double verificaPorcentagem(Double valor1, Double valor2) {
        //Funcao para calcular a porcentagem de variacao das cotacoes (deu trabalho XD)
        return (valor1 - valor2) * (-100) / valor1;
    }

    /**
     * Metodo para sair da JInvestidor e ir para a JLogin
     */
    public void Sair() {
        janela.setVisible(false);
        new JLogin().setVisible(true);
    }

}
