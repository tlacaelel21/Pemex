package com.mob_tk.tlacaelel21.pemex.Model;

/**
 * Created by tlacaelel21 on 27/10/15.
 */
public class WorkersModel {
    String workerName;
    String workerNumber;

    public WorkersModel(String workerName, String workerNumber) {
        this.workerName = workerName;
        this.workerNumber = workerNumber;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getWorkerNumber() {
        return workerNumber;
    }
}
