package com.example.accphys.Simulations.SimplePlaneSimulation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.accphys.R;

public class SimplePlaneActivity extends AppCompatActivity {

    private SimplePlanePanel simplePlanePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_simple_plane);

        simplePlanePanel = findViewById(R.id.controlPanel);
        SimplePlaneView simplePlaneView = findViewById(R.id.simpleView);
        ImageView stopIcon = findViewById(R.id.stopIcon);
        ImageView forceDirectionIcon = findViewById(R.id.forceDirectionIcon);

        simplePlaneView.setSimplePanel(simplePlanePanel);

        simplePlanePanel.setOnStartClickListener(v -> {

            float mass = simplePlanePanel.getMassInput();
            float friction = simplePlanePanel.getFrictionInput();
            float force = simplePlanePanel.getForceInput();

            if(mass <= 0 || mass >= 100 || friction <= 0 || friction >= 1 || force <= -50 || force >= 50) {
                Toast.makeText(this, "One of the inputs is not valid.\nPlease change!", Toast.LENGTH_SHORT).show();
                return;
            }

            simplePlaneView.setObjectMass(mass);
            simplePlaneView.setFrictionCoefficient(friction);
            simplePlaneView.setForce(force);
            simplePlaneView.setVelocity(0);
            simplePlaneView.startAnimation();

            if (force > 0) {
                forceDirectionIcon.setImageResource(R.drawable.ic_force);
            } else if (force < 0) {
                forceDirectionIcon.setImageResource(R.drawable.ic_arrow2);
            }
            forceDirectionIcon.setVisibility(View.VISIBLE);
            stopIcon.setVisibility(View.GONE);
        });

        simplePlanePanel.setOnRestartClickListener(v -> {
            float force = simplePlanePanel.getForceInput();

            simplePlaneView.setObjectMass(simplePlanePanel.getMassInput());
            simplePlaneView.setFrictionCoefficient(simplePlanePanel.getFrictionInput());
            simplePlaneView.setForce(force);
            simplePlaneView.restart();
            simplePlaneView.startAnimation();

            if (force > 0) {
                forceDirectionIcon.setImageResource(R.drawable.ic_force);
            } else if (force < 0) {
                forceDirectionIcon.setImageResource(R.drawable.ic_arrow2);
            }
            forceDirectionIcon.setVisibility(View.VISIBLE);
            stopIcon.setVisibility(View.GONE);
        });

        simplePlanePanel.setOnPauseClickListener(v -> {
            simplePlaneView.setPaused(!simplePlaneView.isPaused());

            if (simplePlaneView.isPaused()) {
                stopIcon.setVisibility(View.VISIBLE);
            } else {
                stopIcon.setVisibility(View.GONE);
            }
        });

        simplePlanePanel.setOnReturnClickListener(view -> {
            finish();
        });
    }
}
