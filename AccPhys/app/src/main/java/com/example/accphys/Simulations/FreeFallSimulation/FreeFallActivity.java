package com.example.accphys.Simulations.FreeFallSimulation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.accphys.R;

public class FreeFallActivity extends AppCompatActivity {
    private FreeFall freeFall;
    private Panel panel;
    private ImageView stopIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);


        panel = new Panel(this);
        freeFall = new FreeFall(this);
        freeFall.setPanel(panel);



        stopIndicator = new ImageView(this);
        stopIndicator.setImageResource(R.drawable.ic_stop);
        stopIndicator.setVisibility(View.GONE);
        stopIndicator.setAdjustViewBounds(true);
        stopIndicator.setMaxHeight(80);
        stopIndicator.setMaxWidth(80);

        layout.addView(stopIndicator, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        layout.addView(freeFall, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0, 1f
        ));

        layout.addView(panel, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        setContentView(layout);
        panel.setOnStartClickListener(v -> {
            float mass = (float) panel.getMassInput();
            float gravity = (float) panel.getGravityInput();

            if (mass > 100.00 || gravity > 20.00 || mass <= 0 || gravity <= 0) {
                Toast.makeText(this, "Mass or gravity are not valid, please choose other values!", Toast.LENGTH_SHORT).show();
                return;
            }

            freeFall.setObjectMass(mass);
            freeFall.setGravity(gravity);
            freeFall.setVelocity(0);
            stopIndicator.setVisibility(View.GONE); // hide stop icon when simulation starts
            freeFall.startAnimation();
        });

        // Pause/resume simulation
        panel.setOnPauseClickListener(v -> {
            boolean paused = !freeFall.isPaused();
            freeFall.setPaused(paused);
            stopIndicator.setVisibility(paused ? View.VISIBLE : View.GONE);
        });

        // Restart simulation
        panel.setOnRestartClickListener(v -> {
            float mass = (float) panel.getMassInput();
            freeFall.setObjectMass(mass);
            freeFall.restart();
            stopIndicator.setVisibility(View.GONE); // hide stop icon on restart
            freeFall.startAnimation();
        });

        // Return to previous screen
        panel.setOnReturnClickListener(view -> finish());
    }
}
