/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.InvestidorDAO;
import DAO.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Investidor;
import view.JLogin;
import view.JInvestidor;

/**
 * Classe para controlar a janela JInvestidor
 *
 * @author moijo
 */
public class ControllerLogin {

    private JLogin janela;

    /**
     * Construtor
     *
     * @param janela obj JLogin
     */
    public ControllerLogin(JLogin janela) {
        this.janela = janela;
    }

    /**
     * Metodo para autenticar o login e receber as informacoes do investidor
     */
    public void loginInvestidor() {
        //Recebndo os valores digitados na janela
        Investidor investidor = new Investidor(null, janela.getTxtCpf().getText(), janela.getTxtSenha().getText());
        //Recebndo os valores armazenados no banco de dados
        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConnection();
            InvestidorDAO dao = new InvestidorDAO(conn);
            //Verificando se as informacoes batem
            ResultSet res = dao.consultar(investidor);
            if (res.next()) {
                //Informando que o login foi efetuado, armazenando os valores em um obj Investidor e trocando de janela
                JOptionPane.showMessageDialog(janela, "Login Feito", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                investidor.setNome(res.getString("nome"));
                investidor.setCpf(res.getString("cpf"));
                investidor.setSenha(res.getString("senha"));
                investidor.getCarteira().getMoedas().get(0).setSaldo(res.getDouble("real"));
                investidor.getCarteira().getMoedas().get(1).setSaldo(res.getDouble("btc"));
                investidor.getCarteira().getMoedas().get(2).setSaldo(res.getDouble("eth"));
                investidor.getCarteira().getMoedas().get(3).setSaldo(res.getDouble("xrp"));
                investidor.getCarteira().getMoedas().get(1).setCota(res.getDouble("ctbtc"));
                investidor.getCarteira().getMoedas().get(2).setCota(res.getDouble("cteth"));
                investidor.getCarteira().getMoedas().get(3).setCota(res.getDouble("ctxrp"));
                JInvestidor jInvest = new JInvestidor(investidor);
                jInvest.setVisible(true);
                janela.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(janela, "CPF ou senha incorretos", "Erro", JOptionPane.ERROR_MESSAGE);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(janela, "Erro de conexão", "Erro", JOptionPane.ERROR_MESSAGE);

        }
    }
}