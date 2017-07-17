package com.zimincom.msggrapher.item;

import io.realm.RealmObject;

/**
 * Created by Zimincom on 2017. 7. 10..
 */

public class MsgInfo extends RealmObject {

    private int month;
    private int day;
    private int withdrawAmount;

    public MsgInfo(){}

    public MsgInfo(int month, int day, int withdrawAmount) {
        this.month = month;
        this.day = day;
        this.withdrawAmount = withdrawAmount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(int withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }
}

