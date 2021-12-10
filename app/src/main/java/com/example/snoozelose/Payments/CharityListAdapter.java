package com.example.snoozelose.Payments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.snoozelose.R;

public class CharityListAdapter extends ArrayAdapter<Charity> {

    CharityListAdapter(Charity[] charities, Context context) {
        super(context, 0, charities);
    }

    @Override
    public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v = convertView == null ?
                LayoutInflater.from(
                        getContext()).inflate(R.layout.charity_list_item, parent, false)
                : convertView;

        Charity charity = getItem(position);

        ((TextView) v.findViewById(R.id.charityListItemTitle)).setText(charity.getName());
        ((TextView) v.findViewById(R.id.charityListItemContent)).setText(charity.getDescription());

        return v;
    }

}
