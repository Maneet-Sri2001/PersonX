package com.collegeproject.personx;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.collegeproject.personx.Database.DAO.UserDAO;
import com.collegeproject.personx.Database.UserDatabase;
import com.collegeproject.personx.Fragments.HomePageFrag;
import com.collegeproject.personx.Fragments.SettingPageFrag;
import com.collegeproject.personx.Fragments.UserPageFrag;
import com.collegeproject.personx.NetworkFile.CheckInternet;
import com.collegeproject.personx.NetworkFile.UserNetwork;
import com.collegeproject.personx.Utils.SharedPreferenceClass;
import com.collegeproject.personx.Utils.TaskCreateFrag;
import com.collegeproject.personx.Utils.UtilService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uk.tastytoasty.TastyToasty;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements LocationListener {
  
  private static Context context;
  private BottomNavigationView bottomNavigationView;
  private FloatingActionButton floatingActionButton;
  private static UtilService utilService;
  private static UserDAO userDAO;
  private final int REQUEST_LOCATION_PERMISSION = 1;
  protected LocationManager locationManager;
  private SharedPreferenceClass sharedPreferenceClass;
  
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
    sharedPreferenceClass = new SharedPreferenceClass(context);
    
    requestLocationPermission();
    
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
          case R.id.user_profile:
            fragment = new UserPageFrag();
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
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return;
    }
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
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
  
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }
  
  @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
  public void requestLocationPermission() {
    String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
    if (EasyPermissions.hasPermissions(context.getApplicationContext(), perms)) {
      //TastyToasty.indigo(context, "Location Access Enabled", null).show();
    } else {
      EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
    }
  }
  
  @Override
  public void onLocationChanged(@NonNull Location location) {
    String latitude = sharedPreferenceClass.getValueString("latitude"),
        longitude = sharedPreferenceClass.getValueString("longitude");
    if (latitude == "") {
      sharedPreferenceClass.setValueString("latitude", String.valueOf(location.getLatitude()));
      sharedPreferenceClass.setValueString("longitude", String.valueOf(location.getLongitude()));
    }
  }
  
  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    LocationListener.super.onStatusChanged(provider, status, extras);
    TastyToasty.indigo(context, "Location Provider : " + provider, null).show();
  }
  
  @Override
  public void onProviderEnabled(@NonNull String provider) {
    LocationListener.super.onProviderEnabled(provider);
  }
  
  @Override
  public void onProviderDisabled(@NonNull String provider) {
    LocationListener.super.onProviderDisabled(provider);
    TastyToasty.error(context, "Location Disabled. Enable it.");
  }
}