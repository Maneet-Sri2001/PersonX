package com.collegeproject.personx.NetworkFile;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.collegeproject.personx.Model.TaskModel;
import com.collegeproject.personx.Utils.UtilService;
import com.collegeproject.personx.ViewModel.TaskViewModel;
import com.uk.tastytoasty.TastyToasty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TaskNetwork {
  private final String userId;
  private final Context context;
  private final View view;
  private UtilService utilService = new UtilService();
  
  public TaskNetwork(String userId, Context context, View view) {
    this.userId = userId;
    this.context = context;
    this.view = view;
  }
  
  //First Add Task in Task DBCollection
  public void addTask(TaskModel taskModal) {
    String url = "https://personx.herokuapp.com/task";
    
    HashMap<String, String> body = new HashMap<>();
    body.put("date", taskModal.getDate());
    body.put("time", taskModal.getTime());
    body.put("reminder", taskModal.getReminder());
    body.put("reminderType", taskModal.getReminderType());
    body.put("taskDes", taskModal.getTaskDes());
    body.put("category", taskModal.getCategory());
    body.put("tags", taskModal.getTags());
    body.put("deadline", taskModal.getDeadline());
    body.put("priority", taskModal.getPriority());
    body.put("note", taskModal.getNote());
    
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
        url, new JSONObject(body), new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        try {
          if (response.getBoolean("success")) {
            String taskId = response.getString("message");
            taskModal.set_id(taskId);
            addUserTask(taskId);
            TaskViewModel.insert(taskModal);
          }
        } catch (Exception e) {
          e.printStackTrace();
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
            Toast.makeText(context.getApplicationContext(), "Error : " + obj.getString("message"), Toast.LENGTH_SHORT).show();
          } catch (JSONException | UnsupportedEncodingException je) {
            je.printStackTrace();
          }
        }
      }
    }) {
      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", userId);
        return headers;
      }
    };
    
    // set retry policy
    int socketTime = 3000;
    RetryPolicy policy = new DefaultRetryPolicy(socketTime,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    jsonObjectRequest.setRetryPolicy(policy);
    
    // request add
    RequestQueue requestQueue = Volley.newRequestQueue(context);
    requestQueue.add(jsonObjectRequest);
  }
  
  //Add Task ID to User DBCollection
  public void addUserTask(String id) throws Exception {
    String url = "https://personx.herokuapp.com/user/" + id + "/" + userId;
    final String[] msg = {""};
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null
        , new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        try {
          if (response.getBoolean("success")) {
            TastyToasty.star(context, response.get("message").toString()).show();
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Toast.makeText(context.getApplicationContext(), "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
      }
    });
    
    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    
    RequestQueue requestQueue = Volley.newRequestQueue(context);
    requestQueue.add(jsonObjectRequest);
    UserNetwork.getUserData(userId, context);
  }
  
  //Set Task as Completed
  public void completeTask(TaskModel taskModal) {
    String url = "https://personx.herokuapp.com/task/" + taskModal.get_id();
    final Boolean[] res = {false};
    StringRequest request = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        try {
          res[0] = new JSONObject(response).getBoolean("success");
          if (res[0]) {
            String taskId = new JSONObject(response).getString("taskId");
            taskModal.set_id(taskId);
            TaskViewModel.insert(taskModal);
          }
          String message = new JSONObject(response).getString("message");
          utilService.showSnackBar(view, message);
        } catch (Exception e) {
          Toast.makeText(context.getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
          e.printStackTrace();
        }
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Toast.makeText(context.getApplicationContext(), "Error : " + error, Toast.LENGTH_SHORT).show();
      }
    });
    RequestQueue requestQueue = Volley.newRequestQueue(context);
    requestQueue.add(request);
  }
  
  //Update Existing Task
  public void updateTask(TaskModel taskModal) {
    String url = "https://personx.herokuapp.com/task/" + taskModal.get_id();
    Toast.makeText(context, taskModal.getTaskDes(), Toast.LENGTH_SHORT).show();
    HashMap<String, String> body = new HashMap<>();
    body.put("date", taskModal.getDate());
    body.put("time", taskModal.getTime());
    body.put("reminder", taskModal.getReminder());
    body.put("reminderType", taskModal.getReminderType());
    body.put("taskDes", taskModal.getTaskDes());
    body.put("category", taskModal.getCategory());
    body.put("tags", taskModal.getTags());
    body.put("deadline", taskModal.getDeadline());
    body.put("priority", taskModal.getPriority());
    body.put("note", taskModal.getNote());
    
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH,
        url, new JSONObject(body), new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        try {
          if (response.getBoolean("success")) {
            TaskViewModel.update(taskModal);
          }
          TastyToasty.star(context, response.getString("message")).show();
        } catch (Exception e) {
          e.printStackTrace();
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
            TastyToasty.error(context.getApplicationContext(), "Error : " + obj.getString("message")).show();
          } catch (JSONException | UnsupportedEncodingException je) {
            je.printStackTrace();
          }
        }
      }
    }) {
      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", userId);
        return headers;
      }
    };
    
    // set retry policy
    int socketTime = 3000;
    RetryPolicy policy = new DefaultRetryPolicy(socketTime,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    jsonObjectRequest.setRetryPolicy(policy);
    
    // request add
    RequestQueue requestQueue = Volley.newRequestQueue(context);
    requestQueue.add(jsonObjectRequest);
  }
}
