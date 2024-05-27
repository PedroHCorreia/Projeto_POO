/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 * Metodo com o modelo de Carteira
 *
 * @author tioli
 */
public class Carteira {

    //As moedas serao armazenadas em um array
    private ArrayList<Moeda> moedas;

    /**
     * getMoedas
     *
     * @return moedas ArrayList de Moeda
     */
    public ArrayList<Moeda> getMoedas() {
        return moedas;
    }

    /**
     * setMoedas
     *
     * @param moedas ArrayList de Moeda
     */
    public void setMoedas(ArrayList<Moeda> moedas) {
        this.moedas = moedas;
    }

    /**
     * Construtor
     */
    public Carteira() {
        moedas = new ArrayList<>();
        moedas.add(new Real());
        moedas.add(new Bitcoin());
        moedas.add(new Ethereum());
        moedas.add(new Ripple());
    }
}
