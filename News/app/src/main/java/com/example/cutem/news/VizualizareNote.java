package com.example.cutem.news;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class VizualizareNote extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private RecyclerView mNotesList;
    private GridLayoutManager gridLayoutManager;
    private DatabaseReference fNotesDatabase;
    private FirebaseRecyclerAdapter adapter;

    TextView t1,t2,t3,t4,t5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizare_note);

        t1=(TextView) findViewById(R.id.textView6);
        t2=(TextView) findViewById(R.id.textView9);
        t3=(TextView) findViewById(R.id.textView7);
        t4=(TextView) findViewById(R.id.textView4);
        t5=(TextView) findViewById(R.id.textView10);

        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            fNotesDatabase= FirebaseDatabase.getInstance().getReference().child("Notes").child(mAuth.getCurrentUser().getUid());
            // Log.d(fNotesDatabase.toString(),"sasd");
            fetch();

        }
    }
    public void onStart(){
        super.onStart();
        adapter.startListening();
        t2.setText("dasdas");
        t1.setText("Dasdasd");

    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Notes");


        FirebaseRecyclerOptions<NoteModel> options =
                new FirebaseRecyclerOptions.Builder<NoteModel>()
                        .setQuery(query, new SnapshotParser<NoteModel>() {
                            @NonNull
                            @Override
                            public NoteModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                                //Log.d("TITLE",snapshot.child("title").getValue().toString());

                               /* return new NoteModel(snapshot.child("title").getValue().toStr
    }ing(),
                                        snapshot.child("timestamp").getValue().toString());*/
                                NoteModel model=new NoteModel(snapshot.child("title").getValue().toString(),snapshot.child("timestamp").getValue().toString());
                                t1.setText("laba");
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
                        Toast.makeText(VizualizareNote.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
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
