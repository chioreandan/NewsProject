package com.example.cutem.news;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Main2Activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private RecyclerView mNotesList;
    private GridLayoutManager gridLayoutManager;
    private DatabaseReference fNotesDatabase;
    private FirebaseRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth=FirebaseAuth.getInstance();
        gridLayoutManager= new GridLayoutManager(this,3, GridLayoutManager.VERTICAL,false);
        mNotesList=findViewById(R.id.main_notes_list);
        mNotesList.setHasFixedSize(true);
        mNotesList.setLayoutManager(gridLayoutManager);
        if(mAuth.getCurrentUser()!=null){
            fNotesDatabase= FirebaseDatabase.getInstance().getReference().child("Notes").child(mAuth.getCurrentUser().getUid());
        }
        fetch();
    }
    public void onStart(){
        super.onStart();
        adapter.startListening();
       /* FirebaseRecyclerAdapter<NoteModel,NoteViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<NoteModel, NoteViewHolder>() {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull NoteModel model) {

            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };*/
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("notes");

        FirebaseRecyclerOptions<NoteModel> options =
                new FirebaseRecyclerOptions.Builder<NoteModel>()
                        .setQuery(query, new SnapshotParser<NoteModel>() {
                            @NonNull
                            @Override
                            public NoteModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new NoteModel(snapshot.child("title").getValue().toString(),
                                        snapshot.child("time").getValue().toString());
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
                holder.setNoteTitle(model.noteTitle);
                holder.setNoteTime(model.noteTime);


            }

        };
        mNotesList.setAdapter(adapter);
    }
}
