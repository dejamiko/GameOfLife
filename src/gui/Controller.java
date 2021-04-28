package gui;

import Model.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

/**
 * The controller class for this application.
 *
 * @author mikolajdeja
 * @version 2021.04.28
 */
public class Controller {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label stepLabel;
    @FXML
    private Label aliveLabel;

    private Game game;

    private static int numOfSteps = 0;
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    /**
     * Initialise gui elements.
     */
    @FXML
    private void initialize() {
        // TODO: add listeners for resizing
        game = new Game(WIDTH, HEIGHT);
        drawBoard();
    }

    /**
     * Run the simulation for some number of steps.
     */
    @FXML
    private void run() {
        for (int i = 0; i < 50; i++) {
            nextStep();
        }
    }

    /**
     * Simulate the next step.
     */
    @FXML
    private void nextStep() {
        new Thread(() -> {
            game.simulateOneStep();
            Platform.runLater(() -> {
                numOfSteps++;
                drawBoard();
                updateLabels();
            });
        }).start();
    }

    /**
     * Draw the board (circles with radius 1 as alive cells)
     *
     * TODO: Make an actual grid for them
     */
    private void drawBoard() {
        Group group = new Group();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (game.isAlive(i, j))
                    // coordinates x, coordinates y, radius
                    group.getChildren().add(new Circle(i - WIDTH / 2.0, -(j - HEIGHT / 2.0), 1));
            }
        }
        borderPane.setCenter(group);
    }

    /**
     * Update the info labels.
     */
    private void updateLabels() {
        stepLabel.setText("Steps: " + numOfSteps);
        aliveLabel.setText("Alive: " + game.getNumOfCells());
    }
}
// TODO: see why the gui doesn't refresh every step upon calling run

// TODO: optimise this bad boi, it's crazily slow now (maybe some multithread graphics stuff?) google it