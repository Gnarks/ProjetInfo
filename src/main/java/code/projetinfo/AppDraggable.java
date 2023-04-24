package code.projetinfo;

import code.projetinfo.normalBlocks.Amogous;
import code.projetinfo.normalBlocks.Bob;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class AppDraggable extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppDraggable.class.getResource("DraggableTest.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 607.5);
        stage.setTitle("Ghost Arranged by Yourself, GAY");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        //launch();
        ImageBlock[] b = {new Amogous(new Position(25,25))};
        Level test = new Level("test", new Cases(3,3, CaseState.EMPTY), b);
        test.saveState();
    }
}
