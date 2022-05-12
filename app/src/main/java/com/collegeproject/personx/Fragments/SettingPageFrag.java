package com.collegeproject.personx.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.collegeproject.personx.Model.UserModel;
import com.collegeproject.personx.R;
import com.collegeproject.personx.ViewModel.UserViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.uk.tastytoasty.TastyToasty;

import java.util.Calendar;
import java.util.UUID;

public class SettingPageFrag extends Fragment {
  
  Dialog myDialog;
  TextView datetimeF, firstdayweek, presetday, sendF, reportB, Cu, AppD, Privacy, Tnc;
  Calendar calendar = Calendar.getInstance();
  DatePickerDialog.OnDateSetListener setListener;
  TextView addPic, userName, userEmail;
  ImageView profilePic;
  FirebaseStorage storage;
  StorageReference storageRef;
  FirebaseAuth mAuth;
  Uri imageUri;
  private BottomAppBar bottomNavigationView;
  private FloatingActionButton floatingActionButton;
  private ScrollView scrollView;
  private UserModel user;
  UserViewModel userViewModel;
  String name = "";
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    FirebaseApp.initializeApp(getContext());
    View view = inflater.inflate(R.layout.fragment_setting_page, container, false);
    myDialog = new Dialog(getContext());
    mAuth = FirebaseAuth.getInstance();
    storage = FirebaseStorage.getInstance();
    storageRef = storage.getReference();
    sendF = view.findViewById(R.id.sendfeedback);
    reportB = view.findViewById(R.id.reportabug);
    Cu = view.findViewById(R.id.idcontactus);
    AppD = view.findViewById(R.id.idappdetails);
    Privacy = view.findViewById(R.id.idprivacy);
    Tnc = view.findViewById(R.id.idtnc);
    addPic = view.findViewById(R.id.addPic);
    userName = view.findViewById(R.id.userName);
    userEmail = view.findViewById(R.id.user_email);
    profilePic = view.findViewById(R.id.profilePic);
    bottomNavigationView = getActivity().findViewById(R.id.bottomAppBar);
    floatingActionButton = getActivity().findViewById(R.id.fab);
    scrollView = view.findViewById(R.id.settinglayout);
    userViewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        .create(UserViewModel.class);
    return view;
  }
  
  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
      imageUri = data.getData();
      // profilePic.setImageURI(imageUri);
      uploadPicture();
    }
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    
    userViewModel.getUser().observe(getViewLifecycleOwner(), userModel -> {
      try {
        userName.setText(userModel.getName());
        userEmail.setText(userModel.getEmail());
        Picasso.get().load(Uri.parse(userModel.getImg())).into(profilePic);
      } catch (Exception e) {
        TastyToasty.error(getContext(), e.toString()).show();
      }
    });

//    datetimeF.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        // Initialize variables
//        TextView txtclose, datetv, timetv;
//        //Assign Variable
//        myDialog.setContentView(R.layout.pop_data_time_formats);
//        datetv = myDialog.findViewById(R.id.tvdate);
//        timetv = myDialog.findViewById(R.id.tvtime);
//        txtclose = myDialog.findViewById(R.id.txtclose);
//        txtclose.setText("Close");
//        datetv.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View view) {
//            Toast.makeText(SettingPageFrag.this.getContext(), "Hello", Toast.LENGTH_SHORT).show();
//            DatePickerDialog datePickerDialog = new DatePickerDialog(SettingPageFrag.this.getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth
//                , setListener, year, month, day);
//            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            datePickerDialog.show();
//
//          }
//        });
//        setListener = new DatePickerDialog.OnDateSetListener() {
//          @Override
//          public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
//            month = month + 1;
//            String date = day + "/" + month + "/" + year;
//            datetv.setText(date);
//
//          }
//        };
//        timetv.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View view) {
//            // Initialize time picker dialog
//            TimePickerDialog timePickerDialog = new TimePickerDialog(
//                SettingPageFrag.this.getContext(),
//                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                new TimePickerDialog.OnTimeSetListener() {
//                  @Override
//                  public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
//                    //Initialize hour and minute
//                    t1Hour = hourOfDay;
//                    t1Minute = minute;
//
//                    //Store hour and minute in String
//                    String time = t1Hour + ":" + t1Minute;
//
//                    //Initialize 24 hours time format
//                    SimpleDateFormat f24Hours = new SimpleDateFormat(
//                        "HH:mm"
//                    );
//                    try {
//                      Date date = f24Hours.parse(time);
//                      //Initialize 12 hours time format
//                      SimpleDateFormat f12Hours = new SimpleDateFormat(
//                          "hh:mm aa"
//                      );
//                      //Set selected time on text view
//                      timetv.setText(f12Hours.format(date));
//                    } catch (ParseException e) {
//                      e.printStackTrace();
//                    }
//
//                  }
//                }, 12, 0, false
//            );
//            // Set Transparent background
//            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            // Displayed previous selected time
//            timePickerDialog.updateTime(t1Hour, t1Minute);
//            //Show dialog
//            timePickerDialog.show();
//          }
//        });
//        txtclose.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//            myDialog.dismiss();
//          }
//        });
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.show();
//      }
//    });
//    firstdayweek.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//
//        TextView txtclose;
//        myDialog.setContentView(R.layout.pop_firstdayweek);
//        txtclose = myDialog.findViewById(R.id.txtclose);
//        txtclose.setText("Close");
//        txtclose.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//            myDialog.dismiss();
//          }
//        });
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.show();
//
//      }
//    });
//    presetday.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//
//        TextView txtclose;
//        myDialog.setContentView(R.layout.pop_allday);
//        txtclose = myDialog.findViewById(R.id.txtclose);
//        txtclose.setText("Close");
//        txtclose.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//            myDialog.dismiss();
//          }
//        });
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.show();
//
//      }
//    });
    addPic.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
      }
    });
    sendF.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        TextView txtclose;
        myDialog.setContentView(R.layout.pop_feedback);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        
      }
    });
    reportB.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        TextView txtclose;
        myDialog.setContentView(R.layout.pop_bug);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        
      }
    });
    Cu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        TextView txtclose;
        myDialog.setContentView(R.layout.pop_feedback);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        
      }
    });
    AppD.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        TextView txtclose;
        myDialog.setContentView(R.layout.pop_bug);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        
      }
    });
    Privacy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        TextView txtclose;
        myDialog.setContentView(R.layout.pop_feedback);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        
      }
    });
    Tnc.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        TextView txtclose;
        myDialog.setContentView(R.layout.pop_bug);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("Close");
        txtclose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myDialog.dismiss();
          }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        
      }
    });
  }
  
  private void uploadPicture() {
    final ProgressDialog pd = new ProgressDialog(getContext());
    pd.setTitle("Uploading Image...");
    pd.show();
    final String randomKey = UUID.randomUUID().toString();
    StorageReference riversRef = storageRef.child("images/" + randomKey);
    
    riversRef.putFile(imageUri)
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            pd.dismiss();
            // Snackbar.make(findViewById(android.R.id.content), "Image uploaded", Snackbar.LENGTH_LONG).show();
            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
              @Override
              public void onSuccess(Uri uri) {
                UserViewModel.delete();
                UserModel userModel = new UserModel(
                    userName.getText().toString(),
                    userEmail.getText().toString(),
                    uri.toString(), "");
                UserViewModel.insert(userModel);
              }
            });
          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception exception) {
            pd.dismiss();
            TastyToasty.error(getContext(), exception.getMessage()).show();
          }
        })
        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
            double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
            pd.setMessage("Percentage: " + (int) progressPercent + "%");
          }
        });
    
  }
}