package com.example.accphys.MainMenuFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.accphys.R;
import com.example.accphys.Simulations.FreeFallSimulation.FreeFallActivity;
import com.example.accphys.Simulations.InclinedPanelSImulation.InclinedActivity;
import com.example.accphys.Simulations.SimplePlaneSimulation.SimplePlaneActivity;


public class SimulatorMenu extends Fragment {

    public SimulatorMenu() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_simulator_menu, container, false);

        view.findViewById(R.id.button_free_fall).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FreeFallActivity.class);
            startActivity(intent);
        });

        view.findViewById(R.id.button_inclined_plane).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InclinedActivity.class);
            startActivity(intent);
        });

        view.findViewById(R.id.button_moving_force).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SimplePlaneActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
