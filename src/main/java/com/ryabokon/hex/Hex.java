package com.ryabokon.hex;

public class Hex {
    Props content;
    Hex selectedBy;

    public Hex(Props content) {
        this.content = content;
    }

    public boolean isEmpty() {
        return Props.EMPTY.equals(content);
    }

    public boolean isMarked() {
        return Props.CLONE.equals(content) || Props.MOVE.equals(content);
    }

    public boolean isProp() {
        return Props.RUBY.equals(content) || Props.NEON.equals(content);
    }
}
