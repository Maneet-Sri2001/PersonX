package com.collegeproject.personx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.collegeproject.personx.Fragments.LogInUserFrag;

public class RegisterActivity extends AppCompatActivity {
  private FrameLayout frameLayout;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    frameLayout = findViewById(R.id.frame_layout_register);
    setFragment(new LogInUserFrag());
  }
  private void setFragment(Fragment fragment) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(frameLayout.getId(), fragment);
    fragmentTransaction.commit();
  }
  
  @Override
  protected void onStart() {
    super.onStart();
    SharedPreferences pref = getSharedPreferences("user@PersonX", MODE_PRIVATE);
    if (pref.contains("token")) {
      startActivity(new Intent(RegisterActivity.this, MainActivity.class));
      finish();
    }
  }
}