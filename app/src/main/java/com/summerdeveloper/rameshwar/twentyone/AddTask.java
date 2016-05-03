package com.summerdeveloper.rameshwar.twentyone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


        add=(Button)findViewById(R.id.button);
        taskName=(EditText)findViewById(R.id.etName);
        final DBHelper db=new DBHelper(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskName.getText().length()==0)
                {
                    Toast.makeText(AddTask.this,"Please enter proper text",1000).show();
                    return;
                }
                else
                {
                    boolean status=db.addTask(new Task(taskName.getText().toString(),new Date(),0));
                    if(status==true)
                    {
                        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        new AlertDialog.Builder(AddTask.this)
                                .setTitle("Alert")
                                .setMessage("There was an error in adding the task.Please enter the details properly.")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                })
                                .show();
                    }
                }

            }
        });
    }

}
