package com.example.accphys;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.accphys.Database.UserDatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, confirmPasswordEditText;
    private ImageView eyeIconPassword, eyeIconConfirmPassword;
    private Button submitButton, switchModeButton;
    private TextView modeTextView;

    private boolean isLoginMode = true;
    private boolean showPassword = false;
    private boolean showConfirmPassword = false;
    private UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new UserDatabaseHelper(this);

        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);
        eyeIconPassword = findViewById(R.id.eyeIconPassword);
        eyeIconConfirmPassword = findViewById(R.id.eyeIconConfirmPassword);
        submitButton = findViewById(R.id.buttonSubmit);
        switchModeButton = findViewById(R.id.buttonSwitchMode);
        modeTextView = findViewById(R.id.textViewMode);

        updateUI();

        eyeIconPassword.setOnClickListener(v -> {
            showPassword = !showPassword;
            togglePasswordVisibility(passwordEditText, showPassword);
            eyeIconPassword.setImageResource(showPassword ? R.drawable.ic_eye_off : R.drawable.ic_eye);
        });

        eyeIconConfirmPassword.setOnClickListener(v -> {
            showConfirmPassword = !showConfirmPassword;
            togglePasswordVisibility(confirmPasswordEditText, showConfirmPassword);
            eyeIconConfirmPassword.setImageResource(showConfirmPassword ? R.drawable.ic_eye_off : R.drawable.ic_eye);
        });

        switchModeButton.setOnClickListener(v -> {
            isLoginMode = !isLoginMode;
            updateUI();
        });

        submitButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || (!isLoginMode && confirmPassword.isEmpty())) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isLoginMode && !password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!confirmPassword(password))
            {
                Toast.makeText(this, "Passwords must contain a uppercase, lowercase letter, a digit and a symbol", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isLoginMode) {
                if (dbHelper.loginUser(username, password)) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (dbHelper.registerUser(username, password)) {
                    Toast.makeText(this, "Account created! You can now log in.", Toast.LENGTH_SHORT).show();
                    isLoginMode = true;
                    updateUI();
                } else {
                    Toast.makeText(this, "Username already taken", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void togglePasswordVisibility(EditText editText, boolean visible) {
        int inputType = visible ?
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        editText.setInputType(inputType);
        editText.setSelection(editText.getText().length());
    }

    private void updateUI() {
        if (isLoginMode) {
            modeTextView.setText("Login");
            submitButton.setText("Login");
            switchModeButton.setText("Don't have an account? Sign Up");
            confirmPasswordEditText.setVisibility(View.GONE);
            eyeIconConfirmPassword.setVisibility(View.GONE);
        } else {
            modeTextView.setText("Sign Up");
            submitButton.setText("Create Account");
            switchModeButton.setText("Already have an account? Login");
            confirmPasswordEditText.setVisibility(View.VISIBLE);
            eyeIconConfirmPassword.setVisibility(View.VISIBLE);
        }
    }

    private boolean confirmPassword(String password){
        boolean lowercase = false;
        boolean uppercase = false;
        boolean number = false;
        boolean symbol = false;

        for(int i=0; i<password.length(); i++){
            char character = password.charAt(i);
            if (Character.isLowerCase(character)){
                lowercase = true;
            }
            if (Character.isUpperCase(character)){
                uppercase = true;
            }
            if (Character.isDigit(character)){
                number = true;
            }
            if (!Character.isLetterOrDigit(character)){
                symbol = true;
            }
        }

        return lowercase && uppercase && number && symbol && !password.contains(" ");

    }
}
