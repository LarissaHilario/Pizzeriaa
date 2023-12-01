package com.example.pizzitas.Controllers;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.example.pizzitas.Threads.Handler;
import com.example.pizzitas.factory.Factory;
import com.example.pizzitas.models.MonitorMesero;
import com.example.pizzitas.models.MonitorRecepcionista;
import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private MonitorMesero monitorMesero;
    private MonitorRecepcionista monitorRecepcionista;
    private Handler handler;
    private static int contadorClientes = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Instantiate Handler with the required parameters
        FXGL.getGameWorld().addEntityFactory(new Factory());
        FXGL.spawn("recepcionist", 1, 180);

        for (int i = 0; i<30; i++){
            int index = i;
            int numClient = contadorClientes++;
            double delay = 3 + Math.random() * 3;
            FXGL.getGameTimer().runOnceAfter(() -> {
                Entity client = FXGL.spawn("Client", 80+(index*10), 215-(index*3));
                handler = new Handler(monitorRecepcionista, monitorMesero, client, numClient );
                System.out.println(client + " llegó al restaurante");
                new Thread(handler).start();
            }, Duration.seconds(delay + i ));
        }
    }



}
