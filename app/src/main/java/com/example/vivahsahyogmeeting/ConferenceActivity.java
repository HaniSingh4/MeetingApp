package com.example.vivahsahyogmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceConfig;
import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceFragment;

public class ConferenceActivity extends AppCompatActivity {
    TextView meetingIdtext;
    ImageView ShareBtn;
    String meetingID,userID,name;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference);
        meetingIdtext = findViewById(R.id.meetingIdTextView);
        ShareBtn = findViewById(R.id.shareBtn);
        meetingID = getIntent().getStringExtra("meeting_ID");
        userID = getIntent().getStringExtra("user_id");
        name = getIntent().getStringExtra("name");
        meetingIdtext.setText("Meeting_ID :" +meetingID);
        addFragment();
        ShareBtn.setOnClickListener(v -> {
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,"Join meeting in VivahSahyog App\n  Meeting ID: "+meetingID);
            startActivities(Intent.createChooser(intent,"Share via"));

        });
    }

    private void startActivities(Intent shareVia) {
    }

    public void addFragment() {
        long appID = AppConstants.appId;
        String appSign = AppConstants.appSign;


        ZegoUIKitPrebuiltVideoConferenceConfig config = new ZegoUIKitPrebuiltVideoConferenceConfig();
        config.turnOnMicrophoneWhenJoining = false;
        config.turnOnCameraWhenJoining = false;
        ZegoUIKitPrebuiltVideoConferenceFragment fragment = ZegoUIKitPrebuiltVideoConferenceFragment.newInstance(appID, appSign, userID, name,meetingID,config);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitNow();
    }

}