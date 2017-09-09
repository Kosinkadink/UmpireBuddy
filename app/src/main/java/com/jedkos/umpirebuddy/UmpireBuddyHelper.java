package com.jedkos.umpirebuddy;


class UmpireBuddyHelper {

    private static final int strikeBound = 3;
    private static final int ballBound = 4;
    private int strikeCount;
    private int ballCount;
    private int outCount;

    UmpireBuddyHelper() {
        ballCount = 0;
        strikeCount = 0;
    }

    /* Increment counts */
    void incrementStrikeCount() {
        if (!enoughStrikes()) {
            strikeCount++;
        }
    }

    void incrementBallCount() {
        if (!enoughBalls()) {
            ballCount++;
        }
    }

    void incrementOutCount() {
        outCount++;
    }

    /* Check if strikes and balls in bound */
    boolean enoughStrikes() {
        return strikeCount >= strikeBound;
    }

    boolean enoughBalls() {
        return ballCount >= ballBound;
    }

    /* Reset all counts */
    void resetAllCounts() {
        strikeCount = 0;
        ballCount = 0;
    }

    /* Getters and setters */
    int getStrikeCount() {
        return strikeCount;
    }

    int getBallCount() {
        return ballCount;
    }

    void setStrikeCount(int count) {
        strikeCount = count;
    }

    void setBallCount(int count) {
        ballCount = count;
    }

    int getOutCount() {
        return outCount;
    }

    void setOutCount(int count) {
        outCount = count;
    }
}
