package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The main class of the Game of Life application.
 *
 * @author mikolajdeja
 * @version 2021.04.28
 */
public class Main extends Application {

    /**
     * Start the gui.
     *
     * @param primaryStage The primary stage used.
     * @throws Exception Exceptions that can be thrown by the FXMLLoader.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameOfLife.fxml")));
        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }

    /**
     * Launch the JavaFX application.
     *
     * @param args The arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
