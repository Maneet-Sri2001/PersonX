package com.collegeproject.personx.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.collegeproject.personx.AlarmNotification;
import com.collegeproject.personx.Model.TaskModel;
import com.collegeproject.personx.NetworkFile.TaskNetwork;
import com.collegeproject.personx.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.uk.tastytoasty.TastyToasty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TaskCreateFrag extends BottomSheetDialogFragment {
  private static UtilService utilService;
  private static String userId;
  private Context context;
  private EditText taskDes, taskNote;
  private TextView taskDate, taskDeadline, taskCategory, taskTag;
  private ImageView category, tags, notes, taskRemind, taskRepeat, submit, taskPriority;
  private SharedPreferenceClass sharedPreferenceClass;
  private TaskNetwork taskNetwork;
  
  private String taskValue = "Low", taskTime = "";
  
  
  public TaskCreateFrag() {
    // Required empty public constructor
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.task_bottom_sheet, container, false);
    utilService = new UtilService();
    sharedPreferenceClass = new SharedPreferenceClass(getActivity().getApplicationContext());
    context = getContext();
    userId = sharedPreferenceClass.getValueString("token");
    taskNetwork = new TaskNetwork(userId, context, getView());
    
    taskDes = view.findViewById(R.id.task_des);
    taskNote = view.findViewById(R.id.task_note);
    taskCategory = view.findViewById(R.id.task_cat);
    taskDate = view.findViewById(R.id.task_date);
    taskTag = view.findViewById(R.id.task_tag);
    taskDeadline = view.findViewById(R.id.task_deadline);
    taskPriority = view.findViewById(R.id.task_priority);
    taskRepeat = view.findViewById(R.id.task_repeat);
    taskRemind = view.findViewById(R.id.task_remind);
    
    category = view.findViewById(R.id.task_category);
    tags = view.findViewById(R.id.tag);
    notes = view.findViewById(R.id.task_show_note);
    submit = view.findViewById(R.id.task_save);
    
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    taskRemind.setOnClickListener(new View.OnClickListener() {
      int button01pos = 1;
      
      @Override
      public void onClick(View view) {
        Animation shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.ring);
        view.startAnimation(shake);
        if (button01pos == 0) {
          taskRemind.setImageResource(R.drawable.ic_alert_off);
          button01pos = 1;
        } else if (button01pos == 1) {
          taskRemind.setImageResource(R.drawable.ic_alert_on);
          button01pos = 0;
        }
      }
    });
    taskDate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        utilService.selectDate(taskDate);
      }
    });
    taskDeadline.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        utilService.selectDate(taskDeadline);
      }
    });
    category.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.category_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem menuItem) {
            taskCategory.setVisibility(View.VISIBLE);
            tags.setVisibility(View.VISIBLE);
            taskCategory.setText(menuItem.getTitle());
            taskCategory.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_category, 0, 0, 0);
            return false;
          }
        });
        popupMenu.show();
      }
    });
    taskPriority.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.priority_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem menuItem) {
            taskPriority.setImageDrawable(menuItem.getIcon());
            taskValue = menuItem.getTitle().toString().toUpperCase(Locale.ROOT);
            return false;
          }
        });
        popupMenu.show();
      }
    });
    notes.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        taskNote.setVisibility(View.VISIBLE);
      }
    });
    tags.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        taskTag.setVisibility(View.VISIBLE);
      }
    });
    taskNote.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      
      }
      
      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        NoteView();
      }
      
      @Override
      public void afterTextChanged(Editable editable) {
      
      }
    });
    taskTag.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      
      }
      
      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        tagView();
      }
      
      @Override
      public void afterTextChanged(Editable editable) {
      
      }
    });
    taskRepeat.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.circle_animation));
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
          @Override
          public void onTimeSet(TimePicker timePicker, int i, int i1) {
            taskTime = i + ":" + i1;
          }
        }, hour, minute, false);
        timePickerDialog.show();
      }
    });
    submit.setOnClickListener(view1 -> {
      String task = taskDes.getText().toString().trim();
      if (!TextUtils.isEmpty(task) && taskDate != null) {
        TaskModel myTask = new TaskModel("000", task, "Uncompleted", taskCategory.getText().toString(), taskTag.getText().toString(),
            taskDate.getText().toString(), taskNote.getText().toString(), taskValue, taskDate.getText().toString(),
            taskDate.getText().toString(), taskRepeat.toString(), "type");
        taskNetwork.addTask(myTask);
        setAlarm();
        dismiss();
      } else {
        Snackbar.make(getView(), "Empty Field", Snackbar.LENGTH_LONG)
            .show();
      }
    });
  }
  
  public void NoteView() {
    if (taskNote.getText().toString().trim().length() > 0)
      notes.setVisibility(View.GONE);
    else
      notes.setVisibility(View.VISIBLE);
  }
  
  public void tagView() {
    if (taskTag.getText().toString().trim().length() > 0) {
      tags.setVisibility(View.GONE);
      category.setVisibility(View.GONE);
    } else {
      tags.setVisibility(View.VISIBLE);
      category.setVisibility(View.VISIBLE);
    }
  }
  
  private void setAlarm() {
    AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
    
    Intent intent = new Intent(getActivity().getApplicationContext(), AlarmNotification.class);
    intent.putExtra("event", taskDes.getText().toString());
    intent.putExtra("time", taskDate.getText().toString());
    intent.putExtra("date", taskTime);
    intent.putExtra("priority", taskValue);
    intent.putExtra("category", taskCategory.getText().toString());
    
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
    String dateandtime = taskDate.getText().toString() + " " + taskTime;
    DateFormat formatter = new SimpleDateFormat("EEE, MMM d, yy hh:mm");
    try {
      Date date1 = formatter.parse(dateandtime);
      manager.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}