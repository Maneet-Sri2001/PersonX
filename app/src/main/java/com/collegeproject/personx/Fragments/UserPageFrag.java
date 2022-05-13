package com.collegeproject.personx.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.uk.tastytoasty.TastyToasty;

import java.util.ArrayList;
import java.util.List;

public class UserPageFrag extends Fragment implements OnNewsClickListener {
  
  public RecyclerView newsRecycle;
  private ImageView getNews, hideNews, weaIcon, getWea, hideWea;
  private TextView weaCond, weaTemp, weaHum, weaVisi, weaWind, weaAp, weaUV, city;
  public NewsRecycleViewAdaptor recycleViewAdaptor;
  public NewsViewModel newsViewModel;
  public WeatherViewModel weatherViewModel;
  private static Context context;
  private LinearLayout wea1, wea2;
  private PieChart pieChart;
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
    pieChart = view.findViewById(R.id.piechart);
    
    newsViewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        .create(NewsViewModel.class);
    weatherViewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        .create(WeatherViewModel.class);
    
    return view;
  }
  
  private void showPieChart() {
    float time[] = {55, 95, 30, 360 - (55 + 95 + 30)};
    String activity[] = {"Jan", "Feb", "March", ""};
    //pupulating list of PieEntires
    List<PieEntry> pieEntires = new ArrayList<>();
    for (int i = 0; i < time.length; i++) {
      pieEntires.add(new PieEntry(time[i], activity[i]));
    }
    PieDataSet dataSet = new PieDataSet(pieEntires, "");
    dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
    PieData data = new PieData(dataSet);
    //Get the chart
    pieChart.setData(data);
    pieChart.invalidate();
    pieChart.setCenterText("50% \n ");
    pieChart.setDrawEntryLabels(false);
    pieChart.setContentDescription("");
    //pieChart.setDrawMarkers(true);
    //pieChart.setMaxHighlightDistance(34);
    pieChart.setEntryLabelTextSize(12);
    pieChart.setHoleRadius(75);
    
    //legend attributes
    Legend legend = pieChart.getLegend();
    legend.setForm(Legend.LegendForm.CIRCLE);
    legend.setTextSize(12);
    legend.setFormSize(20);
    legend.setFormToTextSpace(2);
  }
  
  private void initPieChart() {
    //using percentage as values instead of amount
    pieChart.setUsePercentValues(true);
    
    //remove the description label on the lower left corner, default true if not set
    pieChart.getDescription().setEnabled(false);
    
    //enabling the user to rotate the chart, default true
    pieChart.setRotationEnabled(true);
    //adding friction when rotating the pie chart
    pieChart.setDragDecelerationFrictionCoef(0.9f);
    //setting the first entry start from right hand side, default starting from top
    pieChart.setRotationAngle(0);
    
    //highlight the entry when it is tapped, default true if not set
    pieChart.setHighlightPerTapEnabled(true);
    //adding animation so the entries pop up from 0 degree
    pieChart.animateY(1400, Easing.EaseInOutQuad);
    //setting the color of the hole in the middle, default white
    pieChart.setHoleColor(Color.parseColor("#000000"));
    
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    
    showPieChart();
    newsViewModel.getAllNews().observe(getViewLifecycleOwner(), newsModels -> {
      recycleViewAdaptor = new NewsRecycleViewAdaptor(newsModels, this, getActivity());
      newsRecycle.setAdapter(recycleViewAdaptor);
    });
    weatherViewModel.weather.observe(getViewLifecycleOwner(), weatherModel1 -> {
      weatherModel = weatherModel1;
      try {
        setWeather();
      } catch (Exception e) {
        Log.e("WeatherViewModel", e.getMessage());
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
          TastyToasty.error(context, "Error Here : " + e.getMessage()).show();
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
    TastyToasty.star(context, newsModel.getImgUrl()).show();
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