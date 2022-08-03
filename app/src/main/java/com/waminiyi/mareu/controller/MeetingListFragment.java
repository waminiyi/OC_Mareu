package com.waminiyi.mareu.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeetingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingListFragment extends Fragment {

    private FloatingActionButton mNewMeetingButton;
    private MeetingDatabase mMeetingDatabase = MeetingDatabase.getInstance();

    public static final int ADD_MEETING_REQUEST = 1;
    public static final int VIEW_MEETING_REQUEST = 2;
    private List<Meeting> mMeetingList;
    private RecyclerView mRecyclerView;
    public MeetingRecyclerViewAdapter mAdapter;


    /**
     * @return A new instance of fragment MeetingListFragment.
     */

    public static MeetingListFragment newInstance() {
        MeetingListFragment fragment = new MeetingListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

                        intent.putExtra(NewMeetingActivity.EXTRA_MEETING, meeting);

                        startActivityForResult(intent, VIEW_MEETING_REQUEST);

                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        configureRecyclerView();
    }
}

