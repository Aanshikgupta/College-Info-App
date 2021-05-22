package com.example.collegeinfoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collegeinfoapp.FragmentMainApp.AttendanceFragment;
import com.example.collegeinfoapp.FragmentMainApp.InfoFragment;
import com.example.collegeinfoapp.FragmentMainApp.NoticeFragment;
import com.example.collegeinfoapp.FragmentMainApp.SyllabusFragment;
import com.example.collegeinfoapp.FragmentMainApp.TimeTableFragment;
import com.example.collegeinfoapp.LoginSetup.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth mAuth;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        navigationView=findViewById(R.id.navigation_view);
        drawerLayout=findViewById(R.id.drawer_layout);
        setToolbar();
        navigationView.setItemIconTintList(null);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new InfoFragment()).commit();

    }

    public void setNavHeader(){
        View navigationHeader = navigationView.getHeaderView(0);
        ImageView imageView=navigationHeader.findViewById(R.id.nav_image);
        TextView textViewEmail = navigationHeader.findViewById(R.id.nav_header_email);
        imageView.setImageResource(R.drawable.profile);
        String res=Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();


        assert res != null;
        res=res.substring(0,res.indexOf('@'));
        TextView name=navigationHeader.findViewById(R.id.nav_header_name);
        name.setText(res);
        textViewEmail.setText(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());
    }


    //setting toolbar (Hamburger Icon)
    private void setToolbar() {
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        setSupportActionBar(toolbar);
        setNavHeader();
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //it's instance ties together drawer layout and toolbar
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();//setting up hamburger icon
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.info:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new InfoFragment()).commit();
                break;
            case R.id.notice_board:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new NoticeFragment()).commit();
                break;
            case R.id.syllabus:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new SyllabusFragment()).commit();
                break;
            case R.id.time_table:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new TimeTableFragment()).commit();
                break;
            case R.id.attendance:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new AttendanceFragment()).commit();
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}