package com.ryabokon.hex.ai;

import com.ryabokon.hex.core.Item;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RandomDummy implements HexAi {
    private Map<String, Item> board;
    private Item myItem;
    private Supplier<Map<String, Item>> getBoard;
    private Consumer<String> selectControls;

    public RandomDummy(Supplier<Map<String, Item>> getBoard, Consumer<String> selectControls, Item myItem) {
        this.getBoard = getBoard;
        this.selectControls = selectControls;
        this.myItem = myItem;
    }


    public void makeaMove() {
        board = getBoard.get();
        String coord = findMyItemCoordinates();
        selectControls.accept(coord);

        board = getBoard.get();
        coord = findCloneSpotCoordinates();
        selectControls.accept(coord);
    }

    private String findMyItemCoordinates() {
        return findCoordinateByItemType(myItem).orElseThrow(RuntimeException::new);
    }

    private String findCloneSpotCoordinates() {
        return findCoordinateByItemType(Item.CLONE).orElseGet(this::findJumpSpotCoordinates);
    }

    private String findJumpSpotCoordinates() {
        return findCoordinateByItemType(Item.JUMP).orElseThrow(() -> new RuntimeException("No moves for " + myItem));
    }

    private Optional<String> findCoordinateByItemType(Item type) {
        return board.entrySet().stream()
                .filter(e -> e.getValue().equals(type))
                .map(Map.Entry::getKey)
                .findAny();
    }


}
