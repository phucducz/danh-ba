package com.gameloft.sqlite;

import java.io.Serializable;

/**
 * Created by USER on 19/10/2017.
 */

public class Contact implements Serializable {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String date;
    private String time;
    private String gender;

    public Contact(){

    }

    public Contact(int id, String name, String phone, String address, String date, String time, String gender){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.time = time;
        this.gender = gender;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getGender(){
        return gender;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
}
