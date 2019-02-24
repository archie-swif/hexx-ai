package com.ryabokon.hex.core;

public enum Item {
    RUBY,
    NEON,
    EMPTY,
    CLONE,
    TARGET,
    JUMP;

    public Item getOpposite() {
        if (this.equals(RUBY)) return NEON;
        if (this.equals(NEON)) return RUBY;
        throw new UnsupportedOperationException("No opposite for " + this);
    }
}
