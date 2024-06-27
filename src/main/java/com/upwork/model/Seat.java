package com.upwork.model;

import lombok.Getter;
import lombok.Setter;



public class Seat {
    @Getter
    private final int row;
    @Getter
    private final int col;
    @Setter
    @Getter
    private boolean occupied;

    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
        this.occupied = false;
    }

    public double getDistanceToCenter(int centerX, int centerY) {
        return Math.sqrt(Math.pow(row - centerX, 2) + Math.pow(col - centerY, 2));
    }

}

