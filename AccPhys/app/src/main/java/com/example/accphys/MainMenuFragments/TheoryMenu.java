package com.example.accphys.MainMenuFragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.accphys.R;

public class TheoryMenu extends Fragment {

    public TheoryMenu() {
    }

    public static TheoryMenu newInstance() {
        return new TheoryMenu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theory_menu, container, false);
        LinearLayout theoryLayout = view.findViewById(R.id.theoryLayout);

        String[] motionLaws = getResources().getStringArray(R.array.motion_laws);

        for (String law : motionLaws) {


            if (law.startsWith("• ")) {

                TextView categoryHeader = new TextView(getContext());
                categoryHeader.setText(law.substring(2)); // Remove bullet
                categoryHeader.setTextSize(20);
                categoryHeader.setTypeface(null, Typeface.BOLD_ITALIC);
                categoryHeader.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorOnPrimary));
                categoryHeader.setPadding(16, 32, 16, 8);
                theoryLayout.addView(categoryHeader);
                continue;
            }

            LinearLayout card = new LinearLayout(getContext());
            card.setOrientation(LinearLayout.VERTICAL);
            card.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.card_background));
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            cardParams.setMargins(2, 10, 2, 10);
            card.setLayoutParams(cardParams);
            card.setPadding(0, 8, 0, 8);

            TextView header = new TextView(getContext());
            header.setText(law);
            header.setTextSize(18);
            header.setTypeface(null, Typeface.BOLD);
            header.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorOnPrimary));
            header.setBackgroundResource(R.drawable.header_background);
            header.setPadding(32, 24, 32, 24);
            header.setClickable(true);
            header.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView body = new TextView(getContext());
            body.setText(Html.fromHtml(getDisplayTextForLaw(law), Html.FROM_HTML_MODE_COMPACT));
            body.setTextSize(16);
            body.setTypeface(Typeface.SERIF);
            body.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorOnSurface));
            body.setPadding(32, 16, 32, 16);
            body.setVisibility(View.GONE);

            header.setOnClickListener(v -> {
                if (body.getVisibility() == View.GONE) {
                    body.setVisibility(View.VISIBLE);
                } else {
                    body.setVisibility(View.GONE);
                }
            });

            card.addView(header);
            card.addView(body);
            theoryLayout.addView(card);
        }

        return view;
    }

    private String getDisplayTextForLaw(String selectedLaw) {
        switch (selectedLaw) {
            case "Newton's First Law":
                return "Inertia - An object at rest remains at rest, and an object in motion remains in motion at constant speed and in a straight line unless acted on by an unbalanced force.";
            case "Velocity":
                return "v = ∆s / ∆t<br>v = ds / dt";
            case "Acceleration":
                return "a = ∆v / ∆t<br>a = dv / dt";
            case "Equations of Motion":
                return "Speed: v = v₀ + at<br>" +
                        "Distance: s = s₀ + v₀t + ½at²<br>" +
                        "Speed in terms of distance: v² = v₀² + 2a(s − s₀)<br>" +
                        "Median Speed: v = ½(v + v₀)";
            case "Newton's Second Law":
                return "∑F = ma<br>∑F = dp / dt";
            case "Newton's Third Law":
                return "If two bodies exert forces on each other, these forces have the same magnitude but opposite directions: F<sub>ab</sub> = −F<sub>ba</sub>";
            case "Weight":
                return "G = mg";
            case "Momentum & Impulse":
                return "Momentum: p = mv"+
                        "Impulse: J = F∆t<br>J = ∫F dt" +
                        "Momentum-Impulse: F∆t = m∆v<br>∫F dt = ∆p";
            case "Work and Energies":
                return "Work: W = F∆s cos(θ)<br>W = ∫F · ds" +
                        "Work-Energy: F∆s cos(θ) = ∆E<br>∫F · ds = ∆E" +
                        "Kinetic Energy: F∆s cos(θ) = ∆E<br>∫F · ds = ∆E" +
                        "Gravitational Potential Energy: ∆U<sub>g</sub> = mg∆h";
            case "Efficiency":
                return "η = W<sub>out</sub> / E<sub>in</sub>";
            case "Power":
                return "Power: P = ∆W / ∆t<br>P = dW / dt" +
                        "Power-Velocity: P = Fv cos(θ)<br>P = F · v ";
            case "Hooke's Law":
                return "F = −k∆x";
            default:
                return "Equation not defined.";
        }
    }
}