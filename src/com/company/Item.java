package com.company;

public class Item {
    protected int size;

    public Item(int size){
        setSize(size);
    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Size : " + size;
    }
}
