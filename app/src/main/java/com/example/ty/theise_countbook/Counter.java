package com.example.ty.theise_countbook;

import java.util.Date;

/**
 * Created by Ty on 9/21/2017.
 */


//counter has name date current value initial value comment
public class Counter {

    /*public Counter(String name, int inValue){
        this.name = name;
        this.inValue = inValue;
        this.date = new Date();
        currValue = inValue;
        comment = null;
    }*/

    public Counter(String name, int inValue, String comment) {
        this.name = name;
        this.inValue = inValue;
        this.date = new Date();
        currValue = inValue;
        this.comment = comment;
    }

    private String name;
    private Date date;
    private int currValue;
    private int inValue;
    private String comment;

    @Override
    public String toString() {
        return "Name: " + name + "\n" + date.toString() + "\nCurrent Value: " + currValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public int getCurrValue() {
        return currValue;
    }

    public void setCurrValue(int currValue) {
        this.currValue = currValue;
        date = new Date();
    }

    public int getInValue() {
        return inValue;
    }

    public void setInValue(int inValue) {
        this.inValue = inValue;
        date = new Date();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        date = new Date();
    }

    public void increment() {
        currValue += 1;
        date = new Date();
    }

    public void decrement() {
        if (currValue > 0) {
            currValue -= 1;
            date = new Date();
        }
    }

    public void reset() {
        currValue = inValue;
        date = new Date();
    }

}
