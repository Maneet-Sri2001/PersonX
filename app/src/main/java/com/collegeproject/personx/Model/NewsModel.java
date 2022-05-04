package com.collegeproject.personx.Model;

public class NewsModel {
  String auth, name, tiltle, url, imgUrl, des, content, publishAt;
  
  public NewsModel(String auth, String name, String tiltle, String url, String imgUrl, String des, String content, String publishAt) {
    this.auth = auth;
    this.name = name;
    this.tiltle = tiltle;
    this.url = url;
    this.imgUrl = imgUrl;
    this.des = des;
    this.content = content;
    this.publishAt = publishAt;
  }
  
  public String getAuth() {
    return auth;
  }
  
  public void setAuth(String auth) {
    this.auth = auth;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getTiltle() {
    return tiltle;
  }
  
  public void setTiltle(String tiltle) {
    this.tiltle = tiltle;
  }
  
  public String getUrl() {
    return url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public String getImgUrl() {
    return imgUrl;
  }
  
  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }
  
  public String getDes() {
    return des;
  }
  
  public void setDes(String des) {
    this.des = des;
  }
  
  public String getContent() {
    return content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getPublishAt() {
    return publishAt;
  }
  
  public void setPublishAt(String publishAt) {
    this.publishAt = publishAt;
  }
}
