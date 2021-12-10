package com.example.snoozelose.Alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.snoozelose.MainActivity;
import com.example.snoozelose.R;
import com.example.snoozelose.SharedViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

public class AlarmActivity extends AppCompatActivity {

    public static final String ALARM_ID = "com.example.snoozelose.Alarms.AlarmActivity.ALARM_ID";
    public static final String REQUEST_CODE = "com.example.snoozelose.Alarms.AlarmActivity.REQUEST_CODE";
    private Vibrator vibrate;
    private MediaPlayer mp;

    private static PendingIntent pendingIntent(Context context, int requestCode, int alarmId) {
        Intent alarmIntent = new Intent(context, AlarmActivity.class);
        alarmIntent.putExtra(REQUEST_CODE, requestCode);

        Log.i("activity extra", ""+alarmId);
        if(alarmId >= 0) {
            alarmIntent.putExtra(ALARM_ID, alarmId);
        }

        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        return PendingIntent.getActivity(context, requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void schedule(Context context, Calendar cal, int requestCode, int alarmInfoId) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent(context, requestCode, alarmInfoId));

       //Toast.makeText(context, cal.getTime().toString(), Toast.LENGTH_LONG).show();
    }

    public static void deschedule(Context context, int requestCode) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(pendingIntent(context, requestCode, -1));
    }

    public static void removeAlarms(Context context, int alarmID) {
        for(int i = 1; i < 7; i++) { //Calendar.DAYS is ranging from 1-7
            deschedule(context, (alarmID * 7) + i);
        }
    }

    private int snoozeCharge = 0;

    private long startupTime;

    @Override
    protected void onStop() {
        super.onStop();

        //There is a weird situation where onStop is called multiple times if the phone was locked when the alarm starts
        //this is an ugly solution (to an ugly problem) to make sure that the alarm actually had a chance to start before we finish
        if(System.currentTimeMillis() - startupTime > 1000) {
            if(vibrate != null) vibrate.cancel();
            if(mp != null) mp.stop();

            finishAndRemoveTask();
        }

    }

    private Alarms alarm;

    private void startRinging() {
        snoozeCharge = alarm.amountToPay;

        ((TextView) findViewById(R.id.alarm_desc)).setText(alarm.getmLabel());

        if(alarm.getVib()) {
            vibrate = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            long pattern[] = {0, 1000, 750, 1000};

            vibrate.vibrate(pattern, 1);
        }

        if(alarm.getSound()) {
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mp = MediaPlayer.create(getApplicationContext(), alarmSound);
            mp.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        findViewById(R.id.wakeup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();
                if(alarm != null) alarm.scheduleAlarms(AlarmActivity.this);
            }
        });

        findViewById(R.id.snooze).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDebt(loadDebt() + snoozeCharge);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 15);
                AlarmActivity.schedule(AlarmActivity.this, cal, getIntent().getIntExtra(REQUEST_CODE, 0), AlarmActivity.this.alarm.alarmID);
                finishAndRemoveTask();
            }
        });

        int alarmId = getIntent().getIntExtra(ALARM_ID, -1);
        AlarmRepository repository = new AlarmRepository(this.getApplication());
        repository.getAlarm(alarmId).observe(this, new Observer<Alarms>() {
            @Override
            public void onChanged(Alarms alarm) {
                AlarmActivity.this.alarm = alarm;
                startRinging();
            }
        });

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        startupTime = System.currentTimeMillis();
    }

    private int loadDebt()
    {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("debt", MODE_PRIVATE);
        return sharedPreferences.getInt("debtAmount", 0);
    }

    private void saveDebt(int debt) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("debt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("debtAmount" , debt);
        Log.d("Trial","SAVED as: " + debt);
        editor.commit();
    }
}
