package com.ryabokon.hex;

import java.util.*;


//       (0, 1)
// (-1,1)      (1, 0)
//       (0, 0)
// (-1,0)      (1,-1)
//       (0,-1)

public class HexBoard {

    private Map<String, Hex> combs = new HashMap<>();
    private List<Hex> markedCombs = new ArrayList<>(18);
    private boolean isInMarkedState = false;
    private Hex selectedHex;

    public HexBoard() {
        combs.put("00", new Hex(Props.EMPTY));
        combs.put("01", new Hex(Props.EMPTY));
        combs.put("0-1", new Hex(Props.EMPTY));
        combs.put("-11", new Hex(Props.RUBY)); //*
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
                hex.content = Props.RUBY;
            }

            if (hex.isMarkedMove()) {
                hex.content = Props.RUBY;
                selectedHex.content = Props.EMPTY;
            }
            cleanMovementMarks();
            selectedHex = null;

        }
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
        //TODO optimize 18 similar lines?

        //First circle
        markAndAdd(x + 0, y + 1, Props.CLONE);
        markAndAdd(x + 1, y + 0, Props.CLONE);
        markAndAdd(x + 1, y - 1, Props.CLONE);
        markAndAdd(x + 0, y - 1, Props.CLONE);
        markAndAdd(x - 1, y + 0, Props.CLONE);
        markAndAdd(x - 1, y + 1, Props.CLONE);

        //Second circle
        markAndAdd(x + 0, y + 2, Props.MOVE);
        markAndAdd(x + 1, y + 1, Props.MOVE);
        markAndAdd(x + 2, y + 0, Props.MOVE);
        markAndAdd(x + 2, y - 1, Props.MOVE);

        markAndAdd(x + 2, y - 2, Props.MOVE);
        markAndAdd(x + 1, y - 2, Props.MOVE);
        markAndAdd(x + 0, y - 2, Props.MOVE);
        markAndAdd(x - 1, y - 1, Props.MOVE);

        markAndAdd(x - 2, y + 0, Props.MOVE);
        markAndAdd(x - 2, y + 1, Props.MOVE);
        markAndAdd(x - 2, y + 2, Props.MOVE);
        markAndAdd(x - 1, y + 2, Props.MOVE);

        isInMarkedState = true;
    }

    protected void markAndAdd(int x, int y, Props prop) {
        Optional.ofNullable(getHex(x, y))
                .filter(Hex::isEmpty)
                .ifPresent(h -> {
                    h.content = prop;
                    markedCombs.add(h);
                });
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
