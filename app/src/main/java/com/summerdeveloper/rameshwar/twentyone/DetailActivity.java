package com.summerdeveloper.rameshwar.twentyone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.summerdeveloper.rameshwar.twentyone.dao.DBHelper;
import com.summerdeveloper.rameshwar.twentyone.model.Task;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private Intent intent;
    private Task task;
    private TextView tvEndDate,tvStartDate,tvAmDone,tvHeading,tvNoDaysnumber;
    private CheckBox checkBox;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        db = new DBHelper(this);
        task = db.getTaskById(bundle.getString("taskID"));
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        tvHeading.setText(task.getTaskName());
        tvAmDone = (TextView) findViewById(R.id.tvAmDone);
        tvNoDaysnumber = (TextView) findViewById(R.id.tvNoDaysnumber);
        tvStartDate= (TextView) findViewById(R.id.textView4);
        tvEndDate=(TextView)findViewById(R.id.textView5);
        String date=new SimpleDateFormat("dd-MM-yyyy").format(task.getDateOfStart());

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(task.getDateOfStart());
        calendar.add(Calendar.DATE,20);
        String enddate=new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
        tvEndDate.setText(new StringBuilder("End Date   :    ").append(enddate));
        tvStartDate.setText(new StringBuilder("Start Date :    ").append(date));
        tvNoDaysnumber.setText(task.getNoOfCompletedDays().toString());
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setChecked(db.hasMarkedForTheDay(task));
        //intent=new Intent();
        //intent.putExtra("position", getIntent().getExtras().getString("position"));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox.setEnabled(false);
                    db.incrementTask(task);
                    tvNoDaysnumber.setText(task.getNoOfCompletedDays().toString());
                    //                intent.putExtra("boolresult", true);
                    finishActivity(1);
                }
            }
        });
        if (db.hasMarkedForTheDay(task)) {
            //User cannot change the choice
            checkBox.setVisibility(View.INVISIBLE);
            tvAmDone.setVisibility(View.INVISIBLE);
            //  intent.putExtra("boolresult",true);

        }
        Log.d("DEBUG", task.getNoOfCompletedDays().toString());
        //setResult(RESULT_OK, intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity(1);
    }
}
