package com.example.healthcare;

public class doctorList {
    private String name_doc;
    private int img_doc;
    public doctorList(String n_doc, int img){
        name_doc=n_doc;
        img_doc=img;
    }
    public String getDoc_Name(){
        return name_doc;
    }
    public int getDoc_Img(){
        return img_doc;
    }
}
