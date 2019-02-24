package com.ryabokon.hex.core;

import com.ryabokon.hex.ai.HexAi;
import com.ryabokon.hex.ai.RandomDummy;
import com.ryabokon.hex.ai.movesAhead.OneMoveAhead;
import org.junit.jupiter.api.Test;

public class AiTests {


    @Test
    public void twoDummiesTest() {
        HexBoard game = new HexBoard();
        HexAi cpu1 = new RandomDummy(game::getBoardMap, game::selectComb, Item.RUBY);
        HexAi cpu2 = new RandomDummy(game::getBoardMap, game::selectComb, Item.NEON);

        game.putItem(-2, 2, Item.RUBY);
        game.putItem(2, -2, Item.NEON);


        for (int i = 0; i < 10; i++) {
            cpu1.makeaMove();
            cpu2.makeaMove();
        }

    }

    @Test
    public void dummyAndOneAheadTest() {
        HexBoard game = new HexBoard();
        HexAi cpu1 = new RandomDummy(game::getBoardMap, game::selectComb, Item.RUBY);
        HexAi cpu2 = new OneMoveAhead(game::getBoardMap, game::selectComb, Item.NEON);

        game.putItem(-2, 2, Item.RUBY);
        game.putItem(2, -2, Item.NEON);


        for (int i = 0; i < 10; i++) {
            cpu1.makeaMove();
            cpu2.makeaMove();
        }

    }


    @Test
    public void oneAheadTest() {
        HexBoard game = new HexBoard();
        HexAi cpu1 = new OneMoveAhead(game::getBoardMap, game::selectComb, Item.RUBY);
        HexAi cpu2 = new OneMoveAhead(game::getBoardMap, game::selectComb, Item.NEON);

        game.putItem(-2, 2, Item.RUBY);
        game.putItem(2, -2, Item.NEON);


        while(true){
            cpu1.makeaMove();
            cpu2.makeaMove();
        }

    }
}
