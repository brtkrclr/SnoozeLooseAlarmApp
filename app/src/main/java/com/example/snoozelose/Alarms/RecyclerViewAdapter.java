package com.example.snoozelose.Alarms;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snoozelose.Alarms.Alarms;
import com.example.snoozelose.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ExampleViewHolder> {
    private List<Alarms> mAlarmList = new ArrayList<>();
    private OnItemClickListener mListener;

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, final int position) {
        Alarms currentItem = mAlarmList.get(position);

        holder.mSwitch.setChecked(currentItem.getmSwitch());
        holder.mLabel.setText(currentItem.getmLabel());
        holder.mDates.setText(currentItem.getmDays());
        holder.mTime.setText(currentItem.getTime());
        holder.mAmount.setText(Integer.toString(currentItem.getAmountToPay()) + " SEK");

    }

    @Override
    public int getItemCount() {
        return mAlarmList.size();
    }

    public void setAlarmList(List<Alarms> alarmsList) {
        this.mAlarmList = alarmsList;
        notifyDataSetChanged();
    }

    public Alarms getAlarmAt(int position) {
        return mAlarmList.get(position);
    }

    class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mLabel, mDates, mTime,mAmount;
        public Switch mSwitch;

        public ExampleViewHolder(final View itemView) {
            super(itemView);
            mLabel = itemView.findViewById(R.id.alarm_label);
            mDates = itemView.findViewById(R.id.alarm_days);
            mTime = itemView.findViewById(R.id.alarm_time);
            mSwitch = itemView.findViewById(R.id.alarm_switch);
            mAmount=itemView.findViewById(R.id.text_view_amount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(mAlarmList.get(position));
                    }
                }
            });

            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION)
                        mListener.onSwitchChange(mAlarmList.get(position),b);
                    }
            });

        }
    }

    public interface OnItemClickListener{
        void onItemClick(Alarms clickedAlarm);
        void onSwitchChange(Alarms clickedAlarm, boolean b);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }

}
