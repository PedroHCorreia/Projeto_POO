/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author tioli
 */
public class Bitcoin extends Moeda{
    public Bitcoin() {
        setNome("BTC");
        setTxCompra(0.02);
        setTxVenda(0.03);
        setSaldo(0.0);
    }
}
