package com.collegeproject.personx.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.collegeproject.personx.NetworkFile.UserNetwork;
import com.collegeproject.personx.R;
import com.uk.tastytoasty.TastyToasty;

public class VerifyMailFrag extends Fragment {
  
  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
  private EditText veriMail;
  private Button sendVeri;
  private Context context;
  private TextView goBack;
  private FrameLayout parentFrame;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_verify_mail, container, false);
    context = view.getContext();
    veriMail = view.findViewById(R.id.verify_mail);
    sendVeri = view.findViewById(R.id.send_verify);
    goBack = view.findViewById(R.id.login_acc_for_verify);
    parentFrame = getActivity().findViewById(R.id.frame_layout_register);
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    goBack.setOnClickListener(v -> setFragment(new LogInUserFrag()));
    sendVeri.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (veriMail.getText().toString().matches(emailPattern)) {
          UserNetwork.verifyMail(veriMail.getText().toString(), context);
          sendVeri.setEnabled(false);
        } else
          TastyToasty.indigo(context, "Invalid Email Format", null).show();
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