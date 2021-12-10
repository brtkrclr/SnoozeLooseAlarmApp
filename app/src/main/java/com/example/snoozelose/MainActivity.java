package com.example.snoozelose;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.example.snoozelose.Alarms.AlarmsFragment;
import com.example.snoozelose.Payments.PaymentsFragment;
import com.example.snoozelose.Profile.ProfileFragment;
import com.example.snoozelose.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private enum FragmentType {
        ALARMS, PAYMENTS, PROFILE
    }
    private FragmentType currFragment = FragmentType.ALARMS;

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    private BottomNavigationView bottomNav;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);



        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //ft.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
            ft.replace(R.id.fragment_container, new AlarmsFragment()).commit();

        }
        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AlarmsFragment()).commit();
        }
        */

    }

    @Override
    protected void onStart() {
        super.onStart();

        load(sharedViewModel);
    }

    @Override
    protected void onStop() {
        save(sharedViewModel);
        super.onStop();
    }

        private BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = new AlarmsFragment();

                        switch (item.getItemId()) {
                            case R.id.nav_alarms:
                                selectedFragment = new AlarmsFragment();
                                currFragment = FragmentType.ALARMS;
                                break;
                            case R.id.nav_payment:
                                selectedFragment = new PaymentsFragment();
                                currFragment = FragmentType.PAYMENTS;
                                break;
                            case R.id.nav_profile:
                                selectedFragment = new ProfileFragment();
                                currFragment = FragmentType.PROFILE;
                                break;
                        }

                        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        //ft.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
                        ft.replace(R.id.fragment_container, selectedFragment).commit();

                        return true;
                    }
                };

    public void save(SharedViewModel v) {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("debt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int mNumber = v.getSharedDebt();
        editor.putInt("debtAmount" , mNumber);
        Log.d("Trial","SAVED as: " + mNumber);
        editor.apply();
    }

    public void load(SharedViewModel v) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("debt", MODE_PRIVATE);

        int mNumber = sharedPreferences.getInt("debtAmount",0);

        Log.d("Trial","LOADED as: " + mNumber);
        v.setSharedDebt(mNumber);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        if(currFragment == FragmentType.PAYMENTS)
                        {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            //ft.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
                            ft.replace(R.id.fragment_container, new AlarmsFragment()).commit();
                            bottomNav.setSelectedItemId(R.id.nav_alarms);
                        }
                        else if(currFragment == FragmentType.PROFILE)
                        {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            //ft.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
                            ft.replace(R.id.fragment_container, new PaymentsFragment()).commit();
                            bottomNav.setSelectedItemId(R.id.nav_payment);
                        }
                        //Toast.makeText(this, "Left to Right swipe", Toast.LENGTH_SHORT).show ();
                    }
                    // Right to left swipe action
                    else
                    {
                        if(currFragment == FragmentType.ALARMS)
                        {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            //ft.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
                            ft.replace(R.id.fragment_container, new PaymentsFragment()).commit();
                            bottomNav.setSelectedItemId(R.id.nav_payment);
                        }
                        else if(currFragment == FragmentType.PAYMENTS)
                        {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            //ft.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
                            ft.replace(R.id.fragment_container, new ProfileFragment()).commit();
                            bottomNav.setSelectedItemId(R.id.nav_profile);
                        }
                        //Toast.makeText(this, "Right to Left swipe", Toast.LENGTH_SHORT).show ();
                    }
                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }

}
