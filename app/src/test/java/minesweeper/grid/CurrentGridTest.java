package minesweeper.grid;

import minesweeper.game.exceptions.CellOutOfBoundException;
import minesweeper.game.exceptions.EndOfGameException;
import minesweeper.game.exceptions.InvalidInputException;
import minesweeper.game.grid.Cell;
import minesweeper.game.grid.CurrentGrid;
import minesweeper.game.grid.Grid;
import minesweeper.game.grid.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static minesweeper.game.grid.Grid.createGrid;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CurrentGridTest {

    @Test
    void getNewGridStepNoBombNearby() throws InvalidInputException, EndOfGameException, CellOutOfBoundException {
        Grid grid = Grid.createGrid(GridResources.complexGridOneNoHints());
        CurrentGrid currentGrid = new CurrentGrid();
        CurrentGrid gridAfter = currentGrid.getNewGrid(new Position(0, 4), grid);
        Set<Cell> expectedReavealed = Set.of(
                grid.getCellAt(new Position(0, 3)),
                grid.getCellAt(new Position(0, 4)),
                grid.getCellAt(new Position(1, 3)),
                grid.getCellAt(new Position(1, 4)),
                grid.getCellAt(new Position(2, 4)));

        GridTestUtils.collectionsEquals(expectedReavealed, gridAfter.getRevealedCells());
    }

    @Test
    void getNewGridStepNoBombNearbyLuckyStep() throws InvalidInputException, EndOfGameException, CellOutOfBoundException {
        Grid grid = Grid.createGrid(GridResources.complexGridTwoNoHints());
        CurrentGrid currentGrid = new CurrentGrid();
        CurrentGrid gridAfter = currentGrid.getNewGrid(new Position(4, 1), grid);
        List<Cell> expectedReavealed = List.of(
                grid.getCellAt(new Position(0, 3)),
                grid.getCellAt(new Position(0, 4)),
                grid.getCellAt(new Position(1, 3)),
                grid.getCellAt(new Position(1, 4)),
                grid.getCellAt(new Position(2, 2)),
                grid.getCellAt(new Position(2, 3)),
                grid.getCellAt(new Position(2, 4)),
                grid.getCellAt(new Position(3, 0)),
                grid.getCellAt(new Position(3, 1)),
                grid.getCellAt(new Position(3, 2)),
                grid.getCellAt(new Position(3, 3)),
                grid.getCellAt(new Position(3, 4)),
                grid.getCellAt(new Position(4, 0)),
                grid.getCellAt(new Position(4, 1)),
                grid.getCellAt(new Position(4, 2)),
                grid.getCellAt(new Position(4, 3)),
                grid.getCellAt(new Position(4, 4))
        );

        GridTestUtils.collectionsEquals(expectedReavealed, gridAfter.getRevealedCells());
    }

    @Test
    void getNewGridWithBombNearby() throws EndOfGameException, CellOutOfBoundException, InvalidInputException {
        Grid grid = Grid.createGrid(GridResources.complexGridOneNoHints());
        CurrentGrid currentGrid = new CurrentGrid();
        CurrentGrid gridAfter = currentGrid.getNewGrid(new Position(4, 3), grid);
        List<Cell> expectedReavealed = List.of(grid.getCellAt(new Position(4, 3)));
        GridTestUtils.collectionsEquals(expectedReavealed, gridAfter.getRevealedCells());
    }

    @Test
    void getNewGridStepOnBomb() throws InvalidInputException {
        Grid grid = Grid.createGrid(GridResources.complexGridOneNoHints());
        CurrentGrid currentGrid = new CurrentGrid();
        Assertions.assertThrows(EndOfGameException.class, () -> {
            currentGrid.getNewGrid(new Position(4, 4), grid);
        });
    }
}