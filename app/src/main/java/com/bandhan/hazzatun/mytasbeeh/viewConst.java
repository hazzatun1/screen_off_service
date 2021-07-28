package com.bandhan.hazzatun.mytasbeeh;

public class viewConst {


    //private variables
    String _id;


    String _name;


    String _counts;

    String _date;


    // Empty constructor
    public viewConst() {

    }


    public viewConst(String id, String name, String counts, String date) {
        this._id = id;
        this._name = name;
        this._counts = counts;
        this._date = date;

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

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    // constructor

}
