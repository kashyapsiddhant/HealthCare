package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomedrActivity extends AppCompatActivity {
    ImageView appointment, profile, payments;
    TextView textappointment, textprofile, textpayments;
    // logout
    Button logOut;
    FirebaseAuth authprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homedr);
        payments = findViewById(R.id.paym);
        textpayments = findViewById(R.id.textpaym);
        appointment = findViewById(R.id.appointments);
        profile = findViewById(R.id.profile);
        textappointment = findViewById(R.id.textappointments);
        textprofile = findViewById(R.id.textprofile);
        authprofile = FirebaseAuth.getInstance();

        payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomedrActivity.this, DoctorPayments.class));
            }
        });

        textpayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomedrActivity.this, DoctorPayments.class));
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomedrActivity.this, DrAppointments.class));
            }
        });

        textappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomedrActivity.this, DrAppointments.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomedrActivity.this, Activity_doctorprofile.class));
            }
        });

        textprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomedrActivity.this, Activity_doctorprofile.class));
            }
        });

        // log out
        logOut = findViewById(R.id.logoutdoctor);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authprofile.signOut();
                startActivity(new Intent(HomedrActivity.this, doctorlogin.class));
                finish();
                Toast.makeText(HomedrActivity.this, "Successfully Logged Out!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}