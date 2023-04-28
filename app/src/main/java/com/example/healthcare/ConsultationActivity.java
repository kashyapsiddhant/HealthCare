package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.healthcare.databinding.ActivityConsultationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class ConsultationActivity extends AppCompatActivity {

    // Add to firebase attribute
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    UserProfileChangeRequest userProfileChangeRequest;

    TextView appointdate, appointtime, upidata, moneydata;
    // spinner already added with id DoctorName findview by id so dont add here again

    Button bookdr;
    String strdate, strtime, strdoctor, strpaymentmode, strupi, intmoney;

    // Add to firebase attribute
    public ActivityConsultationBinding binding;
    int cyear,cmonth,cday,chour,cminutes;
    Spinner spinDoctors;
    private ArrayList<doctorList> doctorList;
    private doctorAdapter drAdapter;

    Spinner spinPaymentMode;
    private ArrayList<paymentModeList> paymentList;
    private paymentAdapter payAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsultationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // doctor list spinner
        doctorList = new ArrayList<>();

        doctorList.add(new doctorList("Dr. Suryasen", R.drawable.maledr));
        doctorList.add(new doctorList("Dr. Shivanshu Singh", R.drawable.femaledr));


        spinDoctors = findViewById(R.id.DoctorName);

        drAdapter = new doctorAdapter(this, doctorList);
        spinDoctors.setAdapter(drAdapter);

        //select doctor
        spinDoctors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doctorList doc_lst = (doctorList) parent.getItemAtPosition(position);
                strdoctor = doc_lst.getDoc_Name();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // doctor list spinner

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

        // Select date
        binding.date3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                cyear = calendar.get(Calendar.YEAR);
                cmonth = calendar.get(Calendar.MONTH);
                cday = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ConsultationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        binding.selecteddate3.setText(dayOfMonth + "/" + "0" + (month + 1) + "/" + year);
                    }
                }, cyear, cmonth, cday);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() - 1000);
                datePickerDialog.show();
            }
        });

        // Select Time
        binding.time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                chour = calendar.get(Calendar.HOUR_OF_DAY);
                cminutes = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(ConsultationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay>=0 && hourOfDay<=12)
                            binding.selectedtime3.setText(hourOfDay + ":" + minute + " AM");
                        else
                            binding.selectedtime3.setText(hourOfDay + ":" + minute + " PM");
                    }
                }, chour, cminutes, true);
                timePickerDialog.show();
            }
        });


        // data add to firebase
        upidata = findViewById(R.id.upiid);
        moneydata = findViewById(R.id.moneyinrs);
        appointdate = findViewById(R.id.selecteddate3);
        appointtime = findViewById(R.id.selectedtime3);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        bookdr = findViewById(R.id.makepayment);

        bookdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strdate = appointdate.getText().toString();
                strtime = appointtime.getText().toString();
                strupi = upidata.getText().toString();
                intmoney = "1050";

                //main code
                if (!strdoctor.isEmpty() && !strdate.isEmpty() && !strtime.isEmpty() && !strupi.isEmpty() && !strpaymentmode.isEmpty() && !intmoney.isEmpty()) {

                    FirebaseUser mUser = mAuth.getCurrentUser();
                    userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(strdoctor).build();
                    mUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                readwriteuserappointment writeuserdata = new readwriteuserappointment(strdate, strtime, strdoctor, strpaymentmode, strupi, intmoney);
                                DatabaseReference profilereference = FirebaseDatabase.getInstance().getReference("Patient Appointment Data");
                                profilereference.child(mUser.getUid()).setValue(writeuserdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            //take user to next activity
                                            sendUserToNextActivity();
                                            Toast.makeText(ConsultationActivity.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(ConsultationActivity.this, "Booking failed! Please try again.", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });
                                // User Data, add to Firebase Attributes
                            } else {
                                Toast.makeText(ConsultationActivity.this, "Booking Data not added to firebase, please try again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

                else{
                    Toast.makeText(ConsultationActivity.this, "Enter all details!", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
    private void sendUserToNextActivity(){
        Intent intent = new Intent(ConsultationActivity.this, HomepatientActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    // User Register Attributes

}