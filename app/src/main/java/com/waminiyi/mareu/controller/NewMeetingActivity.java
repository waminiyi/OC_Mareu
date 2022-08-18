package com.waminiyi.mareu.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.Meeting;

public class NewMeetingActivity extends AppCompatActivity {

    public static final String MEETING_MODE = "MEETING_MODE";
    public static final String EXTRA_MEETING = "EXTRA_MEETING";
    private Meeting mMeeting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);

        Intent intent = getIntent();
        int activityMode = intent.getIntExtra(MEETING_MODE, 1);

        if (activityMode == 2) {
            mMeeting = intent.getParcelableExtra(EXTRA_MEETING);
            this.configureAndShowMeetingDetailsFragment();

        } else {
            this.configureAndShowNewMeetingFragment();
        }

    }

    private void configureAndShowMeetingDetailsFragment() {
        MeetingDetailsFragment meetingDetailsFragment = (MeetingDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_new_meeting);

        if (meetingDetailsFragment == null) {
            meetingDetailsFragment = MeetingDetailsFragment.newInstance(mMeeting);
            getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_new_meeting, meetingDetailsFragment).commit();
        }
    }

    private void configureAndShowNewMeetingFragment() {
        NewMeetingFragment newMeetingFragment = (NewMeetingFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_new_meeting);

        if (newMeetingFragment == null) {
            newMeetingFragment = new NewMeetingFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_new_meeting, newMeetingFragment).commit();
        }
    }
}