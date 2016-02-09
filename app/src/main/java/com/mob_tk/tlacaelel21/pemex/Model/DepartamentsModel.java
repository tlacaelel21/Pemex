package com.mob_tk.tlacaelel21.pemex.Model;

/**
 * Created by tlacaelel21 on 26/10/15.
 */
public class DepartamentsModel {
    String wpTitulo;
    String wpDato;

    public DepartamentsModel(String wpTitulo, String wpDato) {
        this.wpTitulo = wpTitulo;
        this.wpDato = wpDato;
    }

    public String getWpTitulo() {
        return wpTitulo;
    }

    public String getWpDato() {
        return wpDato;
    }
}
