package com.collegeproject.personx.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.collegeproject.personx.Adaptor.OnTaskClickListener;
import com.collegeproject.personx.Adaptor.RecyclerViewAdaptor;
import com.collegeproject.personx.Model.TaskModel;
import com.collegeproject.personx.R;
import com.collegeproject.personx.Utils.TaskUpdateFrag;
import com.collegeproject.personx.ViewModel.TaskViewModel;

public class HomePageFrag extends Fragment implements OnTaskClickListener {
  TaskViewModel taskViewModel;
  public RecyclerView recyclerView;
  public RecyclerViewAdaptor recyclerViewAdapter;
  public Context context;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home_page, container, false);
    context = view.getContext();
    recyclerView = view.findViewById(R.id.task_recycler);
    
    taskViewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        .create(TaskViewModel.class);
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    taskViewModel.getAllTask().observe(getViewLifecycleOwner(), taskModals -> {
      recyclerViewAdapter = new RecyclerViewAdaptor(taskModals, this, getActivity());
      recyclerView.setAdapter(recyclerViewAdapter);
    });
  }
  
  @Override
  public void onTaskClick(TaskModel taskModel) {
    TaskUpdateFrag taskUpdateFrag = new TaskUpdateFrag(context, taskModel);
    taskUpdateFrag.show(getActivity().getSupportFragmentManager(), taskUpdateFrag.getTag());
  }
}