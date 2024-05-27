/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Classe modelo de Investidor
 *
 * @author tioli
 */
public class Investidor extends Pessoa {

    private String senha;
    //Composicao
    private Carteira carteira;

    /**
     * getSenha
     *
     * @return senha String
     */
    public String getSenha() {
        return senha;
    }

    /**
     * getSenha
     *
     * @param senha String
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * getCarteira
     *
     * @return carteira obj Carteira
     */
    public Carteira getCarteira() {
        return carteira;
    }

    /**
     * getCarteira
     *
     * @param carteira obj carteira
     */
    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }

    /**
     * Construtor vazio
     */
    public Investidor() {
        carteira = new Carteira();
    }

    /**
     * Construtor completo
     *
     * @param nome String
     * @param cpf String
     * @param senha String
     */
    public Investidor(String nome, String cpf, String senha) {
        super(nome, cpf);
        this.senha = senha;
        carteira = new Carteira();
    }

}
