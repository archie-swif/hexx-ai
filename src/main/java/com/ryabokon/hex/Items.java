package com.ryabokon.hex;

public enum Items {

    RUBY("*"),
    SELECTED_RUBY("#"),
    NEON("o"),
    SELECTED_NEON("O"),
    EMPTY(" "),
    CLONE(":"),
    MOVE(".");

    String symbol;

    Items(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
