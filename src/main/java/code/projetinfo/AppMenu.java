package code.projetinfo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/** Starting App of the project.
 *
 */
public class AppMenu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(AppMenu.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        primaryStage.setTitle("Blocky Afterlife Party");
        primaryStage.getIcons().add(new Image(String.valueOf(AppMenu.class.getResource("Sprites/Blocky.png"))));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args){launch();}
}
