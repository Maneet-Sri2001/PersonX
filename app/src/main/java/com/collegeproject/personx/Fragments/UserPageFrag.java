package com.collegeproject.personx.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.collegeproject.personx.Adaptor.NewsRecycleViewAdaptor;
import com.collegeproject.personx.Adaptor.OnNewsClickListener;
import com.collegeproject.personx.Model.NewsModel;
import com.collegeproject.personx.Model.WeatherModel;
import com.collegeproject.personx.NetworkFile.UserNetwork;
import com.collegeproject.personx.R;
import com.collegeproject.personx.Utils.SharedPreferenceClass;
import com.collegeproject.personx.ViewModel.NewsViewModel;
import com.collegeproject.personx.ViewModel.WeatherViewModel;
import com.uk.tastytoasty.TastyToasty;

public class UserPageFrag extends Fragment implements OnNewsClickListener {
  
  public RecyclerView newsRecycle;
  private ImageView getNews, hideNews, weaIcon, getWea, hideWea;
  private TextView weaCond, weaTemp, weaHum, weaVisi, weaWind, weaAp, weaUV, city;
  public NewsRecycleViewAdaptor recycleViewAdaptor;
  public NewsViewModel newsViewModel;
  public WeatherViewModel weatherViewModel;
  private static Context context;
  private LinearLayout wea1, wea2;
  WeatherModel weatherModel;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_user_page, container, false);
    context = view.getContext();
    newsRecycle = view.findViewById(R.id.news_recycler);
    getNews = view.findViewById(R.id.get_news);
    getWea = view.findViewById(R.id.get_weather);
    hideNews = view.findViewById(R.id.hide_news);
    weaIcon = view.findViewById(R.id.wea_icon);
    weaCond = view.findViewById(R.id.wea_cond);
    weaTemp = view.findViewById(R.id.wea_temp);
    weaHum = view.findViewById(R.id.wea_humidity);
    weaVisi = view.findViewById(R.id.wea_visibility);
    weaWind = view.findViewById(R.id.wea_wind);
    weaAp = view.findViewById(R.id.wea_air_pressure);
    weaUV = view.findViewById(R.id.wea_uv);
    city = view.findViewById(R.id.city);
    hideWea = view.findViewById(R.id.hide_wea_det);
    wea1 = view.findViewById(R.id.wea_det_1);
    wea2 = view.findViewById(R.id.wea_det_2);
    
    newsViewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        .create(NewsViewModel.class);
    weatherViewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        .create(WeatherViewModel.class);
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    
    newsViewModel.getAllNews().observe(getViewLifecycleOwner(), newsModels -> {
      recycleViewAdaptor = new NewsRecycleViewAdaptor(newsModels, this, getActivity());
      newsRecycle.setAdapter(recycleViewAdaptor);
    });
    weatherViewModel.weather.observe(getViewLifecycleOwner(), weatherModel1 -> {
      weatherModel = weatherModel1;
      try {
        setWeather();
      } catch (Exception e) {
        //TastyToasty.error(context, e.getMessage()).show();
      }
    });
    
    newsRecycle.setHasFixedSize(true);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
    linearLayoutManager.setReverseLayout(false);
    linearLayoutManager.setStackFromEnd(false);
    newsRecycle.setLayoutManager(linearLayoutManager);
    
    getNews.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        UserNetwork.getNews(context);
        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.circle_animation));
      }
    });
    getWea.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass(context);
          String loc = sharedPreferenceClass.getValueString("latitude") + "," + sharedPreferenceClass.getValueString("longitude");
          if (sharedPreferenceClass.getValueString("latitude").equals(""))
            TastyToasty.yellow(context, "Please Wait....Getting Location", R.drawable.ic_location_on).show();
          else {
            UserNetwork.getWeather(context, loc);
            setWeather();
          }
          view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.circle_animation));
        } catch (Exception e) {
          TastyToasty.error(context, e.getMessage()).show();
        }
      }
    });
    hideNews.setOnClickListener(new View.OnClickListener() {
      int button01pos = 0;
      
      @Override
      public void onClick(View view) {
        if (button01pos == 0) {
          newsRecycle.setVisibility(View.GONE);
          hideNews.setImageResource(R.drawable.ic_alert_off);
          button01pos = 1;
        } else if (button01pos == 1) {
          hideNews.setImageResource(R.drawable.ic_disabled_visible);
          newsRecycle.setVisibility(View.VISIBLE);
          button01pos = 0;
        }
      }
    });
    hideWea.setOnClickListener(new View.OnClickListener() {
      int button01pos = 1;
      
      @Override
      public void onClick(View view) {
        if (button01pos == 0) {
          wea1.setVisibility(View.GONE);
          wea2.setVisibility(View.GONE);
          button01pos = 1;
        } else if (button01pos == 1) {
          wea1.setVisibility(View.VISIBLE);
          wea2.setVisibility(View.VISIBLE);
          button01pos = 0;
        }
      }
    });
    
  }
  
  @Override
  public void onNewsClick(NewsModel newsModel) {
    TastyToasty.star(context, newsModel.getAuthor()).show();
  }
  
  public void setWeather() {
    city.setText(weatherModel.getCity());
    weaTemp.setText(weatherModel.getTemp() + "°");
    Glide.with(context).load("https:" + weatherModel.getIcon()).into(weaIcon);
    weaCond.setText(weatherModel.getCond());
    weaWind.setText(weatherModel.getWind());
    weaHum.setText(weatherModel.getHumidity());
    weaVisi.setText(weatherModel.getVisibility());
    weaUV.setText(weatherModel.getUv());
  }
}