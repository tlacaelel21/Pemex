package com.mob_tk.tlacaelel21.pemex.Model;

/**
 * Created by tlacaelel21 on 26/10/15.
 */
public class WorkCenterModel {
    String nombreWC;
    String ubicacionWC;
    String distanciaWc;
    public WorkCenterModel(String nombreWCP, String ubicacionWCP, String distanciaWcP){
        this.nombreWC=nombreWCP;
        this.ubicacionWC=ubicacionWCP;
        this.distanciaWc=distanciaWcP;
    }

    public String getNombreWC() {
        return nombreWC;
    }

    public String getUbicacionWC() {
        return ubicacionWC;
    }

    public String getDistanciaWc() {
        return distanciaWc;
    }
}
