package com.example.project3;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class Main extends Application
{
    @Override
    public void start(Stage stage)throws IOException
    {
        FXMLLoader load = new FXMLLoader(Main.class.getResource("view.fxml"));
        Scene scene = new Scene(load.load(), 300,400);
        stage.setTitle("Bank");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[]args)
    {
        launch();
    }
}