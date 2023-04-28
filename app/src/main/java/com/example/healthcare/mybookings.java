package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mybookings extends AppCompatActivity {

    //retrieve user data

    TextView TextDoctor, TextDate, TextTime, TextPaymentMode, TextUpiId, TextAmount;
    String userDoctor, userDate, userTime, userPayment, userUpiid, userAmount;
    ProgressDialog progressDialog;
    FirebaseUser firebaseUser;
    FirebaseAuth authprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybookings);
        TextDoctor = findViewById(R.id.drofappoint);
        TextDate = findViewById(R.id.dateofappoint);
        TextTime = findViewById(R.id.timeofappoint);
        TextPaymentMode = findViewById(R.id.paymentmodeofuser);
        TextUpiId = findViewById(R.id.paymentidofuser);
        TextAmount = findViewById(R.id.amount);


        progressDialog = new ProgressDialog(this);
        authprofile = FirebaseAuth.getInstance();
        firebaseUser = authprofile.getCurrentUser();

        if (firebaseUser == null){
            Toast.makeText(this, "Something went wrong! User's details are not available!", Toast.LENGTH_SHORT).show();
        }
        else{
            progressDialog.setMessage("Please wait..");
            progressDialog.setTitle("Updating Profile");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            showUserProfile(firebaseUser);
        }

    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();
        DatabaseReference referenceprofile = FirebaseDatabase.getInstance().getReference("Patient Appointment Data");
        referenceprofile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readwriteuserappointment readuserappointment = snapshot.getValue(readwriteuserappointment.class);
                if (readuserappointment != null){

                    Toast.makeText(mybookings.this, "Check your booking details!", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();

                    userDoctor = readuserappointment.DrBooked;
                    userDate = readuserappointment.DateBooked;
                    userTime = readuserappointment.TimeBooked;
                    userPayment = readuserappointment.PayMode;
                    userUpiid = readuserappointment.Upi;
                    userAmount = readuserappointment.Money;




                    TextDoctor.setText(userDoctor);
                    TextDate.setText(userDate);
                    TextTime.setText(userTime);
                    TextPaymentMode.setText(userPayment);
                    TextUpiId.setText(userUpiid);
                    TextAmount.setText(userAmount);



                }
                else{

                    progressDialog.dismiss();

                    Toast.makeText(mybookings.this, "No Bookings!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(mybookings.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //retrieve user data
}