package com.example.snoozelose.Alarms;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snoozelose.EditAlarmActivity.EditAlarmActivity;
import com.example.snoozelose.R;
import com.example.snoozelose.SharedViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class AlarmsFragment extends Fragment {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private AlarmViewModel alarmViewModel;
    private SharedViewModel sharedViewModel;

    private ImageButton addButton;
    private RecyclerView recyclerView;
    private TextView noAlarms,addAlarm;
    private Alarms deletedAlarm = null;

    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarms, container, false);

        addButton = v.findViewById(R.id.imageButton);
        recyclerView = v.findViewById(R.id.recyclerView_alarms);
        noAlarms = v.findViewById(R.id.noAlarms);
        addAlarm = v.findViewById(R.id.addAlarms);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(mLayoutManager);

        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);

        alarmViewModel.getAllAlarms().observe(this, new Observer<List<Alarms>>() {
            @Override
            public void onChanged(List<Alarms> alarms) {
                mAdapter.setAlarmList(alarms);
                if(alarms.isEmpty()) {
                    noAlarms.setVisibility(View.VISIBLE);
                    addAlarm.setVisibility(View.VISIBLE);
                }
                else {
                    noAlarms.setVisibility(View.INVISIBLE);
                    addAlarm.setVisibility(View.INVISIBLE);
                }
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,@NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                    deletedAlarm = mAdapter.getAlarmAt(position);
                    alarmViewModel.delete(mAdapter.getAlarmAt(position));
                    Snackbar.make(recyclerView, "Deleted alarm: " + deletedAlarm.getTime(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alarmViewModel.insert(deletedAlarm, new AlarmRepository.InsertAlarmResponse() {
                                @Override
                                public void alarmInserted(int id) {
                                    deletedAlarm.setAlarmID(id);
                                    deletedAlarm.scheduleAlarms(getActivity());
                                }
                            });
                            Toast.makeText(getActivity(), "Alarm Restored", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                    deletedAlarm.descheduleAlarms(getContext());
                }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(getActivity()!=null)
                    new RecyclerViewSwipeDecorator.Builder(getActivity(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccentLow))
                            .addActionIcon(R.drawable.ic_delete_black_24dp)
                            .create()
                            .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);



        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Alarms clickedAlarm) {
                openAlarmActivityEdit(clickedAlarm);
            }

            @Override
            public void onSwitchChange(Alarms clickedAlarm,boolean b) {
                clickedAlarm.setmSwitch(b);
                alarmViewModel.update(clickedAlarm);

                if(b)
                    clickedAlarm.scheduleAlarms(getContext());
                    else
                        clickedAlarm.descheduleAlarms(getContext());

                //Toast.makeText(getContext(), "yay switched :D", Toast.LENGTH_SHORT).show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("Trial","CLICKED");
                openAlarmActivityAdd();
            }
        });

        return v;
    }

    private void openAlarmActivityEdit(Alarms clickedAlarm)
    {
        String currTime = clickedAlarm.getTime();
        String currLabel = clickedAlarm.getmLabel();
        String currDays = clickedAlarm.getmDays();
        boolean currSwitch = clickedAlarm.getmSwitch();
        int currAmountToPay = clickedAlarm.getAmountToPay();
        boolean currMon = clickedAlarm.getMon();
        boolean currTue = clickedAlarm.getTue();
        boolean currWed = clickedAlarm.getWed();
        boolean currThu = clickedAlarm.getThu();
        boolean currFri = clickedAlarm.getFri();
        boolean currSat = clickedAlarm.getSat();
        boolean currSun = clickedAlarm.getSun();
        boolean vib = clickedAlarm.getVib();
        boolean sound = clickedAlarm.getSound();

        Intent intent = new Intent(getActivity(), EditAlarmActivity.class);
        intent.putExtra("TYPE","Edit Alarm");

        intent.putExtra("ID",clickedAlarm.alarmID);
        intent.putExtra("TIME",currTime);
        intent.putExtra("LABEL",currLabel);
        intent.putExtra("DAYS",currDays);
        intent.putExtra("SWITCH",currSwitch);
        intent.putExtra("AMOUNT",currAmountToPay);
        intent.putExtra("MON",currMon);
        intent.putExtra("TUE",currTue);
        intent.putExtra("WED",currWed);
        intent.putExtra("THU",currThu);
        intent.putExtra("FRI",currFri);
        intent.putExtra("SAT",currSat);
        intent.putExtra("SUN",currSun);
        intent.putExtra("VIB",vib);
        intent.putExtra("SOUND",sound);

        startActivityForResult(intent,EDIT_NOTE_REQUEST);
    }

    private void openAlarmActivityAdd() {
        Intent intent = new Intent(getActivity(), EditAlarmActivity.class);
        intent.putExtra("TYPE","Add Alarm");
        startActivityForResult(intent,ADD_NOTE_REQUEST);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity()!= null)
            sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(resultCode != getActivity().RESULT_OK) {
            Toast.makeText(getActivity(), "Alarm NOT Saved", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == ADD_NOTE_REQUEST) {
            Boolean mon,tue,wed,thu,fri,sat,sun,mswitch,mvib,msound;
            int amountToPay;
            String time,label;

            mon = intent.getBooleanExtra("MON",false);
            tue = intent.getBooleanExtra("TUE",false);
            wed = intent.getBooleanExtra("WED",false);
            thu = intent.getBooleanExtra("THU",false);
            fri = intent.getBooleanExtra("FRI",false);
            sat = intent.getBooleanExtra("SAT",false);
            sun = intent.getBooleanExtra("SUN",false);
            mswitch = intent.getBooleanExtra("SWITCH",false);
            mvib = intent.getBooleanExtra("VIB",false);
            msound = intent.getBooleanExtra("SOUND",false);
            amountToPay = intent.getIntExtra("AMOUNT",0);
            time = intent.getStringExtra("TIME");
            label = intent.getStringExtra("LABEL");


            final Alarms newAlarm = new Alarms(time,label,amountToPay,mswitch,msound,mvib,mon,tue,wed,thu,fri,sat,sun);
            alarmViewModel.insert(newAlarm, new AlarmRepository.InsertAlarmResponse() {
                @Override
                public void alarmInserted(int id) {
                    newAlarm.setAlarmID(id);
                    newAlarm.scheduleAlarms(getActivity());
                }
            });
            //mAdapter.notifyDataSetChanged();
        }
        else if (requestCode == EDIT_NOTE_REQUEST) {
            Boolean mon,tue,wed,thu,fri,sat,sun,mswitch,mvib,msound;
            int amountToPay, alarmID;
            String time,label;

            alarmID = intent.getIntExtra("ID", -1);
            if (alarmID == -1) {
                Toast.makeText(getActivity(), "Alarm can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            mon = intent.getBooleanExtra("MON",false);
            tue = intent.getBooleanExtra("TUE",false);
            wed = intent.getBooleanExtra("WED",false);
            thu = intent.getBooleanExtra("THU",false);
            fri = intent.getBooleanExtra("FRI",false);
            sat = intent.getBooleanExtra("SAT",false);
            sun = intent.getBooleanExtra("SUN",false);
            mswitch = intent.getBooleanExtra("SWITCH",false);
            mvib = intent.getBooleanExtra("VIB",false);
            msound = intent.getBooleanExtra("SOUND",false);
            amountToPay = intent.getIntExtra("AMOUNT",0);
            time = intent.getStringExtra("TIME");
            label = intent.getStringExtra("LABEL");


            Alarms newAlarm = new Alarms(time,label,amountToPay,mswitch,msound,mvib,mon,tue,wed,thu,fri,sat,sun);
            newAlarm.setAlarmID(alarmID);
            newAlarm.scheduleAlarms(getActivity());
            alarmViewModel.update(newAlarm);
        }
    }
}
