package com.ryabokon.hex;

public enum Props {

    RUBY("*"),
    NEON("o"),
    EMPTY(" "),
    CLONE(":"),
    MOVE(".");

    String symbol;

    Props(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
