package com.collegeproject.personx.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.collegeproject.personx.R;
import com.collegeproject.personx.Utils.SharedPreferenceClass;
import com.collegeproject.personx.Utils.UtilService;
import com.uk.tastytoasty.TastyToasty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SignUpUserFrag extends Fragment {
  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
  final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
  
  EditText fullName, registerMail, password, confirmPwd;
  AppCompatButton registerBtn;
  TextView registerLogin;
  ProgressBar progressBar;
  CheckBox checkBox;
  private LinearLayout registerView;
  private FrameLayout parentFrame;
  private UtilService utilService;
  private SharedPreferenceClass sharedPreferenceClass;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_sign_up_user, container, false);
    fullName = view.findViewById(R.id.fullName);
    registerMail = view.findViewById(R.id.registerMail);
    password = view.findViewById(R.id.password);
    confirmPwd = view.findViewById(R.id.confirmPwd);
    registerBtn = view.findViewById(R.id.register);
    registerLogin = view.findViewById(R.id.register_login);
    progressBar = view.findViewById(R.id.register_progress_bar);
    checkBox = view.findViewById(R.id.checkbox);
    registerView = view.findViewById(R.id.register_view);
    parentFrame = getActivity().findViewById(R.id.frame_layout_register);
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    utilService = new UtilService();
    sharedPreferenceClass = new SharedPreferenceClass(this.getContext());
    
    registerLogin.setOnClickListener(view1 -> setFragment(new LogInUserFrag()));
    registerBtn.setOnClickListener(view12 -> {
      utilService.hideKeyboard(view12, getActivity());
      checkEmailPass();
    });
    
    registerMail.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      
      }
      
      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        checkInput();
      }
      
      @Override
      public void afterTextChanged(Editable editable) {
      
      }
    });
    password.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      
      }
      
      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        checkInput();
      }
      
      @Override
      public void afterTextChanged(Editable editable) {
      
      }
    });
  }
  
  private void checkEmailPass() {
    //pregess visi
    progressBar.setVisibility(View.VISIBLE);
    registerView.setVisibility(View.GONE);
    String name = fullName.getText().toString().trim(),
        email = registerMail.getText().toString().trim(),
        pass = password.getText().toString().trim(),
        confPass = confirmPwd.getText().toString().trim();
    
    if (checkBox.isChecked()) {
      if (email.matches(emailPattern)) {
        if (pass.matches(PASSWORD_PATTERN)) {
          if (pass.contentEquals(confPass)) {
            final HashMap<String, String> body = new HashMap<>();
            body.put("name", name);
            body.put("email", email);
            body.put("password", pass);
            
            String apiUrl = "https://personx.herokuapp.com/user/signup";
            
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                apiUrl, new JSONObject(body), response -> {
              try {
                if (response.getBoolean("success")) {
                  setFragment(new LogInUserFrag());
                }
                TastyToasty.indigo(getContext(), response.getString("message"), null).show();
              } catch (Exception e) {
                e.printStackTrace();
                progressBar.setVisibility(View.GONE);
              }
            }, error -> {
              NetworkResponse response = error.networkResponse;
              if (error instanceof ServerError && response != null) {
                try {
                  String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                  JSONObject obj = new JSONObject(res);
                  TastyToasty.error(getContext(), obj.getString("message")).show();
                  progressBar.setVisibility(View.GONE);
                } catch (JSONException | UnsupportedEncodingException je) {
                  je.printStackTrace();
                  progressBar.setVisibility(View.GONE);
                }
              }
            }) {
              @Override
              public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return body;
              }
            };
            
            // set retry policy
            int socketTime = 5000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);
            
            // request add
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            requestQueue.add(jsonObjectRequest);
          } else {
            progressBar.setVisibility(View.GONE);
            registerView.setVisibility(View.VISIBLE);
            TastyToasty.yellow(getContext(), "Password Does not matched", null).show();
          }
        } else {
          progressBar.setVisibility(View.GONE);
          registerView.setVisibility(View.VISIBLE);
          TastyToasty.yellow(getContext(), "Please Enter Valid Password", null).show();
        }
      } else {
        progressBar.setVisibility(View.GONE);
        registerView.setVisibility(View.VISIBLE);
        TastyToasty.yellow(getContext(), "Please Enter Valid Email", null).show();
      }
    } else {
      progressBar.setVisibility(View.GONE);
      registerView.setVisibility(View.VISIBLE);
      TastyToasty.yellow(getContext(), "Please Agree TnC.", null).show();
    }
  }
  
  private void checkInput() {
    if (!TextUtils.isEmpty(registerMail.getText())) {
      if (!TextUtils.isEmpty(password.getText()))
        registerBtn.setEnabled(true);
    } else {
      registerBtn.setEnabled(false);
    }
  }
  
  private void setFragment(Fragment signUpFragment) {
    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
    fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
    fragmentTransaction.replace(parentFrame.getId(), signUpFragment);
    fragmentTransaction.commit();
  }
}