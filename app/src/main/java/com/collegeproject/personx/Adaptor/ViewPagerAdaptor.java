package com.collegeproject.personx.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.collegeproject.personx.R;

public class ViewPagerAdaptor extends PagerAdapter {
  Context ctx;
  int[] images = {
      
      R.drawable.image1,
      R.drawable.image2,
      R.drawable.image3,
      R.drawable.image4
  };
  
  int[] headings = {
      
      R.string.heading_one,
      R.string.heading_two,
      R.string.heading_three,
      R.string.heading_fourth
  };
  
  int[] description = {
      
      R.string.desc_one,
      R.string.desc_two,
      R.string.desc_three,
      R.string.desc_fourth
  };
  
  public ViewPagerAdaptor(Context ctx) {
    this.ctx = ctx;
  }
  
  @Override
  public int getCount() {
    return headings.length;
  }
  
  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == object;
  }
  
  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, int position) {
    LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = layoutInflater.inflate(R.layout.on_board_slider, container, false);
    
    ImageView slideTitleImg = view.findViewById(R.id.titleImg);
    TextView slideHeading = view.findViewById(R.id.txtTitle);
    TextView slideDescription = view.findViewById(R.id.txtDescription);
    
    slideTitleImg.setImageResource(images[position]);
    slideHeading.setText(headings[position]);
    slideDescription.setText(description[position]);
    container.addView(view);
    return view;
  }
  
  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((ConstraintLayout) object);
  }
}
