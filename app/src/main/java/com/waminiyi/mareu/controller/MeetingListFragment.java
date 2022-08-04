package com.waminiyi.mareu.controller;

import static com.waminiyi.mareu.controller.NewMeetingActivity.EXTRA_MEETING;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.waminiyi.mareu.R;
import com.waminiyi.mareu.databinding.ActivityMeetingsListingBinding;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;
import com.waminiyi.mareu.utils.ItemClickSupport;
import com.waminiyi.mareu.view.MeetingRecyclerViewAdapter;

import java.util.List;

public class MeetingListFragment extends Fragment {

    private MeetingDatabase mMeetingDatabase = MeetingDatabase.getInstance();


    public static final int VIEW_MEETING_REQUEST = 2;

    private List<Meeting> mMeetingList;
    private RecyclerView mRecyclerView;
    public MeetingRecyclerViewAdapter mAdapter;


    public MeetingListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mMeetingDatabase = MeetingDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meeting_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
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


    private void configureRecyclerView() {

        mMeetingList = mMeetingDatabase.getMeetingList();

        mAdapter = new MeetingRecyclerViewAdapter(this.getContext(), mMeetingList);
        mRecyclerView.setAdapter(mAdapter);

        ItemClickSupport.addTo(mRecyclerView, R.layout.meeting_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Meeting meeting = mAdapter.getMeetingAt(position);
                        Intent intent = new Intent(getContext(), NewMeetingActivity.class);

                        intent.putExtra(NewMeetingActivity.EXTRA_MEETING,meeting );
                        intent.putExtra(NewMeetingActivity.MEETING_MODE,VIEW_MEETING_REQUEST);
                        startActivity(intent);

                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        configureRecyclerView();
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
                mAdapter.filter(parameter, newText);
                return false;
            }
        });

    }

}

