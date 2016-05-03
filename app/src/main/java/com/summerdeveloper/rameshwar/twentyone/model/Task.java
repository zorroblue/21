package com.summerdeveloper.rameshwar.twentyone.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by rameshwar on 2/5/16.
 */
public class Task {

    private Integer taskID;
    private String taskName;
    private Date dateOfStart;
    private Integer noOfCompletedDays;

    public Task()
    {
        dateOfStart=new Date();
        noOfCompletedDays=0;
    }

    public Task(Integer taskID,String taskName,Date dateOfStart,Integer noOfCompletedDays)
    {
        this.taskID=taskID;
        this.taskName=taskName;
        this.dateOfStart=dateOfStart;
        this.noOfCompletedDays=noOfCompletedDays;
    }

    public Task(String taskName,Date dateOfStart,Integer noOfCompletedDays)
    {
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

    @Override
    public String toString()
    {
        return taskName;
    }
}
