package minesweeper.game.display;

import minesweeper.game.exceptions.CellOutOfBoundException;
import minesweeper.game.grid.Cell;
import minesweeper.game.grid.Position;

import java.io.IOException;
import java.util.Set;

public interface GameIO {

    void displayPartialGameGrid(Set<Cell> onlyDisplay) throws CellOutOfBoundException, IOException;

    void displayCompleteGameGrid() throws CellOutOfBoundException, IOException;

    Position takeUserInput() throws IOException;

    void displayWinMessage() throws IOException;

    void displayLooseMessage(int score) throws IOException;

    void displayIncorrectPositionMessage(Position position) throws IOException;
}
