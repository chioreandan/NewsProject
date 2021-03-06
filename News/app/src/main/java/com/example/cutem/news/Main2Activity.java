package com.example.cutem.news;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private RecyclerView mNotesList;
    private GridLayoutManager gridLayoutManager;
    private DatabaseReference fNotesDatabase;
    private FirebaseRecyclerAdapter adapter;
    //private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth=FirebaseAuth.getInstance();
       // linearLayoutManager= new LinearLayoutManager(this,3, LinearLayoutManager.VERTICAL,false);
        //linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mNotesList=findViewById(R.id.main_notes_list);
        mNotesList.setHasFixedSize(true);
        mNotesList.setLayoutManager(gridLayoutManager);
        if(mAuth.getCurrentUser()!=null){
            fNotesDatabase= FirebaseDatabase.getInstance().getReference().child("Notes").child(mAuth.getCurrentUser().getUid());
           // Log.d(fNotesDatabase.toString(),"sasd");
            fetch();


        }
    }
    public void onStart(){
        super.onStart();
        adapter.startListening();

    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void fetch() {
        /*Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Notes");*/


        FirebaseRecyclerOptions<NoteModel> options =
                new FirebaseRecyclerOptions.Builder<NoteModel>()
                        .setQuery(fNotesDatabase, new SnapshotParser<NoteModel>() {
                            @NonNull
                            @Override
                            public NoteModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                                //Log.d("TITLE",snapshot.child("title").getValue().toString());

                               /* return new NoteModel(snapshot.child("title").getValue().toStr
    }ing(),
                                        snapshot.child("timestamp").getValue().toString());*/
                               NoteModel model=new NoteModel(snapshot.child("title").getValue().toString(),snapshot.child("timestamp").getValue().toString());
                               Log.d(model.noteTitle,"title");
                               return model;
                            }

                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<NoteModel, NoteViewHolder>(options) {
            @Override
            public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_main2, parent, false);

                return new NoteViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(NoteViewHolder holder, final int position, NoteModel model) {
                holder.setNoteTitle(model.getNoteTitle());
                holder.setNoteTime(model.getNoteTime());
                //Log.d(model.getNoteTitle(),"----------------");

                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Main2Activity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    }
                });

            }


        };
        try {
            mNotesList.setAdapter(adapter);
        }catch (Exception e){

        }

    }
}
