package com.example.pizzitas;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;


public class Main extends GameApplication {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setMainMenuEnabled(true);
        settings.setWidth(800);
        settings.setHeight(450);
        settings.setTitle("Pizzería");
    }

    @Override
    protected void initGame(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            AnchorPane root = loader.load();
            FXGL.getGameScene().addUINode(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        getGameScene().setBackgroundRepeat("bg.jpg");
    }
}