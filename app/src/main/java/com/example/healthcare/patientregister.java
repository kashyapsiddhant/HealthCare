package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class patientregister extends AppCompatActivity {

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;

    EditText email2, password2, confirmpassword2;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String MobilePattern = "[0-9]{10}";
    Button register2;
    TextView login2;
    // User Register Attributes

    // User Data add to Firebase Attributes
    String strfirstname, strlastname, stremail, strpassword, strconfirmpassword, straadharnumber, strphonenumber;
    EditText firstname, lastname, aadharnumber, phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientregister);

        // User Register Attributes
        email2 = findViewById(R.id.email1);
        password2 = findViewById(R.id.password1);
        confirmpassword2 = findViewById(R.id.confirmpassword1);
        register2 = findViewById(R.id.register2);
        login2 = findViewById(R.id.login2);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        // User Register Attributes

        // Taking user to login window from register window with login button
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(patientregister.this,patientlogin.class));
            }
        });



        // User Data add to Firebase Attributes
        firstname = findViewById(R.id.firstname1);
        lastname = findViewById(R.id.lastname1);
        aadharnumber = findViewById(R.id.aadhar1);
        phonenumber = findViewById(R.id.phonenumber1);
        // User Data add to Firebase Attributes


        // User Register Attributes
        register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stremail = email2.getText().toString();
                strpassword = password2.getText().toString();
                strconfirmpassword = confirmpassword2.getText().toString();
                strfirstname = firstname.getText().toString();
                strlastname = lastname.getText().toString();
                straadharnumber = aadharnumber.getText().toString();
                strphonenumber = phonenumber.getText().toString();



                if(!stremail.matches(emailPattern)){
                    email2.setError("Enter correct Email");
                }
                if (strpassword.isEmpty() || strpassword.length()<8){
                    password2.setError("Enter proper password");
                }
                if (!strpassword.equals(strconfirmpassword)){
                    confirmpassword2.setError("Password not match both field");
                }

                if (TextUtils.isEmpty(straadharnumber)) {
                    aadharnumber.setError("Please enter Aadhaar number");
                    aadharnumber.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(strfirstname)) {
                    firstname.setError("Please enter First Name");
                    firstname.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(strlastname)) {
                    lastname.setError("Please enter Last Name");
                    lastname.requestFocus();
                    return;
                }
                if (!strphonenumber.matches(MobilePattern)) {
                    phonenumber.setError("Enter correct Phone Number");
                }

                else{

                    //if email and password are correct and passes all test cases then data will be entered in firebase and then intent to next activity

                    progressDialog.setMessage("Please wait while Registration...");
                    progressDialog.setTitle("Registration");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(stremail,strpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                // User Data add to Firebase Attributes


                                FirebaseUser mUser = mAuth.getCurrentUser();
                                //display name of user
                                UserProfileChangeRequest userProfileChangeRequest  = new UserProfileChangeRequest.Builder().setDisplayName(strfirstname).build();
                                mUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });

                                readwriteuserdata writeuserdata = new readwriteuserdata(strfirstname, straadharnumber, strphonenumber, strlastname);
                                DatabaseReference profilereference = FirebaseDatabase.getInstance().getReference("Patient Register Data");
                                profilereference.child(mUser.getUid()).setValue(writeuserdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progressDialog.dismiss();
                                            //take user to next activity
                                            sendUserToNextActivity();
                                            Toast.makeText(patientregister.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        }

                                        else{
                                            Toast.makeText(patientregister.this, "Registration failed! Please try again.", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                // User Data, add to Firebase Attributes

                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(patientregister.this, "User not registered, please try again!"+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
        // User Register Attributes

    }

    // User Register Attributes, intent to this new class
    private void sendUserToNextActivity(){
        Intent intent = new Intent(patientregister.this, patientlogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    // User Register Attributes
}











