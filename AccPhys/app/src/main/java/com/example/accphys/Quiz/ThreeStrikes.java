package com.example.accphys.Quiz;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.accphys.Database.Question;
import com.example.accphys.R;
import com.example.accphys.Database.QuizDatabaseHelper;

import java.util.List;

public class ThreeStrikes extends AppCompatActivity {

    private TextView questionText, scoreText, feedbackText, livesText;
    private RadioGroup optionsGroup;
    private RadioButton option1, option2, option3, option4;
    private ImageView questionImage;
    private Button skipButton, exitButton, nextButton;

    private int currentQuestionIndex = -1;
    private int score = 0;
    private int lives = 3;
    private List<Question> questionList;
    private String selectedAnswer = "";
    private String correctAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_strikes);

        livesText = findViewById(R.id.livesText);
        questionText = findViewById(R.id.questionText);
        scoreText = findViewById(R.id.scoreText);
        feedbackText = findViewById(R.id.feedbackText);
        optionsGroup = findViewById(R.id.optionsGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        questionImage = findViewById(R.id.questionImage);
        skipButton = findViewById(R.id.skipButton);
        exitButton = findViewById(R.id.exitButton);
        nextButton = findViewById(R.id.nextButton);

        QuizDatabaseHelper dbHelper = new QuizDatabaseHelper(this);
        questionList = dbHelper.getRandomQuestions(20);

        updateLivesText();
        updateLifeIcons();
        showNextQuestion();

        optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            nextButton.setEnabled(true);
            if (checkedId == R.id.option1) {
                selectedAnswer = "A";
            } else if (checkedId == R.id.option2) {
                selectedAnswer = "B";
            } else if (checkedId == R.id.option3) {
                selectedAnswer = "C";
            } else if (checkedId == R.id.option4) {
                selectedAnswer = "D";
            }
        });

        nextButton.setOnClickListener(v -> {
            checkAnswer();
            nextButton.setEnabled(false);
        });

        skipButton.setOnClickListener(v -> {
            showNextQuestion();
            feedbackText.setVisibility(View.GONE);
        });

        exitButton.setOnClickListener(v -> {
            Toast.makeText(this, "Quiz Over! Your score: " + score, Toast.LENGTH_LONG).show();
            finish();
        });
    }

    private void updateLivesText() {
        livesText.setText("Lives: " + lives);
    }

    private void checkAnswer() {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        correctAnswer = currentQuestion.getCorrectAnswer();

        if (selectedAnswer.equals(correctAnswer)) {
            score++;
            scoreText.setText("Score: " + score);
            feedbackText.setText("Correct!");
            feedbackText.setTextColor(Color.GREEN);
        } else {
            lives--;
            updateLivesText();
            updateLifeIcons();
            feedbackText.setText("Incorrect! Correct answer: " + correctAnswer);
            feedbackText.setTextColor(Color.RED);
        }

        feedbackText.setVisibility(View.VISIBLE);
        disableOptions();

        new android.os.Handler().postDelayed(() -> {
            if (lives <= 0) {
                Toast.makeText(this, "Out of lives! Final score: " + score, Toast.LENGTH_LONG).show();
                finish();
            } else {
                showNextQuestion();
                feedbackText.setVisibility(View.GONE);
            }
        }, 1500);
    }

    private void disableOptions() {
        for (int i = 0; i < optionsGroup.getChildCount(); i++) {
            optionsGroup.getChildAt(i).setEnabled(false);
        }
    }

    private void enableOptions() {
        for (int i = 0; i < optionsGroup.getChildCount(); i++) {
            optionsGroup.getChildAt(i).setEnabled(true);
        }
    }

    private void showNextQuestion() {
        currentQuestionIndex++;

        if (currentQuestionIndex >= questionList.size()) {
            Toast.makeText(this, "Quiz Completed! Final score: " + score, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Question question = questionList.get(currentQuestionIndex);
        correctAnswer = question.getCorrectAnswer();

        questionText.setText(question.getQuestionText());
        option1.setText("A. " + question.getOptionA());
        option2.setText("B. " + question.getOptionB());
        option3.setText("C. " + question.getOptionC());
        option4.setText("D. " + question.getOptionD());

        if (question.getImagePath() != null && !question.getImagePath().isEmpty()) {
            questionImage.setVisibility(View.VISIBLE);
            int resId = getResources().getIdentifier(question.getImagePath(), "drawable", getPackageName());
            if (resId != 0) {
                questionImage.setImageResource(resId);
            } else {
                questionImage.setVisibility(View.GONE);
                Log.e("ImageLoad", "Could not find image: " + question.getImagePath());
            }
        } else {
            questionImage.setVisibility(View.GONE);
        }

        optionsGroup.clearCheck();
        enableOptions();
    }

    private void updateLifeIcons() {
        ImageView life1 = findViewById(R.id.life1);
        ImageView life2 = findViewById(R.id.life2);
        ImageView life3 = findViewById(R.id.life3);

        life1.setVisibility(lives >= 1 ? View.VISIBLE : View.INVISIBLE);
        life2.setVisibility(lives >= 2 ? View.VISIBLE : View.INVISIBLE);
        life3.setVisibility(lives >= 3 ? View.VISIBLE : View.INVISIBLE);
    }
}
