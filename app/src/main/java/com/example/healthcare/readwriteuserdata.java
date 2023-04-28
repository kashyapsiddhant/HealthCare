package com.example.healthcare;

//write and retreive data, we use this constructor

public class readwriteuserdata {
    public String FirstName, AadharNumber, PhoneNumber, LastName;

    public readwriteuserdata(){};

    public readwriteuserdata(String textfirstname, String textaadharnumber, String textphonenumber, String textlastname) {

        this.FirstName = textfirstname;
        this.AadharNumber = textaadharnumber;
        this.PhoneNumber = textphonenumber;
        this.LastName = textlastname;
    }
}
