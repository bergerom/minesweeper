package minesweeper.stubs;

import minesweeper.game.display.GameIO;
import minesweeper.game.exceptions.CellOutOfBoundException;
import minesweeper.game.grid.Cell;
import minesweeper.game.grid.Position;

import java.io.IOException;
import java.util.Queue;
import java.util.Set;

public class StubIO implements GameIO {

    private final Queue<Position> testScenarioMoves;

    private boolean gameOver = false;

    private boolean gameWon = false;

    private Integer round = 0;

    public StubIO(Queue<Position> testScenarioMoves) {
        this.testScenarioMoves = testScenarioMoves;
    }

    @Override
    public void displayPartialGameGrid(Set<Cell> onlyDisplay) throws CellOutOfBoundException, IOException {
        round++;
    }

    @Override
    public void displayCompleteGameGrid() throws CellOutOfBoundException, IOException {
        round++;
    }

    @Override
    public Position takeUserInput() throws IOException {
        return testScenarioMoves.poll();
    }

    @Override
    public void displayWinMessage() throws IOException {
        this.gameWon = true;
    }

    @Override
    public void displayLooseMessage(int score) throws IOException {
        this.gameOver = true;
    }

    @Override
    public void displayIncorrectPositionMessage(Position position) throws IOException {
        // leave blank
    }

    //  ************************************************
    //  stubs methods for checking the state of the class
    //  ************************************************

    public Boolean gameIsOver() {
        return this.gameOver;
    }

    public Boolean gameWon() {
        return this.gameWon;
    }

    public Integer getRound() {
        return this.round;
    }

}
