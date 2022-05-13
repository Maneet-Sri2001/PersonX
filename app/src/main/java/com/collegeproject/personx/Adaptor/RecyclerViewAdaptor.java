package com.collegeproject.personx.Adaptor;

import android.animation.Animator;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.collegeproject.personx.Model.TaskModel;
import com.collegeproject.personx.NetworkFile.TaskNetwork;
import com.collegeproject.personx.R;
import com.collegeproject.personx.Utils.UtilService;
import com.google.android.material.chip.Chip;

import java.util.List;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder> {
  private final List<TaskModel> taskList;
  private final OnTaskClickListener onTaskClickListener;
  Activity activity;
  
  public RecyclerViewAdaptor(List<TaskModel> taskList, OnTaskClickListener onTaskClickListener, Activity activity) {
    this.taskList = taskList;
    this.onTaskClickListener = onTaskClickListener;
    this.activity = activity;
  }
  
  @NonNull
  @Override
  public RecyclerViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_task, parent, false);
    return new ViewHolder(view);
  }
  
  @Override
  public void onBindViewHolder(@NonNull RecyclerViewAdaptor.ViewHolder holder, int position) {
    TaskModel task = taskList.get(position);
    String formatted = UtilService.formatDate(task.getDate());
    ColorStateList colorStateList = new ColorStateList(new int[][]{
        new int[]{-android.R.attr.state_enabled},
        new int[]{android.R.attr.state_enabled}
    },
        new int[]{
            Color.LTGRAY,  //disabled
            UtilService.priorityColor(task)
        });
    
    holder.task.setText(task.getTaskDes());
    holder.todayChip.setText(formatted);
    holder.todayChip.setTextColor(UtilService.priorityColor(task));
    holder.todayChip.setChipIconTint(colorStateList);
    if (task.getStatus().equals("Completed"))
      holder.delete.setImageResource(R.drawable.ic_task_done);
    else {
      holder.delete.setOnClickListener(new View.OnClickListener() {
        int button01pos = 0;
        
        @Override
        public void onClick(View view) {
          if (button01pos == 0) {
            holder.delete.setImageResource(R.drawable.ic_task_done);
            TaskNetwork.completeTask(activity.getApplicationContext(), task);
            button01pos = 1;
          } else if (button01pos == 1) {
            holder.delete.setImageResource(R.drawable.ic_circle);
            button01pos = 0;
          }
          int cx = view.getMeasuredWidth() / 2;
          int cy = view.getMeasuredHeight() / 2;
          int finalRadius = Math.max(view.getWidth(), view.getHeight()) / 2;
          Animator anim =
              ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
          view.setVisibility(View.VISIBLE);
          anim.start();
        }
      });
    }
  }
  
  @Override
  public int getItemCount() {
    return taskList.size();
  }
  
  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView delete;
    public AppCompatTextView task;
    public Chip todayChip;
    OnTaskClickListener onToDoClickListener;
    
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      delete = itemView.findViewById(R.id.todo_radio_button);
      task = itemView.findViewById(R.id.todo_row_todo);
      todayChip = itemView.findViewById(R.id.todo_row_chip);
      this.onToDoClickListener = onTaskClickListener;
      itemView.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
      TaskModel currTask = taskList.get(getAdapterPosition());
      onToDoClickListener.onTaskClick(currTask);
    }
  }
}
