package com.bandhan.hazzatun.mytasbeeh;

import java.io.Serializable;

public class KhatamUser implements Serializable {
    String k_acc_email;
    String k_target;
    String k_count_name;
    String date;
    String myCount;

    public String getMyCount() {
        return myCount;
    }

    public void setMyCount(String myCount) {
        this.myCount = myCount;
    }

    public KhatamUser() {

    }

    public KhatamUser( String email) {
        this.k_acc_email=email;
    }
    public KhatamUser(String target, String count_name, String date) {
        this.k_target=target;
        this.k_count_name=count_name;
        this.date=date;
    }

    public KhatamUser( String email, String target, String count_name, String date, String myCount) {
        this.k_acc_email=email;
        this.k_target=target;
        this.k_count_name=count_name;
        this.date=date;
        this.myCount=myCount;
    }


    public String getK_acc_email() {
        return k_acc_email;
    }

    public void setK_acc_email(String k_acc_email) {
        this.k_acc_email = k_acc_email;
    }

    public String getK_target() {
        return k_target;
    }

    public void setK_target(String k_target) {
        this.k_target = k_target;
    }

    public String getK_count_name() {
        return k_count_name;
    }

    public void setK_count_name(String k_count_name) {
        this.k_count_name = k_count_name;
    }

   public String getDate() {
       return date;
   }

    public void setDate(String date) {
        this.date = date;
    }


}
