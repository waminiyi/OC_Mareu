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
    public static final int VIEW_MEETING_REQUEST = 2;
    public MeetingRecyclerViewAdapter adapter;
    private int gridColumnCount;
    private ActivityMeetingsListingBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMeetingsListingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        this.configureOnClickRecyclerView();

        binding.newMeetingFab.setOnClickListener(new View.OnClickListener() {
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
                filterParameter = getResources().getString(R.string.date_filter_label);
                filterPlaceholder = getString(R.string.date_filter_placeholder);
                filterMeetings(item, filterParameter, filterPlaceholder);
                return true;

            case R.id.filter_room:
                filterParameter = getResources().getString(R.string.room_filter_label);
                filterPlaceholder = getString(R.string.room_filter_placeholder);
                filterMeetings(item, filterParameter, filterPlaceholder);
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

        binding.recyclerview.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        List<Meeting> meetingList = mMeetingDatabase.getMeetingList();

        adapter = new MeetingRecyclerViewAdapter(meetingList);
        binding.recyclerview.setAdapter(adapter);
        ItemClickSupport.addTo(binding.recyclerview, R.layout.meeting_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Meeting meeting = adapter.getMeetingAt(position);

                        Intent intent = new Intent(MeetingsListingActivity.this, NewMeetingActivity.class);

                        intent.putExtra(NewMeetingActivity.EXTRA_MEETING, meeting);

                        startActivityForResult(intent, VIEW_MEETING_REQUEST);

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_MEETING_REQUEST && resultCode == RESULT_OK) {
            Meeting newMeeting = data.getParcelableExtra(NewMeetingActivity.EXTRA_MEETING);

            newMeeting.setColorIndex(getColorFromRoom(newMeeting.getMeetingRoom()));
            mMeetingDatabase.addMeeting(newMeeting);
            adapter.setMeetingsList(mMeetingDatabase.getMeetingList());
        }
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

}