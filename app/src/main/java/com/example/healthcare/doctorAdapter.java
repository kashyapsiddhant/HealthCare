package com.example.healthcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healthcare.doctorList;

import java.util.ArrayList;

public class doctorAdapter extends ArrayAdapter<doctorList> {
    public doctorAdapter(Context context, ArrayList<doctorList> doctorlistsvar){
        super(context,0,doctorlistsvar);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }
    private View initView(int pos, View convertView, ViewGroup parent){
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.doctorlistview,parent,false);
        }
        ImageView imgViewFlag = convertView.findViewById(R.id.doc_pic);
        TextView textViewName = convertView.findViewById(R.id.doctor);
        doctorList currentItem=getItem(pos);
        if(currentItem!=null)
        {
            imgViewFlag.setImageResource(currentItem.getDoc_Img());
            textViewName.setText(currentItem.getDoc_Name());
        }

        return convertView;
    }
}