package com.ryabokon.hex;

import org.junit.jupiter.api.Test;

public class Main {


    @Test
    public void manualGame() {
        HexBoard hexx = new HexBoard();
        hexx.printBoard();

        hexx.putProp(-1, 1, Props.RUBY);
        hexx.printBoard();


        hexx.markSurroundingsForMove(-1, 1);
        hexx.printBoard();

        hexx.putProp(0, 0, Props.RUBY);
        hexx.printBoard();


        hexx.cleanMovementMarks();
        hexx.printBoard();


    }

    @Test
    public void semiAutoGame() {
        HexBoard hexx = new HexBoard();
        hexx.printBoard();

        hexx.selectHex(-1, 1);
        hexx.printBoard();

        hexx.selectHex(0, 0);
        hexx.printBoard();

        hexx.selectHex(0, 0);
        hexx.printBoard();

        hexx.selectHex(-1, 1);
        hexx.printBoard();
    }

    @Test
    public void longMoveGame() {
        HexBoard hexx = new HexBoard();
        hexx.printBoard();

        hexx.selectHex(-1, 1);
        hexx.printBoard();

        hexx.selectHex(1, -1);
        hexx.printBoard();

        hexx.selectHex(1, -1);
        hexx.printBoard();

        hexx.selectHex(0, -1);
        hexx.printBoard();
    }


}
