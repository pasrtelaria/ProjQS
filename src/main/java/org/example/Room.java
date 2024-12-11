package org.example;

import java.io.Serializable;

public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    private int num;
    private int capacity;
    private Room left;
    private Room right;
    private boolean timeout;

    public Room(int num, int capacity) {
        this.num = num;
        this.capacity = capacity;
        this.timeout = false;
    }

    public int getNum() {
        return num;
    }

    public int getCapacity() {
        return capacity;
    }

    public Room getLeft() {
        return left;
    }

    public void setLeft(Room left) {
        this.left = left;
    }

    public Room getRight() {
        return right;
    }

    public void setRight(Room right) {
        this.right = right;
    }

    public boolean isTimeout() {
        return timeout;
    }

    public void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }
}