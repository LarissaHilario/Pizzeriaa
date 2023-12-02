package com.example.pizzitas.Controllers;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.example.pizzitas.Threads.Handler;
import com.example.pizzitas.factory.Factory;
import com.example.pizzitas.models.MonitorMesero;
import com.example.pizzitas.models.MonitorRecepcionista;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



    public class HelloController implements Initializable {
        private MonitorMesero monitorMesero;
        private MonitorRecepcionista monitorRecepcionista;
        private Handler handler;
        private static int contadorClientes = 0;
        private static int contadorMeseros = 0;

        List<Entity> waitersList = new ArrayList<>();
        List<Point2D> posicionesInicialesMeseros = new ArrayList<>();

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            // Instantiate Handler with the required parameters
            FXGL.getGameWorld().addEntityFactory(new Factory());
            FXGL.spawn("recepcionist", 120, 179);


            for (int i = 0; i < 7; i++) {
                int index = i;
                int numMeseros = contadorMeseros++;
                Entity waiters = FXGL.spawn("Waiters", 200 + (1 * 10), 100 + (index * 40));
                waitersList.add(waiters);
                posicionesInicialesMeseros.add(new Point2D(waiters.getX(), waiters.getY()));
            }

            for (int i = 0; i < 30; i++) {
                int index = i;
                int numClient = contadorClientes++;
                double delay = 5 + Math.random() * 1;
                FXGL.getGameTimer().runOnceAfter(() -> {
                    Entity client = FXGL.spawn("Client", 1 + (1 * 10), (1 * 3));
                    Entity pizza = FXGL.spawn("Pizza", 1 + (1 * 10), (1 * 3));
                    System.out.println("hay meseros1" + waitersList);
                    handler = new Handler(monitorRecepcionista, monitorMesero, client, numClient, waitersList, posicionesInicialesMeseros, pizza);
                    System.out.println(client + " llegó al restaurante");
                    new Thread(handler).start();
                }, Duration.seconds(delay + i));
            }
        }
    }




