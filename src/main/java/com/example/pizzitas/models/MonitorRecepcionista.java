package com.example.pizzitas.models;


import com.almasb.fxgl.entity.Entity;
import com.example.pizzitas.Threads.HiloCliente;
import javafx.geometry.Point2D;

import java.util.*;

public class MonitorRecepcionista {
    private boolean restauranteLleno;
    private int mesasOcupadas;
    private final int capacidadMesas;
    private Queue<HiloCliente> colaClientes;
    private Map<HiloCliente, Integer> asignacionMesas;
    private MonitorMesero monitorMesero;

    public MonitorRecepcionista(int capacidadMesas) {
        this.restauranteLleno = false;
        this.mesasOcupadas = 0;
        this.capacidadMesas = capacidadMesas;
        this.colaClientes = new LinkedList<>();
        this.asignacionMesas = new HashMap<>();
    }

    public void setMonitorMesero(MonitorMesero monitorMesero) {
        this.monitorMesero = monitorMesero;
    }

    public synchronized void llegarCliente(HiloCliente cliente) {
        System.out.println("Cliente " + cliente.getId() + " llegó al restaurante.");

        if (mesasOcupadas == capacidadMesas) {
            System.out.println("Restaurante lleno. Cliente " + cliente.getId() + " en cola de espera.");
            colaClientes.add(cliente);
            while (restauranteLleno || colaClientes.peek() != cliente) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Cliente " + cliente.getId() + " sale de la cola de espera");
            colaClientes.remove();
        }

        int mesaAsignada;
        Set<Integer> mesasDisponibles = new HashSet<>();
        for (int i = 1; i <= capacidadMesas; i++) {
            mesasDisponibles.add(i);
        }
        for (Integer mesaOcupada : asignacionMesas.values()) {
            mesasDisponibles.remove(mesaOcupada);
        }

        if (!mesasDisponibles.isEmpty()) {
            mesaAsignada = mesasDisponibles.iterator().next();
        } else {
            mesasOcupadas++;
            mesaAsignada = mesasOcupadas;
        }

        asignacionMesas.put(cliente, mesaAsignada);

        if (mesasOcupadas == capacidadMesas) {
            restauranteLleno = true;
        }

        System.out.println("Recepcionista asignó al Cliente " + cliente.getId() + " a la mesa " + mesaAsignada);

        notifyAll();

    }

    public synchronized void abandonarRestaurante(HiloCliente cliente, Entity client, Entity pizza) {
        mesasOcupadas--;

        if (mesasOcupadas < capacidadMesas) {
            restauranteLleno = false;
            notifyAll();
        }

        asignacionMesas.remove(cliente);
        Point2D posicionLiberada = cliente.getPosicionAsignada();
        List<Point2D> posiciones = cliente.getPosiciones();

        // Realiza cualquier lógica necesaria para liberar la posición, por ejemplo:
        posiciones.add(posicionLiberada);
        System.out.println("Cliente " + cliente.getId() + " abandonó el restaurante.");
        client.setVisible(false);
        pizza.setVisible(false);
    }

    public synchronized void entregarPizza(HiloCliente cliente) {
        System.out.println("Pizza entregada al Cliente " + cliente.getId());

        notify();

    }
}
