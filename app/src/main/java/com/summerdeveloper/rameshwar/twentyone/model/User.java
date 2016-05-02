package com.summerdeveloper.rameshwar.twentyone.model;

import java.util.ArrayList;

/**
 * Created by rameshwar on 2/5/16.
 */
public class User {

    private String userName;
    private ArrayList<Task> tasks;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public User()
    {
        userName="ABC";
        tasks=new ArrayList<Task>();
    }
}
