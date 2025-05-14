package com.example.accphys.Simulations.FreeFallSimulation;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import androidx.core.content.ContextCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;

import com.example.accphys.R;

public class Panel extends LinearLayout {

    private final EditText massInput;
    private final EditText gravityInput;
    private final Button startButton;
    private final Button pauseButton;
    private final Button restartButton;

    private final TextView timeText;
    private final TextView distanceText;
    private final TextView velocityText;
    private final TextView kineticEnergyText;
    private final TextView potentialEnergyText;
    private final Button returnButton;


    public Panel(Context context) {
        super(context);
        setOrientation(VERTICAL);
        setPadding(40, 40, 40, 40);
        setBackgroundColor(Color.parseColor("#1E1E1E"));


        LinearLayout inputRow = new LinearLayout(context);
        inputRow.setOrientation(HORIZONTAL);
        inputRow.setGravity(Gravity.CENTER_HORIZONTAL);
        inputRow.setPadding(0, 0, 0, 30);
        inputRow.setWeightSum(2);

        massInput = createStyledInput(context, "Mass (kg)");
        gravityInput = createStyledInput(context, "Gravity (m/s²)");

        inputRow.addView(massInput);
        inputRow.addView(gravityInput);
        addView(inputRow);


        CardView cardView = new CardView(context);
        cardView.setCardElevation(10f);
        cardView.setRadius(16f);
        cardView.setUseCompatPadding(true);
        cardView.setCardBackgroundColor(Color.parseColor("#2C2C2C"));


        LinearLayout cardContainer = new LinearLayout(context);
        cardContainer.setOrientation(HORIZONTAL);
        cardContainer.setPadding(30, 30, 30, 30);


        LinearLayout buttonColumn = new LinearLayout(context);
        buttonColumn.setOrientation(VERTICAL);
        buttonColumn.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));

        startButton = createStyledButton(context, "Start");
        pauseButton = createStyledButton(context, "Pause");
        restartButton = createStyledButton(context, "Restart");
        returnButton = createStyledButton(context, "Return");

        buttonColumn.addView(startButton);
        buttonColumn.addView(pauseButton);
        buttonColumn.addView(restartButton);
        buttonColumn.addView(returnButton);

        GridLayout grid = new GridLayout(context);
        grid.setColumnCount(2);
        grid.setRowCount(5);
        grid.setUseDefaultMargins(true);
        grid.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2));
        grid.setPadding(20, 0, 0, 0);

        timeText = createLabelValuePair(context, grid, "Time:", "0.00 s");
        distanceText = createLabelValuePair(context, grid, "Distance:", "0.00 m");
        velocityText = createLabelValuePair(context, grid, "Velocity:", "0.00 m/s");
        kineticEnergyText = createLabelValuePair(context, grid, "Kinetic Energy:", "0.00 J");
        potentialEnergyText = createLabelValuePair(context, grid, "Potential Energy:", "0.00 J");

        cardContainer.addView(buttonColumn);
        cardContainer.addView(grid);
        cardView.addView(cardContainer);
        addView(cardView);
    }

    public Panel(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setOrientation(VERTICAL);
        setPadding(40, 40, 40, 40);
        setBackgroundColor(Color.parseColor("#1E1E1E"));


        LinearLayout inputRow = new LinearLayout(context);
        inputRow.setOrientation(HORIZONTAL);
        inputRow.setGravity(Gravity.CENTER_HORIZONTAL);
        inputRow.setPadding(0, 0, 0, 30);
        inputRow.setWeightSum(2);

        massInput = createStyledInput(context, "Mass (kg)");
        gravityInput = createStyledInput(context, "Gravity (m/s²)");

        inputRow.addView(massInput);
        inputRow.addView(gravityInput);
        addView(inputRow);


        CardView cardView = new CardView(context);
        cardView.setCardElevation(10f);
        cardView.setRadius(16f);
        cardView.setUseCompatPadding(true);
        cardView.setCardBackgroundColor(Color.parseColor("#2C2C2C"));


        LinearLayout cardContainer = new LinearLayout(context);
        cardContainer.setOrientation(HORIZONTAL);
        cardContainer.setPadding(30, 30, 30, 30);


        LinearLayout buttonColumn = new LinearLayout(context);
        buttonColumn.setOrientation(VERTICAL);
        buttonColumn.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));

        startButton = createStyledButton(context, "Start");
        pauseButton = createStyledButton(context, "Pause");
        restartButton = createStyledButton(context, "Restart");
        returnButton = createStyledButton(context, "Return");

        buttonColumn.addView(startButton);
        buttonColumn.addView(pauseButton);
        buttonColumn.addView(restartButton);
        buttonColumn.addView(returnButton);

        GridLayout grid = new GridLayout(context);
        grid.setColumnCount(2);
        grid.setRowCount(5);
        grid.setUseDefaultMargins(true);
        grid.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2));
        grid.setPadding(20, 0, 0, 0);

        timeText = createLabelValuePair(context, grid, "Time:", "0.00 s");
        distanceText = createLabelValuePair(context, grid, "Distance:", "0.00 m");
        velocityText = createLabelValuePair(context, grid, "Velocity:", "0.00 m/s");
        kineticEnergyText = createLabelValuePair(context, grid, "Kinetic Energy:", "0.00 J");
        potentialEnergyText = createLabelValuePair(context, grid, "Potential Energy:", "0.00 J");

        cardContainer.addView(buttonColumn);
        cardContainer.addView(grid);
        cardView.addView(cardContainer);
        addView(cardView);
    }



    private EditText createStyledInput(Context context, String hint) {
        EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setHint(hint);
        input.setTextColor(Color.BLACK);
        input.setBackgroundColor(Color.WHITE);
        input.setPadding(20, 10, 20, 10);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        params.setMargins(10, 0, 10, 0);
        input.setLayoutParams(params);

        return input;
    }


    private Button createStyledButton(Context context, String text) {
        Button btn = new Button(context);
        btn.setText(text);
        btn.setAllCaps(false);
        btn.setTextSize(14);
        btn.setTextColor(Color.WHITE);
        btn.setBackground(ContextCompat.getDrawable(context, R.drawable.button_background));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 10, 0, 10);
        btn.setLayoutParams(lp);
        return btn;
    }

    private TextView createLabelValuePair(Context context, GridLayout grid, String label, String value) {
        TextView labelView = new TextView(context);
        labelView.setText(label);
        labelView.setTextColor(Color.LTGRAY);
        labelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        TextView valueView = new TextView(context);
        valueView.setText(value);
        valueView.setTextColor(Color.WHITE);
        valueView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        grid.addView(labelView);
        grid.addView(valueView);
        return valueView;
    }

    public float getMassInput() {
        try {
            return (float) Double.parseDouble(massInput.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public float getGravityInput() {
        try {
            return (float) Double.parseDouble(gravityInput.getText().toString());
        } catch (NumberFormatException e) {
            return 0F;
        }
    }

    public void setTime(double time) {
        timeText.setText(String.format("%.2f s", time));
    }

    public void setDistance(double distance) {
        distanceText.setText(String.format("%.2f m", distance));
    }

    public void setVelocity(double velocity) {
        velocityText.setText(String.format("%.2f m/s", velocity));
    }

    public void setKineticEnergy(double ke) {
        kineticEnergyText.setText(String.format("%.2f J", ke));
    }

    public void setPotentialEnergy(double pe) {
        potentialEnergyText.setText(String.format("%.2f J", pe));
    }

    public void setOnStartClickListener(OnClickListener listener) {
        startButton.setOnClickListener(listener);
    }

    public void setOnPauseClickListener(OnClickListener listener) {
        pauseButton.setOnClickListener(listener);
    }

    public void setOnRestartClickListener(OnClickListener listener) {
        restartButton.setOnClickListener(listener);
    }

    public void setOnReturnClickListener(OnClickListener listener) {
        returnButton.setOnClickListener(listener);
    }

}
