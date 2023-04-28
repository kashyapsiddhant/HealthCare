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

public class HomepatientActivity extends AppCompatActivity {

    ImageView bookings, consultation, myprofile, pharmacy;
    TextView textbooking, textconsultation, textmyprofile, textpharmacy;

    FirebaseAuth authprofile;
    // log out
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepatient);

        bookings = findViewById(R.id.bookings);
        consultation = findViewById(R.id.consultation);
        myprofile = findViewById(R.id.myprofile);
        pharmacy = findViewById(R.id.pharmacy);
        textbooking = findViewById(R.id.textbookings);
        textconsultation = findViewById(R.id.textconsult);
        textmyprofile = findViewById(R.id.textprofile);
        textpharmacy = findViewById(R.id.textpharmacy);


        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomepatientActivity.this, mybookings.class));
            }
        });

        textbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomepatientActivity.this, mybookings.class));
            }
        });

        consultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomepatientActivity.this, ConsultationActivity.class));
            }
        });

        textconsultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomepatientActivity.this, ConsultationActivity.class));
            }
        });

        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomepatientActivity.this, myprofile.class));
            }
        });

        textmyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomepatientActivity.this, myprofile.class));
            }
        });

        pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomepatientActivity.this, medicinestore.class));
            }
        });

        textpharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomepatientActivity.this, medicinestore.class));
            }
        });

        logOut = findViewById(R.id.logoutpatient);
        authprofile = FirebaseAuth.getInstance();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authprofile.signOut();
                startActivity(new Intent(HomepatientActivity.this, patientlogin.class));
                finish();
                Toast.makeText(HomepatientActivity.this, "Successfully Logged Out!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}