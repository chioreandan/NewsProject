package com.example.cutem.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccountActivity extends AppCompatActivity {
    Button addButton,seeNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        addButton=(Button) findViewById(R.id.button6);
        seeNotes=(Button)findViewById(R.id.button8);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(AccountActivity.this,Notes.class);
                AccountActivity.this.startActivity(myIntent);
            }
        });
        seeNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(AccountActivity.this,VizualizareNote.class);
                AccountActivity.this.startActivity(myIntent);
            }
        });
    }
}
