package com.ryabokon.hex.ai.movesAhead;

import com.ryabokon.hex.core.Item;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class OneMoveAhead extends AbstractAi {

    private Supplier<Map<String, Item>> getBoard;
    private Consumer<String> controls;

    public OneMoveAhead(Supplier<Map<String, Item>> getBoard, Consumer<String> controls, Item myItem) {
        this.getBoard = getBoard;
        this.controls = controls;
        this.myItem = myItem;
    }


    public void makeaMove() {

        this.board = getBoard.get();

        Optional<MoveNode> move = findCoordinateByItemType(myItem)
                .flatMap(this::getAvailableMoves)
                .max(Comparator.comparing(MoveNode::getScore));


        move.ifPresentOrElse(x -> {
                    controls.accept(x.getFrom());
                    controls.accept(x.getTo());
                },
                () -> {
                    throw new RuntimeException("No moves for " + myItem);
                });
    }


}
