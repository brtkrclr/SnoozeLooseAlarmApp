package com.example.snoozelose.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.snoozelose.R;
import com.example.snoozelose.SharedViewModel;

public class ProfileFragment extends Fragment {

    private SharedViewModel sharedViewModel;

    private TextView debtTitle, amountDebt;

    private int DEBT;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        debtTitle = v.findViewById(R.id.debtTextView_profile);
        amountDebt = v.findViewById(R.id.amountDebt_profile);

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        DEBT = sharedViewModel.getSharedDebt();

        String tempDebt = DEBT + " SEK";
        amountDebt.setText(tempDebt);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
