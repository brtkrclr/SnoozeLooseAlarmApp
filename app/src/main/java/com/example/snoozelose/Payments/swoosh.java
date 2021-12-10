package com.example.snoozelose.Payments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.*;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.snoozelose.MainActivity;
import com.example.snoozelose.R;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.snoozelose.Payments.PaymentsFragment.EX_NUMB;
import static com.example.snoozelose.Payments.PaymentsFragment.EX_STRING;

public class swoosh extends AppCompatActivity {

    private int progressTime = 0;
    private ProgressBar spinner;
    Handler myHandler = new Handler();

    private static final String INTENT_PATH = "com.example.snoozelose.Payments.swoosh.";

    public static final String EX_NUMB1 = INTENT_PATH + "EX_NUMB1";
    public static final String EX_STRING1 = INTENT_PATH + "EX_STRING1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swoosh);

        Intent intent = getIntent();
        final String charity = intent.getStringExtra(EX_STRING);

        final int number = intent.getIntExtra(EX_NUMB, 0);

        final TextView numberView = (TextView) findViewById(R.id.numb);
        numberView.setText("" + number +"kr" + " " + "till" + " " + charity);



        Button pay = findViewById(R.id.pay);

        pay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(charity);
                builder.setMessage("Vänta medans betalningen pågår...");
                builder.setCancelable(true);

                View ProgressView = getLayoutInflater().inflate(R.layout.alert_dialog,
                        null);
                spinner=(ProgressBar)ProgressView.findViewById(R.id.progressBar);

                spinner.setMax(10);
                builder.setView(ProgressView);
                final AlertDialog alert = builder.create();
                alert.show();


                new CountDownTimer(5000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onFinish() {
                        // TODO Auto-generated method stub

                        alert.dismiss();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(EX_STRING1, charity);
                        resultIntent.putExtra(EX_NUMB1, number);
                        setResult(0, resultIntent);
                        finish();


                    }
                }.start();



            }
        });



    }

}
