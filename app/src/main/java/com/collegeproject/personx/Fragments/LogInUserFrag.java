package com.collegeproject.personx.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.collegeproject.personx.MainActivity;
import com.collegeproject.personx.R;
import com.collegeproject.personx.Utils.SharedPreferenceClass;
import com.collegeproject.personx.Utils.UtilService;
import com.uk.tastytoasty.TastyToasty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LogInUserFrag extends Fragment {
  
  EditText loginMail, loginPwd;
  AppCompatButton loginBtn;
  TextView createAcct, forgotPassword, verifyMail;
  ProgressBar progressBar;
  private LinearLayout loginView;
  private FrameLayout parentFrame;
  private UtilService utilService;
  private SharedPreferenceClass sharedPreferenceClass;
  
  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
  final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_log_in_user, container, false);
    loginMail = view.findViewById(R.id.loginMail);
    loginPwd = view.findViewById(R.id.loginPwd);
    loginBtn = view.findViewById(R.id.loginBtn);
    createAcct = view.findViewById(R.id.create);
    verifyMail = view.findViewById(R.id.verify_mail);
    forgotPassword = view.findViewById(R.id.forgot_password);
    progressBar = view.findViewById(R.id.login_progress_bar);
    loginView = view.findViewById(R.id.login_view);
    parentFrame = getActivity().findViewById(R.id.frame_layout_register);
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    utilService = new UtilService();
    sharedPreferenceClass = new SharedPreferenceClass(this.getContext());
    
    createAcct.setOnClickListener(view1 -> setFragment(new SignUpUserFrag()));
    forgotPassword.setOnClickListener(view12 -> setFragment(new ForgetPassFrag()));
    verifyMail.setOnClickListener(view13 -> setFragment(new VerifyMailFrag()));
    loginBtn.setOnClickListener(view14 -> {
      utilService.hideKeyboard(view14, getActivity());
      loginUser(loginMail.getText().toString().trim(),
          loginPwd.getText().toString().trim());
    });
    loginMail.addTextChangedListener(new TextWatcher() {
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
    loginPwd.addTextChangedListener(new TextWatcher() {
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
  private void loginUser(String email, String password) {
    if (email.matches(emailPattern)) {
      if (password.matches(PASSWORD_PATTERN)) {
        progressBar.setVisibility(View.VISIBLE);
        loginView.setVisibility(View.GONE);
        final HashMap<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        
        String apiUrl = "https://personx.herokuapp.com/user/login";
        
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
            apiUrl, new JSONObject(body), new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            try {
              if (response.getBoolean("success")) {
                String token = response.getString("token");
                TastyToasty.success(getContext(), response.getString("message")).show();
                sharedPreferenceClass.setValueString("token", token);
                startActivity(new Intent(getActivity(), MainActivity.class));
                ///getActivity().finish();
              }
              progressBar.setVisibility(View.GONE);
            } catch (Exception e) {
              e.printStackTrace();
              progressBar.setVisibility(View.GONE);
              loginView.setVisibility(View.VISIBLE);
            }
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            NetworkResponse response = error.networkResponse;
            if (error instanceof ServerError && response != null) {
              try {
                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                JSONObject obj = new JSONObject(res);
                TastyToasty.error(getContext(), obj.getString("message")).show();
                progressBar.setVisibility(View.GONE);
                loginView.setVisibility(View.VISIBLE);
              } catch (JSONException | UnsupportedEncodingException je) {
                je.printStackTrace();
                progressBar.setVisibility(View.GONE);
                loginView.setVisibility(View.VISIBLE);
              }
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
        
        //et Retry Policy
        int socketTime = 3000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTime,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(retryPolicy);
        
        //Adding Request
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
      } else {
        TastyToasty.violet(getContext(), "Please Enter Valid Password", null).show();
      }
    } else {
      TastyToasty.violet(getContext(), "Please Enter Valid Email", null).show();
    }
  }
  
  private void checkInput() {
    if (!TextUtils.isEmpty(loginMail.getText())) {
      if (!TextUtils.isEmpty(loginPwd.getText()))
        loginBtn.setEnabled(true);
    } else {
      loginBtn.setEnabled(false);
    }
  }
  
  private void setFragment(Fragment signUpFragment) {
    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
    fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
    fragmentTransaction.replace(parentFrame.getId(), signUpFragment);
    fragmentTransaction.commit();
  }
}