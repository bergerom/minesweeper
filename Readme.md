# Minesweeper
A simple minesweeper game with two interfaces :
- graphical interface
- console interface

## Arguments
``-g : enable graphical interface``

``-d : difficulty Difficulty level (from 1 to 3)``
## Design Patterns and architectural concerns
This is an overview of some of the features of this project that follow guidelines from
clean code, clean architecture and design patterns :
- Use of Strategy pattern with the `GameIO` interface
- Joshua Bloch's Builder pattern for building `Grid`
- Use of unmutable classes (`CurrentGrid` class, `Position` record)
- Use of unmutable variables in classes, ie `final` variable
- Dependency Inversion principle achieved using dependency injection (`Game` class)
- Testing using stubs

## Improvement ideas
- Use light threads instead of `Thread.stop` in GraphicIO
- Implement retry for console I/O when cell is out of bounds or user input is wrong
- Implements other game using the same pattern (`Game` class could be reused)