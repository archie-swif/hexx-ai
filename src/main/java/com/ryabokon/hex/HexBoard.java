package com.ryabokon.hex;

import java.util.*;
import java.util.stream.Collectors;


//       (0, 1)
// (-1,1)      (1, 0)
//       (0, 0)
// (-1,0)      (1,-1)
//       (0,-1)

public class HexBoard {

    private Map<String, Hex> combs = new HashMap<>();
    private List<Hex> markedCombs = new ArrayList<>();
    private boolean isInMarkedState = false;
    private Hex selectedHex;

    public HexBoard() {
        combs.put("00", new Hex(Props.EMPTY));
        combs.put("01", new Hex(Props.EMPTY));
        combs.put("0-1", new Hex(Props.EMPTY));
        combs.put("-11", new Hex(Props.EMPTY));
        combs.put("-10", new Hex(Props.EMPTY));
        combs.put("10", new Hex(Props.EMPTY));
        combs.put("1-1", new Hex(Props.EMPTY));
    }


    public void selectHex(int x, int y) {
        Hex hex = getHex(x, y);

        if (!isInMarkedState) {
            if (hex.isProp()) {
                markSurroundingsForMove(x, y);
                selectedHex = hex;
            }
        } else {
            if (hex.isMarkedClone()) {
                hex.content = selectedHex.content;
            }

            if (hex.isMarkedMove()) {
                hex.content = selectedHex.content;
                selectedHex.content = Props.EMPTY;
            }
            fight(x, y);

            cleanMovementMarks();
            selectedHex = null;

        }
        printBoard();
    }

    protected void fight(int x, int y) {
        printBoard();
        Props oppositeProp = Props.RUBY.equals(selectedHex.content) ? Props.NEON : Props.RUBY;

        getFirstCircle(x, y)
                .stream()
                .filter(h -> oppositeProp.equals(h.content))
                .forEach(h -> h.content = selectedHex.content);
    }

    protected void cleanMovementMarks() {
        markedCombs
                .stream()
                .filter(Hex::isMarked)
                .forEach(h -> h.content = Props.EMPTY);
        markedCombs.clear();
        isInMarkedState = false;
    }

    protected void markSurroundingsForMove(int x, int y) {

        List<Hex> firstCircle = getFirstCircle(x, y)
                .stream()
                .filter(Hex::isEmpty)
                .peek(h -> h.content = Props.CLONE)
                .collect(Collectors.toList());

        List<Hex> secondCircle = getSecondCircle(x, y)
                .stream()
                .filter(Hex::isEmpty)
                .peek(h -> h.content = Props.MOVE)
                .collect(Collectors.toList());

        markedCombs.addAll(firstCircle);
        markedCombs.addAll(secondCircle);

        isInMarkedState = true;
    }

    protected List<Hex> getFirstCircle(int x, int y) {
        //TODO optimize 18 similar lines?

        List<Hex> circle = new ArrayList<>();
        circle.add(getHex(x + 0, y + 1));
        circle.add(getHex(x + 1, y + 0));
        circle.add(getHex(x + 1, y - 1));
        circle.add(getHex(x + 0, y - 1));
        circle.add(getHex(x - 1, y + 0));
        circle.add(getHex(x - 1, y + 1));
        circle.removeIf(Objects::isNull);
        return circle;
    }

    protected List<Hex> getSecondCircle(int x, int y) {
        //TODO optimize 18 similar lines?

        List<Hex> circle = new ArrayList<>();
        circle.add(getHex(x + 0, y + 2));
        circle.add(getHex(x + 1, y + 1));
        circle.add(getHex(x + 2, y + 0));
        circle.add(getHex(x + 2, y - 1));
        circle.add(getHex(x + 2, y - 2));
        circle.add(getHex(x + 1, y - 2));
        circle.add(getHex(x + 0, y - 2));
        circle.add(getHex(x - 1, y - 1));
        circle.add(getHex(x - 2, y + 0));
        circle.add(getHex(x - 2, y + 1));
        circle.add(getHex(x - 2, y + 2));
        circle.add(getHex(x - 1, y + 2));
        circle.removeIf(Objects::isNull);
        return circle;
    }

    //------------------ visualization -------------

    protected String getSymbol(int x, int y) {
        return getHex(x, y).content.toString();
    }

    public void printBoard() {

//      "    < >    "
//      "< >     < >"
//      "    < >    "
//      "< >     < >"
//      "    < >    "

        String asciiBoard = "    <" + getSymbol(0, 1) + ">    \n" +
                "<" + getSymbol(-1, 1) + ">     <" + getSymbol(1, 0) + ">\n" +
                "    <" + getSymbol(0, 0) + ">    \n" +
                "<" + getSymbol(-1, 0) + ">     <" + getSymbol(1, -1) + ">\n" +
                "    <" + getSymbol(0, -1) + ">    \n" +
                "___________";

        System.out.println(asciiBoard);

    }

    //----------------- internal --------------

    protected Hex getHex(int x, int y) {
        return combs.get("" + x + y);
    }

    protected void putProp(int x, int y, Props prop) {
        getHex(x, y).content = prop;
    }

}
