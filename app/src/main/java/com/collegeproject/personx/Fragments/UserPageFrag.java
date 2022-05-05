package com.collegeproject.personx.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.collegeproject.personx.Adaptor.NewsRecycleViewAdaptor;
import com.collegeproject.personx.Adaptor.OnNewsClickListener;
import com.collegeproject.personx.Model.NewsModel;
import com.collegeproject.personx.NetworkFile.UserNetwork;
import com.collegeproject.personx.R;
import com.collegeproject.personx.ViewModel.NewsViewModel;
import com.uk.tastytoasty.TastyToasty;

public class UserPageFrag extends Fragment implements OnNewsClickListener {
  
  public RecyclerView newsRecycle;
  private ImageView getNews, hideNews;
  public NewsRecycleViewAdaptor recycleViewAdaptor;
  public NewsViewModel newsViewModel;
  private static Context context;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_user_page, container, false);
    context = view.getContext();
    newsRecycle = view.findViewById(R.id.news_recycler);
    getNews = view.findViewById(R.id.get_news);
    hideNews = view.findViewById(R.id.hide_news);
    newsViewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        .create(NewsViewModel.class);
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
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
    
    newsViewModel.getAllNews().observe(getViewLifecycleOwner(), newsModels -> {
      recycleViewAdaptor = new NewsRecycleViewAdaptor(newsModels, this, getActivity());
      newsRecycle.setAdapter(recycleViewAdaptor);
    });
  }
  
  @Override
  public void onNewsClick(NewsModel newsModel) {
    TastyToasty.star(context, newsModel.getAuthor()).show();
  }
}