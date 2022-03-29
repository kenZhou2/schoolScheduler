package com.example.schoolscheduler;

public class Event {
    int key;
    String time;
    String message;


    public void setKey(int k){
        this.key = k;
    }
    public int getKey(){
        return key;
    }
    public void setTime(String t){
        this.time = t;
    }
    public String getTime(){
        return time;
    }
    public void setMessage(String m){
        this.message = m;
    }
    public String getMessage(){
        return message;
    }


}
