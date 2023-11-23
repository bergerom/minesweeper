package minesweeper.grid;

public class GridResources {

    public static String invalidCharacterGrid() {
        return """
                |- -|
                |* 2|
                |2 *|
                |- -|""";
    }

    public static String complexGridOneWithHints() {
        return """
                |- - - - -|
                |1 2 2 1 o|
                |1 x x 1 o|
                |1 2 2 2 1|
                |o 1 1 3 x|
                |o 1 x 3 x|
                |- - - - -|""";
    }

    public static String complexGridOneNoHints() {
        return complexGridOneWithHints().replaceAll("[0-9]", "o");
    }

    public static String complexGridTwoWithHints() {
        return """
                |- - - - -|
                |o 1 x 1 o|
                |1 2 2 1 o|
                |1 x 1 o o|
                |1 1 1 o o|
                |o o o o o|
                |- - - - -|""";
    }

    public static String complexGridTwoNoHints() {
        return complexGridTwoWithHints().replaceAll("[0-9]", "o");
    }

    public static String complexGridTwoHiddenCells() {
        return """
                |- - - - -|
                |? ? ? 1 o|
                |? ? ? 1 o|
                |? ? 1 o o|
                |1 1 1 o o|
                |o o o o o|
                |- - - - -|""";
    }

}
