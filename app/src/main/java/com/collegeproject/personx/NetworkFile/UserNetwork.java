package com.collegeproject.personx.NetworkFile;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.collegeproject.personx.Model.TaskModel;
import com.collegeproject.personx.Model.UserModel;
import com.collegeproject.personx.ViewModel.TaskViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserNetwork {
  public static void getUserData(String userID, Context context) throws Exception {
    try {
      String apiUrl = "https://personx.herokuapp.com/user/" + userID.trim();
      StringRequest request = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
          try {
            JSONObject userObj = new JSONObject(response).getJSONObject("user");
            String name = userObj.get("name").toString(),
                email = userObj.get("email").toString(),
                //createdAt = userObj.get("createdAt").toString(),
                img = userObj.get("userImg").toString();
            
            UserModel userModal = new UserModel(name, email, img, "");
            new Thread(new Runnable() {
              @Override
              public void run() {
                //userDAO.registerUser(userModal);
              }
            });
            JSONArray taskArray = userObj.getJSONArray("TaskCollection");
            for (int i = 0; i < taskArray.length(); i++) {
              JSONObject task = taskArray.getJSONObject(i);
              JSONObject taskDetail = task.getJSONObject("taskDetail");
              String taskDes = task.get("taskDes").toString(),
                  category = task.get("category").toString(),
                  tags = task.get("tags").toString(),
                  deadline = task.get("deadline").toString(),
                  note = task.get("note").toString(),
                  priority = task.get("priority").toString(),
                  time = taskDetail.get("time").toString(),
                  reminder = taskDetail.get("reminder").toString(),
                  reminderType = taskDetail.get("reminderType").toString(),
                  status = task.get("status").toString(),
                  date = taskDetail.get("date").toString(),
                  _id = task.get("_id").toString();
              TaskModel taskModal = new TaskModel(_id, taskDes, status, category, tags,
                  deadline, note, priority, date, time, reminder, reminderType);
              TaskViewModel.insert(taskModal);
            }
          } catch (JSONException e) {
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
    } catch (Exception e) {
      Toast.makeText(context.getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
  }
}
