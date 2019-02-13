package com.ryabokon.hex;

import java.util.*;
import java.util.stream.Collectors;


//       (0, 1)
// (-1,1)      (1, 0)
//       (0, 0)
// (-1,0)      (1,-1)
//       (0,-1)

public class HexBoard {

    Map<String, Hex> combs = new HashMap<>();
    List<Hex> markedCombs = Collections.EMPTY_LIST;
    boolean isInMarkedState = false;

    public HexBoard() {
        combs.put("00", new Hex(Props.EMPTY));
        combs.put("01", new Hex(Props.EMPTY));
        combs.put("0-1", new Hex(Props.EMPTY));
        combs.put("-11", new Hex(Props.RUBY)); //*
        combs.put("-10", new Hex(Props.EMPTY));
        combs.put("10", new Hex(Props.EMPTY));
        combs.put("1-1", new Hex(Props.EMPTY));
    }


    public void selectComb(int x, int y) {
        Hex hex = getHex(x, y);

        if (!isInMarkedState) {
            if (hex.isProp()) {
                markSurroundingsForMove(x, y);
            }
        } else {
            if (hex.isMarked()) {
                putProp(x, y, Props.RUBY);
            }
            cleanMovementMarks();
        }
    }

    protected void markSurroundingsForMove(int x, int y) {
        markedCombs = getSurroundingHexes(x, y)
                .stream()
                .filter(Hex::isEmpty)
                .peek(h -> h.content = Props.CLONE)
                .collect(Collectors.toList());
        isInMarkedState = true;

    }

    protected void cleanMovementMarks() {
        markedCombs
                .stream()
                .filter(Hex::isMarked)
                .forEach(h -> h.content = Props.EMPTY);
        isInMarkedState = false;
    }

    protected List<Hex> getSurroundingHexes(int x, int y) {
        List<Hex> surroundings = new ArrayList<>(6);

        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (i == 0 && j == 0) continue;
                Optional.ofNullable(getHex(x + i, y + j)).ifPresent(surroundings::add);
            }
        }
        return surroundings;
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
