package com.ryabokon.hex.ui;

import com.ryabokon.hex.core.Item;

import java.util.Map;
import java.util.function.Supplier;

public class ConsolePrinter {

    private Supplier<Map<String, Item>> getBoard;
    private Map<String, Item> board;
    private Integer size;

    public ConsolePrinter(Supplier<Map<String, Item>> getBoard) {
        this.getBoard = getBoard;
    }

    protected String gs(String xy) {
        return getSymbolByItemType(board.get(xy));
    }

    protected String getSymbolByItemType(Item item) {
        switch (item) {
            case RUBY:
                return "*";
            case NEON:
                return "o";
            case EMPTY:
                return " ";
            case JUMP:
                return ".";
            case CLONE:
                return ":";
            case TARGET:
                return "@";
            default:
                return "?";
        }
    }

    public void printBoard() {
        this.board = getBoard.get();
        this.size = board.size();

        if (size == 19) {
            printNineteenCombBoard();
        } else {
            printSevenCombBoard();
        }
    }

    protected void printSevenCombBoard() {

//      "    < >    "
//      "< >     < >"
//      "    < >    "
//      "< >     < >"
//      "    < >    "

        String asciiBoard = "    <" + gs("0:1") + ">    \n" +
                "<" + gs("-1:1") + ">     <" + gs("1:0") + ">\n" +
                "    <" + gs("0:0") + ">    \n" +
                "<" + gs("-1:0") + ">     <" + gs("1:-1") + ">\n" +
                "    <" + gs("0:-1") + ">    \n" +
                "___________";

        System.out.println(asciiBoard);

    }

    protected void printNineteenCombBoard() {
        //TODO use logger and patterns:
        //TODO log.info("<{}>", gs("02"));

//      "        < >         "
//      "    < >     < >     "
//      "< >     < >     < > "
//      "    < >     < >     "
//      "< >     < >     < > "
//      "    < >     < >     "
//      "< >     < >     < > "
//      "    < >     < >     "
//      "        < >         "


        StringBuilder sb = new StringBuilder()
                .append("        <" + gs("0:2") + ">         \n")
                .append("    <" + gs("-1:2") + ">     <" + gs("1:1") + ">    \n")
                .append("<" + gs("-2:2") + ">     <" + gs("0:1") + ">     <" + gs("2:0") + "> \n")
                .append("    <" + gs("-1:1") + ">     <" + gs("1:0") + ">     \n")
                .append("<" + gs("-2:1") + ">     <" + gs("0:0") + ">     <" + gs("2:-1") + "> \n")
                .append("    <" + gs("-1:0") + ">     <" + gs("1:-1") + ">     \n")
                .append("<" + gs("-2:0") + ">     <" + gs("0:-1") + ">     <" + gs("2:-2") + "> \n")
                .append("    <" + gs("-1:-1") + ">     <" + gs("1:-2") + ">     \n")
                .append("        <" + gs("0:-2") + ">         \n");

        System.out.println(sb);
    }
}
