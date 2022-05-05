package com.collegeproject.personx.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.collegeproject.personx.Model.NewsModel;
import com.collegeproject.personx.Repository.NewsRepository;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
  public static NewsRepository newsRepository;
  public final LiveData<List<NewsModel>> allNews;
  
  public NewsViewModel(@NonNull Application application) {
    super(application);
    newsRepository = new NewsRepository(application);
    this.allNews = newsRepository.getAllNews();
  }
  
  public static void insert(NewsModel newsModel) {
    newsRepository.insert(newsModel);
  }
  
  public static void deleteAll() {
    newsRepository.deleteAll();
  }
  
  public LiveData<List<NewsModel>> getAllNews() {
    return allNews;
  }
}
