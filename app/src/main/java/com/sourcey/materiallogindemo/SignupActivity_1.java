package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import rest.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Arjun on 21-05-2018
 */

public class SignupActivity_1 extends AppCompatActivity {
    private EditText etName,etEmail,etMobile,etPassword,etreEnterPassword;
    private Button btn_signup;
    private TextView btn_login;
    private String username,email,mobile,password,reenterpassword;


    @Override
    protected void onCreate(Bundle  savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etName=findViewById(R.id.input_name);
        etEmail= findViewById(R.id.etemail);
        etMobile=findViewById(R.id.input_mobile);
        etPassword=findViewById(R.id.etpassword);
        etreEnterPassword=findViewById(R.id.input_reEnterPassword);
        btn_signup=findViewById(R.id.btn_signup);
        btn_login = findViewById( R.id.link_login );

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username=etName.getText().toString();
                email=etEmail.getText().toString();
                mobile=etMobile.getText().toString();
                password=etPassword.getText().toString();
                reenterpassword=etreEnterPassword.getText().toString();


                boolean isValid = true;
                if (username.isEmpty() || username.length() < 3){
                    isValid = false;
                    etName.setError("Please Enter username");
                    etName.requestFocus();
                    // Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
                if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    isValid = false;
                    etEmail.setError("Please Enter Email");
                    etEmail.requestFocus();
                    // Toast.makeText(getApplicationContext(), "Please Enter Mobile number", Toast.LENGTH_SHORT).show();
                }
                if (mobile.isEmpty() || mobile.length()!=10)  {
                    isValid = false;
                    etMobile.setError("Please Enter mobile");
                    etMobile.requestFocus();
                    //Toast.makeText(getApplicationContext(), "Please Enter Date of Birth", Toast.LENGTH_SHORT).show();
                }
                if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                    isValid = false;
                    etPassword.setError("Please Enter password");
                    etPassword.requestFocus();
                    // Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                if (reenterpassword.isEmpty() || reenterpassword.length() < 4 || reenterpassword.length() > 10 || !(reenterpassword.equals(password)))  {
                    isValid = false;
                    Toast.makeText(getApplicationContext(), "Password mismatched", Toast.LENGTH_SHORT).show();
                }

                if (isValid) {
                    callRegisterApi();
                }
            }
        });

        btn_login.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent( SignupActivity_1.this, LoginActivity.class );
                startActivity( intent );
                finish();
                overridePendingTransition( R.anim.push_left_in, R.anim.push_left_out );
            }
        } );
    }

    private void callRegisterApi() {

        RestClient.getAuthenticationApiServices().GetRegister(etName.getText().toString(),etPassword.getText().toString(),etMobile.getText().toString())
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        Log.e("TAG","Status is" +response.body().getMessage());
                        if(response.body().getMessage().equals("success")){
                            //you can get id,status here by writing response.body().getId();
                            //if success msg i will get then and then go to next screen
                            Intent intent=new Intent(SignupActivity_1.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            //get error message
                            Toast.makeText(SignupActivity_1.this,""+response.body().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toast.makeText(SignupActivity_1.this,"error is" +t.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void close(View view) {
        onBackPressed();
    }
}



