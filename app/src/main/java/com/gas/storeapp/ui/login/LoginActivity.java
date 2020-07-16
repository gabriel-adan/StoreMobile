package com.gas.storeapp.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.gas.storeapp.R;
import com.gas.storeapp.model.User;
import com.gas.storeapp.ui.MainActivity;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.logIn(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        loginViewModel.onSuccessLogin().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                loadingProgressBar.setVisibility(View.GONE);
                String userName = user.getName();
                String fullName = user.getFullName();
                String token = user.getToken();
                setResult(Activity.RESULT_OK);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("UserName", userName);
                intent.putExtra("FullName", fullName);
                intent.putExtra("Token", token);
                startActivity(intent);
                finish();
            }
        });

        loginViewModel.onErrorLogin().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                loadingProgressBar.setVisibility(View.GONE);
                Snackbar.make(loginButton, s, Snackbar.LENGTH_LONG).show();
            }
        });

        loginViewModel.logIn("gsantillan", "12345");
    }
}
