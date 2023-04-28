package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.healthcare.databinding.ActivityDrAppointmentsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DrAppointments extends AppCompatActivity {

    ActivityDrAppointmentsBinding binding;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDrAppointmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String drname = binding.drusername.getText().toString();
                if (!drname.isEmpty()){

                    readData(drname);
                }else{

                    Toast.makeText(DrAppointments.this,"PLease Enter Username",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void readData(String drname) {

        reference = FirebaseDatabase.getInstance().getReference("Patient Appointment Data");
        reference.child(drname).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){

                        Toast.makeText(DrAppointments.this,"Successfully Read",Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String DrName = String.valueOf(dataSnapshot.child("DrBooked").getValue());
                        String DrPay = String.valueOf(dataSnapshot.child("PayMode").getValue());
                        String DrDate = String.valueOf(dataSnapshot.child("DateBooked").getValue());
                        String DrTime = String.valueOf(dataSnapshot.child("TimeBooked").getValue());
                        String DrUpi = String.valueOf(dataSnapshot.child("Upi").getValue());
                        String DrAmount = String.valueOf(dataSnapshot.child("Money").getValue());
                        binding.drappoint.setText(DrName);
                        binding.drpayment.setText(DrPay);
                        binding.drdate.setText(DrDate);
                        binding.drtime.setText(DrTime);
                        binding.drupi.setText(DrUpi);
                        binding.dramount.setText(DrAmount);


                    }else {

                        Toast.makeText(DrAppointments.this,"User Doesn't Exist",Toast.LENGTH_SHORT).show();

                    }


                }else {

                    Toast.makeText(DrAppointments.this,"Failed to read",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}