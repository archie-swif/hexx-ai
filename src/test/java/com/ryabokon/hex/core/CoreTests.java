package com.ryabokon.hex.core;

import org.junit.jupiter.api.Test;

public class CoreTests {


    @Test
    public void boardControlsTest() {
        HexBoard hexx = new HexBoard();
        hexx.putItem(-1, 1, Item.RUBY);
        hexx.printBoard();

        hexx.markSurroundingsForMove(-1, 1);
        hexx.printBoard();

        hexx.putItem(0, 0, Item.RUBY);
        hexx.printBoard();

        hexx.cleanMovementMarks();
        hexx.printBoard();
    }

    @Test
    public void semiAutoGame() {
        HexBoard hexx = new HexBoard();
        hexx.putItem(-1, 1, Item.RUBY);
        hexx.printBoard();

        hexx.selectComb(-1, 1);

        hexx.selectComb(0, 0);

        hexx.selectComb(0, 0);

        hexx.selectComb(-1, 1);
    }

    @Test
    public void longMoveGame() {
        HexBoard hexx = new HexBoard();
        hexx.putItem(-1, 1, Item.RUBY);

        hexx.selectComb(-1, 1);

        hexx.selectComb(1, -1);

        hexx.selectComb(1, -1);

        hexx.selectComb(0, -1);
    }

    @Test
    public void warGame() {
        HexBoard hexx = new HexBoard();
        hexx.printBoard();

        hexx.putItem(-1, 1, Item.RUBY);
        hexx.printBoard();

        hexx.putItem(1, -1, Item.NEON);
        hexx.printBoard();

        hexx.selectComb(-1, 1);

        hexx.selectComb(-1, 0);

        hexx.selectComb(1, -1);

        hexx.selectComb(-1, -1);
    }

    @Test
    public void warXYGame() {
        HexBoard hexx = new HexBoard();
        hexx.printBoard();

        hexx.putItem(-1, 1, Item.RUBY);
        hexx.printBoard();

        hexx.putItem(1, -1, Item.NEON);
        hexx.printBoard();

        hexx.selectComb("-1:1");

        hexx.selectComb("-1:0");

        hexx.selectComb("1:-1");

        hexx.selectComb("-1:-1");
    }


}
