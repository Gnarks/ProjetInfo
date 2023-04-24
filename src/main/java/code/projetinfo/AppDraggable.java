package code.projetinfo;

import code.projetinfo.normalBlocks.Amogous;
import code.projetinfo.normalBlocks.Bob;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //launch();
        Level test = new Level("t", new Cases(2,2, CaseState.EMPTY), new ImageBlock[25]);
        test.loadState("test");
        test.show();
    }
}
