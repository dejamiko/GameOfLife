package Model;

import java.util.Arrays;
import java.util.Random;

/**
 * The game class for the game of life.
 *
 * @author mikolajdeja
 * @version 2021.04.28
 */
public class Game {
    private final boolean[][] current;

    /**
     * The constructor for the game with a given
     * number of rows and columns.
     *
     * @param rows The number of rows.
     * @param cols The number of columns.
     */
    public Game(int rows, int cols, boolean randomise) {
        current = new boolean[rows][cols];
        if (randomise)
            populateGame();
    }

    /**
     * Populate the game (with random cells)
     */
    private void populateGame() {
        Random random = new Random();
        double probability = 0.1;
        for (int i = 0; i < current.length; i++) {
            for (int j = 0; j < current[i].length; j++) {
                current[i][j] = random.nextDouble() < probability;
            }
        }
    }

    /**
     * Simulate one step of the simulation.
     */
    public void simulateOneStep() {
        boolean[][] nextTurn = new boolean[current.length][current[0].length];
        for (int i = 0; i < current.length; i++) {
            nextTurn[i] = Arrays.copyOf(current[i], current[i].length);
        }

        for (int i = 0; i < nextTurn.length; i++) {
            for (int j = 0; j < nextTurn[i].length; j++) {
                int n = getNumOfNeighbours(i, j);

                nextTurn[i][j] = (current[i][j] && (n == 2 || n == 3)) || (!current[i][j] && n == 3);
            }
        }
        for (int i = 0; i < current.length; i++) {
            current[i] = Arrays.copyOf(nextTurn[i], current[i].length);
        }
    }

    /**
     * Get the number of alive neighbours of a given cell.
     *
     * @param row The row of the given cell.
     * @param col The column of the given cell.
     * @return The number of alive neighbours.
     */
    private int getNumOfNeighbours(int row, int col) {
        int num = 0;
        // Make the board loop onto itself
        int rowBelow = (row - 1 + current.length) % current.length;
        int rowAbove = (row + 1 + current.length) % current.length;
        int colLeft = (col - 1 + current[row].length) % current[row].length;
        int colRight = (col + 1 + current[row].length) % current[row].length;
        // Check row "above"
        if (current[rowBelow][colLeft])
            num++;
        if (current[rowBelow][col])
            num++;
        if (current[rowBelow][colRight])
            num++;
        // Check same row
        if (current[row][colLeft])
            num++;
        if (current[row][colRight])
            num++;
        // Check row "below"
        if (current[rowAbove][colLeft])
            num++;
        if (current[rowAbove][col])
            num++;
        if (current[rowAbove][colRight])
            num++;

        return num;
    }

    /**
     * Get the number of cells that are alive.
     *
     * @return The number of cells that are alive.
     */
    public int getNumOfCells() {
        int num = 0;
        for (boolean[] row : current) {
            for (boolean cell : row) {
                if (cell)
                    num++;
            }
        }
        return num;
    }

    /**
     * Check if a given cell is alive.
     *
     * @param row The row of interest.
     * @param col The column of interest.
     * @return True if the cell of interest is alive, false otherwise.
     */
    public boolean isAlive(int row, int col) {
        return current[row][col];
    }

    /**
     * Make a cell alive at a given row and column.
     *
     * @param row The given row.
     * @param col The given column.
     */
    public void makeAlive(int row, int col) {
        current[row][col] = true;
    }

    /**
     * Make the cell dead at a given row and column.
     *
     * @param row The given row.
     * @param col The given column.
     */
    public void makeDead(int row, int col) {
        current[row][col] = false;
    }
}
