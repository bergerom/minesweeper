package minesweeper.game.display;

import minesweeper.game.grid.Cell;
import minesweeper.game.grid.Grid;
import minesweeper.game.grid.Position;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GraphicIO implements GameIO {

    private final Grid grid;

    private final HashMap<Cell, Button> positionCells = new HashMap<>();

    private Position lastPositionClicked;

    public GraphicIO(Grid grid) {
        this.grid = grid;
    }

    @Override
    public void displayPartialGameGrid(Set<Cell> onlyDisplay) {
        if (positionCells.isEmpty()) {
            initGraphicGrid();
        } else {
            updateGraphicGrid(onlyDisplay);
        }
    }

    private void initGraphicGrid() {
        Frame frame = new Frame();
        frame.setLayout(new BorderLayout());

        Panel topPanel = getTopPanel();
        Panel middlePanel = getCenterPanel();
        Panel bottomPanel = getBottomPanel();

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private Panel getCenterPanel() {
        Panel panel = new Panel();
        //panel.setTitle("Minesweeper");
        panel.setSize(300, 300);

        panel.setLayout(new GridLayout(grid.getRowLength(), grid.getRowLength()));
        for (Cell cell : grid.getCells()) {

            Button button = new Button();
            button.setLabel("?");
            button.setActionCommand(getPositionStr(cell.getPosition()));
            button.addActionListener(new ButtonClickListener());

            panel.add(button);
            positionCells.put(cell, button);
        }
        return panel;
    }

    private Panel getTopPanel() {
        Panel panel = new Panel();
        panel.setLayout(new FlowLayout());
        // TODO : replace with dynamic text
        panel.add(new TextField("Number of Bombs : 10"));
        panel.add(new TextField("Score : 0"));
        panel.add(new TextField("Round : 0"));
        return panel;
    }

    private Panel getBottomPanel() {
        Panel panel = new Panel();
        panel.setLayout(new FlowLayout());
        panel.add(new TextField("This is a commentary"));
        return panel;
    }

    private void updateGraphicGrid(Set<Cell> onlyDisplay) {
        positionCells.forEach((cell, btn) -> {
            if (onlyDisplay.contains(cell)) {
                btn.setLabel(cell.toString());
                btn.setFont(new Font("Dialog", Font.BOLD, 20));
            }
        });
    }

    public static String getPositionStr(Position position) {
        return position.row() + "," + position.col();
    }

    public static Position parsePositionStr(String str) {
        return new Position(Integer.parseInt(str.substring(0, 1)),
                Integer.parseInt(str.substring(2, 3)));
    }

    @Override
    public void displayCompleteGameGrid() {
        HashSet<Cell> allCells = new HashSet<>(this.grid.getCells());
        displayPartialGameGrid(allCells);
    }

    @Override
    public Position takeUserInput() {
        while (lastPositionClicked == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Position position = lastPositionClicked;
        lastPositionClicked = null;
        return position;
    }

    @Override
    public void displayWinMessage() throws IOException {
        // TODO : implement
    }

    @Override
    public void displayLooseMessage(int score) throws IOException {
        // TODO : implement
    }

    @Override
    public void displayIncorrectPositionMessage(Position position) throws IOException {
        // TODO : implement
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            lastPositionClicked = parsePositionStr(command);
        }
    }
}
