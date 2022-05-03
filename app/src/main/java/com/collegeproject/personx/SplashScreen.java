package com.collegeproject.personx;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
    
    Thread thread = new Thread() {
      public void run() {
        try {
          sleep(2000);
          startActivity(new Intent(SplashScreen.this, OnBoardActivity.class));
          finish();
        } catch (Exception e) {
          Toast.makeText(SplashScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
      }
    };
    thread.start();
  }
}