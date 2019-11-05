package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersListActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ArrayList<String> usernames;
    ArrayList<HashMap<String,String>> dataMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        usernames = new ArrayList<>();
        dataMap = new ArrayList<>();
        final ListView usersList = findViewById(R.id.usersList);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,usernames);
        usersList.setAdapter(adapter);
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(UsersListActivity.this,UserDetailActivity.class);
                HashMap<String,String> userMap = dataMap.get(position);
                i.putExtra("name",userMap.get("name"));
                i.putExtra("username",userMap.get("username"));
                i.putExtra("email",userMap.get("email"));
                startActivity(i);
            }
        });
        loadData();
    }

    void loadData(){
        FirebaseDatabase.getInstance().getReference("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                usernames.add(dataSnapshot.child("username").getValue().toString());
                HashMap<String,String> userMap = new HashMap<>();
                userMap.put("username",dataSnapshot.child("username").getValue().toString());
                userMap.put("name",dataSnapshot.child("name").getValue().toString());
                userMap.put("email",dataSnapshot.child("email").getValue().toString());
                dataMap.add(userMap);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
