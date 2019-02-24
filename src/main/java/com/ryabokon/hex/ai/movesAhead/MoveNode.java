package com.ryabokon.hex.ai.movesAhead;

public class MoveNode {
    private String from;
    private String to;
    private Long score;

    public MoveNode(String from, String to, Long score) {
        this.from = from;
        this.to = to;
        this.score = score;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
