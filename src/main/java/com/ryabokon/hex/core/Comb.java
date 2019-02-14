package com.ryabokon.hex.core;

public class Comb {
    Item content = Item.EMPTY;

    public boolean isEmpty() {
        return Item.EMPTY.equals(content);
    }

    public boolean isMarked() {
        return Item.CLONE.equals(content) || Item.MOVE.equals(content);
    }

    public boolean isMarkedClone() {
        return Item.CLONE.equals(content);
    }

    public boolean isMarkedMove() {
        return Item.MOVE.equals(content);
    }

    public boolean hasPlayer() {
        return Item.RUBY.equals(content) || Item.NEON.equals(content);
    }
}
