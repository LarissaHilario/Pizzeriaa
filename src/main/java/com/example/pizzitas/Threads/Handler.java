package com.example.pizzitas.Threads;

import com.almasb.fxgl.entity.Entity;
import com.example.pizzitas.models.MonitorMesero;
import com.example.pizzitas.models.MonitorRecepcionista;

import java.util.List;


import javafx.geometry.Point2D;

public class Handler implements Runnable {

    private MonitorRecepcionista monitorRecepcionista;
    private MonitorMesero monitorMesero;
    private final Entity client;
    private final Entity pizza;

    private int numClient;
    private List<Entity> waitersList;

    private List<Point2D> posicionesMeseros;

    public Handler(MonitorRecepcionista monitorRecepcionista, MonitorMesero monitorMesero, Entity client, int numClient, List<Entity> waitersList, List<Point2D> posicionesInicialesMeseros, Entity pizza) {
        this.monitorMesero = new MonitorMesero(7);
        this.monitorRecepcionista = new MonitorRecepcionista(20);
        this.client = client;
        this.numClient = numClient;
        this.pizza = pizza;
        this.waitersList = waitersList;
        this.posicionesMeseros = posicionesInicialesMeseros;
    }

    @Override
    public void run() {
        monitorRecepcionista.setMonitorMesero(monitorMesero);
        while (true) {


            HiloCliente cliente = new HiloCliente(monitorRecepcionista, monitorMesero, client, numClient,  waitersList, posicionesMeseros, pizza);
            cliente.start();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



