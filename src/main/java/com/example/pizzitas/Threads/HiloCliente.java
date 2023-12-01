package com.example.pizzitas.Threads;


import com.almasb.fxgl.entity.Entity;
import com.example.pizzitas.models.MonitorMesero;
import com.example.pizzitas.models.MonitorRecepcionista;

public class HiloCliente extends Thread {
    private static int contadorClientes = 0;
    private MonitorRecepcionista monitorRecepcionista;
    private MonitorMesero monitorMesero;

    private final Entity client;

    public HiloCliente(MonitorRecepcionista monitorRecepcionista, MonitorMesero monitorMesero, Entity client, int numCliente) {
        this.monitorRecepcionista = monitorRecepcionista;
        this.monitorMesero = monitorMesero;
        this.setName("Cliente-" + numCliente);
        this.client = client;
    }


    public void entrarRestaurante(){
        System.out.println("Cliente " + getId() + " ha entrado al restaurante y su mesa.");

    }

    public void pedirPizza() {
        System.out.println("Cliente " + getId() + " ha pedido una pizza.");
    }

    public void esperarPizza(){
        System.out.println("Cliente " + getId() + " esperando su pizza.");
    }

    public void entregarPizza() {
        monitorRecepcionista.entregarPizza(this);
    }

    public void comerPizza(){
        System.out.println("Cliente " + getId() + " esta comiendo su Pizza");
    }


    @Override
    public void run() {
        monitorRecepcionista.llegarCliente(this);
        entrarRestaurante();
        pedirPizza();
        monitorMesero.atenderPedido(this);
        esperarPizza();
        entregarPizza();
        comerPizza();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        monitorRecepcionista.abandonarRestaurante(this);
       /* try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }
}
