package com.example.collegeinfoapp.FragmentMainApp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeinfoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


public class AttendanceFragment extends Fragment {

   private FirebaseRemoteConfig firebaseRemoteConfig;
   View view;
   TextView attendancetext;
    public AttendanceFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_attendance, container, false);
        setView(view);
        setFirebaseRemoteConfig();
        fetchFirebaseRemoteConfig();


        return view;
    }

    private void setView(View view) {
        attendancetext=view.findViewById(R.id.textView);
    }

    private void setFirebaseRemoteConfig(){
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);

    }

    private void fetchFirebaseRemoteConfig(){
        HashMap<String,Object> defaults=new HashMap<>();
        defaults.put("ATTENDANCE",0);
        defaults.put("IS_UPDATE",false);
        firebaseRemoteConfig.setDefaultsAsync(defaults);

        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String text="Attendance = "+firebaseRemoteConfig.getLong("ATTENDANCE");
                attendancetext.setText(text);
            }
            else{
                Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
            }

        });
    }
}