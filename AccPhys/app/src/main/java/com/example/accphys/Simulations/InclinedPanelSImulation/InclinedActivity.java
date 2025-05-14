package com.example.accphys.Simulations.InclinedPanelSImulation;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.accphys.MainMenuFragments.SimulatorMenu;
import com.example.accphys.R;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class InclinedActivity extends AppCompatActivity {

    private InclinePanel inclinePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inclined);

        inclinePanel = findViewById(R.id.controlPanel);
        InclinedPlaneView inclinedPlaneView = findViewById(R.id.inclineView);
        ImageView stopIcon = findViewById(R.id.stopIcon);

        inclinedPlaneView.setPanel(inclinePanel);

        inclinePanel.setOnStartClickListener(v -> {

            float mass = inclinePanel.getMassInput();
            float friction = inclinePanel.getFrictionInput();
            float angle = inclinePanel.getAngleInput();

             if(mass <= 0 || mass >=100 || friction<=0 || friction>=1 || angle <=0 || angle>=90)
            {
                Toast.makeText(this, "One of the three inputs is not valid. \n Please change! .", Toast.LENGTH_SHORT).show();
                return;
            }

            inclinedPlaneView.setObjectMass(inclinePanel.getMassInput());
            inclinedPlaneView.setFrictionCoefficient(inclinePanel.getFrictionInput());
            inclinedPlaneView.setAngle(inclinePanel.getAngleInput());
            inclinedPlaneView.setVelocity(0);
            inclinedPlaneView.startAnimation();
        });

        inclinePanel.setOnRestartClickListener(v->{
            inclinedPlaneView.setObjectMass(inclinePanel.getMassInput());
            inclinedPlaneView.restart();
            inclinedPlaneView.startAnimation();
        });

        inclinePanel.setOnPauseClickListener(v -> {
            boolean isPaused = !inclinedPlaneView.isPaused();
            inclinedPlaneView.setPaused(isPaused);

            if (isPaused) {
                stopIcon.setVisibility(ImageView.VISIBLE);
            } else {
                stopIcon.setVisibility(ImageView.GONE);
            }
        });

        inclinePanel.setOnReturnClickListener(view -> {
            finish();
        });
    }
}
