package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class medicinestore extends AppCompatActivity {

    ImageView apollostore1;
    TextView apollostore2;
    Button apollostore3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicinestore);

        apollostore1=findViewById(R.id.apollo1);
        apollostore2=findViewById(R.id.apollo2);
        apollostore3=findViewById(R.id.apollo3);

        apollostore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.apollopharmacy.in/?gclid=Cj0KCQjwi46iBhDyARIsAE3nVrZCa-45z-7K1YmuesNaQtbHZB30l5-u9ibh9yIEukZR4Y8lbb1LHiIaApBuEALw_wcB");
            }
        });

        apollostore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.apollopharmacy.in/?gclid=Cj0KCQjwi46iBhDyARIsAE3nVrZCa-45z-7K1YmuesNaQtbHZB30l5-u9ibh9yIEukZR4Y8lbb1LHiIaApBuEALw_wcB");
            }
        });

        apollostore3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.apollopharmacy.in/?gclid=Cj0KCQjwi46iBhDyARIsAE3nVrZCa-45z-7K1YmuesNaQtbHZB30l5-u9ibh9yIEukZR4Y8lbb1LHiIaApBuEALw_wcB");
            }
        });
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}