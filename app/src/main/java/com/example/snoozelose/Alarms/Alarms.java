package com.example.snoozelose.Alarms;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.snoozelose.R;

import java.sql.Time;
import java.util.Calendar;

@Entity(tableName = "alarms_list")
public class Alarms {
    @PrimaryKey(autoGenerate = true)
    protected int alarmID;

    public String time,mLabel,mDays;
    public Boolean mSwitch, mon,tue,wed,thu,fri,sat,sun, sound, vib;
    public int amountToPay;

    public Alarms(String time, String mLabel, int amountToPay, Boolean mSwitch, Boolean sound, Boolean vib,
                  Boolean mon, Boolean tue, Boolean wed, Boolean thu, Boolean fri, Boolean sat, Boolean sun) {
        this.time = time;
        this.mLabel = mLabel;
        this.mDays = "";
        this.mSwitch = mSwitch;
        this.amountToPay = amountToPay;
        this.sound = sound;
        this.vib = vib;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        setmDays();
    }

    public void setmDays(String mDays) {
        this.mDays = mDays;
    }

    public int getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }

    public Boolean getSound() {
        return sound;
    }

    public void setSound(Boolean sound) {
        this.sound = sound;
    }

    public Boolean getVib() {
        return vib;
    }

    public void setVib(Boolean vib) {
        this.vib = vib;
    }

    public Boolean getMon() {
        return mon;
    }

    public void setMon(Boolean mon) {
        this.mon = mon;
    }

    public Boolean getTue() {
        return tue;
    }

    public void setTue(Boolean tue) {
        this.tue = tue;
    }

    public Boolean getWed() {
        return wed;
    }

    public void setWed(Boolean wed) {
        this.wed = wed;
    }

    public Boolean getThu() {
        return thu;
    }

    public void setThu(Boolean thu) {
        this.thu = thu;
    }

    public Boolean getFri() {
        return fri;
    }

    public void setFri(Boolean fri) {
        this.fri = fri;
    }

    public Boolean getSat() {
        return sat;
    }

    public void setSat(Boolean sat) {
        this.sat = sat;
    }

    public Boolean getSun() {
        return sun;
    }

    public void setSun(Boolean sun) {
        this.sun = sun;
    }

    public int getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(int amountToPay) {
        this.amountToPay = amountToPay;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getmLabel() {
        return mLabel;
    }

    public void setmLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public String getmDays() {
        return mDays;
    }

    public void setmDays() {
        if(mon)
            mDays += "Mon ";
        if(tue)
            mDays += "Tue ";
        if(wed)
            mDays += "Wed ";
        if(thu)
            mDays += "Thu ";
        if(fri)
            mDays += "Fri ";
        if(sat)
            mDays += "Sat ";
        if(sun)
            mDays += "Sun ";
        if(mDays.equals("")) {
            mDays += "Today";

            //yes this is the ugliest shit ever but do u think i give damn? no
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(this.time.split(":")[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt(this.time.split(":")[1]));
            cal.set(Calendar.SECOND, 0);

            if(cal.getTimeInMillis() <= System.currentTimeMillis()) {
                mDays = "Tomorrow";
            }
        }

    }

    public Boolean getmSwitch() {
        return mSwitch;
    }

    public void setmSwitch(Boolean mSwitch) {
        this.mSwitch = mSwitch;
    }

    public void descheduleAlarms(Context context) {
        AlarmActivity.removeAlarms(context, this.alarmID);
    }

    private Calendar initCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(this.time.split(":")[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(this.time.split(":")[1]));
        cal.set(Calendar.SECOND, 0);
        return cal;
    }

    public void scheduleAlarms(Context context) {
        descheduleAlarms(context);

        boolean today = true; // if this bool is true it means we have not selected any days and the alarm should be scheduled today

        if (mon) {
            Calendar cal2 = initCalendar();
            cal2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            if(cal2.getTimeInMillis() < System.currentTimeMillis()) {
                cal2.add(Calendar.WEEK_OF_YEAR, 1);
            }
            AlarmActivity.schedule(context, cal2, (alarmID * 7) + Calendar.MONDAY, alarmID);
            today = false;
        }

        if (tue) {
            Calendar cal2 = initCalendar();
            cal2.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            if(cal2.getTimeInMillis() < System.currentTimeMillis()) {
                cal2.add(Calendar.WEEK_OF_YEAR, 1);
            }
            AlarmActivity.schedule(context, cal2, (alarmID * 7) + Calendar.TUESDAY, alarmID);
            today = false;
        }

        if (wed) {
            Calendar cal2 = initCalendar();
            cal2.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            if(cal2.getTimeInMillis() < System.currentTimeMillis()) {
                cal2.add(Calendar.WEEK_OF_YEAR, 1);
            }
            AlarmActivity.schedule(context, cal2, (alarmID * 7) + Calendar.WEDNESDAY, alarmID);
            today = false;
        }

        if (thu) {
            Calendar cal2 = initCalendar();
            cal2.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            if(cal2.getTimeInMillis() < System.currentTimeMillis()) {
                cal2.add(Calendar.WEEK_OF_YEAR, 1);
            }
            AlarmActivity.schedule(context, cal2, (alarmID * 7) + Calendar.THURSDAY, alarmID);
            today = false;
        }

        if (fri) {
            Calendar cal2 = initCalendar();
            cal2.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            if(cal2.getTimeInMillis() < System.currentTimeMillis()) {
                cal2.add(Calendar.WEEK_OF_YEAR, 1);
            }
            AlarmActivity.schedule(context, cal2, (alarmID * 7) + Calendar.FRIDAY, alarmID);
            today = false;
        }

        if (sat) {
            Calendar cal2 = initCalendar();
            cal2.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            if(cal2.getTimeInMillis() < System.currentTimeMillis()) {
                cal2.add(Calendar.WEEK_OF_YEAR, 1);
            };
            AlarmActivity.schedule(context, cal2, (alarmID * 7) + Calendar.SATURDAY, alarmID);
            today = false;
        }

        if (sun) {
            Calendar cal2 = initCalendar();
            cal2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            if(cal2.getTimeInMillis() < System.currentTimeMillis()) {
                cal2.add(Calendar.WEEK_OF_YEAR, 1);
            }
            AlarmActivity.schedule(context, cal2, (alarmID * 7) + Calendar.SUNDAY, alarmID);
            today = false;
        }

        if (today) {
            Calendar cal = initCalendar();
            if(cal.getTimeInMillis() < System.currentTimeMillis()) {
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
            
            AlarmActivity.schedule(context, cal, (alarmID * 7) + cal.get(Calendar.DAY_OF_WEEK), alarmID);
        }

    }

}
