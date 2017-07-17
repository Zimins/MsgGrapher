package com.zimincom.msggrapher.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import com.zimincom.msggrapher.R;
import com.zimincom.msggrapher.item.MsgInfo;
import com.zimincom.msggrapher.item.Wallet;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // TODO: 2017. 7. 10. 사용내용, 입출금 그래프
    Context context = MainActivity.this;

    //BarChart moneyBarChart;
    HorizontalBarChart moneyBarHorizontal;
    Realm realm;
    Button setBudgetButton;
    TextView budgetText;
    TextView sumOfMonthText;

    Wallet wallet;

    float sumOfMonth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(context);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        realm = Realm.getDefaultInstance();
        //moneyBarChart = (BarChart) findViewById(R.id.money_chart);
        moneyBarHorizontal = (HorizontalBarChart) findViewById(R.id.money_chart);
        setBudgetButton = (Button) findViewById(R.id.button_set_budget);
        budgetText = (TextView) findViewById(R.id.money_left);
        sumOfMonthText = (TextView) findViewById(R.id.sum_month);

        Wallet myWallet = new Wallet(0);

        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                drawChart();
                // TODO: 2017. 7. 17. refresh graph
            }
        });
    }

    private void drawChart() {

        List<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 1;i <= 31;i++) {
            RealmResults<MsgInfo> dayResult = realm.where(MsgInfo.class).equalTo("month", 7).equalTo("day", i).findAll();
            float sumOfDay = dayResult.sum("withdrawAmount").floatValue();
            if (sumOfDay != 0) {
                entries.add(new BarEntry((float) i, sumOfDay));
            }
        }

        RealmResults<MsgInfo> monthResult = realm.where(MsgInfo.class).equalTo("month", 7).findAll();
        sumOfMonth = monthResult.sum("withdrawAmount").floatValue();
        sumOfMonthText.setText(String.valueOf(sumOfMonth));
        BarDataSet barDataSet = new BarDataSet(entries, "witdraw per day");


        BarData barData = new BarData(barDataSet);

        //moneyBarChart.setData(barData);
        //moneyBarChart.invalidate();

        moneyBarHorizontal.setData(barData);
        moneyBarHorizontal.setFitBars(false);
        moneyBarHorizontal.animateXY(1000,1000);
        moneyBarHorizontal.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawChart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch(viewId) {
            case R.id.button_set_budget:
                wallet.setBudget(10000);
                break;
        }
    }
}
