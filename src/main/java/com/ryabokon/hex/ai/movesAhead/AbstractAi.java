package com.ryabokon.hex.ai.movesAhead;

import com.ryabokon.hex.ai.HexAi;
import com.ryabokon.hex.core.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.ryabokon.hex.core.Item.EMPTY;

public abstract class AbstractAi implements HexAi {

    Item myItem;
    Map<String, Item> board;

    Stream<MoveNode> getAvailableMoves(String from) {
        Stream<MoveNode> cloneMoves = getFirstCircle(from).entrySet().stream()
                .filter(e -> EMPTY.equals(e.getValue()))
                .map(to -> getMoveScore(from, to.getKey(), true));

        Stream<MoveNode> jumpMoves = getSecondCircle(from).entrySet().stream()
                .filter(e -> EMPTY.equals(e.getValue()))
                .map(to -> getMoveScore(from, to.getKey(), false));

        return Stream.concat(cloneMoves, jumpMoves);
    }

    MoveNode getMoveScore(String from, String to, Boolean isClone) {
        long score = getFirstCircle(to).values().stream()
                .filter(i -> myItem.getOpposite().equals(i))
                .count();
        if (isClone) {
            score = score + 1;
        }

        return new MoveNode(from, to, score);
    }


    Stream<String> findCoordinateByItemType(Item type) {
        return board.entrySet().stream()
                .filter(e -> e.getValue().equals(type))
                .map(Map.Entry::getKey);
    }

    //       (0, 1)
    // (-1,1)      (1, 0)
    //       (0, 0)
    // (-1,0)      (1,-1)
    //       (0,-1)
    Map<String, Item> getFirstCircle(String xy) {

        String[] coords = xy.split(":");
        Integer x = Integer.valueOf(coords[0]);
        Integer y = Integer.valueOf(coords[1]);

        Map<String, Item> circle = new HashMap<>();

        String N = (x) + ":" + (y + 1);
        String NE = (x + 1) + ":" + (y);
        String SE = (x + 1) + ":" + (y - 1);
        String S = (x) + ":" + (y - 1);
        String SW = (x - 1) + ":" + (y);
        String NW = (x - 1) + ":" + (y + 1);

        Optional.ofNullable(board.get(N)).ifPresent(c -> circle.put(N, c));
        Optional.ofNullable(board.get(NE)).ifPresent(c -> circle.put(NE, c));
        Optional.ofNullable(board.get(SE)).ifPresent(c -> circle.put(SE, c));
        Optional.ofNullable(board.get(S)).ifPresent(c -> circle.put(S, c));
        Optional.ofNullable(board.get(SW)).ifPresent(c -> circle.put(SW, c));
        Optional.ofNullable(board.get(NW)).ifPresent(c -> circle.put(NW, c));
        return circle;
    }

//               (0, 2)
//        (-1, 2)      (1, 1)
// (-2, 2)       (    )      (2, 0)
//        (     )      (    )
// (-2, 1)       (    )      (2, -1)
//        (     )      (    )
// (-2, 0)       (    )      (2, -2)
//        (-1,-1)      (1,-2)
//               (0,-2)

    Map<String, Item> getSecondCircle(String xy) {

        String[] coords = xy.split(":");
        Integer x = Integer.valueOf(coords[0]);
        Integer y = Integer.valueOf(coords[1]);

        Map<String, Item> circle = new HashMap<>();

        String N = (x) + ":" + (y + 2);

        String NNE = (x + 1) + ":" + (y + 1);
        String NEE = (x + 2) + ":" + (1);

        String E = (x + 2) + ":" + (y - 1);

        String SEE = (x + 2) + ":" + (y - 2);
        String SSE = (x + 1) + ":" + (y - 2);

        String S = (x) + ":" + (y - 2);

        String SSW = (x - 1) + ":" + (y - 1);
        String SWW = (x - 2) + ":" + (0);

        String W = (x - 2) + ":" + (y);

        String NWW = (x - 2) + ":" + (y + 1);
        String NNW = (x - 2) + ":" + (y + 2);

        Optional.ofNullable(board.get(N)).ifPresent(c -> circle.put(N, c));
        Optional.ofNullable(board.get(NNE)).ifPresent(c -> circle.put(NNE, c));
        Optional.ofNullable(board.get(NEE)).ifPresent(c -> circle.put(NEE, c));
        Optional.ofNullable(board.get(E)).ifPresent(c -> circle.put(E, c));
        Optional.ofNullable(board.get(SEE)).ifPresent(c -> circle.put(SEE, c));
        Optional.ofNullable(board.get(SSE)).ifPresent(c -> circle.put(SSE, c));
        Optional.ofNullable(board.get(S)).ifPresent(c -> circle.put(S, c));
        Optional.ofNullable(board.get(SSW)).ifPresent(c -> circle.put(SSW, c));
        Optional.ofNullable(board.get(SWW)).ifPresent(c -> circle.put(SWW, c));
        Optional.ofNullable(board.get(W)).ifPresent(c -> circle.put(W, c));
        Optional.ofNullable(board.get(NWW)).ifPresent(c -> circle.put(NWW, c));
        Optional.ofNullable(board.get(NNW)).ifPresent(c -> circle.put(NNW, c));
        return circle;
    }
}
