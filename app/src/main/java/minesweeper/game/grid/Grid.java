package minesweeper.game.grid;

import minesweeper.game.exceptions.CellOutOfBoundException;
import minesweeper.game.exceptions.InvalidInputException;

import java.util.*;
import java.util.stream.Stream;

public class Grid {
    protected InternalGrid cells;

    private Grid(Builder builder) {
        this.cells = builder.grid.cells;
    }

    public Grid() {
    }

    public static Grid createGrid(Integer nbCells) throws InvalidInputException {
        return createGrid(nbCells, null);
    }

    public static Grid createGrid(Integer nbCells, List<Position> bombCells) throws InvalidInputException {
        Builder builder = new Builder();
        builder.setGridSize(nbCells);
        if (bombCells != null) {
            builder.initFromBombPosition(bombCells);
        }
        builder.initializeCellValues();
        return builder.build();
    }

    public static Grid createGrid(String gridInput) throws InvalidInputException {
        return new Builder()
                .initFromAsciiGrid(gridInput)
                .initializeCellValues()
                .build();
    }

    public static Grid createRandomGrid(int difficulty) throws InvalidInputException {
        Random random = new Random();
        int rowLength = random.nextInt(3, 10);
        int nbCells = rowLength * rowLength;

        int nbBombs = getNbBombs(rowLength, difficulty, random);
        Set<Position> bombs = new HashSet<>();
        while (bombs.size() < nbBombs) {
            int row = random.nextInt(rowLength);
            int col = random.nextInt(rowLength);
            bombs.add(new Position(row, col));
        }

        return createGrid(nbCells, bombs.stream().toList());
    }

    private static int getNbBombs(int rowLength, int difficulty, Random random) {
        return random.nextInt(rowLength - 1, rowLength + (int) Math.sqrt(rowLength) * (difficulty - 1));
    }

    public List<Cell> getCells() {
        return this.cells.getAllCells();
    }

    public int getNbBombs() {
        return this.cells.getBombCells().size();
    }

    public int getNbCells() {
        return this.cells.getAllCells().size();
    }

    public int getRowLength() {
        return this.cells.getRowLength();
    }

    public Cell getCellAt(Position position) throws CellOutOfBoundException {
        return cells.getCellAt(position)
                .orElseThrow(() -> {
                    String excMsg = String.format("Cell at position (%s,%s) is outside of grid.",
                            position.row(), position.col());
                    return new CellOutOfBoundException(excMsg);
                });
    }

    public Cell getCellFromIndex(int index) throws CellOutOfBoundException {
        return getCellAt(cells.getPositionFromIndex(index));
    }

    public static class Builder {

        private final Grid grid;

        public Builder() {
            grid = new Grid();
        }

        Builder setGridSize(int nbCells) throws InvalidInputException {
            if (squareRootIsNotWhole(nbCells)) {
                throw new InvalidInputException("Grid must be square.");
            }
            grid.cells = new InternalGrid(nbCells);
            return this;
        }

        private static boolean squareRootIsNotWhole(int number) {
            return Math.ceil(Math.sqrt(number)) != Math.sqrt(number);
        }


        Builder initFromBombPosition(List<Position> bombCells) {

            int rowLength = grid.cells.getRowLength();

            for (int row = 0; row < rowLength; row++) {
                for (int col = 0; col < rowLength; col++) {
                    Position position = new Position(col, row);
                    boolean isABomb = bombCells.contains(position);

                    int index = row * rowLength + col;
                    grid.cells.addElement(new Cell(grid.cells.getPositionFromIndex(index), isABomb));

                }
            }
            return this;
        }

        public Builder initFromAsciiGrid(String gridInput) throws InvalidInputException {
            verifyCharacters(gridInput);

            List<String> lines = Arrays
                    .stream(gridInput.split("\n")).toList();

            int nbLines = getNbLines(lines);

            allLinesHaveSameLength(lines);

            List<CellType> lineCells = lines
                    .subList(1, nbLines)
                    .stream()
                    .flatMapToInt(String::chars)
                    .mapToObj(c -> (char) c)
                    .map(Object::toString)
                    .filter(c -> CellType.fromString(c) != null)
                    .map(CellType::fromString)
                    .toList();

            int nbCellsGridInput = lineCells.size();
            if (squareRootIsNotWhole(nbCellsGridInput)) {
                throw new InvalidInputException("Grid must be square.");
            }
            grid.cells = new InternalGrid(nbCellsGridInput);

            for (int index = 0; index < nbCellsGridInput; index++) {
                CellType character = lineCells.get(index);
                switch (character) {
                    case EMPTY -> grid.cells.addElement(new Cell(grid.cells.getPositionFromIndex(index), false));
                    case BOMB -> grid.cells.addElement(new Cell(grid.cells.getPositionFromIndex(index), true));
                    default -> {
                    }
                    // pass
                }
            }
            return this;
        }

        private static int getNbLines(List<String> lines) throws InvalidInputException {
            int nbLines = lines.size();
            if (nbLines < 2) {
                throw new InvalidInputException("Number of lines should be > 2.");
            }
            return nbLines;
        }

        private void verifyCharacters(String gridInput) throws InvalidInputException {
            List<String> allowedChars = CellType.getAllowedCharactersForCell();
            if (Stream.of(gridInput).anyMatch(allowedChars::contains)) {
                throw new InvalidInputException("Invalid characted detected in grid input.");
            }
        }

        private static void allLinesHaveSameLength(List<String> lines) throws InvalidInputException {
            int lineLength = lines.get(0).length();
            if (lines.stream().anyMatch(l -> l.length() != lineLength))
                throw new InvalidInputException("Invalid input format. All lines should have the same length.");
        }

        public Builder initializeCellValues() {
            for (Cell cell : grid.cells.getBombCells()) {
                cell.computeBombsNearby(grid);
            }
            return this;
        }

        public Grid build() {
            return new Grid(this);
        }
    }
}
