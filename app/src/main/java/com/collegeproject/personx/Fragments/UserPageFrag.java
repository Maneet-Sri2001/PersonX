package com.collegeproject.personx.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.collegeproject.personx.R;

public class UserPageFrag extends Fragment {
  private TextView goBack;
  private FrameLayout parentFrame;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_user_page, container, false);
    goBack = view.findViewById(R.id.login_acc_for_pass);
    parentFrame = getActivity().findViewById(R.id.frame_layout_register);
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    goBack.setOnClickListener(v -> setFragment(new LogInUserFrag()));
  }
  
  private void setFragment(Fragment logInFragment) {
    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
    fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
    fragmentTransaction.replace(parentFrame.getId(), logInFragment);
    fragmentTransaction.commit();
  }
}