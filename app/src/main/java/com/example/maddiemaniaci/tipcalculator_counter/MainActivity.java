package com.example.maddiemaniaci.tipcalculator_counter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText bill = findViewById(R.id.bill);
                EditText check = findViewById(R.id.check_num);
                RadioGroup radioGroup = findViewById(R.id.radioGroup);
                EditText customText = findViewById(R.id.custom_tip);
                bill.setText("");
                check.setText("");
                customText.setText("");
                radioGroup.check(R.id.radioButton_15);
            }
        });

        Button calculate = findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CalculatedActivity.class);
                EditText bill = findViewById(R.id.bill);
                EditText check = findViewById(R.id.check_num);
                RadioGroup radioGroup = findViewById(R.id.radioGroup);
                String billString = bill.getText().toString();
                String checkString = check.getText().toString();
                String tipString;
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                if (radioButtonId == R.id.radioButton_custom) {
                    EditText tipText = findViewById(R.id.custom_tip);
                    tipString = tipText.getText().toString();
                }
                else {
                    RadioButton radio = findViewById(radioButtonId);
                    tipString = radio.getText().toString().substring(0, 2);
                }

                if (billString.length() == 0) {
                    showErrorAlert(getString(R.string.bill_error), R.id.bill);
                }
                else if (checkString.length() == 0) {
                    showErrorAlert(getString(R.string.check_error), R.id.check_num);
                }
                else if (tipString.length() == 0) {
                    showErrorAlert(getString(R.string.tip_error), R.id.custom_tip);
                }
                else {
                    double billValue = Double.parseDouble(billString);
                    int checkNum = Integer.parseInt(check.getText().toString());
                    int tipPercent = Integer.parseInt(tipString);
                    if (billValue < 1) {
                        showErrorAlert(getString(R.string.bill_error), R.id.bill);
                    }
                    else if (checkNum < 1) {
                        showErrorAlert(getString(R.string.check_error), R.id.check_num);
                    }
                    else if (tipPercent < 1) {
                        showErrorAlert(getString(R.string.tip_error), R.id.custom_tip);
                    }
                    else {
                        intent.putExtra("bill", billValue);
                        intent.putExtra("checkNum", checkNum);
                        intent.putExtra("tipPercent", tipPercent);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void showErrorAlert(String errorMessage, final int fieldId) {
        new AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setNeutralButton("Close",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        findViewById(fieldId).requestFocus();
                    }
                }).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int id) {
        if (id == R.id.radioButton_custom) {
            EditText customText = findViewById(R.id.custom_tip);
            customText.setEnabled(true);
        } else {
            EditText customText = findViewById(R.id.custom_tip);
            customText.setEnabled(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        // Saving bill
        EditText bill = findViewById(R.id.bill);
        String billString = bill.getText().toString();
        outState.putString("bill",billString);
        // saving check
        EditText check = findViewById(R.id.check_num);
        String checkString = check.getText().toString();
        outState.putString("check",checkString);

    }
}
