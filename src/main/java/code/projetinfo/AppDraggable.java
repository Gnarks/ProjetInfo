package code.projetinfo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppDraggable extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppDraggable.class.getResource("DraggableTest.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 607.5);
        stage.setTitle("Ghost Arranged by Yourself, GAY");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
