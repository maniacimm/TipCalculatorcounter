package com.example.maddiemaniaci.tipcalculator_counter;

import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class CalculatedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculated_activity);
        Bundle extras = getIntent().getExtras();
        double bill = (double) extras.get("bill");
        int checkNum = (int) extras.get("checkNum");
        int tipPercent = (int) extras.get("tipPercent");
        double tip = bill * tipPercent / 100;
        double perPerson = tip / checkNum;
        TextView billText = findViewById(R.id.bill_text_value);
        TextView tipText = findViewById(R.id.tip_value);
        TextView perPersonText = findViewById(R.id.per_person_value);
        billText.setText(String.format(Locale.US, "$%.2f", bill));
        tipText.setText(String.format(Locale.US, "$%.2f", tip));
        perPersonText.setText(String.format(Locale.US, "$%.2f", perPerson));
    }
}
