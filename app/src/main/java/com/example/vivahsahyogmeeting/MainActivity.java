package com.example.vivahsahyogmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    TextInputEditText meetingIdinput,nameInput;
    MaterialButton joinbtn,createbtn;
    SharedPreferences sharedPreferences;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("name_pref",MODE_PRIVATE);
        meetingIdinput = findViewById(R.id.MeetingIdinput);
        nameInput = findViewById(R.id.NameInput);
        joinbtn = findViewById(R.id.JoinBtn);
        createbtn= findViewById(R.id.CreateBtn);
        nameInput.setText(sharedPreferences.getString("name",""));
        joinbtn.setOnClickListener((v)->
        {
            String meetingId= meetingIdinput.getText().toString();
            if (meetingId.length()!=10)
            {
                meetingIdinput.setError("Invalid Meeting ID");
                meetingIdinput.requestFocus();
                return;
            }
            String name = nameInput.getText().toString();
            if(name.isEmpty())
            {
                nameInput.setError("Name is required to join the meeting");
                nameInput.requestFocus();
                return;
            }
         startMeeting(meetingId,name);
        });
        createbtn.setOnClickListener(v ->{
            String name = nameInput.getText().toString();
            if(name.isEmpty())
            {
                nameInput.setError("Name is required to create the meeting");
                nameInput.requestFocus();
                return;
            }
            startMeeting(getRandomMeetingID(),name);

        });
    }
    void startMeeting(String meetingID,String name){
        sharedPreferences.edit().putString("name",name).apply();
        String userID= UUID.randomUUID().toString();
        Intent intent = new Intent(MainActivity.this,ConferenceActivity.class);
        intent.putExtra("meeting_ID",meetingID);
        intent.putExtra("name",name);
        intent.putExtra("user_id",userID);
        startActivity(intent);
    }
    String getRandomMeetingID(){
        StringBuilder id= new StringBuilder();
        while (id.length()!=10)
        {
            int random = new Random().nextInt(10);
            id.append(random);
        }
        return id.toString();
    }
} 