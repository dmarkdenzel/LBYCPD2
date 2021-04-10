package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class customerhome extends AppCompatActivity {


    public BottomNavigationView navbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerhome);
        //MAKES THE APPLICATION FULL SCREEN TO HIDE THE STATUS BAR
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        //INSTANTIATE THE NAVIGATION BAR
        navbar=findViewById(R.id.navbar);

        //PLACES A DEFAULT PAGE WHEN THE APPLICATION IS OPENED SPECIFICALLY THE FIRST TAB OF NAV BAR
        Fragment selected=new tab1();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag,selected).commit();

        //ADDS A LISTENER TO CHECK WHICH TAB THE USER HAS CLICKED
        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //SETS THE FRAGMENT TO NULL
                Fragment selected=null;
                switch(item.getItemId()){
                    //DEPENDING ON THE TAB THE FRAGMENT CHANGES TO THE DESIRED NEW CLASS
                    case R.id.item1:
                        selected=new tab1();
                        break;
                    case R.id.item2:
                        selected=new tab2();
                        break;
                    case R.id.item3:
                        selected=new tab3();
                        break;
                    case R.id.item4:
                        selected=new tab4();
                        break;
                    default:
                        break;
                }
                //COMMITS THE NEW FRAGMENT
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,selected).commit();
                return true;
            }
        });
    }
}