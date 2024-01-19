package com.example.task1;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText resultText;

    private String currentNumber = "";
    private String operator = "";
    private double firstOperand = Double.NaN;
    private boolean operatorClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = findViewById(R.id.resultText);
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "=":
                calculateResult();
                break;
            case "C":
                clearText();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
                setOperator(buttonText);
                break;
            default:
                appendNumber(buttonText);
                break;
        }
    }

    private void appendNumber(String number) {
        if (operatorClicked) {
            currentNumber = "";
            operatorClicked = false;
        }

        currentNumber += number;
        resultText.setText(currentNumber);
    }

    private void setOperator(String op) {
        if (operatorClicked) {
            operator = op;
            return;
        }

        if (!Double.isNaN(firstOperand)) {
            calculateResult();
        } else {
            firstOperand = Double.parseDouble(currentNumber);
        }

        operator = op;
        operatorClicked = true;
    }

    private void calculateResult() {
        if (!Double.isNaN(firstOperand) && !operator.isEmpty() && !currentNumber.isEmpty()) {
            double secondOperand = Double.parseDouble(currentNumber);
            double result;

            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    result = firstOperand / secondOperand;
                    break;
                case "%":
                    result = (firstOperand * secondOperand) / 100;
                    break;
                default:
                    result = Double.NaN;
            }

            resultText.setText(String.valueOf(result));

            firstOperand = result;
            currentNumber = "";
            operator = "";
            operatorClicked = false;
        }
    }

    private void clearText() {
        resultText.setText("");
        currentNumber = "";
        operator = "";
        firstOperand = Double.NaN;
        operatorClicked = false;
    }
}