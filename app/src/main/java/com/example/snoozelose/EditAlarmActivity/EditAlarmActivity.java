package com.example.snoozelose.EditAlarmActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snoozelose.MainActivity;
import com.example.snoozelose.R;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class EditAlarmActivity extends AppCompatActivity implements DescriptionDialog.ExampleDialogListener {
    private TimePicker timePicker;
    private Button monButton, tueButton, wedButton, thuButton, friButton, satButton, sunButton;
    private ImageButton save, cancel;
    private TextView amountTextView, title;
    private SeekBar amountSeekBar;
    private EditText alarmLabel;
    private Switch vib, sound;

    private Boolean mon = false, tue = false, wed = false, thu = false, fri = false, sat = false, sun = false, mswitch, mvib, msound;

    private int amountToPay, alarmID;
    private String time = "", label, days;

    private Boolean _mon, _tue, _wed, _thu, _fri, _sat, _sun, _mswitch = true, _mvib, _msound;
    private int _amountToPay;
    private String _time, _label, _days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        attachViews();

        if (getIntent().hasExtra("ID")) {
            String typeTitle = getIntent().getStringExtra("TYPE");
            mon = getIntent().getBooleanExtra("MON", false);
            tue = getIntent().getBooleanExtra("TUE", false);
            wed = getIntent().getBooleanExtra("WED", false);
            thu = getIntent().getBooleanExtra("THU", false);
            fri = getIntent().getBooleanExtra("FRI", false);
            sat = getIntent().getBooleanExtra("SAT", false);
            sun = getIntent().getBooleanExtra("SUN", false);
            amountToPay = getIntent().getIntExtra("AMOUNT", 0);
            mswitch = getIntent().getBooleanExtra("SWITCH", false);
            time = getIntent().getStringExtra("TIME");
            label = getIntent().getStringExtra("LABEL");
            days = getIntent().getStringExtra("DAYS");
            mvib = getIntent().getBooleanExtra("VIB", false);
            msound = getIntent().getBooleanExtra("SOUND", false);
            alarmID = getIntent().getIntExtra("ID", -1);

            title.setText(typeTitle);
            alarmLabel.setText(label);
            vib.setChecked(mvib);
            sound.setChecked(msound);
            amountSeekBar.setProgress(amountToPay);
            amountTextView.setText(amountToPay + "SEK");
            int hour = Integer.valueOf(time.substring(0, 2));
            timePicker.setHour(hour);
            int min = Integer.valueOf(time.substring(3));
            timePicker.setMinute(min);

            saveOldStats();

            if (mon) {
                monButton.setBackground(getDrawable(R.drawable.day_clicked));
                monButton.setTextColor(getColor(R.color.colorPrimary));
            } else {
                monButton.setBackground(getDrawable(R.drawable.day_unclicked));
                monButton.setTextColor(getColor(R.color.colorAccent));
            }

            if (tue) {
                tueButton.setBackground(getDrawable(R.drawable.day_clicked));
                tueButton.setTextColor(getColor(R.color.colorPrimary));
            } else {
                tueButton.setBackground(getDrawable(R.drawable.day_unclicked));
                tueButton.setTextColor(getColor(R.color.colorAccent));
            }

            if (wed) {
                wedButton.setBackground(getDrawable(R.drawable.day_clicked));
                wedButton.setTextColor(getColor(R.color.colorPrimary));
            } else {
                wedButton.setBackground(getDrawable(R.drawable.day_unclicked));
                wedButton.setTextColor(getColor(R.color.colorAccent));
            }

            if (thu) {
                thuButton.setBackground(getDrawable(R.drawable.day_clicked));
                thuButton.setTextColor(getColor(R.color.colorPrimary));
            } else {
                thuButton.setBackground(getDrawable(R.drawable.day_unclicked));
                thuButton.setTextColor(getColor(R.color.colorAccent));
            }

            if (fri) {
                friButton.setBackground(getDrawable(R.drawable.day_clicked));
                friButton.setTextColor(getColor(R.color.colorPrimary));
            } else {
                friButton.setBackground(getDrawable(R.drawable.day_unclicked));
                friButton.setTextColor(getColor(R.color.colorAccent));
            }

            if (sat) {
                satButton.setBackground(getDrawable(R.drawable.day_clicked));
                satButton.setTextColor(getColor(R.color.colorPrimary));
            } else {
                satButton.setBackground(getDrawable(R.drawable.day_unclicked));
                satButton.setTextColor(getColor(R.color.colorAccent));
            }

            if (sun) {
                sunButton.setBackground(getDrawable(R.drawable.day_clicked));
                sunButton.setTextColor(getColor(R.color.colorPrimary));
            } else {
                sunButton.setBackground(getDrawable(R.drawable.day_unclicked));
                sunButton.setTextColor(getColor(R.color.colorAccent));
            }

        } else {
            String typeTitle = getIntent().getStringExtra("TYPE");
            title.setText(typeTitle);
        }

        alarmLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                int hour = i;
                int minute = i1;
                String mHour = Integer.toString(hour);
                String mMinute = Integer.toString(minute);
                if (mHour.length() == 1) {
                    mHour = "0" + mHour;
                }
                if (mMinute.length() == 1) {
                    mMinute = "0" + mMinute;
                }

                time = mHour + ":" + mMinute;
            }
        });

        //mvib = vib.isChecked();
        //msound = sound.isChecked();
        mswitch = true;

        amountSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String amount = Integer.toString(i);
                amountTextView.setText(amount + " SEK");
                amountToPay = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                if (alarmLabel.getText().toString().equals(""))
                    label = "ALARM";
                else
                    label = alarmLabel.getText().toString();
                if (time.equals("")) {
                    String mHour = (timePicker.getHour() % 24) + "";
                    String mMinute = timePicker.getMinute() + "";
                    if (mHour.length() == 1) {
                        mHour = "0" + mHour;
                    }
                    if (mMinute.length() == 1) {
                        mMinute = "0" + mMinute;
                    }
                    time = mHour + ":" + mMinute;
                }
                openMainActivitySavedVersion();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                openMainActivityCancel();
            }
        });

        vib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mvib = isChecked;
            }
        });

        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                msound = isChecked;
            }
        });

        monButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (monButton.getBackground().getConstantState().equals(getDrawable(R.drawable.day_unclicked).getConstantState())) {
                    monButton.setBackground(getDrawable(R.drawable.day_clicked));
                    monButton.setTextColor(getColor(R.color.colorPrimary));
                    mon = true;
                } else if (monButton.getBackground().getConstantState().equals(getDrawable(R.drawable.day_clicked).getConstantState())) {
                    monButton.setBackground(getDrawable(R.drawable.day_unclicked));
                    monButton.setTextColor(getColor(R.color.colorAccent));
                    mon = false;
                }
            }
        });

        tueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tueButton.getBackground().getConstantState().equals(getDrawable(R.drawable.day_unclicked).getConstantState())) {
                    tueButton.setBackground(getDrawable(R.drawable.day_clicked));
                    tueButton.setTextColor(getColor(R.color.colorPrimary));
                    tue = true;
                } else if (tueButton.getBackground().getConstantState().equals(getDrawable(R.drawable.day_clicked).getConstantState())) {
                    tueButton.setBackground(getDrawable(R.drawable.day_unclicked));
                    tueButton.setTextColor(getColor(R.color.colorAccent));
                    tue = false;
                }
            }
        });

        wedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wedButton.getBackground().getConstantState().equals(getDrawable(R.drawable.day_unclicked).getConstantState())) {
                    wedButton.setBackground(getDrawable(R.drawable.day_clicked));
                    wedButton.setTextColor(getColor(R.color.colorPrimary));
                    wed = true;
                } else if (wedButton.getBackground().getConstantState().equals(getDrawable(R.drawable.day_clicked).getConstantState())) {
                    wedButton.setBackground(getDrawable(R.drawable.day_unclicked));
                    wedButton.setTextColor(getColor(R.color.colorAccent));
                    wed = false;
                }
            }
        });

        thuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thuButton.getBackground().getConstantState().equals(getDrawable(R.drawable.day_unclicked).getConstantState())) {
                    thuButton.setBackground(getDrawable(R.drawable.day_clicked));
                    thuButton.setTextColor(getColor(R.color.colorPrimary));
                    thu = true;
                } else if (thuButton.getBackground().getConstantState().equals(getDrawable(R.drawable.day_clicked).getConstantState())) {
                    thuButton.setBackground(getDrawable(R.drawable.day_unclicked));
                    thuButton.setTextColor(getColor(R.color.colorAccent));
                    thu = false;
                }
            }
        });

        friButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (friButton.getBackground().getConstantState().equals(getDrawable(R.drawable.day_unclicked).getConstantState())) {
                    friButton.setBackground(getDrawable(R.drawable.day_clicked));
                    friButton.setTextColor(getColor(R.color.colorPrimary));
                    fri = true;
                } else if (friButton.getBackground().getConstantState().equals(getDrawable(R.drawable.day_clicked).getConstantState())) {
                    friButton.setBackground(getDrawable(R.drawable.day_unclicked));
                    friButton.setTextColor(getColor(R.color.colorAccent));
                    fri = false;
                }
            }
        });

        satButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (satButton.getBackground().getConstantState().equals(getDrawable(R.drawable.day_unclicked).getConstantState())) {
                    satButton.setBackground(getDrawable(R.drawable.day_clicked));
                    satButton.setTextColor(getColor(R.color.colorPrimary));
                    sat = true;
                } else if (satButton.getBackground().getConstantState().equals(getDrawable(R.drawable.day_clicked).getConstantState())) {
                    satButton.setBackground(getDrawable(R.drawable.day_unclicked));
                    satButton.setTextColor(getColor(R.color.colorAccent));
                    sat = false;
                }
            }
        });

        sunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sunButton.getBackground().getConstantState().equals(getDrawable(R.drawable.day_unclicked).getConstantState())) {
                    sunButton.setBackground(getDrawable(R.drawable.day_clicked));
                    sunButton.setTextColor(getColor(R.color.colorPrimary));
                    sun = true;
                } else if (sunButton.getBackground().getConstantState().equals(getDrawable(R.drawable.day_clicked).getConstantState())) {
                    sunButton.setBackground(getDrawable(R.drawable.day_unclicked));
                    sunButton.setTextColor(getColor(R.color.colorAccent));
                    sun = false;
                }
            }
        });
    }

    private void openMainActivitySavedVersion() {
        Intent intent = new Intent();
        intent.putExtra("TIME", time);
        intent.putExtra("LABEL", label);
        intent.putExtra("DAYS", days);
        intent.putExtra("SWITCH", mswitch);
        intent.putExtra("AMOUNT", amountToPay);
        intent.putExtra("MON", mon);
        intent.putExtra("TUE", tue);
        intent.putExtra("WED", wed);
        intent.putExtra("THU", thu);
        intent.putExtra("FRI", fri);
        intent.putExtra("SAT", sat);
        intent.putExtra("SUN", sun);
        intent.putExtra("VIB", mvib);
        intent.putExtra("SOUND", msound);
        intent.putExtra("ID", alarmID);

        setResult(RESULT_OK, intent);
        finish();

    }

    private void openMainActivityCancel() {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("TIME", _time);
        intent.putExtra("LABEL", _label);
        intent.putExtra("DAYS", _days);
        intent.putExtra("SWITCH", _mswitch);
        intent.putExtra("AMOUNT", _amountToPay);
        intent.putExtra("MON", _mon);
        intent.putExtra("TUE", _tue);
        intent.putExtra("WED", _wed);
        intent.putExtra("THU", _thu);
        intent.putExtra("FRI", _fri);
        intent.putExtra("SAT", _sat);
        intent.putExtra("SUN", _sun);
        intent.putExtra("VIB", _mvib);
        intent.putExtra("SOUND", _msound);

        intent.setFlags(FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intent);
    }

    public void openDialog() {
        DescriptionDialog dialog = new DescriptionDialog();
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String description) {
        alarmLabel.setText(description);
    }

    private void saveOldStats() {
        _time = time;
        _days = days;
        _label = label;
        _msound = msound;
        _mvib = mvib;
        _mswitch = mswitch;
        _amountToPay = amountToPay;
        _mon = mon;
        _tue = tue;
        _wed = wed;
        _thu = thu;
        _fri = fri;
        _sat = sat;
        _sun = sun;
    }

    private void attachViews() {
        timePicker = findViewById(R.id.time_picker);
        timePicker.setIs24HourView(DateFormat.is24HourFormat(this));
        monButton = findViewById(R.id.button_mon);
        tueButton = findViewById(R.id.button_tue);
        wedButton = findViewById(R.id.button_wed);
        thuButton = findViewById(R.id.button_thu);
        friButton = findViewById(R.id.button_fri);
        satButton = findViewById(R.id.button_sat);
        sunButton = findViewById(R.id.button_sun);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        amountSeekBar = findViewById(R.id.seekBar);
        amountTextView = findViewById(R.id.amountTextView);
        title = findViewById(R.id.titleEditAlarm);
        alarmLabel = findViewById(R.id.alarm_label_editText);
        vib = findViewById(R.id.edit_alarm_vibrate);
        sound = findViewById(R.id.edit_alarm_sound);
    }

}