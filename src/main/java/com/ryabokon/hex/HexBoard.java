package com.ryabokon.hex;

import java.util.*;
import java.util.stream.Collectors;


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
    private boolean boardInMarkedState = false;
    private Comb selectedComb;
    private ConsolePrinter printer;

    public HexBoard() {
        //Center
        combs.put("00", new Comb());

        //First circle
        combs.put("01", new Comb());
        combs.put("0-1", new Comb());
        combs.put("-11", new Comb());
        combs.put("-10", new Comb());
        combs.put("10", new Comb());
        combs.put("1-1", new Comb());

        //Second circle
        combs.put("02", new Comb());
        combs.put("11", new Comb());
        combs.put("20", new Comb());
        combs.put("2-1", new Comb());
        combs.put("2-2", new Comb());
        combs.put("1-2", new Comb());
        combs.put("0-2", new Comb());
        combs.put("-1-1", new Comb());
        combs.put("-20", new Comb());
        combs.put("-21", new Comb());
        combs.put("-22", new Comb());
        combs.put("-12", new Comb());

        printer = new ConsolePrinter(x -> combs.get(x), combs.size());
    }


    public void selectComb(int x, int y) {
        Comb clickedComb = getComb(x, y);

        if (boardInMarkedState) {
            if (clickedComb.isMarkedClone()) {
                clickedComb.content = this.selectedComb.content;
                fight(x, y);
                cleanMovementMarks();
            } else if (clickedComb.isMarkedMove()) {
                clickedComb.content = this.selectedComb.content;
                this.selectedComb.content = Items.EMPTY;
                this.selectedComb = clickedComb;
                fight(x, y);
                cleanMovementMarks();
            }

            this.selectedComb = null;
            cleanMovementMarks();

        } else {
            if (clickedComb.hasItem()) {
                markSurroundingsForMove(x, y);
                this.selectedComb = clickedComb;
            }
        }
        printer.printBoard();

    }

    protected void fight(int x, int y) {
        Items enemyItem = Items.RUBY.equals(selectedComb.content) ? Items.NEON : Items.RUBY;

        getFirstCircle(x, y)
                .stream()
                .filter(h -> enemyItem.equals(h.content))
                .forEach(h -> h.content = selectedComb.content);
    }

    protected void cleanMovementMarks() {
        markedCombs
                .stream()
                .filter(Comb::isMarked)
                .forEach(h -> h.content = Items.EMPTY);
        markedCombs.clear();
        boardInMarkedState = false;
    }

    protected void markSurroundingsForMove(int x, int y) {

        List<Comb> firstCircle = getFirstCircle(x, y)
                .stream()
                .filter(Comb::isEmpty)
                .peek(h -> h.content = Items.CLONE)
                .collect(Collectors.toList());

        List<Comb> secondCircle = getSecondCircle(x, y)
                .stream()
                .filter(Comb::isEmpty)
                .peek(h -> h.content = Items.MOVE)
                .collect(Collectors.toList());

        markedCombs.addAll(firstCircle);
        markedCombs.addAll(secondCircle);

        boardInMarkedState = true;
    }

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
        return combs.get("" + x + y);
    }

    protected void putItem(int x, int y, Items item) {
        getComb(x, y).content = item;
    }

}
