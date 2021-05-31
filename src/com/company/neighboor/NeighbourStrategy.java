package com.company.neighboor;

import com.company.Bin;

import java.util.List;

public interface NeighbourStrategy {
    public int[] move(int[] assignedBin, List<Integer> items, List<Integer> bins);
}
