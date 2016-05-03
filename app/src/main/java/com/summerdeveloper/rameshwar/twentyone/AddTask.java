package com.summerdeveloper.rameshwar.twentyone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.summerdeveloper.rameshwar.twentyone.dao.DBHelper;
import com.summerdeveloper.rameshwar.twentyone.model.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTask extends AppCompatActivity {

    private Button add;
    private EditText date,taskName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        add=(Button)findViewById(R.id.button);
        date=(EditText)findViewById(R.id.etDate);
        taskName=(EditText)findViewById(R.id.etName);
        final DBHelper db=new DBHelper(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(date.getText().length()==0 || taskName.getText().length()==0)
                {
                    //put a dialog here
                    //TODO
                    return;
                }
                else
                {

                    try
                    {
                        Date d=new Date(new SimpleDateFormat("dd-mm-yyyy").parse(date.getText().toString());
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();;boolean status=db.addTask(new Task(taskName.getText().toString(),d),0)
                    });
                    if(status==true)
                    {
                        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        //TODO
                        //Dialog here
                    }
                }

            }
        });
    }

}
