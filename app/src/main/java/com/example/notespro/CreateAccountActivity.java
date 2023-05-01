package com.example.notespro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button createAccountBtn;
    ProgressBar progressBar;
    TextView loginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailEditText = findViewById(R.id.email_id_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        createAccountBtn = findViewById(R.id.create_account_btn);
        progressBar = findViewById(R.id.progress_bar);
        loginTextView = findViewById(R.id.login_text_view);

        createAccountBtn.setOnClickListener((v)->createAccount());
        loginTextView.setOnClickListener((v)->finish());
    }

    public void createAccount()
    {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        boolean isValidated = validateData(email,password,confirmPassword);
        if(!isValidated)
        {
            // Lenh return o day nghia la gi
            return;
        }

        createAcountInFireBase(email,password);
    }

    boolean validateData(String email, String password , String confirmPassword)
    {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailEditText.setError("Email is invalid");
            return false;
        }
        if(password.length()<6)
        {
            passwordEditText.setError("Password is invalid");
            return false;
        }
        if(!password.equals(confirmPassword))
        {
            confirmPasswordEditText.setError("Confirm Password is invalid");
            return false;
        }
        return true;
    }

    void createAcountInFireBase(String email,String password)
    {
        changeInProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Utility.showToast(CreateAccountActivity.this, "Successfully create account ,Check email to verify");
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();
                    // Tai sao can co lenh finish
                    finish();
                }
                else
                {
                    Utility.showToast(CreateAccountActivity.this, task.getException().getLocalizedMessage());
                }
            }
        });
    }

    void changeInProgress(boolean inProgress)
    {
        if(inProgress)
        {
            progressBar.setVisibility(View.VISIBLE);
            createAccountBtn.setVisibility(View.GONE);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            createAccountBtn.setVisibility(View.VISIBLE);
        }
    }

}