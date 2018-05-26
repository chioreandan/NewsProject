package com.example.cutem.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText mail;
    private EditText pass;
    private EditText rePass;
    private Button register;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        mAuth=FirebaseAuth.getInstance();
        mail=(EditText) findViewById(R.id.editText3);
        pass=(EditText) findViewById(R.id.editText4);
        rePass=(EditText) findViewById(R.id.editText5);
        register=(Button) findViewById(R.id.button4);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
