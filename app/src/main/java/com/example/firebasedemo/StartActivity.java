package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    Button mLogoutButton,mAddButton;
    EditText mNameEditText;
    ListView mListView;
    FirebaseAuth mAuth;
    static  int k=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mAuth=FirebaseAuth.getInstance();
        mLogoutButton=findViewById(R.id.LogoutButton);
        mNameEditText=findViewById(R.id.addEditText);
        mListView=findViewById(R.id.listview);
        final ArrayList<String>dsp=new ArrayList<String>();
        final ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dsp);
        mListView.setAdapter(ad);

        mAddButton=findViewById(R.id.addButton);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mNameEditText.getText().toString();
                if(name.isEmpty()==true){
                    Toast.makeText(StartActivity.this,"Empty name",Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("Demo").child("n"+k).setValue(name);
                    k++;
                }
            }
        });

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(StartActivity.this,MainActivity.class));
                finish();
            }
        });

        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("Demo");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dsp.clear();
                for(DataSnapshot it:snapshot.getChildren()){
                    dsp.add(it.getValue().toString());
                }
                ad.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}