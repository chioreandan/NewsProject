package com.example.cutem.news;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class Notes extends AppCompatActivity {
    private Button btnCreate;
    private EditText etTitle,etContent;
    private FirebaseAuth fAuth;
    private DatabaseReference fNotesDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        btnCreate=(Button) findViewById(R.id.button5);
        etTitle=(EditText) findViewById(R.id.editText6);
        etContent=(EditText) findViewById(R.id.editText7);
        fAuth=FirebaseAuth.getInstance();
        fNotesDatabase=FirebaseDatabase.getInstance().getReference().child("Notes").child((fAuth.getCurrentUser().getUid()));
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=etTitle.getText().toString().trim();
                String content=etContent.getText().toString().trim();
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)){
                    createNote(title,content);
                }else{
                    Toast.makeText(Notes.this,"Fields are empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void createNote(String title,String content){
        if(fAuth.getCurrentUser()!=null){
            final DatabaseReference newNoteRef=fNotesDatabase.push();

            final Map noteMap=new HashMap();

            noteMap.put("title",title);
            noteMap.put("content",content);
            noteMap.put("timestamp", ServerValue.TIMESTAMP);

            Thread mainThread=new Thread(new Runnable() {
                @Override
                public void run() {
                    newNoteRef.setValue(noteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Notes.this,"Note Added",Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(Notes.this,"ERROR",Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }
            });
            mainThread.start();


        }else{
            Toast.makeText(Notes.this,"User not signed in",Toast.LENGTH_LONG).show();

        }

    }
}
