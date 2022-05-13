package com.collegeproject.personx.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.collegeproject.personx.R;
import com.collegeproject.personx.Utils.UtilService;
import com.uk.tastytoasty.TastyToasty;

public class ForgetPassFrag extends Fragment {
  private TextView goBack;
  private EditText email;
  private Button recover;
  private FrameLayout parentFrame;
  private ProgressBar progressBar;
  private LinearLayout forPass;
  private UtilService utilService;
  private Context context;
  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_forget_pass, container, false);
    context = view.getContext();
    email = view.findViewById(R.id.reset_mail);
    recover = view.findViewById(R.id.for_pass_btn);
    goBack = view.findViewById(R.id.login_acc_for_pass);
    forPass = view.findViewById(R.id.for_pass_layout);
    progressBar = view.findViewById(R.id.forpass_progress_bar);
    parentFrame = getActivity().findViewById(R.id.frame_layout_register);
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    utilService = new UtilService();
    goBack.setOnClickListener(v -> setFragment(new LogInUserFrag()));
    recover.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String mail = email.getText().toString();
        if (!TextUtils.isEmpty(mail))
          if (mail.matches(emailPattern)) {
          
          } else
            TastyToasty.error(context, "Invalid Email Pattern").show();
        else
          TastyToasty.error(context, "Email Field Empty").show();
      }
    });
  }
  
  private void setFragment(Fragment logInFragment) {
    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
    fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
    fragmentTransaction.replace(parentFrame.getId(), logInFragment);
    fragmentTransaction.commit();
  }
}