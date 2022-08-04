package com.waminiyi.mareu.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.Meeting;

public class NewMeetingActivity extends AppCompatActivity {

    private NewMeetingFragment mNewMeetingFragment;
    private MeetingDetailsFragment mMeetingDetailsFragment;
    public static final String MEETING_MODE= "MEETING_MODE";
    public static final String EXTRA_MEETING="EXTRA_MEETING";
    private Meeting mMeeting;
    private int mActivityMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);

        Intent intent = getIntent();
        mActivityMode=intent.getIntExtra(MEETING_MODE,1);

        if (mActivityMode==2){
            mMeeting=intent.getParcelableExtra(EXTRA_MEETING);

            mMeetingDetailsFragment= (MeetingDetailsFragment)getSupportFragmentManager().findFragmentById(R.id.frame_layout_new_meeting);
            mMeetingDetailsFragment=MeetingDetailsFragment.newInstance(mMeeting);

            getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_new_meeting, mMeetingDetailsFragment).commit();
        }

        else{
            mNewMeetingFragment = (NewMeetingFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_new_meeting);

            mNewMeetingFragment = new NewMeetingFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_new_meeting, mNewMeetingFragment).commit();

        }

    }
}