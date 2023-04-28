package com.example.healthcare;

public class DoctorPaymentDataBase {
    public String PayMode, Upi, Money;

    public DoctorPaymentDataBase(){

    };

    public DoctorPaymentDataBase(String textpaymode, String textupi, String textmoney) {

        this.PayMode = textpaymode;
        this.Upi = textupi;
        this.Money = textmoney;

    }
}
