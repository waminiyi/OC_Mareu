package com.waminiyi.mareu.controller;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;

import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;
import com.waminiyi.mareu.utils.ItemClickSupport;
import com.waminiyi.mareu.utils.StringsUtils;
import com.waminiyi.mareu.utils.UIUtils;
import com.waminiyi.mareu.view.MeetingRecyclerViewAdapter;

import java.util.Arrays;
import java.util.List;


public class MeetingListFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private MeetingDatabase mMeetingDatabase;
    public static final int VIEW_MEETING_REQUEST = 2;
    private List<Meeting> mMeetingList;
    private RecyclerView mRecyclerView;
    public MeetingRecyclerViewAdapter mAdapter;
    private String mDateFilter;
    private String mRoomFilter;
    private Context mContext;
    private List<String> mMeetingRoomsList;
    private int mLayoutMode;
    private FrameLayout mFrameLayout;
    private MeetingsListingActivity mMeetingsListingActivity;

    public MeetingListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mMeetingDatabase = MeetingDatabase.getInstance();
        mMeetingRoomsList = Arrays.asList(getResources().getStringArray(R.array.rooms));
        mLayoutMode = getResources().getInteger(R.integer.layout_mode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meeting_list, container, false);
        mContext = view.getContext();
        mMeetingsListingActivity=(MeetingsListingActivity)getActivity();
        mRecyclerView = view.findViewById(R.id.recyclerview);

        if (mLayoutMode == 2) {
            mFrameLayout = getActivity().findViewById(R.id.frame_layout_meeting);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.filter_date:

                showDatePickerDialog();
                return true;

            case R.id.filter_room:

                UIUtils uiUtils = new UIUtils(this);
                uiUtils.showRoomDialogAndReturnRoomFilter();
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

                        if (mFrameLayout != null) {
                            mFrameLayout.setVisibility(View.VISIBLE);
                            configureAndShowMeetingDetailsFragment(meeting);
                        } else {
                            Intent intent = new Intent(getContext(), NewMeetingActivity.class);
                            intent.putExtra(NewMeetingActivity.EXTRA_MEETING, meeting);
                            intent.putExtra(NewMeetingActivity.MEETING_MODE, VIEW_MEETING_REQUEST);
                            startActivity(intent);
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        configureRecyclerView();
        mMeetingsListingActivity.hideFilterClear();
    }

    public void showDatePickerDialog() {
        DatePickerFragment datePickerFragment = new DatePickerFragment(this, false);
        datePickerFragment.show(getParentFragmentManager(), getString(R.string.datePicker));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mDateFilter = StringsUtils.formatDate(year, month, dayOfMonth);
        filterByDate();
    }

    private void filterByDate() {
        String filterParameter = getResources().getString(R.string.date_filter_label);
        mAdapter.filter(filterParameter, mDateFilter);
        mMeetingsListingActivity.showFilterClear();
    }

    public void clearFilter() {
        mAdapter.setMeetingsList(mMeetingList);
        mMeetingsListingActivity.hideFilterClear();
    }

    public void filterByRoom() {
        String filterParameter = getResources().getString(R.string.room_filter_label);
        mAdapter.filter(filterParameter, mRoomFilter);
        mMeetingsListingActivity.showFilterClear();
    }

    private void configureAndShowMeetingDetailsFragment(Meeting meeting) {

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        MeetingDetailsFragment meetingDetailsFragment = MeetingDetailsFragment.newInstance(meeting);
        transaction.replace(R.id.frame_layout_meeting, meetingDetailsFragment);
        transaction.commitNow();
    }

    public void setRoomFilter(String roomFilter) {
        mRoomFilter = roomFilter;
    }
}


