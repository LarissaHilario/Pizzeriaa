package com.example.pizzitas.Threads;

import com.almasb.fxgl.entity.Entity;
import com.example.pizzitas.models.MonitorMesero;
import com.example.pizzitas.models.MonitorRecepcionista;


import java.util.Observable;

public class Handler implements Runnable {

    MonitorRecepcionista monitorRecepcionista ;
    MonitorMesero monitorMesero ;
    private final Entity client;

    private int numClient;

    public Handler(MonitorRecepcionista monitorRecepcionista, MonitorMesero monitorMesero, Entity client, int numClient) {
        this.monitorMesero =  new MonitorMesero(7);
        this.monitorRecepcionista = new MonitorRecepcionista(20);
        this.client = client;
        this.numClient = numClient;
    }


    @Override
    public void run() {

        monitorRecepcionista.setMonitorMesero(monitorMesero);
        while (true) {

            HiloCliente cliente = new HiloCliente(monitorRecepcionista, monitorMesero, client, numClient );
            cliente.start();

            try {

                Thread.sleep(1000);

            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

