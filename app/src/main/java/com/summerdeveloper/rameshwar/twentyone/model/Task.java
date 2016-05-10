package com.summerdeveloper.rameshwar.twentyone.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by rameshwar on 2/5/16.
 */
public class Task {

    private String taskID;
    private String taskName;
    private Date dateOfStart;

    public String getTaskID() {
        return taskID;
    }

    private Integer noOfCompletedDays;

    public Task()
    {
        dateOfStart=new Date();
        noOfCompletedDays=0;
    }

    public Task(String taskID,String taskName,Date dateOfStart,Integer noOfCompletedDays)
    {
        this.taskID=taskID;
        this.taskName=taskName;
        this.dateOfStart=dateOfStart;
        this.noOfCompletedDays=noOfCompletedDays;
    }

    public Task(String taskName,Date dateOfStart,Integer noOfCompletedDays)
    {
        taskID=UUID.randomUUID().toString();
        this.taskName=taskName;
        this.dateOfStart=dateOfStart;
        this.noOfCompletedDays=noOfCompletedDays;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(Date dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public Integer getNoOfCompletedDays() {
        return noOfCompletedDays;
    }

    public void setNoOfCompletedDays(Integer noOfCompletedDays) {
        this.noOfCompletedDays = noOfCompletedDays;
    }

    /*specialised methods */

    public boolean isTaskCompleted()
    {
        if(noOfCompletedDays==21)
            return true;
        return false;
    }

    public void incrementDay()
    {

        noOfCompletedDays++;
    }

    public boolean hasMarkedForTheDay()
    {
        Date dt2=new Date();
        int diffInDays = (int) ((dt2.getTime() - this.getDateOfStart().getTime()) / (1000 * 60 * 60 * 24));
        if(diffInDays+1<=this.getNoOfCompletedDays()) //has marked
            return true;
        else
            return false;
    }

    @Override
    public String toString()
    {
        return taskName;
    }
}
