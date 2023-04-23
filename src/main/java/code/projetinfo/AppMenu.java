package code.projetinfo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMenu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(AppMenu.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        primaryStage.setTitle("Ghost Arranged by Yourself, GAY");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args){launch();}
}
