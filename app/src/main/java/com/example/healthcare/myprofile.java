package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myprofile extends AppCompatActivity {

    //retrieve user data

    TextView TextEmail, TextfirstName, TextLastName, TextPhoneNumber, TextAadharNumber;
    String useremail, userfirstname, userlastname, userphonenumber, useraadharnumber;
    ProgressDialog progressDialog;
    FirebaseUser firebaseUser;
    FirebaseAuth authprofile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        TextEmail = findViewById(R.id.email2);
        TextfirstName = findViewById(R.id.firstname2);
        TextLastName = findViewById(R.id.lastname2);
        TextPhoneNumber = findViewById(R.id.phonenumber2);
        TextAadharNumber = findViewById(R.id.addharnumber2);

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
        DatabaseReference referenceprofile = FirebaseDatabase.getInstance().getReference("Patient Register Data");
        referenceprofile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readwriteuserdata readuserdata = snapshot.getValue(readwriteuserdata.class);
                if (readuserdata != null){

                    Toast.makeText(myprofile.this, "Successfully logged in with user details!", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();

                    useremail = firebaseUser.getEmail();
                    userfirstname = readuserdata.FirstName;
                    userlastname = readuserdata.LastName;
                    userphonenumber = readuserdata.PhoneNumber;
                    useraadharnumber = readuserdata.AadharNumber;


                    TextfirstName.setText(userfirstname);
                    TextEmail.setText(useremail);
                    TextLastName.setText(userlastname);
                    TextPhoneNumber.setText(userphonenumber);
                    TextAadharNumber.setText(useraadharnumber);

                }
                else{

                    progressDialog.dismiss();

                    Toast.makeText(myprofile.this, "Something went wrong! User's details are not synced to firebase!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(myprofile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

        // log out


    }

    //retrieve user data
}