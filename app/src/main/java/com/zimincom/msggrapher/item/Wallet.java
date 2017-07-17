package com.zimincom.msggrapher.item;

/**
 * Created by Zimincom on 2017. 7. 10..
 */

public class Wallet {

    public Wallet(int budget) {
        this.budget = budget;
    }

    private int budget;

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
