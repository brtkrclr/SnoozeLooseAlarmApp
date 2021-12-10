package com.example.snoozelose.Payments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.snoozelose.MainActivity;
import com.example.snoozelose.R;
import com.example.snoozelose.SharedViewModel;

import static android.content.Context.MODE_PRIVATE;

public class PaymentsFragment extends Fragment {

    private SharedViewModel viewModel;

    private TextView debtText;


    public static final String EX_NUMB = "com.example.snoozelose.Payments.PaymentsFragment.EX_NUMB";
    public static final String EX_STRING = "com.example.snoozelose.Payments.PaymentsFragment.EX_STRING";

    private void showDialog(final Charity selectedCharity) {
        if(viewModel.getSharedDebt()!=0) {
            DialogFragment fragment = new ConfirmCharityDialogFragment(selectedCharity, viewModel.getSharedDebt(), new ConfirmCharityDialogFragment.CharityDialogListener() {
                @Override
                public void onConfirm(int donateAmount) {
                    openSwoosh(donateAmount, selectedCharity.getName());

                    Log.i("charity", "Donation of " + donateAmount + " confirmed to " + selectedCharity.getName());
                }
            });


            fragment.show(getFragmentManager(), "dialog");
        }
        else{
            final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setCancelable(true);
            Log.i("debbie", "" + viewModel.getSharedDebt());

            builder.setTitle("Debt free!");
            builder.setIcon(R.drawable.ic_check_black_24dp);
            builder.setMessage("You have no debt so you can't donate! Good job!");

            builder.create().show();

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_payments, container, false);

        debtText = v.findViewById(R.id.debtTextView);


        ListView charityList = v.findViewById(R.id.charityListView);
        charityList.setAdapter(new CharityListAdapter(Charity.dummyCharities, getContext()));

        charityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //charity selected
                showDialog(Charity.dummyCharities[position]);
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        viewModel.getLiveSharedDebt().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                debtText.setText("Remaining debt: " + integer + " SEK");
            }
        });
    }

    public void openSwoosh(int real, String charity){
        Intent intent = new Intent(getActivity(), com.example.snoozelose.Payments.swoosh.class);
        intent.putExtra(EX_STRING, charity);
        intent.putExtra(EX_NUMB, real);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("dab", "onActivityResult");

        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setCancelable(true);

        if(data == null) {
            builder.setTitle("Failure");
            builder.setIcon(R.drawable.ic_error_outline_black_24dp);
            builder.setMessage("Something went wrong, try again!");
            builder.create().show();
            //todo: error message
            return;
        }
        builder.setTitle("Success!");
        builder.setIcon(R.drawable.ic_check_black_24dp);
        String name = data.getStringExtra(swoosh.EX_STRING1);
        int number = data.getIntExtra(swoosh.EX_NUMB1, 0);
        builder.setMessage("Thank you for donating " + number +  " SEK to " + name);

        builder.create().show();
        Log.i("payment result", "Payment result from " + name + " of " + number);

        int current = viewModel.getSharedDebt() - number;

        Log.i("currentD", "" + current);

        save(viewModel, current);


    }

    public void save(SharedViewModel v, int debt) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("debt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("debtAmount" , debt);
        Log.d("Trial","SAVED as: " + debt);
        editor.apply();
    }
}
