package com.collegeproject.personx;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.collegeproject.personx.Database.DAO.UserDAO;
import com.collegeproject.personx.Database.UserDatabase;
import com.collegeproject.personx.Fragments.HomePageFrag;
import com.collegeproject.personx.Fragments.SettingPageFrag;
import com.collegeproject.personx.NetworkFile.CheckInternet;
import com.collegeproject.personx.NetworkFile.UserNetwork;
import com.collegeproject.personx.Utils.SharedPreferenceClass;
import com.collegeproject.personx.Utils.TaskCreateFrag;
import com.collegeproject.personx.Utils.UtilService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uk.tastytoasty.TastyToasty;

public class MainActivity extends AppCompatActivity {
  
  private static Context context;
  private BottomNavigationView bottomNavigationView;
  private FloatingActionButton floatingActionButton;
  private static UtilService utilService;
  private static UserDAO userDAO;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    context = getApplicationContext();
    UserDatabase userDatabase = UserDatabase.getUserRoomDatabase(context.getApplicationContext());
    userDAO = userDatabase.userDao();
    utilService = new UtilService();
    bottomNavigationView = findViewById(R.id.bottomNavigationView);
    floatingActionButton = findViewById(R.id.fab);
    
    bottomNavigationView.setBackground(null);
    bottomNavigationView.getMenu().getItem(2).isEnabled();
    defaultFragment(new HomePageFrag());
    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
          case R.id.home:
            fragment = new HomePageFrag();
            break;
          case R.id.setting:
            fragment = new SettingPageFrag();
            break;
        }
        return defaultFragment(fragment);
      }
    });
    
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        TaskCreateFrag taskCreateFrag = new TaskCreateFrag();
        taskCreateFrag.show(getSupportFragmentManager(), taskCreateFrag.getTag());
      }
    });
    
    
  }
  
  private boolean defaultFragment(Fragment fragment) {
    if (fragment != null) {
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.container, fragment)
          .commit();
      return true;
    }
    return false;
  }
  
  @Override
  protected void onStart() {
    super.onStart();
    SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass(context);
    if (CheckInternet.isConnected(context)) {
      try {
        UserNetwork.getUserData(sharedPreferenceClass.getValueString("token"), context);
      } catch (Exception e) {
        TastyToasty.violet(context, e.getMessage(), null).show();
      }
    } else
      TastyToasty.violet(context, "No Internet Connection", null).show();
  }
}