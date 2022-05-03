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
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomePageFrag extends Fragment implements OnTaskClickListener {
  TaskViewModel taskViewModel;
  public RecyclerView recyclerView;
  private BottomAppBar bottomNavigationView;
  private FloatingActionButton floatingActionButton;
  public RecyclerViewAdaptor recyclerViewAdapter;
  public Context context;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home_page, container, false);
    context = view.getContext();
    recyclerView = view.findViewById(R.id.task_recycler);
    bottomNavigationView = getActivity().findViewById(R.id.bottomAppBar);
    floatingActionButton = getActivity().findViewById(R.id.fab);
    
    taskViewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        .create(TaskViewModel.class);
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
      }
      
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0 && bottomNavigationView.isShown()) {
          bottomNavigationView.setVisibility(View.GONE);
          floatingActionButton.setVisibility(View.GONE);
        } else if (dy < 0) {
          bottomNavigationView.setVisibility(View.VISIBLE);
          floatingActionButton.setVisibility(View.VISIBLE);
        }
      }
    });
    
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