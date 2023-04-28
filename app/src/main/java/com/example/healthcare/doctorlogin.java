package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class doctorlogin extends AppCompatActivity {

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    EditText dremail, drpassword2;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String MobilePattern = "[0-9]{10}";
    Button drlogin;
    TextView drregister;
    // User Login Attributes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorlogin);
        // User Login Attributes
        dremail = findViewById(R.id.dremail);
        drpassword2 = findViewById(R.id.drpassword);
        drlogin = findViewById(R.id.drlogin);
        drregister = findViewById(R.id.drregister);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        // User Login Attributes


        //taking user to register window from login window using register button
        drregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(doctorlogin.this, DrregisterActivity.class));
            }
        });


        // User Login Attributes
        drlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = dremail.getText().toString();
                String password = drpassword2.getText().toString();

                if (!email.matches(emailPattern)) {
                    dremail.setError("Enter correct Email");
                } else if (password.isEmpty() || password.length() < 8) {
                    drpassword2.setError("Enter proper password");
                } else {
                    progressDialog.setMessage("Please wait...");
                    progressDialog.setTitle("Login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(doctorlogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(doctorlogin.this, HomedrActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(doctorlogin.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }

        });
        // User Login Attributes
    }

    // User Login Attributes
}