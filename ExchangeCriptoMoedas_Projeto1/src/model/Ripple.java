/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Classe da criptomoeda Ripple
 *
 * @author tioli
 */
public class Ripple extends Moeda {

    /**
     * Construtor com os valores padroes da moeda
     */
    public Ripple() {
        setNome("XRP");
        setTxCompra(0.01);
        setTxVenda(0.01);
        setSaldo(0.0);
    }

}
