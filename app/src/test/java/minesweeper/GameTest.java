package minesweeper;

import minesweeper.game.Game;
import minesweeper.game.display.GameIO;
import minesweeper.game.exceptions.CellOutOfBoundException;
import minesweeper.game.exceptions.InvalidInputException;
import minesweeper.game.grid.Grid;
import minesweeper.game.grid.Position;
import minesweeper.grid.GridResources;
import minesweeper.stubs.StubIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static minesweeper.game.grid.Grid.createGrid;

public class GameTest {
    @Test
    public void firstRoundStepOnMine() throws InvalidInputException, IOException, CellOutOfBoundException {
        Grid grid = createGrid(GridResources.complexGridOneNoHints());

        Queue<Position> playerMoves = new LinkedList<>();
        playerMoves.add(new Position(1, 1));

        StubIO stubIO = new StubIO(playerMoves);
        Game game = new Game(grid, stubIO);
        game.gameLoop();

        Assertions.assertEquals(2, stubIO.getRound());
        Assertions.assertTrue(stubIO.gameIsOver());
    }

    @Test
    public void secondRoundStepOnMine() throws InvalidInputException, IOException, CellOutOfBoundException {
        Grid grid = createGrid(GridResources.complexGridOneNoHints());

        Queue<Position> playerMoves = new LinkedList<>();
        playerMoves.add(new Position(0, 1));
        playerMoves.add(new Position(4, 2));

        StubIO stubIO = new StubIO(playerMoves);
        Game game = new Game(grid, stubIO);
        game.gameLoop();

        Assertions.assertEquals(3, stubIO.getRound());
        Assertions.assertTrue(stubIO.gameIsOver());
    }

    @Test
    public void gameWon() throws InvalidInputException, IOException, CellOutOfBoundException {
        Grid grid = createGrid(GridResources.complexGridOneNoHints());

        Queue<Position> playerMoves = new LinkedList<>();
        playerMoves.add(new Position(0, 4));
        playerMoves.add(new Position(4, 0));
        playerMoves.add(new Position(0, 0));
        playerMoves.add(new Position(0, 1));
        playerMoves.add(new Position(0, 2));
        playerMoves.add(new Position(1, 0));
        playerMoves.add(new Position(4, 3));
        playerMoves.add(new Position(2, 1));
        playerMoves.add(new Position(3, 2));
        playerMoves.add(new Position(3, 3));
        playerMoves.add(new Position(2, 2));
        playerMoves.add(new Position(2, 3)); // 13 moves


        StubIO stubIO = new StubIO(playerMoves);
        Game game = new Game(grid, stubIO);
        game.gameLoop();

        Assertions.assertEquals(13, stubIO.getRound());
        Assertions.assertFalse(stubIO.gameIsOver());
        Assertions.assertTrue(stubIO.gameWon());
    }


}
