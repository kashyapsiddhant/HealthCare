package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.healthcare.databinding.ActivityConsultationBinding;
import com.example.healthcare.databinding.ActivityDoctorPaymentsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class DoctorPayments extends AppCompatActivity {

    // popup window
    LinearLayout linearLayout;


    // Add to firebase attribute
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    UserProfileChangeRequest userProfileChangeRequest;

    TextView  upidata, moneydata;
    // spinner already added with id DoctorName findview by id so dont add here again

    Button bookdr;
    String strdoctor, strpaymentmode, strupi, intmoney;

    // Add to firebase attribute
    public ActivityDoctorPaymentsBinding binding;


    Spinner spinPaymentMode;
    private ArrayList<paymentModeList> paymentList;
    private paymentAdapter payAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorPaymentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        linearLayout = findViewById(R.id.linear);

        // paymentmode list spinner

        paymentList = new ArrayList<>();

        paymentList.add(new paymentModeList("UPI", R.drawable.payupi));
        paymentList.add(new paymentModeList("Net Banking", R.drawable.paynetbanking));
        paymentList.add(new paymentModeList("Card Payment", R.drawable.paycard));


        spinPaymentMode = findViewById(R.id.paymentmode);

        payAdapter = new paymentAdapter(this, paymentList);
        spinPaymentMode.setAdapter(payAdapter);

        //select paymentmode

        spinPaymentMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                paymentModeList pay_lst = (paymentModeList) parent.getItemAtPosition(position);
                strpaymentmode = pay_lst.getPay_Mode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // paymentmode list spinner



        // data add to firebase
        upidata = findViewById(R.id.upiid);
        moneydata = findViewById(R.id.moneyinrs);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        bookdr = findViewById(R.id.makepayment);

        bookdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strupi = upidata.getText().toString();
                intmoney = "1050";

                //main code
                if (!strupi.isEmpty() && !strpaymentmode.isEmpty() && !intmoney.isEmpty()) {

                    FirebaseUser mUser = mAuth.getCurrentUser();
                    userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(strdoctor).build();
                    mUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                DoctorPaymentDataBase writeuserdata = new DoctorPaymentDataBase(strpaymentmode, strupi, intmoney);
                                DatabaseReference profilereference = FirebaseDatabase.getInstance().getReference("Doctor Payment Data");
                                profilereference.child(mUser.getUid()).setValue(writeuserdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            popUpWindow();
                                            // pop up window

                                        } else {
                                            Toast.makeText(DoctorPayments.this, "Payment Failed! Please Try Again", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });
                                // User Data, add to Firebase Attributes
                            } else {
                                Toast.makeText(DoctorPayments.this, "Payment Data not added to firebase, please try again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

                else{
                    Toast.makeText(DoctorPayments.this, "Complete all Fields!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void popUpWindow() {

        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView=inflater.inflate(R.layout.popupmain_drpay,null);

        int width=ViewGroup.LayoutParams.MATCH_PARENT;
        int height=ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable=true;
        PopupWindow popupWindow=new PopupWindow(popUpView,width,height,focusable);
        linearLayout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(linearLayout,Gravity.BOTTOM,0,0);

            }
        });
        TextView Skip ,Gotit;
        Skip=popUpView.findViewById(R.id.Skip);
        Gotit=popUpView.findViewById(R.id.Gotit);
        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        Gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write code anything you want
                popupWindow.dismiss();
                sendUserToNextActivity();
            }
        });
        // and if you want to close popup when touch Screen
    }


    private void sendUserToNextActivity(){
        Intent intent = new Intent(DoctorPayments.this, HomedrActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    // User Register Attributes

}