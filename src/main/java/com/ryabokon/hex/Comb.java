package com.ryabokon.hex;

public class Comb {
    Items content = Items.EMPTY;

    public boolean isEmpty() {
        return Items.EMPTY.equals(content);
    }

    public boolean isMarked() {
        return Items.CLONE.equals(content) || Items.MOVE.equals(content);
    }

    public boolean isMarkedClone() {
        return Items.CLONE.equals(content);
    }

    public boolean isMarkedMove() {
        return Items.MOVE.equals(content);
    }

    public boolean hasItem() {
        return Items.RUBY.equals(content) || Items.NEON.equals(content);
    }
}
