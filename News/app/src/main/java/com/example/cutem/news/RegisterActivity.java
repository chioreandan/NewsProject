package com.example.cutem.news;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextMail;
    private EditText editTextPass;
    private EditText editTextRePass;
    private Button buttonRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference fUsersDataBase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        mAuth=FirebaseAuth.getInstance();
        fUsersDataBase= FirebaseDatabase.getInstance().getReference().child("Users");
        editTextMail=(EditText) findViewById(R.id.editText3);
        editTextPass=(EditText) findViewById(R.id.editText4);
        editTextRePass=(EditText) findViewById(R.id.editText5);
        buttonRegister=(Button) findViewById(R.id.button4);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    private void registerUser(){
        final String username=editTextMail.getText().toString().trim();
        String password=editTextPass.getText().toString().trim();
        String rePassword=editTextRePass.getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            Toast.makeText(RegisterActivity.this,"Email not valid",Toast.LENGTH_LONG).show();
            editTextMail.requestFocus();
            return;
        }
        if(password.length()<6){
            Toast.makeText(RegisterActivity.this,"Password lenght has to be grater then 6",Toast.LENGTH_LONG).show();

        }

        if(username.isEmpty()){
            Toast.makeText(RegisterActivity.this,"Email is required",Toast.LENGTH_LONG).show();
            editTextMail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            Toast.makeText(RegisterActivity.this,"Password is required",Toast.LENGTH_LONG).show();
            editTextPass.requestFocus();
            return;
        }
        if(rePassword.isEmpty()){
            Toast.makeText(RegisterActivity.this,"Retype password is required",Toast.LENGTH_LONG).show();
            editTextRePass.requestFocus();
            return;
        }
        Log.d(password,"PASSWORD");
        Log.d(rePassword,"RE-----------------PASSWORD");

        if(rePassword==password){
            Log.d("------------","fdddddddddddddddddddddddddddddddddddddd");
            Toast.makeText(RegisterActivity.this,"Password does not match",Toast.LENGTH_LONG).show();
            editTextRePass.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    fUsersDataBase.child(mAuth.getCurrentUser().getUid()).child("basic").child("name").setValue(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            }else{

                            }
                        }
                    });
                    Intent myIntent=new Intent(RegisterActivity.this,AccountActivity.class);
                    RegisterActivity.this.startActivity(myIntent);
                }
            }
        });
    }
}
