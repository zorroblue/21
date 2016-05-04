package com.summerdeveloper.rameshwar.twentyone.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.summerdeveloper.rameshwar.twentyone.model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by rameshwar on 3/5/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private final static String databaseName="Tasks.db";
    private final static String taskColumn="taskName";
    private final static String dateColumn="taskDate";
    private final static String noOfCompletedDaysColumn="noOfCompletedDays";

    public DBHelper(Context context)
    {
        super(context, databaseName, null, 1);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists tasks");
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table tasks(id varchar(100), taskDate date ,taskName varchar(40),noOfCompletedDays int, primary key(id));");
    }

    public ArrayList<Task> getAllTasks()
    {
        ArrayList<Task> result=new ArrayList<Task>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from tasks",null);
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false)
        {
            try {
                result.add(new Task(cursor.getString(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("taskName")), new SimpleDateFormat("dd-MM-yyyy").parse(cursor.getString(cursor.getColumnIndex("taskDate"))), cursor.getInt(cursor.getColumnIndex("noOfCompletedDays"))));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        return result;
    }

    public Task getTaskById(String Id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(new StringBuilder("select * from tasks where id=\'").append(Id).append("\'").toString(), null);
        cursor.moveToFirst();
        //only one task will show up
        try
        {
            return new Task(cursor.getString(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("taskName")),new SimpleDateFormat("dd-MM-yyyy").parse(cursor.getString(cursor.getColumnIndex("taskDate"))),cursor.getInt(cursor.getColumnIndex("noOfCompletedDays")));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }


    }

    //CRUD Operations
    public boolean addTask(Task t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",t.getTaskID());
        contentValues.put("taskName", t.getTaskName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        contentValues.put("taskDate", sdf.format(t.getDateOfStart()));
        contentValues.put("noOfCompletedDays", t.getNoOfCompletedDays());
        try {
            db.insert("tasks", null, contentValues);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void remove(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(new StringBuilder("delete from tasks where id =\'").append(id).append("\'").toString());

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean hasMarkedForTheDay(Task task)
    {
        Date dt2=new Date();
        int diffInDays = (int) ((dt2.getTime() - task.getDateOfStart().getTime()) / (1000 * 60 * 60 * 24));
        if(diffInDays+1==task.getNoOfCompletedDays()) //has marked
            return true;
        else
            return false;
    }
}

