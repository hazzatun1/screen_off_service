package com.bandhan.hazzatun.mytasbeeh;

import android.content.Context;

public class viewConst {


    //private variables
    String _id;



    String _name;


    String _counts;


    // Empty constructor
    public viewConst(){

    }
    public viewConst(String id, String name, String counts){
        this._id=id;
        this._name = name;
        this._counts = counts;

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




    // constructor

}
