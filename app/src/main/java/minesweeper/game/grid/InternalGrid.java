package minesweeper.game.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InternalGrid {
    private final ArrayList<Cell> cells = new ArrayList<>();
    private final int nbCells;
    private final int rowLength;

    public InternalGrid(int nbCells) {
        this.nbCells = nbCells;
        this.rowLength = getRowLength();
    }

    protected void addElement(Cell cell) {
        cells.add(cell);
    }

    public Optional<Cell> getCellAt(Position position) {
        if (isOutsideBounds(position)) {
            return Optional.empty();
        }
        return Optional.of(cells.get(rowLength * position.row() + position.col()));
    }

    protected Position getPositionFromIndex(int index) {
        int row = index / getRowLength();
        int col = index % getRowLength();

        return new Position(row, col);
    }

    protected boolean isOutsideBounds(Position position) {
        int rowLength = getRowLength();
        return position.row() < 0 || position.row() >= rowLength ||
                position.col() < 0 || position.col() >= rowLength;
    }

    protected int getRowLength() {
        return (int) Math.round(Math.sqrt(nbCells));
    }

    protected List<Cell> getAdjacentCells(Position position) {
        ArrayList<Cell> adjacentCells = new ArrayList<>();
        int col_index = position.col();
        int row_index = position.row();
        for (int row = row_index - 1; row <= row_index + 1; row++) {
            for (int col = col_index - 1; col <= col_index + 1; col++) {
                Position adjPosition = new Position(row, col);
                if (!adjPosition.equals(position)) {
                    getCellAt(new Position(row, col))
                            .ifPresent(adjacentCells::add);
                }
            }
        }
        return adjacentCells;
    }

    protected Optional<Cell> getAdjacentCell(Cell cell, MoveDirection moveDirection) {
        return switch (moveDirection) {
            case LEFT -> getCellAt(new Position(cell.row, cell.col - 1));
            case RIGHT -> getCellAt(new Position(cell.row, cell.col + 1));
            case DOWN -> getCellAt(new Position(cell.row + 1, cell.col));
            case UP -> getCellAt(new Position(cell.row - 1, cell.col));
        };
    }

    protected List<Cell> getBombCells() {
        return cells.stream().filter(Cell::isABomb).toList();
    }

    protected List<Cell> getAllCells() {
        return this.cells;
    }


}
