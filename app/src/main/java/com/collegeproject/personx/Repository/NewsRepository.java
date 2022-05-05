package com.collegeproject.personx.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.collegeproject.personx.Database.DAO.NewsDAO;
import com.collegeproject.personx.Database.NewsDatabase;
import com.collegeproject.personx.Model.NewsModel;

import java.util.List;

public class NewsRepository {
  private final NewsDAO newsDAO;
  public final LiveData<List<NewsModel>> allNews;
  
  public NewsRepository(Application application) {
    NewsDatabase database = NewsDatabase.getInstance(application);
    this.newsDAO = database.newsDAO();
    this.allNews = newsDAO.getAll();
  }
  
  public void insert(NewsModel news) {
    new InsertNewsAsync(newsDAO).execute(news);
  }
  
  public LiveData<List<NewsModel>> getAllNews() {
    return allNews;
  }
  
  public void deleteAll() {
    new DeleteAll(newsDAO).execute();
  }
  
  //Async Task Insert
  private static class InsertNewsAsync extends AsyncTask<NewsModel, Void, Void> {
    private final NewsDAO newsDAO;
    
    private InsertNewsAsync(NewsDAO newsDAO) {
      this.newsDAO = newsDAO;
    }
    
    @Override
    protected Void doInBackground(NewsModel... newsModels) {
      newsDAO.insert(newsModels[0]);
      return null;
    }
  }
  
  //Async Delete News
  private static class DeleteAll extends AsyncTask<NewsModel, Void, Void> {
    private final NewsDAO newsDAO;
    
    private DeleteAll(NewsDAO newsDAO) {
      this.newsDAO = newsDAO;
    }
    
    @Override
    protected Void doInBackground(NewsModel... newsModels) {
      newsDAO.deleteAll();
      return null;
    }
  }
}
