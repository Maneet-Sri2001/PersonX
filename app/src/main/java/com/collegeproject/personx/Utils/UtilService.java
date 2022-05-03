package com.collegeproject.personx.Utils;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.collegeproject.personx.Model.TaskModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilService {
  
  public void hideKeyboard(View view, Activity activity) {
    try {
      InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void showSnackBar(View view, String message) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
  }
  
  public static int priorityColor(TaskModel taskModal) {
    int color = 0;
    if (taskModal.getPriority().equals("HIGH"))
      color = Color.argb(200, 201, 21, 23);
    else if (taskModal.getPriority().equals("MEDIUM"))
      color = Color.argb(200, 155, 179, 0);
    else
      color = Color.argb(200, 51, 181, 129);
    return color;
  }
  
  public static String formatDate(String date) {
//        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
//        simpleDateFormat.applyPattern("EEE, MMM d, ''yy");
//        return simpleDateFormat.format(date);
    return date;
  }
  
  public void selectDate(TextView textView) {
    final Calendar calendar = Calendar.getInstance();
    int mYear = calendar.get(Calendar.YEAR),
        mMonth = calendar.get(Calendar.MONTH),
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
    DatePickerDialog datePickerDialog = new DatePickerDialog(textView.getContext(), new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Date date = new Date(i, i1, i2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yy");
        textView.setText(dateFormat.format(date));
      }
    }, mYear, mMonth, mDay);
    datePickerDialog.show();
  }
}

