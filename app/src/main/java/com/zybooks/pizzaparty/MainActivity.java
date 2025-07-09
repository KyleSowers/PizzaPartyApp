package com.zybooks.pizzaparty;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//    public final static int SLICES_PER_PIZZA = 8;
    private final static String TAG = "MainActivity";

    private EditText mNumAttendEditText;
    private TextView mNumPizzasTextView;
    private RadioGroup mHowHungryRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate was called");

        // Assign the widgets to fields
        mNumAttendEditText = findViewById(R.id.num_attend_edit_text);

        mNumAttendEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 1) {
                    mNumAttendEditText.setError("Please enter at least 1 person");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mNumPizzasTextView = findViewById(R.id.num_pizzas_text_view);
        mHowHungryRadioGroup = findViewById(R.id.hungry_radio_group);
    }

    public void calculateClick(View view) {
        String numAttendStr = mNumAttendEditText.getText().toString().trim();

        // check if input is empty
        if (numAttendStr.isEmpty()) {
//            Toast.makeText(this, "Please enter number of people", Toast.LENGTH_SHORT).show();
            mNumAttendEditText.setError("Please enter number of people");
            mNumAttendEditText.requestFocus();
            return;
        }

        // Parse and calculate
        int numAttend = Integer.parseInt(numAttendStr);

        // Get how many are attending the party
//        int numAttend;
//        try {
//            String numAttendStr = mNumAttendEditText.getText().toString();
//            numAttend = Integer.parseInt(numAttendStr);
//        }
//        catch (NumberFormatException ex) {
//            numAttend = 0;
//        }

        // Get hunger level selection
        int checkedId = mHowHungryRadioGroup.getCheckedRadioButtonId();
        PizzaCalculator.HungerLevel hungerLevel = PizzaCalculator.HungerLevel.RAVENOUS;
        if (checkedId == R.id.light_radio_button) {
            hungerLevel = PizzaCalculator.HungerLevel.LIGHT;
        }
        else if (checkedId == R.id.medium_radio_button) {
            hungerLevel = PizzaCalculator.HungerLevel.MEDIUM;
        }

        // Get the number of pizzas needed
        PizzaCalculator calc = new PizzaCalculator(numAttend, hungerLevel);
        int totalPizzas = calc.getTotalPizzas();

        // Place totalPizzas into the string resource and display
        String totalText = getString(R.string.total_pizzas_num, totalPizzas);
        mNumPizzasTextView.setText(totalText);
    }
}