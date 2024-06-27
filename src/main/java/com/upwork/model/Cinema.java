package com.upwork.model;


import lombok.Getter;


public class Cinema {
    @Getter
    private final Seat[][] seats;
    @Getter
    private final int rows;
    @Getter
    private final int cols;

    public Cinema(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.seats = new Seat[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                seats[i][j] = new Seat(i, j);
            }
        }
    }


    public Seat getSeat(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return null;
        }
        return seats[row][col];
    }

    public boolean isAvailable(int row, int col) {
        if (getSeat(row, col) == null || getSeat(row, col).isOccupied()) {
            return false;
        }
        return !isAdjacentOccupied(row, col);
    }

    private boolean isAdjacentOccupied(int row, int col) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            Seat adjacentSeat = getSeat(newRow, newCol);
            if (adjacentSeat != null && adjacentSeat.isOccupied()) {
                return true;
            }
        }
        return false;
    }

    public void occupySeat(int row, int col) {
        if (isAvailable(row, col)) {
            seats[row][col].setOccupied(true);
        } else {
            throw new IllegalStateException("Seat is not available.");
        }
    }
}

