package com.example.pizzitas.Threads;


import com.almasb.fxgl.entity.Entity;
import com.example.pizzitas.models.MonitorMesero;
import com.example.pizzitas.models.MonitorRecepcionista;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HiloCliente extends Thread {
    private static int contadorClientes = 0;

    private MonitorRecepcionista monitorRecepcionista;
    private MonitorMesero monitorMesero;


    private final Entity client;
    private final Entity pizza;
    private List<Entity> waitersList;


    List<Point2D> posiciones = new ArrayList<>();
    private Point2D posicionAsignada;

    List<Point2D> posicionesMeseros = new ArrayList<>();
    Entity meseroAsignado;




    public HiloCliente(MonitorRecepcionista monitorRecepcionista, MonitorMesero monitorMesero, Entity client, int numCliente, List<Entity> waitersList, List<Point2D> posicionesMeseros, Entity pizza) {
        this.monitorRecepcionista = monitorRecepcionista;
        this.monitorMesero = monitorMesero;
        this.setName("Cliente-" + numCliente);
        this.client = client;
        this.pizza= pizza;
        this.waitersList = waitersList;
        this.posicionesMeseros = posicionesMeseros;
        //posiciones.add(new Point2D(200, 200));
        posiciones.add(new Point2D(400, 320));
        posiciones.add(new Point2D(500, 320));
        posiciones.add(new Point2D(300, 320));
        posiciones.add(new Point2D(610, 320));
        posiciones.add(new Point2D(710, 320));
        posiciones.add(new Point2D(610, 190));
        posiciones.add(new Point2D(400, 190));
        posiciones.add(new Point2D(500, 190));
        posiciones.add(new Point2D(300, 190));
        posiciones.add(new Point2D(710, 190));
        posiciones.add(new Point2D(610, 50));
        posiciones.add(new Point2D(400, 50));
        posiciones.add(new Point2D(500, 50));
        posiciones.add(new Point2D(300, 50));
        posiciones.add(new Point2D(710, 50));

        /*;
        ;
        posiciones.add(new Point2D(350, 400));*/

    }


    public void entrarRestaurante(){
        System.out.println("Cliente " + getId() + " ha entrado al restaurante y su mesa.");

        Random random = new Random();
        int indicePosicionElegida = random.nextInt(posiciones.size());
        Point2D posicionElegida = posiciones.get(indicePosicionElegida);
        posiciones.remove(indicePosicionElegida);
        client.setPosition(posicionElegida);

        posicionAsignada = posicionElegida;

    }

    public void pedirPizza() {
        System.out.println("Cliente " + getId() + " ha pedido una pizza.");

        if (waitersList != null) {
            Point2D newPosition = getPosicionAsignada();
            for (Entity waiter : waitersList) {
                waiter.setPosition(newPosition);
                // Asigna el mesero a este cliente
                if (waiter.equals(meseroAsignado)) {
                    meseroAsignado = waiter;
                }
            }
        } else {
            System.err.println("La lista de waiters es null. Verifica la inicialización antes de usarla.");
        }
    }


    public void esperarPizza(){
        System.out.println("Cliente " + getId() + " esperando su pizza.");

        if (meseroAsignado != null && posicionesMeseros.contains(meseroAsignado.getPosition())) {
            meseroAsignado.setPosition(posicionesMeseros.get(waitersList.indexOf(meseroAsignado)));
        }
    }

    public void entregarPizza() {
        monitorRecepcionista.entregarPizza(this);
        Point2D newposition = this.getPosicionAsignada();
        pizza.setPosition(newposition);
    }

    public void comerPizza(){
        System.out.println("Cliente " + getId() + " esta comiendo su Pizza");

    }
    public Point2D getPosicionAsignada() {
        return posicionAsignada;
    }
    public List<Point2D> getPosiciones() {
        return posiciones;
    }



    @Override
    public void run() {
        monitorRecepcionista.llegarCliente(this);
        entrarRestaurante();

        pedirPizza();

        monitorMesero.atenderPedido(this);
        esperarPizza();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        entregarPizza();

        comerPizza();
        try {
            Thread.sleep(10000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        monitorRecepcionista.abandonarRestaurante(this, client, pizza);
       try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
