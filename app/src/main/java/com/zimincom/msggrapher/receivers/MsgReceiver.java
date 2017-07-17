package com.zimincom.msggrapher.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.zimincom.msggrapher.item.MsgInfo;
import com.zimincom.msggrapher.parser.SMSParser;

import io.realm.Realm;

/**
 * Created by Zimincom on 2017. 7. 10..
 */

public class MsgReceiver extends BroadcastReceiver {

    SMSParser smsParser = new SMSParser();


    private static final String TAG = "MsgReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "msg received",Toast.LENGTH_SHORT).show();

        Realm realm = Realm.getDefaultInstance();

        //get msg content
        Bundle bundle = intent.getExtras();
        Object messages[] = (Object[]) bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];

        for (int i = 0;i < messages.length;i++) {
            smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
        }

        String content = smsMessage[0].getMessageBody().toString();

        final MsgInfo msgInfo = smsParser.parseSMS(content);

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (msgInfo != null) {
                    MsgInfo dbInfo = realm.createObject(MsgInfo.class);
                    dbInfo.setMonth(msgInfo.getMonth());
                    dbInfo.setDay(msgInfo.getDay());
                    dbInfo.setWithdrawAmount(msgInfo.getWithdrawAmount());
                }
            }
        });

    }
}
