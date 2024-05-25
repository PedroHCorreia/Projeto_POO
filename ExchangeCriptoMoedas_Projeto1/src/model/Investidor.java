/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author tioli
 */
public class Investidor extends Pessoa{
    private String senha;
    private Carteira carteira;

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }

    public Investidor() {
    }

    public Investidor(String nome, String cpf, String senha) {
        super(nome, cpf);
        this.senha = senha; 
        carteira = new Carteira();
    }
    
}
