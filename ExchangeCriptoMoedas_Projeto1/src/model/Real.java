/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Classe da moeda Real
 *
 * @author tioli
 */
public class Real extends Moeda {

    /**
     * Construtor com os valores padroes da moeda
     */
    public Real() {
        setNome("Real");
        setTxCompra(0.0);
        setTxVenda(0.0);
        setSaldo(0.0);
        setCota(1.0);
    }

}
