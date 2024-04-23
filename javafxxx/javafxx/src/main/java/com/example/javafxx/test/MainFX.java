package com.example.javafxx.test;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/javafxx/SignIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        primaryStage.setTitle("SignIn!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch();
    }


}
