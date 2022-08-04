package com.waminiyi.mareu.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.waminiyi.mareu.R;
import com.waminiyi.mareu.databinding.ActivityMeetingsListingBinding;
import com.waminiyi.mareu.databinding.ActivityNewMeetingBinding;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;
import com.waminiyi.mareu.utils.ItemClickSupport;
import com.waminiyi.mareu.view.MeetingRecyclerViewAdapter;

import java.util.List;

public class MeetingsListingActivity extends AppCompatActivity {
    private FloatingActionButton mNewMeetingButton;
    private MeetingDatabase mMeetingDatabase = MeetingDatabase.getInstance();

    public static final int ADD_MEETING_REQUEST = 1;
    private ActivityMeetingsListingBinding binding;
    private MeetingListFragment mMeetingListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMeetingsListingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        setSupportActionBar(binding.topAppBar);

        this.configureAndShowDetailFragment();

        binding.newMeetingFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeetingsListingActivity.this, NewMeetingActivity.class);
                intent.putExtra(NewMeetingActivity.MEETING_MODE,ADD_MEETING_REQUEST );
                startActivity(intent);
            }
        });
    }



    private int getColorFromRoom(String room) {

        String[] rooms = getResources().getStringArray(R.array.rooms);
        int colorIndex = R.color.red;

        for (int i = 0; i < 10; i++) {
            if (rooms[i].equals(room)) {
                colorIndex = i;
                break;
            }
        }
        return colorIndex;
    }

    private void configureAndShowDetailFragment() {
        mMeetingListFragment = (MeetingListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);

        mMeetingListFragment = new MeetingListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_main, mMeetingListFragment).commit();
    }
}