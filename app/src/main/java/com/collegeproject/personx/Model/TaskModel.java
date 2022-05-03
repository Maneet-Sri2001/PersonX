package com.collegeproject.personx.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "taskTable")
public class TaskModel {
  @PrimaryKey(autoGenerate = false)
  @NonNull
  @ColumnInfo(name = "_id")
  String _id;
  @ColumnInfo(name = "taskDes")
  String taskDes;
  @ColumnInfo(name = "status")
  String status;
  @ColumnInfo(name = "category")
  String category;
  @ColumnInfo(name = "tags")
  String tags;
  @ColumnInfo(name = "deadline")
  String deadline;
  @ColumnInfo(name = "note")
  String note;
  @ColumnInfo(name = "priority")
  String priority;
  @ColumnInfo(name = "date")
  String date;
  @ColumnInfo(name = "time")
  String time;
  @ColumnInfo(name = "reminder")
  String reminder;
  @ColumnInfo(name = "reminderType")
  String reminderType;
  
  public TaskModel(@NonNull String _id, String taskDes, String status, String category, String tags, String deadline, String note, String priority, String date, String time, String reminder, String reminderType) {
    this._id = _id;
    this.taskDes = taskDes;
    this.status = status;
    this.category = category;
    this.tags = tags;
    this.deadline = deadline;
    this.note = note;
    this.priority = priority;
    this.date = date;
    this.time = time;
    this.reminder = reminder;
    this.reminderType = reminderType;
  }
  
  @NonNull
  public String get_id() {
    return _id;
  }
  
  public void set_id(@NonNull String _id) {
    this._id = _id;
  }
  
  public String getTaskDes() {
    return taskDes;
  }
  
  public void setTaskDes(String taskDes) {
    this.taskDes = taskDes;
  }
  
  public String getStatus() {
    return status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getCategory() {
    return category;
  }
  
  public void setCategory(String category) {
    this.category = category;
  }
  
  public String getTags() {
    return tags;
  }
  
  public void setTags(String tags) {
    this.tags = tags;
  }
  
  public String getDeadline() {
    return deadline;
  }
  
  public void setDeadline(String deadline) {
    this.deadline = deadline;
  }
  
  public String getNote() {
    return note;
  }
  
  public void setNote(String note) {
    this.note = note;
  }
  
  public String getPriority() {
    return priority;
  }
  
  public void setPriority(String priority) {
    this.priority = priority;
  }
  
  public String getDate() {
    return date;
  }
  
  public void setDate(String date) {
    this.date = date;
  }
  
  public String getTime() {
    return time;
  }
  
  public void setTime(String time) {
    this.time = time;
  }
  
  public String getReminder() {
    return reminder;
  }
  
  public void setReminder(String reminder) {
    this.reminder = reminder;
  }
  
  public String getReminderType() {
    return reminderType;
  }
  
  public void setReminderType(String reminderType) {
    this.reminderType = reminderType;
  }
  
  @NonNull
  @Override
  public String toString() {
    return "TaskModal{" +
        "_id='" + _id + '\'' +
        ", taskDes='" + taskDes + '\'' +
        ", status='" + status + '\'' +
        ", category='" + category + '\'' +
        ", tags='" + tags + '\'' +
        ", deadline='" + deadline + '\'' +
        ", note='" + note + '\'' +
        ", priority='" + priority + '\'' +
        ", date='" + date + '\'' +
        ", time='" + time + '\'' +
        ", reminder='" + reminder + '\'' +
        ", reminderType='" + reminderType + '\'' +
        '}';
  }
}
