package com.example.healthcare;

public class paymentModeList {
    private String pay_mode;
    private int pay_img;
    public paymentModeList(String n_pay, int n_img){
        pay_mode=n_pay;
        pay_img=n_img;
    }
    public String getPay_Mode(){

        return pay_mode;
    }
    public int getPay_Img(){

        return pay_img;
    }
}