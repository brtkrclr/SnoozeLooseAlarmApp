package com.example.snoozelose.Payments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.snoozelose.R;

public class ConfirmCharityDialogFragment extends DialogFragment {

    private class DonateAmountSeekBar implements SeekBar.OnSeekBarChangeListener {

        int debt;
        int amount;

        TextView amountText;
        TextView remainingText;

        DonateAmountSeekBar(SeekBar seekBar, TextView amountText, TextView remainingText, int debt) {
            this.debt = debt;
            this.amount = debt;
            this.amountText = amountText;
            this.remainingText = remainingText;

            seekBar.setMax(debt);

            seekBar.setOnSeekBarChangeListener(this);
            seekBar.setProgress(debt);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            this.amount = progress;
            amountText.setText("Donating: " + progress + " SEK");

            int remaining = debt - progress;
            remainingText.setText("Remaining debt: " + (remaining > 0 ? (remaining + " SEK") : "none!"));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    }

    private final Charity charity;
    private final int debt;

    public interface CharityDialogListener {
        public void onConfirm(int donateAmount);
    }

    private CharityDialogListener listener;

    ConfirmCharityDialogFragment(Charity charity, int debt, CharityDialogListener listener) {
        this.charity = charity;
        this.debt = debt;
        this.listener = listener;
    }

    @Override
    public @NonNull Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.charity_confirm_dialog, null);
        final DonateAmountSeekBar seekBar = new DonateAmountSeekBar(
                (SeekBar) view.findViewById(R.id.donateAmountSeekBar),
                (TextView) view.findViewById(R.id.donationTextView),
                (TextView) view.findViewById(R.id.remainingDebtTextView),
                debt);

        builder.setView(view);
        builder.setTitle("Confirm donation to " + charity.getName());
        builder.setPositiveButton("Donate with Swoosh", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onConfirm(seekBar.amount);
            }
        });
        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }

}
