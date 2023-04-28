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

import java.util.ArrayList;

public class paymentAdapter extends ArrayAdapter<paymentModeList> {
    public paymentAdapter(Context context, ArrayList<paymentModeList> paymentModevar){
        super(context,0,paymentModevar);
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
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.paymentmode_list,parent,false);
        }
        ImageView imgViewFlag = convertView.findViewById(R.id.pay_img);
        TextView textViewName = convertView.findViewById(R.id.payment_mode);
        paymentModeList currentItem=getItem(pos);
        if(currentItem!=null)
        {
            imgViewFlag.setImageResource(currentItem.getPay_Img());
            textViewName.setText(currentItem.getPay_Mode());
        }

        return convertView;
    }
}
