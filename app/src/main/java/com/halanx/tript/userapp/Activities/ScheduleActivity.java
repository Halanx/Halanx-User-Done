package com.halanx.tript.userapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.halanx.tript.userapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class ScheduleActivity extends AppCompatActivity {

    Calendar startDate,endDate;

    RadioGroup group,rg1,rg2,rg3;
    HorizontalCalendar horizontalCalendar;
    RadioButton t1,t2,t3,t4,t5,t6;

    Button done;
    String date_array;

    String date_selected,time_selected;
    Boolean flagselected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        group = (RadioGroup) findViewById(R.id.rg);
        rg1 = (RadioGroup) findViewById(R.id.rg1);
        rg2 = (RadioGroup) findViewById(R.id.rg2);
        rg3 = (RadioGroup) findViewById(R.id.rg3);

        endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, 10);

/** start before 1 month from now */
        startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 0);


        done = (Button) findViewById(R.id.button_done);
        t1 = (RadioButton) findViewById(R.id.t1);
        t2 = (RadioButton) findViewById(R.id.t2);
        t3 = (RadioButton) findViewById(R.id.t3);
        t4 = (RadioButton) findViewById(R.id.t4);
        t5 = (RadioButton) findViewById(R.id.t5);
        t6 = (RadioButton) findViewById(R.id.t6);





        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t1.isChecked()) {
                    check();
                    flagselected = false;
                    t1.setChecked(true);
                    t1.setBackgroundColor(Color.RED);
                    time_selected = String.valueOf(t1.getText());
                }
                else{
                    flagselected = true;
                    t1.setChecked(false);
                    t1.setBackgroundColor(Color.WHITE);
                    time_selected = null;

                }
            }
        }); t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t2.isChecked()) {
                    check();
                    flagselected = false;
                    t2.setChecked(true);
                    t2.setBackgroundColor(Color.RED);
                    time_selected = String.valueOf(t2.getText());
                }
                else{
                    flagselected = true;
                    t2.setChecked(false);
                    t2.setBackgroundColor(Color.WHITE);
                    time_selected = null;

                }
            }
        }); t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t3.isChecked()) {
                    check();
                    flagselected = false;
                    t3.setChecked(true);
                    t3.setBackgroundColor(Color.RED);

                    time_selected = String.valueOf(t3.getText());
                }
                else{
                    flagselected = true;
                    t3.setChecked(false);
                    t3.setBackgroundColor(Color.WHITE);
                    time_selected = null;

                }
            }
        }); t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t4.isChecked()) {
                    check();
                    flagselected = false;
                    t4.setChecked(true);
                    t4.setBackgroundColor(Color.RED);

                    time_selected = String.valueOf(t4.getText());
                }
                else{
                    flagselected = true;
                    t4.setChecked(false);
                    t4.setBackgroundColor(Color.WHITE);
                    time_selected = null;

                }
            }
        });
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t5.isChecked()) {
                    check();
                    flagselected = false;
                    t5.setChecked(true);
                    t5.setBackgroundColor(Color.RED);

                    time_selected = String.valueOf(t5.getText());
                }
                else{
                    flagselected = true;
                    t5.setChecked(false);
                    t5.setBackgroundColor(Color.WHITE);
                    time_selected = null;

                }
            }
        });
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t6.isChecked()) {
                    check();
                    flagselected = false;
                    t6.setChecked(true);
                    t6.setBackgroundColor(Color.RED);

                    time_selected = String.valueOf(t6.getText());
                }
                else{
                    flagselected = true;
                    t6.setChecked(false);
                    t6.setBackgroundColor(Color.WHITE);
                    time_selected = null;

                }
            }
        });


        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.datePicker)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {

                date_selected = new SimpleDateFormat("yyyy-MM-dd").format(date);
                Log.d("datevalue",date_selected);


                //do something
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time_selected != null) {


                    Intent intent = new Intent(ScheduleActivity.this, CartActivity.class);
                    intent.putExtra("date", date_selected);
                    intent.putExtra("time_selected", time_selected);
                    Log.d("timingsdata", String.valueOf(date_selected));
                    Log.d("datedata", String.valueOf(time_selected));

                    setResult(RESULT_OK, intent);
                    finish();
                }

                else{
                    Toast.makeText(getApplicationContext(),"Select Suitable Timings",Toast.LENGTH_SHORT).show();
                }


            }
        });



    }
    public void check(){

        if (!flagselected ){
            t1.setChecked(false);
            t1.setBackgroundColor(Color.WHITE);t2.setChecked(false);
            t2.setBackgroundColor(Color.WHITE);t3.setChecked(false);
            t3.setBackgroundColor(Color.WHITE);t4.setChecked(false);
            t4.setBackgroundColor(Color.WHITE);t5.setChecked(false);
            t5.setBackgroundColor(Color.WHITE);t6.setChecked(false);
            t6.setBackgroundColor(Color.WHITE);
        }


    }


}
