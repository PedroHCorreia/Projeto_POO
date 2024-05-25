/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author tioli
 */
public class Moeda implements Tarifacao {
    private String nome;
    private Double txCompra, txVenda;
    private Double cota;
    private Double saldo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getTxCompra() {
        return txCompra;
    }

    public void setTxCompra(Double txCompra) {
        this.txCompra = txCompra;
    }

    public Double getTxVenda() {
        return txVenda;
    }

    public void setTxVenda(Double txVenda) {
        this.txVenda = txVenda;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getCota() {
        return cota;
    }

    public void setCota(Double cota) {
        this.cota = cota;
    }
    
    @Override
    public Double tarifaCompra(Double valor) {
        return (valor-(valor*getTxCompra()));
    }

    @Override
    public Double tarifaVenda(Double valor) {
        return (valor-(valor*getTxVenda()));
    }
    
}
