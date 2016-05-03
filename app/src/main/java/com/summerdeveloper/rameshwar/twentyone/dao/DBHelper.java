package com.summerdeveloper.rameshwar.twentyone.dao;

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

        db.execSQL("create table tasks(id INT AUTO_INCREMENT, taskDate date ,taskName varchar(40),noOfCompletedDays int, primary key(id));");
    }

    public ArrayList<Task> getAllTasks()
    {
        ArrayList<Task> result=new ArrayList<Task>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from tasks",null);
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false)
        {
            result.add(new Task(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("taskName")),new Date(cursor.getString(cursor.getColumnIndex("taskDate"))),cursor.getInt(cursor.getColumnIndex("noOfCompletedDays"))));
            cursor.moveToNext();
        }
        return result;
    }

    public Task getTaskById(Integer Id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(new StringBuilder("select * from tasks where id=").append(Id).toString(), null);
        cursor.moveToFirst();
        //only one task will show up
        return new Task(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("taskName")),new Date(new SimpleDateFormat("dd-mm-yyyy").format(cursor.getString(cursor.getColumnIndex("taskDate")))),cursor.getInt(cursor.getColumnIndex("noOfCompletedDays")));

    }

    //CRUD Operations
    public boolean addTask(Task t) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL(new StringBuilder("insert into tasks (taskName,taskDate,noOfCompletedDays) values(").append(t.getTaskName()).append(",").append(t.getDateOfStart()).append(",").append(t.getNoOfCompletedDays()).append(";").toString());
            return true;
        }catch(Exception e)
        {
            return false;
        }
    }
}
