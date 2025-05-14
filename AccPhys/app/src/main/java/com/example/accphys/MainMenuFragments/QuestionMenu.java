package com.example.accphys.MainMenuFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.accphys.Quiz.RaceAgainstTheClock;
import com.example.accphys.Quiz.ThreeStrikes;
import com.example.accphys.R;


public class QuestionMenu extends Fragment {


    public QuestionMenu() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_menu, container, false);

        Button raceButton = view.findViewById(R.id.button_race);
        Button strikesButton = view.findViewById(R.id.button_strikes);

        raceButton.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), RaceAgainstTheClock.class));
        });

        strikesButton.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ThreeStrikes.class));
        });

        return view;
    }

}