package com.company;

import java.util.ArrayList;

public class Bin {
    protected int capacity;
    protected int total;
    protected ArrayList<Item> listItems;

    public Bin(int capacity){
        setCapacity(capacity);
        listItems = new ArrayList<>();
        setTotal(0);
    }
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean addItem(Item item){
        System.out.println("Taille item:" + item.getSize() + " remplissage bin: " + total + " capacité: " + capacity);
        if(item.getSize() + total <= capacity){
            listItems.add(item);
            setTotal(total+item.getSize());
//            System.out.println("ajout");
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return listItems.toString();
    }
}
