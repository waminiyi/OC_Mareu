package com.waminiyi.mareu.controller;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private FloatingActionButton clearFilterButton;
    private LinearLayout placeHolderLayout;


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
        mMeetingsListingActivity = (MeetingsListingActivity) getActivity();
        mRecyclerView = view.findViewById(R.id.recyclerview);
        clearFilterButton = view.findViewById(R.id.clear_filter_meeting_fab);
        placeHolderLayout = view.findViewById(R.id.holder_layout);

        placeHolderLayout.setVisibility(View.GONE);

        if (mLayoutMode == 2) {
            mFrameLayout = getActivity().findViewById(R.id.frame_layout_meeting);
        }

        clearFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilter();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (mMeetingList.isEmpty()) {
                    mRecyclerView.setVisibility(View.GONE);
                    placeHolderLayout.setVisibility(View.VISIBLE);
                }
                else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    placeHolderLayout.setVisibility(View.GONE);
                }
            }
        });


        ItemClickSupport.addTo(mRecyclerView, R.layout.meeting_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Meeting meeting = mAdapter.getMeetingAt(position);

                        if (mFrameLayout != null) {
                            mFrameLayout.setVisibility(View.VISIBLE);
                            configureAndShowMeetingDetailsFragmentInTabLandMode(meeting);
                        } else {
                            configureAndShowMeetingDetailsFragment(meeting);
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        configureRecyclerView();
        clearFilterButton.hide();
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
        mAdapter.setMeetingsList(mMeetingDatabase.getMeetingListFilteredByDate(mDateFilter));
        clearFilterButton.show();
    }

    public void clearFilter() {
        mAdapter.setMeetingsList(mMeetingList);
        clearFilterButton.hide();
    }

    public void filterByRoom() {
        mAdapter.setMeetingsList(mMeetingDatabase.getMeetingListFilteredByRoom(mRoomFilter));
        clearFilterButton.show();
    }

    private void configureAndShowMeetingDetailsFragmentInTabLandMode(Meeting meeting) {

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setReorderingAllowed(true);
        MeetingDetailsFragment meetingDetailsFragment = MeetingDetailsFragment.newInstance(meeting);
        transaction.replace(R.id.frame_layout_meeting, meetingDetailsFragment);
        transaction.commitNow();
    }


    private void configureAndShowMeetingDetailsFragment(Meeting meeting) {

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        MeetingDetailsFragment meetingDetailsFragment = MeetingDetailsFragment.newInstance(meeting);

        transaction.add(R.id.frame_layout_main, meetingDetailsFragment).commit();

        mMeetingsListingActivity.hideNewMeetingFab();
    }

    public void setRoomFilter(String roomFilter) {
        mRoomFilter = roomFilter;
    }

}


