package app.com.ieeedtu;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    String topic, date, month, time, loc, desc, coordiName, coordiCont;
    TextView tvLoc, tvTime, tvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        topic = getIntent().getStringExtra("Topic");
        date = getIntent().getStringExtra("Date");
        month = getIntent().getStringExtra("Month");
        time = getIntent().getStringExtra("Time");
        loc = getIntent().getStringExtra("Loc");
        desc = getIntent().getStringExtra("Desc");
        coordiName = getIntent().getStringExtra("CoordiName");
        coordiCont = getIntent().getStringExtra("CoordiCon");
        setTitle(topic);

        tvLoc = (TextView) findViewById(R.id.tv_loc);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvLoc.setText(loc);
        tvDesc.setText(desc);

        if (time != null && !time.isEmpty()) {
            tvTime.setText("Date : " + date + "/" + month + " " + "Time : " + time);
        }
        else {
            tvTime.setText("Date : " + date + "/" + month);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = "Contact coordinator " + coordiName;
                AlertDialog dial = new AlertDialog.Builder(DetailsActivity.this).setTitle("Contact").setMessage(message)
                        .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialPhoneNumber(coordiCont);

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create();

                dial.show();

            }
        });
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
