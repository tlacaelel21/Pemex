package com.mob_tk.tlacaelel21.pemex.Model;

/**
 * Created by tlacaelel21 on 26/10/15.
 */
public class AreasModel {
    String areaTitulo;
    String areaDato;

    public AreasModel(String areaTitulo, String areaDato) {
        this.areaTitulo = areaTitulo;
        this.areaDato = areaDato;
    }

    public String getAreaTitulo() {
        return areaTitulo;
    }

    public String getAreaDato() {
        return areaDato;
    }
}
