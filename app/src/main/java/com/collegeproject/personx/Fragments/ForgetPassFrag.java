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
import com.collegeproject.personx.R;
import com.collegeproject.personx.Utils.UtilService;
import com.uk.tastytoasty.TastyToasty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ForgetPassFrag extends Fragment {
  private TextView goBack;
  private EditText email, newPass;
  private Button recover;
  private FrameLayout parentFrame;
  private ProgressBar progressBar;
  private LinearLayout forPass;
  private UtilService utilService;
  private Context context;
  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
  final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_forget_pass, container, false);
    context = view.getContext();
    email = view.findViewById(R.id.reset_mail);
    newPass = view.findViewById(R.id.new_password);
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
        String mail = email.getText().toString().trim(),
            pass = newPass.getText().toString().trim();
        if (!TextUtils.isEmpty(mail) && !TextUtils.isEmpty(pass))
          if (mail.matches(emailPattern)) {
            if (pass.matches(PASSWORD_PATTERN)) {
              recoverPass(mail, pass);
            } else TastyToasty.error(context, "Invalid Password Pattern").show();
          } else
            TastyToasty.error(context, "Invalid Email Pattern").show();
        else
          TastyToasty.error(context, "Email or Password Field Empty").show();
      }
    });
  }
  
  private void recoverPass(String mail, String pass) {
    progressBar.setVisibility(View.VISIBLE);
    forPass.setVisibility(View.GONE);
    final HashMap<String, String> body = new HashMap<>();
    body.put("email", mail);
    body.put("new_password", pass);
    
    String apiUrl = "https://personx.herokuapp.com/user/recover/email";
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, apiUrl,
        new JSONObject(body), new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        boolean result=false;
        try {
          result = response.getBoolean("success");
          if (result) {
            setFragment(new VerifyMailFrag());
            TastyToasty.success(getContext(), response.getString("message")).show();
          } else
            TastyToasty.error(context, response.getString("message")).show();
          progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
          e.printStackTrace();
          progressBar.setVisibility(View.GONE);
          forPass.setVisibility(View.VISIBLE);
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
            forPass.setVisibility(View.VISIBLE);
          } catch (JSONException | UnsupportedEncodingException je) {
            je.printStackTrace();
            progressBar.setVisibility(View.GONE);
            forPass.setVisibility(View.VISIBLE);
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
  }
  
  private void setFragment(Fragment logInFragment) {
    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
    fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
    fragmentTransaction.replace(parentFrame.getId(), logInFragment);
    fragmentTransaction.commit();
  }
}