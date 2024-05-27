/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Classe com o modelo geral de Pessoa
 *
 * @author tioli
 */
public class Pessoa {

    private String nome, cpf;

    /**
     * getNome
     *
     * @return nome String
     */
    public String getNome() {
        return nome;
    }

    /**
     * setNome
     *
     * @param nome String
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * getCpf
     *
     * @return cpf String
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * setCpf
     *
     * @param cpf String
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Construtor vazio
     */
    public Pessoa() {
    }

    /**
     * Costrutor completo
     *
     * @param nome String
     * @param cpf String
     */
    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

}
