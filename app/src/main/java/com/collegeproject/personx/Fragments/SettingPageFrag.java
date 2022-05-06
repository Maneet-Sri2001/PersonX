package com.collegeproject.personx.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.collegeproject.personx.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uk.tastytoasty.TastyToasty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SettingPageFrag extends Fragment {
  
  Dialog myDialog;
  TextView datetimeF, firstdayweek, presetday, sendF, reportB, Cu, AppD, Privacy, Tnc;
  Calendar calendar = Calendar.getInstance();
  DatePickerDialog.OnDateSetListener setListener;
  final int year = calendar.get(Calendar.YEAR);
  final int month = calendar.get(Calendar.MONTH);
  final int day = calendar.get(Calendar.DAY_OF_MONTH);
  int t1Hour, t1Minute;
  private BottomAppBar bottomNavigationView;
  private FloatingActionButton floatingActionButton;
  private ScrollView scrollView;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_setting_page, container, false);
    myDialog = new Dialog(getContext());
    datetimeF = view.findViewById(R.id.datentimeformat);
    firstdayweek = view.findViewById(R.id.dayweek);
    presetday = view.findViewById(R.id.presetday);
    sendF = view.findViewById(R.id.sendfeedback);
    reportB = view.findViewById(R.id.reportabug);
    Cu = view.findViewById(R.id.idcontactus);
    AppD = view.findViewById(R.id.idappdetails);
    Privacy = view.findViewById(R.id.idprivacy);
    Tnc = view.findViewById(R.id.idtnc);
    bottomNavigationView = getActivity().findViewById(R.id.bottomAppBar);
    floatingActionButton = getActivity().findViewById(R.id.fab);
    scrollView = view.findViewById(R.id.settinglayout);
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
//    scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//      @Override
//      public void onScrollChanged() {
//        int scrollY = scrollView.getScrollY(); // For ScrollView
//        if (scrollY > 200 && bottomNavigationView.isShown()) {
//          bottomNavigationView.setVisibility(View.GONE);
//          floatingActionButton.setVisibility(View.GONE);
//        } else if (scrollY < 200) {
//          bottomNavigationView.setVisibility(View.VISIBLE);
//          floatingActionButton.setVisibility(View.VISIBLE);
//        }
//      }
//    });
    datetimeF.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // Initialize variables
        TextView txtclose, datetv, timetv;
        //Assign Variable
        myDialog.setContentView(R.layout.pop_data_time_formats);
        datetv = myDialog.findViewById(R.id.tvdate);
        timetv = myDialog.findViewById(R.id.tvtime);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        datetv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Toast.makeText(SettingPageFrag.this.getContext(), "Hello", Toast.LENGTH_SHORT).show();
            DatePickerDialog datePickerDialog = new DatePickerDialog(SettingPageFrag.this.getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth
                , setListener, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
            
          }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
          @Override
          public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            month = month + 1;
            String date = day + "/" + month + "/" + year;
            datetv.setText(date);
            
          }
        };
        timetv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            // Initialize time picker dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                SettingPageFrag.this.getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                  @Override
                  public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                    //Initialize hour and minute
                    t1Hour = hourOfDay;
                    t1Minute = minute;
                    
                    //Store hour and minute in String
                    String time = t1Hour + ":" + t1Minute;
                    
                    //Initialize 24 hours time format
                    SimpleDateFormat f24Hours = new SimpleDateFormat(
                        "HH:mm"
                    );
                    try {
                      Date date = f24Hours.parse(time);
                      //Initialize 12 hours time format
                      SimpleDateFormat f12Hours = new SimpleDateFormat(
                          "hh:mm aa"
                      );
                      //Set selected time on text view
                      timetv.setText(f12Hours.format(date));
                    } catch (ParseException e) {
                      e.printStackTrace();
                    }
                    
                  }
                }, 12, 0, false
            );
            // Set Transparent background
            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // Displayed previous selected time
            timePickerDialog.updateTime(t1Hour, t1Minute);
            //Show dialog
            timePickerDialog.show();
          }
        });
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
      }
    });
    firstdayweek.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        TextView txtclose;
        myDialog.setContentView(R.layout.pop_firstdayweek);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        
      }
    });
    presetday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        TextView txtclose;
        myDialog.setContentView(R.layout.pop_allday);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        
      }
    });
    sendF.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        TextView txtclose;
        myDialog.setContentView(R.layout.pop_feedback);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        
      }
    });
    reportB.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        TextView txtclose;
        myDialog.setContentView(R.layout.pop_bug);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        
      }
    });
    Cu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        TextView txtclose;
        myDialog.setContentView(R.layout.pop_feedback);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        
      }
    });
    AppD.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        TextView txtclose;
        myDialog.setContentView(R.layout.pop_bug);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        
      }
    });
    Privacy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        TextView txtclose;
        myDialog.setContentView(R.layout.pop_feedback);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        
      }
    });
    Tnc.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        TextView txtclose;
        myDialog.setContentView(R.layout.pop_bug);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        
      }
    });
  }
}