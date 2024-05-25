/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author tioli
 */
public class Carteira {
    private ArrayList<Moeda> moedas;

    public ArrayList<Moeda> getMoedas() {
        return moedas;
    }

    public void setMoedas(ArrayList<Moeda> moedas) {
        this.moedas = moedas;
    }

    public Carteira() {
        moedas = new ArrayList<>();
        moedas.add(new Real());
        moedas.add(new Bitcoin());
        moedas.add(new Ethereum());
        moedas.add(new Ripple());
    }
}
