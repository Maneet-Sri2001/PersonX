package com.collegeproject.personx;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.collegeproject.personx.Adaptor.ViewPagerAdaptor;
import com.collegeproject.personx.Utils.SharedPreferenceClass;

public class OnBoardActivity extends AppCompatActivity {
  ViewPager mSlideViewPager;
  LinearLayout mDotLayout;
  Button backBtn, nextBtn, skipBtn;
  TextView[] dots;
  ViewPagerAdaptor viewPagerAdaptor;
  
  private SharedPreferenceClass sharedPreferenceClass;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_on_board);
    backBtn = findViewById(R.id.backBtn);
    nextBtn = findViewById(R.id.nextBtn);
    skipBtn = findViewById(R.id.skipBtn);
    
    sharedPreferenceClass = new SharedPreferenceClass(getApplicationContext());
    
    backBtn.setOnClickListener(view -> {
      if (getItem(0) > 0) {
        mSlideViewPager.setCurrentItem(getItem(-1), true);
      }
    });
    
    nextBtn.setOnClickListener(view -> {
      backBtn.setVisibility('1');
      if (getItem(0) < 3) {
        mSlideViewPager.setCurrentItem(getItem(1), true);
      } else {
        Intent intent = new Intent(OnBoardActivity.this, RegisterActivity.class);
        startActivity(intent);
        sharedPreferenceClass.setValueBoolean("userBoarded", true);
        finish();
      }
    });
    
    skipBtn.setOnClickListener(view -> {
      Intent intent = new Intent(OnBoardActivity.this, RegisterActivity.class);
      startActivity(intent);
      sharedPreferenceClass.setValueBoolean("userBoarded", true);
      finish();
      
    });
    
    mSlideViewPager = findViewById(R.id.slideViewPager);
    mDotLayout = findViewById(R.id.indicator_layout);
    
    viewPagerAdaptor = new ViewPagerAdaptor(this);
    
    mSlideViewPager.setAdapter(viewPagerAdaptor);
    
    setUpIndicator(0);
    mSlideViewPager.addOnPageChangeListener(viewListener);
  }
  
  public void setUpIndicator(int position) {
    dots = new TextView[4];
    mDotLayout.removeAllViews();
    
    for (int i = 0; i < dots.length; i++) {
      dots[i] = new TextView(this);
      dots[i].setText(Html.fromHtml("&#8226"));
      dots[i].setTextSize(35);
      dots[i].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.inactive));
      mDotLayout.addView(dots[i]);
    }
    dots[position].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.active));
  }
  
  ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    
    }
    
    @Override
    public void onPageSelected(int position) {
      setUpIndicator(position);
      if (position > 0) {
        backBtn.setVisibility(View.VISIBLE);
      } else
        backBtn.setVisibility(View.INVISIBLE);
      
    }
    
    @Override
    public void onPageScrollStateChanged(int state) {
    
    }
  };
  
  private int getItem(int i) {
    return mSlideViewPager.getCurrentItem() + i;
  }
  
  @Override
  protected void onStart() {
    super.onStart();
    if (sharedPreferenceClass.getValueBoolean("userBoarded")) {
      startActivity(new Intent(this, RegisterActivity.class));
      finish();
    }
  }
}