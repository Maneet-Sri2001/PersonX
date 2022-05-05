package com.collegeproject.personx.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "newsTable")
public class NewsModel {
  @PrimaryKey(autoGenerate = true)
  @NonNull
  int key;
  @ColumnInfo
  String sourceName;
  @ColumnInfo
  String author;
  @ColumnInfo
  String title;
  @ColumnInfo
  String description;
  @ColumnInfo
  String newsUrl;
  @ColumnInfo
  String imgUrl;
  @ColumnInfo
  String publishAt;
  
  public int getKey() {
    return key;
  }
  
  public void setKey(int key) {
    this.key = key;
  }
  
  public String getSourceName() {
    return sourceName;
  }
  
  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }
  
  public String getAuthor() {
    return author;
  }
  
  public void setAuthor(String author) {
    this.author = author;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getNewsUrl() {
    return newsUrl;
  }
  
  public void setNewsUrl(String newsUrl) {
    this.newsUrl = newsUrl;
  }
  
  public String getImgUrl() {
    return imgUrl;
  }
  
  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }
  
  public String getPublishAt() {
    return publishAt;
  }
  
  public void setPublishAt(String publishAt) {
    this.publishAt = publishAt;
  }
  
  public NewsModel(String sourceName, String author, String title, String description, String newsUrl, String imgUrl, String publishAt) {
    this.sourceName = sourceName;
    this.author = author;
    this.title = title;
    this.description = description;
    this.newsUrl = newsUrl;
    this.imgUrl = imgUrl;
    this.publishAt = publishAt;
  }
}
