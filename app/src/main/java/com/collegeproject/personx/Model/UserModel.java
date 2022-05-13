package com.collegeproject.personx.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userTable")
public class UserModel {
  @PrimaryKey(autoGenerate = false)
  @NonNull
  @ColumnInfo(name = "userName")
  String name;
  
  @ColumnInfo(name = "userEmail")
  String email;
  
  @ColumnInfo(name = "userImg")
  String img;
  
  @ColumnInfo(name = "createdAt")
  String created;
  
  public UserModel(String name, String email, String img, String created) {
    this.name = name;
    this.email = email;
    this.img = img;
    this.created = created;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getImg() {
    return img;
  }
  
  public void setImg(String img) {
    this.img = img;
  }
  
  public String getCreated() {
    return created;
  }
  
  public void setCreated(String created) {
    this.created = created;
  }
}