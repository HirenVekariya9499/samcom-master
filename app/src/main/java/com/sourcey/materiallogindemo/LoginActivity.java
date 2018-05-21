package com.sourcey.materiallogindemo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import rest.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private TextView btn_signup;
    private EditText etName, etPassword;
    private String username, password;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        etName = findViewById( R.id.input_email );
        etPassword = findViewById( R.id.input_password );
        btn_signup = findViewById( R.id.link_signup);
        btn_login = findViewById( R.id.btn_login );

        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = etName.getText().toString();
                password = etPassword.getText().toString();

                boolean isValid = true;
                if (username.isEmpty() || username.length() < 3) {
                    isValid = false;
                    etName.setError( "Please Enter username" );
                    etName.requestFocus();
                    // Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                }

                if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                    isValid = false;
                    etPassword.setError( "Please Enter password" );
                    etPassword.requestFocus();
                    // Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                }

                if (isValid) {
                    callLoginApi();
                }
            }
        } );


        btn_signup.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent( LoginActivity.this, SignupActivity_1.class );
                startActivity( intent );
                finish();
                overridePendingTransition( R.anim.push_left_in, R.anim.push_left_out );
            }
        } );
    }


    private void callLoginApi() {

        RestClient.getAuthenticationApiServices().GetLogin( etName.getText().toString(), etPassword.getText().toString() )
                .enqueue( new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        Log.e( "TAG", "Status is" + response.body().getMessage() );
                        if (response.body().getMessage().equals( "success" )) {
                            //you can get id,status here by writing response.body().getId();
                            //if success msg i will get then and then go to next screen
                            Intent intent = new Intent( LoginActivity.this, MainActivity.class );
                            startActivity( intent );
                        } else {
                            //get error message
                            Toast.makeText( LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_LONG ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toast.makeText( LoginActivity.this, "error is" + t.getMessage(), Toast.LENGTH_LONG ).show();

                    }
                } );

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
