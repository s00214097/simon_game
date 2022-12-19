package com.example.mad_simonsays;

public class Score {
    private long id;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //score property
    public String score;
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return score;
    }
}
