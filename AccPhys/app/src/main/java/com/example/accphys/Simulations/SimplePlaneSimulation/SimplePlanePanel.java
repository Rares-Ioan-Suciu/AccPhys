package com.example.accphys.Simulations.SimplePlaneSimulation;

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

public class SimplePlanePanel extends LinearLayout {

    private final EditText massInput;
    private final EditText frictionInput;
    private final EditText forceInput;

    private final Button startButton;
    private final Button pauseButton;
    private final Button restartButton;
    private final Button returnButton;

    private final TextView timeText;
    private final TextView distanceText;
    private final TextView velocityText;
    private final TextView kineticEnergyText;
    private final TextView momentumText;

    public SimplePlanePanel(Context context) {
        this(context, null);
    }

    public SimplePlanePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setPadding(0, 50, 0, 40);
        setBackgroundColor(Color.parseColor("#1E1E1E"));

        LayoutParams fullParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(fullParams);

        LinearLayout inputRow = new LinearLayout(context);
        inputRow.setOrientation(HORIZONTAL);
        inputRow.setGravity(Gravity.CENTER);
        inputRow.setWeightSum(3);

        massInput = createStyledInput(context, "Mass (kg)");
        frictionInput = createStyledInput(context, "Friction");
        forceInput = createStyledInput(context, "Force (N)");

        inputRow.addView(massInput);
        inputRow.addView(frictionInput);
        inputRow.addView(forceInput);

        addView(inputRow, new LayoutParams(LayoutParams.WRAP_CONTENT, 0, 1f)); // occupy 1/4 height

        CardView cardView = new CardView(context);
        cardView.setCardElevation(16f);
        cardView.setRadius(40f);
        cardView.setUseCompatPadding(true);
        cardView.setCardBackgroundColor(Color.parseColor("#2C2C2C"));

        LinearLayout cardContainer = new LinearLayout(context);
        cardContainer.setOrientation(HORIZONTAL);
        cardContainer.setPadding(0, 40, 0, 40);
        cardContainer.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout buttonColumn = new LinearLayout(context);
        buttonColumn.setOrientation(VERTICAL);
        buttonColumn.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
        buttonColumn.setGravity(Gravity.CENTER);

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
        grid.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2f));

        timeText = createLabelValuePair(context, grid, "Time:", "0.00 s");
        distanceText = createLabelValuePair(context, grid, "Distance:", "0.00 m");
        velocityText = createLabelValuePair(context, grid, "Velocity:", "0.00 m/s");
        kineticEnergyText = createLabelValuePair(context, grid, "Kinetic Energy:", "0.00 J");
        momentumText = createLabelValuePair(context, grid, "Momentum:", "0.00 kg⋅m/s");

        cardContainer.addView(buttonColumn);
        cardContainer.addView(grid);
        cardView.addView(cardContainer);
        addView(cardView, new LayoutParams(LayoutParams.WRAP_CONTENT, 0, 4f));
    }

    private EditText createStyledInput(Context context, String hint) {
        EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        input.setHint(hint);
        input.setTextColor(Color.BLACK);
        input.setBackgroundColor(Color.WHITE);
        input.setPadding(20, 10, 20, 10);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
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
        labelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        TextView valueView = new TextView(context);
        valueView.setText(value);
        valueView.setTextAppearance(android.R.style.TextAppearance_Material_Body1);
        valueView.setTextColor(Color.LTGRAY);
        valueView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        grid.addView(labelView);
        grid.addView(valueView);
        return valueView;
    }

    public float getMassInput() {
        try {
            return Float.parseFloat(massInput.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public float getFrictionInput() {
        try {
            return Float.parseFloat(frictionInput.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public float getForceInput() {
        try {
            return Float.parseFloat(forceInput.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
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

    public void setMomentum(double momentum)
    {
        momentumText.setText(String.format("%.2f kg⋅m/s", momentum));
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

    public void setOnReturnClickListener(OnClickListener listener){
        returnButton.setOnClickListener(listener);
    }
}
