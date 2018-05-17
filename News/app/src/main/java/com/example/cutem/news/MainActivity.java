package com.example.cutem.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button logBtn;
    Button regBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logBtn=(Button) findViewById(R.id.button3);
        regBtn=(Button) findViewById(R.id.button);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, LogActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(MainActivity.this,RegisterActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });




    }
    @Override
    public void onStart() {
        super.onStart();

    }
}
