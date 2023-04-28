package com.example.healthcare;

//write and retreive data, we use this constructor

public class readwriteuserappointment {
    public String DateBooked, TimeBooked, DrBooked,PayMode, Upi, Money;

    public readwriteuserappointment(){

    };

    public readwriteuserappointment(String textdate, String texttime, String textdoctor, String textpaymode, String textupi, String textmoney) {

        this.PayMode = textpaymode;
        this.Upi = textupi;
        this.Money = textmoney;
        this.DateBooked = textdate;
        this.TimeBooked = texttime;
        this.DrBooked = textdoctor;
    }
}