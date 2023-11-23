package minesweeper.grid;

import minesweeper.game.exceptions.CellOutOfBoundException;
import minesweeper.game.display.ConsoleIO;
import minesweeper.game.grid.Grid;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

public class GridTestUtils {
    public static <T> void collectionsEquals(Collection<T> expectedValues, Collection<T> actualValues) {
        Assertions.assertEquals(expectedValues.size(), actualValues.size());
        for (Object expected : expectedValues) {
            boolean anyMatch = actualValues.stream().anyMatch(actual -> actual.equals(expected));
            if (!anyMatch) {
                String errMsg = String.format("List are not equal. \n Expected: %s\n  Actual: %s\n",
                        expectedValues.stream().map(o -> o != null ? o + " - " + o.hashCode() : "null").collect(Collectors.toList()),
                        actualValues.stream().map(o -> o != null ? o + " - " + o.hashCode() : "null").collect(Collectors.toList()));
                Assertions.fail(errMsg);
            }
        }
    }

    public static void verifyGridEquals(Grid grid, String expectedGrid) throws IOException, CellOutOfBoundException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ConsoleIO consoleIO = new ConsoleIO(grid, null, outputStream);
            consoleIO.displayCompleteGameGrid();
            Assertions.assertEquals(expectedGrid, outputStream.toString());
        }
    }
}
