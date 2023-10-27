package com.rehan.qdtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity {
    Button btn_next;
    private SharedPreferences sharedPreferences;
    private Boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        btn_next=findViewById(R.id.btn_next);
        sharedPreferences=getSharedPreferences("AppData",MODE_PRIVATE);
        isLogin=sharedPreferences.getBoolean("isLogin",false);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginForm();//For new users , it will open , login form other wise open Dashboard page

            }
        });
    }
    private  void openLoginForm(){
        if(isLogin){
            Intent intent= new Intent(LandingActivity.this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else {
            LoginFragment loginFragment= new LoginFragment();
            loginFragment.show(getSupportFragmentManager(),
                    "login_form");
        }
    }
}