/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Classe da criptomoeda Ethereum
 *
 * @author tioli
 */
public class Ethereum extends Moeda {

    /**
     * Construtor com os valores padroes da moeda
     */
    public Ethereum() {
        setNome("ETH");
        setTxCompra(0.01);
        setTxVenda(0.02);
        setSaldo(0.0);
    }
}
