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

        hexx.selectComb(-1,1);
        hexx.printBoard();

        hexx.selectComb(0,0);
        hexx.printBoard();

        hexx.selectComb(0,0);
        hexx.printBoard();

        hexx.selectComb(-1,1);
        hexx.printBoard();



    }


}
