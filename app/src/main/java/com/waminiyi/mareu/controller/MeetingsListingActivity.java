package com.waminiyi.mareu.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;
import com.waminiyi.mareu.utils.ItemClickSupport;
import com.waminiyi.mareu.view.MeetingRecyclerViewAdapter;

import java.util.List;

public class MeetingsListingActivity extends AppCompatActivity {
    private FloatingActionButton mNewMeetingButton;
    private MeetingDatabase mMeetingDatabase = MeetingDatabase.getInstance();

    public static final int ADD_MEETING_REQUEST = 1;
    public static final int VIEW_MEETING_REQUEST = 2;
    public RecyclerView recyclerView;
    public MeetingRecyclerViewAdapter adapter;
    private int gridColumnCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_listing);

        gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        mNewMeetingButton = findViewById(R.id.new_meeting_fab);

        this.configureOnClickRecyclerView();

        mNewMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeetingsListingActivity.this, NewMeetingActivity.class);
                startActivityForResult(intent, ADD_MEETING_REQUEST);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String filterParameter;
        String filterPlaceholder;
        switch (item.getItemId()) {
            case R.id.filter_date:
                filterParameter= getResources().getString(R.string.date_filter_label);
                filterPlaceholder=getString(R.string.date_filter_placeholder);
                filterMeetings(item,filterParameter,filterPlaceholder);
                Toast.makeText(this, "Meetings filtered by date", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.filter_room:
                filterParameter= getResources().getString(R.string.room_filter_label);
                filterPlaceholder=getString(R.string.room_filter_placeholder);
                filterMeetings(item,filterParameter,filterPlaceholder);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void filterMeetings(MenuItem searchItem, String parameter, String placeHolder) {
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(placeHolder);
        searchView.setIconifiedByDefault(false);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(parameter, newText);
                return false;
            }
        });

    }

    private void configureOnClickRecyclerView() {

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        List<Meeting> meetingList = mMeetingDatabase.getMeetingList();

        adapter = new MeetingRecyclerViewAdapter(meetingList);
        recyclerView.setAdapter(adapter);
        ItemClickSupport.addTo(recyclerView, R.layout.meeting_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Meeting meeting = adapter.getMeetingAt(position);

                        Intent intent = new Intent(MeetingsListingActivity.this, NewMeetingActivity.class);

                        intent.putExtra(NewMeetingActivity.EXTRA_TOPIC, meeting.getMeetingTopic());
                        intent.putExtra(NewMeetingActivity.EXTRA_ROOM, meeting.getMeetingRoom());
                        intent.putExtra(NewMeetingActivity.EXTRA_DATE, meeting.getMeetingDate());
                        intent.putExtra(NewMeetingActivity.EXTRA_TIME, meeting.getMeetingTime());
                        intent.putExtra(NewMeetingActivity.EXTRA_MAIL, meeting.getMeetingAttendees());
                        startActivityForResult(intent, VIEW_MEETING_REQUEST);

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_MEETING_REQUEST && resultCode == RESULT_OK) {

            String topic = data.getStringExtra(NewMeetingActivity.EXTRA_TOPIC);
            String room = data.getStringExtra(NewMeetingActivity.EXTRA_ROOM);
            String date = data.getStringExtra(NewMeetingActivity.EXTRA_DATE);
            String time = data.getStringExtra(NewMeetingActivity.EXTRA_TIME);
            String attendees = data.getStringExtra(NewMeetingActivity.EXTRA_MAIL);
// penser à mettre la création du meeting dans l'activity new meeting, rendre mmeting parcelable
            Meeting meeting = new Meeting(date, time, room, topic, attendees);
            meeting.setColorIndex(getColorFromRoom(room));

            mMeetingDatabase.addMeeting(meeting);
            adapter.setMeetingsList(mMeetingDatabase.getMeetingList());

            Toast.makeText(this, "Nouvelle réunion programmée", Toast.LENGTH_SHORT).show();
        }
    }

    private int getColorFromRoom(String room) {

        int[] colors = getResources().getIntArray(R.array.colors_array);
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

}