package com.bandhan.hazzatun.mytasbeeh;

import java.io.Serializable;

public class User implements Serializable {


    //private variables
    String _id="";


    String _name="";


    String _counts;

    String _date;

    String _target;


    String _email;

    // Empty constructor
    public User() {

    }

    public User(String name) {
        this._name = name;

    }


    public User(String id, String name, String counts, String date, String target, String email) {
        this._id = id;
        this._name = name;
        this._counts = counts;
        this._date = date;
        this._target=target;
        this._email=email;

    }

    public String get_counts() {
        return _counts;
    }

    public void set_counts(String _counts) {
        this._counts = _counts;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_id() {
        return _id;
    }

    public String set_id(String _id) {
        this._id = _id;
        return _id;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_target() {
        return _target;
    }

    public void set_target(String _target) {
        this._target = _target;
    }

    public void setBackgroundColor(int parseColor) {
    }
    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    // constructor

}
