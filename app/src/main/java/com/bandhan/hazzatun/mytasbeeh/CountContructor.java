package com.bandhan.hazzatun.mytasbeeh;

public class CountContructor {
    int _id;


    int _count;


    String _name;

    public CountContructor() {
    }

    public CountContructor(int id, String name, int count){
this._id=id;
this._name=name;
this._count=count;
    }
    public CountContructor( String name, int count){

        this._name=name;
        this._count=count;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }


    public int get_count() {
        return _count;
    }

    public void set_count(int _count) {
        this._count = _count;
    }
}
