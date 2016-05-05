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

public class DetailActivity extends AppCompatActivity {

    private Task task;
    private TextView tvAmDone,tvHeading,tvNoDaysnumber;
    private CheckBox checkBox;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle=getIntent().getExtras();
        db=new DBHelper(this);
        task=db.getTaskById(bundle.getString("taskID"));
        tvHeading=(TextView)findViewById(R.id.tvHeading);
        tvHeading.setText(task.getTaskName());
        tvAmDone=(TextView)findViewById(R.id.tvAmDone);
        tvNoDaysnumber=(TextView)findViewById(R.id.tvNoDaysnumber);
        tvNoDaysnumber.setText(task.getNoOfCompletedDays().toString());
        checkBox=(CheckBox)findViewById(R.id.checkBox);
        checkBox.setChecked(db.hasMarkedForTheDay(task));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    checkBox.setEnabled(false);
                    db.incrementTask(task);
                    tvNoDaysnumber.setText(task.getNoOfCompletedDays().toString());
                }
            }
        });
        if(db.hasMarkedForTheDay(task))
        {
            //User cannot change the choice
            checkBox.setVisibility(View.INVISIBLE);
            tvAmDone.setVisibility(View.INVISIBLE);
        }
        Log.d("DEBUG",task.getNoOfCompletedDays().toString());

    }

}
