package com.upwork.service;

import com.upwork.model.Seat;

import java.util.Comparator;

public class SeatComparator implements Comparator<Seat> {
    private final int centerX;
    private final int centerY;

    public SeatComparator(int centerX, int centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    public int compare(Seat s1, Seat s2) {
        double distance1 = s1.getDistanceToCenter(centerX, centerY);
        double distance2 = s2.getDistanceToCenter(centerX, centerY);
        return Double.compare(distance1, distance2);
    }
}

