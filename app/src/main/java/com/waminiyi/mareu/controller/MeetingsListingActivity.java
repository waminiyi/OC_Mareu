package com.waminiyi.mareu.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.waminiyi.mareu.R;

public class MeetingsListingActivity extends AppCompatActivity {
    private FloatingActionButton clearFilterButton;
    private  MeetingListFragment mMeetingListFragment;

    public static final int ADD_MEETING_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_listing);

        FrameLayout meetingFrameLayout = findViewById(R.id.frame_layout_meeting);
        Toolbar toolbar = findViewById(R.id.top_app_bar);
        FloatingActionButton newMeetingButton = findViewById(R.id.new_meeting_fab);
        clearFilterButton = findViewById(R.id.clear_filter_meeting_fab);

        setSupportActionBar(toolbar);
        if (meetingFrameLayout != null) {
            meetingFrameLayout.setVisibility(View.GONE);
        }

        this.configureAndShowMeetingListFragment();

        hideFilterClear();

        clearFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeetingListFragment.clearFilter();
            }
        });

        newMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MeetingsListingActivity.this, NewMeetingActivity.class);
                intent.putExtra(NewMeetingActivity.MEETING_MODE, ADD_MEETING_REQUEST);
                startActivity(intent);
            }
        });
    }

    private void configureAndShowMeetingListFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        mMeetingListFragment = (MeetingListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);

        if (mMeetingListFragment == null) {
            mMeetingListFragment = new MeetingListFragment();
            transaction.add(R.id.frame_layout_main, mMeetingListFragment).commit();
        }
    }

    public void hideFilterClear(){
        clearFilterButton.hide();
    }

    public void showFilterClear(){
        clearFilterButton.show();
    }

}