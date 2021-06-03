package com.company.neighboor;

import java.util.List;

public interface NeighbourStrategy {
    public int[] move(int[] assignedBin, List<Integer> items, List<Integer> bins, int sizeOfBin);
}
