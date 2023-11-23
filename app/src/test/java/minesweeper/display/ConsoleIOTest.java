package minesweeper.display;

import minesweeper.game.exceptions.CellOutOfBoundException;
import minesweeper.game.exceptions.EndOfGameException;
import minesweeper.game.exceptions.InvalidInputException;
import minesweeper.game.display.ConsoleIO;
import minesweeper.game.grid.CurrentGrid;
import minesweeper.game.grid.Grid;
import minesweeper.grid.GridResources;
import minesweeper.game.grid.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static minesweeper.game.grid.Grid.createGrid;

class ConsoleIOTest {


    @Test
    public void displaySimpleGameGridTest() throws CellOutOfBoundException, InvalidInputException, EndOfGameException, IOException {
        Grid grid = createGrid(GridResources.complexGridTwoNoHints());

        CurrentGrid currentGrid = new CurrentGrid();
        currentGrid = currentGrid.getNewGrid(new Position(4, 4), grid);

        String expectedDisplay = GridResources.complexGridTwoHiddenCells();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ConsoleIO consoleIO = new ConsoleIO(grid, null, outputStream);
        consoleIO.displayPartialGameGrid(currentGrid.getRevealedCells());

        Assertions.assertEquals(expectedDisplay, outputStream.toString());
    }


    @Test
    void takeUserInput() throws IOException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream("4,4".getBytes(StandardCharsets.UTF_8))) {
            ConsoleIO consoleIO = new ConsoleIO(new Grid(), inputStream, System.out);
            Assertions.assertEquals(new Position(4, 4), consoleIO.takeUserInput());
        }
    }

}