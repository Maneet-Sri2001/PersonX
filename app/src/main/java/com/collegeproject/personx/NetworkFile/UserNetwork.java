package com.collegeproject.personx.NetworkFile;

import android.content.Context;
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
import com.collegeproject.personx.Model.NewsModel;
import com.collegeproject.personx.Model.TaskModel;
import com.collegeproject.personx.Model.UserModel;
import com.collegeproject.personx.Model.WeatherModel;
import com.collegeproject.personx.R;
import com.collegeproject.personx.ViewModel.NewsViewModel;
import com.collegeproject.personx.ViewModel.TaskViewModel;
import com.collegeproject.personx.ViewModel.UserViewModel;
import com.collegeproject.personx.ViewModel.WeatherViewModel;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import com.uk.tastytoasty.TastyToasty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UserNetwork {
  
  static NewsApiClient newsApiClient = new NewsApiClient("47cd26ff5d6c4c0e9bcb0a4890a4152b");
  
  public static void getUserData(String userID, Context context) throws Exception {
    try {
      String apiUrl = "https://personx.herokuapp.com/user/" + userID.trim();
      StringRequest request = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
          try {
            TaskViewModel.deleteAll();
            JSONObject userObj = new JSONObject(response).getJSONObject("user");
            String name = userObj.get("name").toString(),
                email = userObj.get("email").toString(),
                createdAt = userObj.get("createdAt").toString(),
                img = userObj.get("userImg").toString();
            UserModel userModel = new UserModel(name, email, img, createdAt);
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
            UserViewModel.insert(userModel);
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
      TastyToasty.error(context.getApplicationContext(), "Error : " + e).show();
    }
  }
  
  public static void updateUser(String userId, Context context, UserModel userModel) {
    TastyToasty.success(context, userId).show();
    String url = "https://personx.herokuapp.com/user/" + userId.trim();
    HashMap<String, String> body = new HashMap<>();
    body.put("name", userModel.getName());
    body.put("email", userModel.getEmail());
    body.put("userImg", userModel.getImg());
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH,
        url, new JSONObject(body), new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        try {
          if (response.getBoolean("success"))
            UserViewModel.updateUser(userModel);
          TastyToasty.blue(context, response.getString("message"), R.drawable.ic_account).show();
        } catch (JSONException e) {
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
            TastyToasty.error(context.getApplicationContext(), obj.getString("message")).show();
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
  
  public static void verifyMail(String email, Context context) {
    String url = "https://personx.herokuapp.com/user/authenticate/mail";
    HashMap<String, String> body = new HashMap<>();
    body.put("email", email);
    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
        new JSONObject(body), new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        try {
          TastyToasty.violet(context, response.getString("message"), null).show();
        } catch (JSONException e) {
          TastyToasty.error(context, e.toString()).show();
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
            TastyToasty.error(context.getApplicationContext(), obj.getString("message")).show();
          } catch (JSONException | UnsupportedEncodingException je) {
            je.printStackTrace();
          }
        }
      }
    });
    // set retry policy
    int socketTime = 3000;
    RetryPolicy policy = new DefaultRetryPolicy(socketTime,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    request.setRetryPolicy(policy);
    
    // request add
    RequestQueue requestQueue = Volley.newRequestQueue(context);
    requestQueue.add(request);
  }
  
  public static void getNews(Context context) {
    NewsViewModel.deleteAll();
    newsApiClient.getTopHeadlines(
        new TopHeadlinesRequest.Builder()
            .country("in")
            .build(), new NewsApiClient.ArticlesResponseCallback() {
          @Override
          public void onSuccess(ArticleResponse articleResponse) {
            for (Article newsObj : articleResponse.getArticles()) {
              String name = newsObj.getSource().getName(),
                  author = newsObj.getAuthor(),
                  title = newsObj.getTitle(),
                  descrip = newsObj.getDescription(),
                  url = newsObj.getUrl(),
                  imgUrl = newsObj.getUrlToImage(),
                  publish = newsObj.getPublishedAt();
              NewsModel newsModel = new NewsModel(name, author, title, descrip, url, imgUrl, publish);
              NewsViewModel.insert(newsModel);
            }
          }
          
          @Override
          public void onFailure(Throwable throwable) {
            TastyToasty.error(context, throwable.getMessage()).show();
          }
        }
    );
  }
  
  public static void getWeather(Context context, String location) {
    try {
      String apiUrl = "https://api.weatherapi.com/v1/current.json?key=25e6784139754723ab7135730212711&q=" + location + "&aqi=yes";
      JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
          try {
            WeatherViewModel.delete();
            String name = response.getJSONObject("location").getString("name") + ", "
                + response.getJSONObject("location").getString("region") + "\n\t"
                + response.getJSONObject("location").getString("country"),
                temp = response.getJSONObject("current").getString("temp_c"),
                icon = response.getJSONObject("current").getJSONObject("condition").getString("icon"),
                cond = response.getJSONObject("current").getJSONObject("condition").getString("text"),
                wind = response.getJSONObject("current").getString("wind_kph") + " km/h "
                    + response.getJSONObject("current").getString("wind_degree") + "° "
                    + response.getJSONObject("current").getString("wind_dir"),
                pressure = response.getJSONObject("current").getString("pressure_mb"),
                humi = response.getJSONObject("current").getString("humidity"),
                visi = response.getJSONObject("current").getString("vis_km") + "km",
                uv = response.getJSONObject("current").getString("uv"),
                co = response.getJSONObject("current").getJSONObject("air_quality").getString("co"),
                no2 = response.getJSONObject("current").getJSONObject("air_quality").getString("no2"),
                o3 = response.getJSONObject("current").getJSONObject("air_quality").getString("o3"),
                so2 = response.getJSONObject("current").getJSONObject("air_quality").getString("so2"),
                pm = response.getJSONObject("current").getJSONObject("air_quality").getString("pm2_5");
            WeatherModel weatherModel = new WeatherModel(name, temp, icon, cond, wind, pressure, humi, visi,
                uv, co, no2, o3, so2, pm);
            WeatherViewModel.insert(weatherModel);
          } catch (JSONException e) {
            TastyToasty.error(context, e.toString()).show();
          }
        }
      }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
          TastyToasty.error(context, error.toString()).show();
        }
      });
      RequestQueue requestQueue = Volley.newRequestQueue(context);
      requestQueue.add(request);
    } catch (Exception e) {
      TastyToasty.error(context.getApplicationContext(), "Error : " + e).show();
    }
  }
}
