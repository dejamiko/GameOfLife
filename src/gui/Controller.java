package gui;

import Model.Game;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;


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

    private Stage stage;
    private Game game;
    private boolean running;
    private static int numOfSteps;
    private static final int delay = 50;
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    /**
     * Initialise gui elements.
     */
    @FXML
    private void initialize() {
        game = new Game(WIDTH, HEIGHT, true);
        numOfSteps = 0;
        Platform.runLater(() -> {
            stage = ((Stage) borderPane.getScene().getWindow());
            ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> drawBoard();
            stage.widthProperty().addListener(stageSizeListener);
            stage.heightProperty().addListener(stageSizeListener);
            drawBoard();
            updateLabels();
        });
    }

    /**
     * Run the simulation for an indefinite number of steps.
     */
    @FXML
    private void run() {
        if (!running) {
            running = true;
            Thread thread = new Thread(new GameTask());
            thread.setDaemon(true);
            thread.start();
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
     * Restart the game.
     */
    @FXML
    private void restart() {
        game = new Game(WIDTH, HEIGHT, true);
        numOfSteps = 0;
        stop();
        drawBoard();
        updateLabels();
    }

    /**
     * Stop running the game.
     */
    @FXML
    private void stop() {
        running = false;
    }

    /**
     * Draw the board (squares 1x1 as alive cells)
     */
    private void drawBoard() {
        Canvas canvas = new Canvas(borderPane.getWidth() * 0.9, borderPane.getHeight() * 0.9);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Paint.valueOf("#C0C8CF"));
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (game.isAlive(i, j)) {
                    double x = i * borderPane.getWidth() * 0.9 / WIDTH;
                    double y = j * borderPane.getHeight() * 0.9 / HEIGHT;
                    double width = borderPane.getWidth() * 0.85 / WIDTH;
                    double height = borderPane.getHeight() * 0.85 / HEIGHT;
                    graphicsContext.fillPolygon(new double[]{x, x + width, x + width, x},
                            new double[]{y, y, y + height, y + height}, 4);
                }
            }
        }
        borderPane.setCenter(canvas);
    }

    /**
     * Update the info labels.
     */
    private void updateLabels() {
        stepLabel.setText("Steps: " + numOfSteps);
        aliveLabel.setText("Alive: " + game.getNumOfCells());
    }

    /**
     * A nested class for the game task.
     *
     * @author mikolajdeja
     * @version 2021.05.02
     */
    class GameTask extends Task<Void> {
        /**
         * As long as running is true, call next steps.
         *
         * @return null
         */
        @Override
        protected Void call() {
            while (running) {
                nextStep();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    System.out.println("The task was interrupted");
                }
            }
            return null;
        }
    }
}
