package com.zybooks.pizzaparty;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//    public final static int SLICES_PER_PIZZA = 8;
    private final static String TAG = "MainActivity";

    private final String KEY_TOTAL_PIZZAS = "totalPizzas";
    private int mTotalPizzas;

    private EditText mNumAttendEditText;
    private TextView mNumPizzasTextView;
    private Spinner mHungerSpinner;
//    private RadioGroup mHowHungryRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate was called");

        // Assign the widgets to fields
        mNumAttendEditText = findViewById(R.id.num_attend_edit_text);
        mNumPizzasTextView = findViewById(R.id.num_pizzas_text_view);
        //        mHowHungryRadioGroup = findViewById(R.id.hungry_radio_group);
        mHungerSpinner = findViewById(R.id.hunger_spinner);

        // Restore state
        if (savedInstanceState != null) {
            mTotalPizzas = savedInstanceState.getInt(KEY_TOTAL_PIZZAS);
            displayTotal();
        }

// Set up TextWatcher
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

// Set up adapter for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hunger_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHungerSpinner.setAdapter(adapter);

// Clear result when user changes hunger level
        mHungerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mNumPizzasTextView.setText(""); // clear result
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // restore state
        if (savedInstanceState != null) {
            mTotalPizzas = savedInstanceState.getInt(KEY_TOTAL_PIZZAS);
            displayTotal();
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_TOTAL_PIZZAS, mTotalPizzas);
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
        int position = mHungerSpinner.getSelectedItemPosition();
        PizzaCalculator.HungerLevel hungerLevel = PizzaCalculator.HungerLevel.RAVENOUS;

        if (position == 0) {
            hungerLevel = PizzaCalculator.HungerLevel.LIGHT;
        } else if (position == 1) {
            hungerLevel = PizzaCalculator.HungerLevel.MEDIUM;
        }



        // Get hunger level selection
//        int checkedId = mHowHungryRadioGroup.getCheckedRadioButtonId();
//        PizzaCalculator.HungerLevel hungerLevel = PizzaCalculator.HungerLevel.RAVENOUS;
//        if (checkedId == R.id.light_radio_button) {
//            hungerLevel = PizzaCalculator.HungerLevel.LIGHT;
//        }
//        else if (checkedId == R.id.medium_radio_button) {
//            hungerLevel = PizzaCalculator.HungerLevel.MEDIUM;
//        }



        // Get the number of pizzas needed
        PizzaCalculator calc = new PizzaCalculator(numAttend, hungerLevel);
        mTotalPizzas = calc.getTotalPizzas();
        displayTotal();

        // Place totalPizzas into the string resource and display
//        String totalText = getString(R.string.total_pizzas_num, totalPizzas);
//        mNumPizzasTextView.setText(totalText);
    }
    private void  displayTotal() {
        String totalText = getString(R.string.total_pizzas_num, mTotalPizzas);
        mNumPizzasTextView.setText(totalText);
    }
}