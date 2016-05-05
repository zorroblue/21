package com.summerdeveloper.rameshwar.twentyone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.summerdeveloper.rameshwar.twentyone.dao.DBHelper;
import com.summerdeveloper.rameshwar.twentyone.model.Task;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by rameshwar on 3/5/16.
 */
public class CustomAdapter extends BaseAdapter {

    private ArrayList<Task> tasks;
    private Context context;
    CustomAdapter(ArrayList<Task> tasks,Context context)
    {
        this.tasks=tasks;
        this.context=context;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task=tasks.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.row,null);
        TextView taskName=(TextView)convertView.findViewById(R.id.row_taskName);
        TextView taskDate=(TextView)convertView.findViewById(R.id.row_taskDate);
        ProgressBar progressBar=(ProgressBar)convertView.findViewById(R.id.row_progress);
        Date to=new Date();
        Date from=task.getDateOfStart();
        progressBar.setProgress((task.getNoOfCompletedDays()));
        //else
        //{
            //TODO add notifications about the task failing
          //  fail();

        //}
        progressBar.setMax(21);
        progressBar.setIndeterminate(false);
        taskName.setText(task.getTaskName());
        taskDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(task.getDateOfStart()));
        return convertView;


    }
    //TODO
    //The function that resets stuff and sends notifications to the user
    public void fail()
    {

    }

    //TODO : Fix bug of the progress bar not updating.
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
