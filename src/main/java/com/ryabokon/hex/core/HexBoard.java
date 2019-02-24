package com.ryabokon.hex.core;

import com.ryabokon.hex.ui.ConsolePrinter;

import java.util.*;
import java.util.stream.Collectors;

import static com.ryabokon.hex.core.State.SELECTED_FOR_MOVE;
import static com.ryabokon.hex.core.State.WAITING_FOR_SELECTION;


//               (0, 2)
//        (-1, 2)      (1, 1)
// (-2, 2)       (0, 1)      (2, 0)
//        (-1, 1)      (1, 0)
// (-2, 1)       (0, 0)      (2, -1)
//        (-1, 0)      (1,-1)
// (-2, 0)       (0,-1)      (2, -2)
//        (-1,-1)      (1,-2)
//               (0,-2)

public class HexBoard {

    private Map<String, Comb> combs = new HashMap<>();
    private List<Comb> markedCombs = new ArrayList<>();
    private State boardState = WAITING_FOR_SELECTION;
    private Comb selectedComb;
    private ConsolePrinter printer = new ConsolePrinter(this::getBoardMap);

    public HexBoard() {
        //Center
        combs.put("0:0", new Comb());

        //First circle
        combs.put("0:1", new Comb());
        combs.put("0:-1", new Comb());
        combs.put("-1:1", new Comb());
        combs.put("-1:0", new Comb());
        combs.put("1:0", new Comb());
        combs.put("1:-1", new Comb());

        //Second circle
        combs.put("0:2", new Comb());
        combs.put("1:1", new Comb());
        combs.put("2:0", new Comb());
        combs.put("2:-1", new Comb());
        combs.put("2:-2", new Comb());
        combs.put("1:-2", new Comb());
        combs.put("0:-2", new Comb());
        combs.put("-1:-1", new Comb());
        combs.put("-2:0", new Comb());
        combs.put("-2:1", new Comb());
        combs.put("-2:2", new Comb());
        combs.put("-1:2", new Comb());
    }

    public void printBoard() {
        printer.printBoard();
    }

    public Map<String, Item> getBoardMap() {
        return combs.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(Map.Entry::getKey, v -> v.getValue().content)
                );
    }

    public void selectComb(String xy) {
        String[] split = xy.split(":");
        selectComb(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }

    public void selectComb(int x, int y) {
        Comb clickedComb = getComb(x, y);

        if (SELECTED_FOR_MOVE.equals(boardState)) {
            if (clickedComb.isMarkedClone()) {

                clickedComb.content = Item.TARGET;
                printer.printBoard();

                clickedComb.content = this.selectedComb.content;
                fight(x, y);
            } else if (clickedComb.isMarkedMove()) {
                clickedComb.content = Item.TARGET;
                printer.printBoard();

                clickedComb.content = this.selectedComb.content;
                this.selectedComb.content = Item.EMPTY;
                this.selectedComb = clickedComb;
                fight(x, y);
            }

            this.selectedComb = null;
            cleanMovementMarks();

        } else {
            if (clickedComb.hasPlayer()) {
                markSurroundingsForMove(x, y);
                this.selectedComb = clickedComb;
            }
        }

        printer.printBoard();
    }

    protected void fight(int x, int y) {
        Item enemyItem = selectedComb.content.getOpposite();

        getFirstCircle(x, y)
                .stream()
                .filter(h -> enemyItem.equals(h.content))
                .forEach(h -> h.content = selectedComb.content);
    }

    protected void cleanMovementMarks() {
        markedCombs
                .stream()
                .filter(Comb::isMarked)
                .forEach(h -> h.content = Item.EMPTY);
        markedCombs.clear();
        boardState = WAITING_FOR_SELECTION;
    }

    protected void markSurroundingsForMove(int x, int y) {

        List<Comb> firstCircle = getFirstCircle(x, y)
                .stream()
                .filter(Comb::isEmpty)
                .peek(h -> h.content = Item.CLONE)
                .collect(Collectors.toList());

        List<Comb> secondCircle = getSecondCircle(x, y)
                .stream()
                .filter(Comb::isEmpty)
                .peek(h -> h.content = Item.JUMP)
                .collect(Collectors.toList());

        markedCombs.addAll(firstCircle);
        markedCombs.addAll(secondCircle);

        boardState = SELECTED_FOR_MOVE;
    }

    //       (0, 1)
    // (-1,1)      (1, 0)
    //       (0, 0)
    // (-1,0)      (1,-1)
    //       (0,-1)
    protected List<Comb> getFirstCircle(int x, int y) {
        //TODO optimize 18 similar lines?

        List<Comb> circle = new ArrayList<>();
        circle.add(getComb(x + 0, y + 1));
        circle.add(getComb(x + 1, y + 0));
        circle.add(getComb(x + 1, y - 1));
        circle.add(getComb(x + 0, y - 1));
        circle.add(getComb(x - 1, y + 0));
        circle.add(getComb(x - 1, y + 1));
        circle.removeIf(Objects::isNull);
        return circle;
    }

    protected List<Comb> getSecondCircle(int x, int y) {
        //TODO optimize 18 similar lines?

        List<Comb> circle = new ArrayList<>();
        circle.add(getComb(x + 0, y + 2));
        circle.add(getComb(x + 1, y + 1));
        circle.add(getComb(x + 2, y + 0));
        circle.add(getComb(x + 2, y - 1));
        circle.add(getComb(x + 2, y - 2));
        circle.add(getComb(x + 1, y - 2));
        circle.add(getComb(x + 0, y - 2));
        circle.add(getComb(x - 1, y - 1));
        circle.add(getComb(x - 2, y + 0));
        circle.add(getComb(x - 2, y + 1));
        circle.add(getComb(x - 2, y + 2));
        circle.add(getComb(x - 1, y + 2));
        circle.removeIf(Objects::isNull);
        return circle;
    }


    //----------------- internal --------------

    protected Comb getComb(int x, int y) {
        return combs.get(x + ":" + y);
    }

    public void putItem(int x, int y, Item item) {
        getComb(x, y).content = item;
    }

}
