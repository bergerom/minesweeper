package minesweeper.grid;

import minesweeper.game.grid.Cell;
import minesweeper.game.grid.InternalGrid;
import minesweeper.game.grid.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class InternalGridTest {
    public static class MockInternalGrid extends InternalGrid {
        public MockInternalGrid(int nbCells) {
            super(nbCells);
        }

        public Optional<Cell> getCellAt(Position position) {
            List<Position> bombs = List.of(new Position(2, 2),
                    new Position(2, 3));

            if (super.isOutsideBounds(position)) {
                return Optional.empty();
            }

            if (bombs.contains(position)) {
                return Optional.of(new Cell(position, true));
            }
            return Optional.of(new Cell(position, false));
        }

        @Override
        public List<Cell> getAdjacentCells(Position position) {
            return super.getAdjacentCells(position);
        }
    }


    @Test
    public void getAdjacentCellsTest() {
        MockInternalGrid internalGrid = new MockInternalGrid(16);
        List<Position> adjCells = internalGrid.getAdjacentCells(new Position(1, 1))
                .stream()
                .map(c -> new Position(c.row, c.col))
                .toList();

        List<Position> expectedCells = List.of(new Position(0, 0),
                new Position(0, 1),
                new Position(0, 2),
                new Position(1, 2),
                new Position(2, 2),
                new Position(2, 1),
                new Position(2, 0),
                new Position(1, 0));

        Assertions.assertEquals(expectedCells.size(), adjCells.size());
        for (Position expected : expectedCells) {
            Assertions.assertTrue(adjCells.contains(expected));
        }
    }

    @Test
    public void getAdjacentCellsCornerTest() {
        MockInternalGrid internalGrid = new MockInternalGrid(16);
        List<Position> adjCells = internalGrid.getAdjacentCells(new Position(0, 0))
                .stream()
                .map(c -> new Position(c.row, c.col))
                .toList();

        List<Position> expectedCells = List.of(
                new Position(0, 1),
                new Position(1, 0),
                new Position(1, 1));

        Assertions.assertEquals(expectedCells.size(), adjCells.size());
        for (Position expected : expectedCells) {
            Assertions.assertTrue(adjCells.contains(expected));
        }
    }
}
