package minesweeper.game.grid;

import java.util.Objects;

public class Cell {
    public Integer row;
    public Integer col;
    private final CellType cellType;

    private Integer bombsNearby; // number of bombs in adjacent cells

    public Cell(Position position, boolean isABomb) {

        this.row = position.row();
        this.col = position.col();

        if (isABomb) {
            this.cellType = CellType.BOMB;
        } else {
            this.cellType = CellType.EMPTY;
        }

        this.bombsNearby = 0;
    }

    protected void computeBombsNearby(Grid grid) {
        for (Cell nextToBomb : grid.cells.getAdjacentCells(this.getPosition())) {
            nextToBomb.bombsNearby += 1;
        }
    }

    public Boolean hasBombsNearby() {
        return bombsNearby > 0;
    }

    public Boolean isABomb() {
        return CellType.BOMB.equals(cellType);
    }


    public Position getPosition() {
        return new Position(this.row, this.col);
    }

    @Override
    public String toString() {
        if (this.isABomb()) {
            return CellType.BOMB.getRepresentation();
        } else if (this.bombsNearby == 0) {
            return CellType.EMPTY.getRepresentation();
        } else {
            return bombsNearby.toString();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Cell) {
            return Objects.equals(((Cell) other).row, this.row)
                    && Objects.equals(((Cell) other).col, this.col)
                    && Objects.equals(((Cell) other).cellType, this.cellType);
        }
        return false;
    }
}
