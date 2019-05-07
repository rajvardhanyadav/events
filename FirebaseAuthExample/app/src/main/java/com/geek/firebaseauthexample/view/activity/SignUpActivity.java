package com.geek.firebaseauthexample.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.geek.firebaseauthexample.R;
import com.geek.firebaseauthexample.viewmodel.SignupViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import static com.geek.firebaseauthexample.MyApplication.getApp;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SignUpActivity";
    private EditText etEmailId;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btLogin;
    private SignupViewModel signupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmailId = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btLogin = findViewById(R.id.btnSignUp);
        btLogin.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        signupViewModel = ViewModelProviders.of(this).get(SignupViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void signUp(String email, String password, String confirmPassword) {
        showProgressDialog();
        if (password.equals(confirmPassword)) {
            getApp().getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dismissProgressDialog();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = getApp().getFirebaseAuth().getCurrentUser();
                                Toast.makeText(SignUpActivity.this, "Sign up successful",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Passwords does not match", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSignUp:
                String email = etEmailId.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etPassword.getText().toString();
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    signUp(email,password,confirmPassword);
                }
                break;
        }
    }
}