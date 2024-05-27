/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

/**
 * Interface com as funcoes de tarifa para as moedas
 * @author tioli
 */
public interface Tarifacao {
    /**
     * Metodo para calcular o valor tarifado da compra de uma moeda
     * @param valor Double
     * @return valor tarifado
     */
    public Double tarifaCompra(Double valor);
    /**
     * Metodo para calcular o valor tarifado da venda de uma moeda
     * @param valor Double
     * @return valor tarifado
     */
    public Double tarifaVenda(Double valor);
}
