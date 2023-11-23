package minesweeper.game.grid;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public enum CellType {
    EMPTY("o"),
    BOMB("x");

    private final String cellAscii;

    CellType(String cellAscii) {
        this.cellAscii = cellAscii;
    }

    public static CellType fromString(String character) {
        for (CellType c : CellType.values()) {
            if (c.cellAscii.equals(character)) {
                return c;
            }
        }
        return null;
    }

    public String getRepresentation() {
        return cellAscii;
    }

    public static List<String> getAllowedCharactersForCell() {
        Stream<String> enumChars = Arrays.stream(CellType.values()).map(Enum::toString);
        Stream<String> integers = IntStream.range(0, 10).mapToObj(String::valueOf);

        return Stream.concat(enumChars, integers).collect(Collectors.toList());

    }

}
