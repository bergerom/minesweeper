package minesweeper.game;

import minesweeper.game.display.GameIO;
import minesweeper.game.exceptions.CellOutOfBoundException;
import minesweeper.game.exceptions.EndOfGameException;
import minesweeper.game.grid.CurrentGrid;
import minesweeper.game.grid.Grid;
import minesweeper.game.grid.Position;

import java.io.IOException;

public class Game {
    private final Grid grid; // Immutable starting grid

    private CurrentGrid gameGrid; // The grid representing the state of the game, one grid is generated each turn

    private final GameIO gameIO;

    public Game(Grid grid, GameIO gameIO) {
        this.grid = grid;
        this.gameGrid = new CurrentGrid();
        this.gameIO = gameIO;
    }

    public void gameLoop() throws CellOutOfBoundException, IOException {

        // InitGridAndDisplay
        gameIO.displayPartialGameGrid(gameGrid.getRevealedCells());

        // Game loop
        while (gameGrid.getRevealedCells().size() + grid.getNbBombs() != grid.getNbCells()) {
            // Ask for next move
            Position position = getNextMove(gameIO);

            try {
                // Apply next move
                gameGrid = gameGrid.getNewGrid(position, grid);
                gameIO.displayPartialGameGrid(gameGrid.getRevealedCells());
            } catch (EndOfGameException e) {
                // Game lost
                gameIO.displayCompleteGameGrid();
                gameIO.displayLooseMessage(gameGrid.getRevealedCells().size());
                return;
            }
        }

        // Win
        gameIO.displayWinMessage();
    }

    private Position getNextMove(GameIO gameIO) throws IOException {
        Position position = null;
        boolean positionWithinBounds;

        do {
            // Ask for next move
            position = gameIO.takeUserInput();
            positionWithinBounds = true;
            try {
                grid.getCellAt(position);
            } catch (CellOutOfBoundException cob) {
                gameIO.displayIncorrectPositionMessage(position);
                positionWithinBounds = false;
            }
        } while (!positionWithinBounds);

        return position;
    }


}
