/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Classe com o modelo geral de moeda
 *
 * @author tioli
 */
public class Moeda implements Tarifacao {

    private String nome;
    private Double txCompra, txVenda;
    private Double cota;
    private Double saldo;

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
     * getTxCompra
     *
     * @return txCompra Double
     */
    public Double getTxCompra() {
        return txCompra;
    }

    /**
     * setTxCompra
     *
     * @param txCompra Double
     */
    public void setTxCompra(Double txCompra) {
        this.txCompra = txCompra;
    }

    /**
     * getTxVenda
     *
     * @return txVenda Double
     */
    public Double getTxVenda() {
        return txVenda;
    }

    /**
     * setTxVenda
     *
     * @param txVenda Double
     */
    public void setTxVenda(Double txVenda) {
        this.txVenda = txVenda;
    }

    /**
     * getSaldo
     *
     * @return saldo Double
     */
    public Double getSaldo() {
        return saldo;
    }

    /**
     * setSaldo
     *
     * @param saldo Double
     */
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    /**
     * getCota
     *
     * @return cota Double
     */
    public Double getCota() {
        return cota;
    }

    /**
     * setCota
     *
     * @param cota Double
     */
    public void setCota(Double cota) {
        this.cota = cota;
    }

    /**
     * Metodo para calcular o valor tarifado da compra de uma moeda
     *
     * @param valor Double
     * @return valor tarifado
     */
    @Override
    public Double tarifaCompra(Double valor) {
        return (valor + (valor * getTxCompra()));
    }

    /**
     * Metodo para calcular o valor tarifado da venda de uma moeda
     *
     * @param valor Double
     * @return valor tarifado
     */
    @Override
    public Double tarifaVenda(Double valor) {
        return (valor + (valor * getTxVenda()));
    }

}
