package com.example.pizzitas.models;


import com.example.pizzitas.Threads.HiloCliente;
import com.example.pizzitas.Threads.HiloMesero;

import java.util.LinkedList;
import java.util.Queue;

public class MonitorMesero {
    private int meserosDisponibles;
    private Queue<HiloCliente> colaPedidos;

    public MonitorMesero(int cantidadMeseros) {
        this.meserosDisponibles = cantidadMeseros;
        this.colaPedidos = new LinkedList<>();
    }

    public synchronized void atenderPedido(HiloCliente cliente) {

        if (meserosDisponibles == 0) {
            System.out.println("No hay meseros disponibles. Cliente " + cliente.getId() + " en cola de pedidos.");
            colaPedidos.add(cliente);
            while (meserosDisponibles == 0 || colaPedidos.peek() != cliente) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Cliente " + cliente.getId() + " sale de la cola de pedidos.");
            colaPedidos.remove();
        }

        meserosDisponibles--;
        System.out.println("Pedido del Cliente " + cliente.getId() + " atendido por un mesero.");
        meserosDisponibles++;
        notifyAll();
    }

    public synchronized void liberarMesero(HiloMesero hiloMesero) {
        if (meserosDisponibles < 7) {
            meserosDisponibles++;
        }
        if (colaPedidos.size() > 0) {
            notify();
        }
    }

}


