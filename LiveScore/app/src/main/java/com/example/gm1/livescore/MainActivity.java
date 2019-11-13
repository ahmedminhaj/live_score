package com.example.gm1.livescore;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText mTextEmail;
    EditText mTextPassword;
    Button mButtonSignIn;
    CheckBox mShowPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextEmail = (EditText)findViewById(R.id.edittext_email);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonSignIn = (Button)findViewById(R.id.button_singin);
        mShowPassword = (CheckBox)findViewById(R.id.show_password);

        //password visibility option
        mShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //show password
                    mTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //hide password
                    mTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        // profile sign in operation
        mButtonSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                userLogin();
            }
        });
    }

    //user login validation
    private void userLogin() {
        String email = mTextEmail.getText().toString().trim();
        String password = mTextPassword.getText().toString().trim();
        int role_id = 3;

        if(email.isEmpty()){
            mTextEmail.setError("Email required");
            mTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mTextEmail.setError("Required vail email");
            mTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            mTextPassword.setError("Password required");
            mTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            mTextPassword.setError("Password require 6 digit length");
            mTextPassword.requestFocus();
            return;
        }

        Call<SignInResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .userSignIn(email, password, role_id);

        call.enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                SignInResponse signInResponse = response.body();

                if(signInResponse.isStatus())
                {
                    // save the response from API in sharedPrefManager
                    SharedPrefManager.getInstance(MainActivity.this)
                            .saveUser(signInResponse.getData());

                    // check if android id is matched

                    // View the message from api server
                    Toast.makeText(MainActivity.this, signInResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    // Start homeActivity to show the homepage
                    Intent homeIntent = new Intent(MainActivity.this,LiveScoreActivity.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);

                }
                else{
                    //error message
                    Toast.makeText(MainActivity.this, signInResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {

            }

        });
    }
}
