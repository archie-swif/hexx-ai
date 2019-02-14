package com.ryabokon.hex;

import java.util.function.Function;

public class ConsolePrinter {

    private Function<String, Comb> getCombFunction;
    private Integer size;

    public ConsolePrinter(Function getCombFunction, Integer size) {
        this.getCombFunction = getCombFunction;
        this.size = size;
    }


    protected String gs(String xy) {
        return getCombFunction.apply(xy).content.symbol;
    }

    public void printBoard() {
        if (size == 19) {
            printNineteenCombBoard();
        } else {
            printSevenCombBoard();
        }
    }

    public void printSevenCombBoard() {

//      "    < >    "
//      "< >     < >"
//      "    < >    "
//      "< >     < >"
//      "    < >    "

        String asciiBoard = "    <" + gs("01") + ">    \n" +
                "<" + gs("-11") + ">     <" + gs("10") + ">\n" +
                "    <" + gs("00") + ">    \n" +
                "<" + gs("-10") + ">     <" + gs("1-1") + ">\n" +
                "    <" + gs("0-1") + ">    \n" +
                "___________";

        System.out.println(asciiBoard);

    }

    public void printNineteenCombBoard() {
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
                .append("        <" + gs("02") + ">         \n")
                .append("    <" + gs("-12") + ">     <" + gs("11") + ">    \n")
                .append("<" + gs("-22") + ">     <" + gs("01") + ">     <" + gs("20") + "> \n")
                .append("    <" + gs("-11") + ">     <" + gs("10") + ">     \n")
                .append("<" + gs("-21") + ">     <" + gs("00") + ">     <" + gs("2-1") + "> \n")
                .append("    <" + gs("-10") + ">     <" + gs("1-1") + ">     \n")
                .append("<" + gs("-20") + ">     <" + gs("0-1") + ">     <" + gs("2-2") + "> \n")
                .append("    <" + gs("-1-1") + ">     <" + gs("1-2") + ">     \n")
                .append("        <" + gs("0-2") + ">         \n");

        System.out.println(sb);
    }
}
