package com.company.neighboor;

import com.company.Bin;

import java.util.List;

public interface NeighbourStrategy {
    public List<Bin> move(List<Bin> listBins);
}
