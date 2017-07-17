package com.zimincom.msggrapher.parser;

import com.zimincom.msggrapher.item.MsgInfo;

/**
 * Created by Zimincom on 2017. 7. 10..
 */

public class SMSParser {
    public MsgInfo parseSMS(String msgContent) {

        int month = parseMonth(msgContent);
        int day = parseDay(msgContent);
        int withdrawAmount = parseWithdraw(msgContent);

        return new MsgInfo(month, day, withdrawAmount);
    }

    int parseMonth(String msg) {

        int monthStart = msg.indexOf("\n");
        int monthEnd = msg.indexOf("/");
        String monthString = msg.substring(monthStart, monthEnd).trim();

        return Integer.parseInt(monthString);
    }

    int parseDay(String msg) {

        int dayStart = msg.indexOf("/") + 1;
        int dayEnd = msg.indexOf(" ");
        String dayString = msg.substring(dayStart, dayEnd);

        return Integer.parseInt(dayString);
    }

    int parseWithdraw(String msg) {

        int withdrawInfoStart = msg.indexOf("출금:") + 4;
        int withdrawInfoEnd = msg.indexOf("원");
        String withdrawString = msg.substring(withdrawInfoStart, withdrawInfoEnd);

        return Integer.parseInt(withdrawString);
    }

}
