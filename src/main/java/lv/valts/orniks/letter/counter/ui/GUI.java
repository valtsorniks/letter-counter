package lv.valts.orniks.letter.counter.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Burtu skaitītājs");
        Parent fxml = FXMLLoader.load(getClass().getResource("/main.fxml"));
        Scene scene = new Scene(fxml);
        primaryStage.setMinWidth(400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
