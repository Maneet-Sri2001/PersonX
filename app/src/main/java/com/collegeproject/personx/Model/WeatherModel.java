package com.collegeproject.personx.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "WeatherRecord")
public class WeatherModel {
  @PrimaryKey
  @NonNull
  @ColumnInfo
  String city;
  @ColumnInfo
  String temp;
  @ColumnInfo
  String icon;
  @ColumnInfo
  String cond;
  @ColumnInfo
  String wind;
  @ColumnInfo
  String pressure;
  @ColumnInfo
  String humidity;
  @ColumnInfo
  String visibility;
  @ColumnInfo
  String uv;
  @ColumnInfo
  String co;
  @ColumnInfo
  String no2;
  @ColumnInfo
  String o3;
  @ColumnInfo
  String so2;
  @ColumnInfo
  String pm;
  
  public String getCity() {
    return city;
  }
  
  public void setCity(String city) {
    this.city = city;
  }
  
  public String getTemp() {
    return temp;
  }
  
  public void setTemp(String temp) {
    this.temp = temp;
  }
  
  public String getIcon() {
    return icon;
  }
  
  public void setIcon(String icon) {
    this.icon = icon;
  }
  
  public String getCond() {
    return cond;
  }
  
  public void setCond(String cond) {
    this.cond = cond;
  }
  
  public String getWind() {
    return wind;
  }
  
  public void setWind(String wind) {
    this.wind = wind;
  }
  
  public String getPressure() {
    return pressure;
  }
  
  public void setPressure(String pressure) {
    this.pressure = pressure;
  }
  
  public String getHumidity() {
    return humidity;
  }
  
  public void setHumidity(String humidity) {
    this.humidity = humidity;
  }
  
  public String getVisibility() {
    return visibility;
  }
  
  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }
  
  public String getUv() {
    return uv;
  }
  
  public void setUv(String uv) {
    this.uv = uv;
  }
  
  public String getCo() {
    return co;
  }
  
  public void setCo(String co) {
    this.co = co;
  }
  
  public String getNo2() {
    return no2;
  }
  
  public void setNo2(String no2) {
    this.no2 = no2;
  }
  
  public String getO3() {
    return o3;
  }
  
  public void setO3(String o3) {
    this.o3 = o3;
  }
  
  public String getSo2() {
    return so2;
  }
  
  public void setSo2(String so2) {
    this.so2 = so2;
  }
  
  public String getPm() {
    return pm;
  }
  
  public void setPm(String pm) {
    this.pm = pm;
  }
  
  public WeatherModel(String city, String temp, String icon, String cond, String wind, String pressure, String humidity, String visibility, String uv, String co, String no2, String o3, String so2, String pm) {
    this.city = city;
    this.temp = temp;
    this.icon = icon;
    this.cond = cond;
    this.wind = wind;
    this.pressure = pressure;
    this.humidity = humidity;
    this.visibility = visibility;
    this.uv = uv;
    this.co = co;
    this.no2 = no2;
    this.o3 = o3;
    this.so2 = so2;
    this.pm = pm;
  }
}
