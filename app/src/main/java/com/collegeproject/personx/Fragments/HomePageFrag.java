package com.collegeproject.personx.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.collegeproject.personx.Adaptor.OnTaskClickListener;
import com.collegeproject.personx.Adaptor.RecyclerViewAdaptor;
import com.collegeproject.personx.MainActivity;
import com.collegeproject.personx.Model.TaskModel;
import com.collegeproject.personx.R;
import com.collegeproject.personx.Utils.TaskUpdateFrag;
import com.collegeproject.personx.ViewModel.TaskViewModel;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uk.tastytoasty.TastyToasty;

public class HomePageFrag extends Fragment implements OnTaskClickListener {
  TaskViewModel taskViewModel;
  public RecyclerView recyclerView;
  private BottomAppBar bottomNavigationView;
  private FloatingActionButton floatingActionButton;
  public RecyclerViewAdaptor recyclerViewAdapter;
  Button personalBtn, workBtn, allBtn, wishlistBtn, birthdayBtn;
  HorizontalScrollView horizonatlScroll;
  public Context context;

  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home_page, container, false);
    context = view.getContext();
    recyclerView = view.findViewById(R.id.task_recycler);
    bottomNavigationView = getActivity().findViewById(R.id.bottomAppBar);
    floatingActionButton = getActivity().findViewById(R.id.fab);
    horizonatlScroll = getActivity().findViewById(R.id.horizontalScroll);

    
    taskViewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        .create(TaskViewModel.class);

    return view;
  }


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    personalBtn = getActivity().findViewById(R.id.personalBtn);
    workBtn = getActivity().findViewById(R.id.workBtn);
    allBtn = getActivity().findViewById(R.id.allBtn);
    wishlistBtn = getActivity().findViewById(R.id.wishlistBtn);
    birthdayBtn = getActivity().findViewById(R.id.birthdayBtn);

    //all task are catergorized
    allBtn.setOnClickListener(view15 -> getTaskByCategory("all"));
    personalBtn.setOnClickListener(view1 -> getTaskByCategory("Personal"));
    workBtn.setOnClickListener(view12 -> getTaskByCategory("Work"));
    wishlistBtn.setOnClickListener(view14 -> getTaskByCategory("Wishlist"));
    birthdayBtn.setOnClickListener(view13 -> getTaskByCategory("Birthday"));



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
    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        TastyToasty.success(getContext(), "Check");
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        TastyToasty.yellow(recyclerView.getContext(), "Item Swiped", null);
      }
    }).attachToRecyclerView(recyclerView);

    taskViewModel.getAllTask().observe(getViewLifecycleOwner(), taskModals -> {
      recyclerViewAdapter = new RecyclerViewAdaptor(taskModals, this, getActivity());
      recyclerView.setAdapter(recyclerViewAdapter);
    });
  }
  private void getTaskByCategory(String category){
    if (category.equals("all")){
      taskViewModel.getAllTask().observe(getViewLifecycleOwner(), taskModals -> {
        recyclerViewAdapter = new RecyclerViewAdaptor(taskModals, this, getActivity());
        recyclerView.setAdapter(recyclerViewAdapter);
      });
    }else {
      taskViewModel.getCategory(category).observe(getViewLifecycleOwner(), taskModals -> {
        recyclerViewAdapter = new RecyclerViewAdaptor(taskModals, this, getActivity());
        recyclerView.setAdapter(recyclerViewAdapter);
      });
    }
  }



  
  @Override
  public void onTaskClick(TaskModel taskModel) {
    TaskUpdateFrag taskUpdateFrag = new TaskUpdateFrag(context, taskModel);
    taskUpdateFrag.show(getActivity().getSupportFragmentManager(), taskUpdateFrag.getTag());
  }
}