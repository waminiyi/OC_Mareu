package com.waminiyi.mareu.controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;
import com.waminiyi.mareu.utils.ItemClickSupport;
import com.waminiyi.mareu.view.MeetingRecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MeetingListFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private MeetingDatabase mMeetingDatabase;
    public static final int VIEW_MEETING_REQUEST = 2;
    private List<Meeting> mMeetingList;
    private RecyclerView mRecyclerView;
    public MeetingRecyclerViewAdapter mAdapter;
    private FloatingActionButton clearFilterButton;
    private String mDateFilter;
    private String mRoomFilter;
    private Dialog mRoomDialog;
    private Context mContext;
    private List<String> mMeetingRoomsList;
    private MeetingDetailsFragment mMeetingDetailsFragment;
    private int mLayoutMode;
    private FrameLayout mFrameLayout;


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
        mRecyclerView = view.findViewById(R.id.recyclerview);
        clearFilterButton = view.findViewById(R.id.clear_filter_meeting_fab);

        if (mLayoutMode == 2) {
            mFrameLayout = getActivity().findViewById(R.id.frame_layout_meeting);
        }

        clearFilterButton.hide();

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.filter_date:

                showDatePickerDialog();
                return true;

            case R.id.filter_room:

                showRoomDialog();
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
    }

    public void showDatePickerDialog() {
        DatePickerFragment newDatePickerFragment = new DatePickerFragment(false);
        newDatePickerFragment.setListener(this);
        newDatePickerFragment.show(getParentFragmentManager(), getString(R.string.datePicker));
    }

    public String getDateFilter(int year, int month, int day) {

        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, day);
        Date date = calendar.getTime();

        String pattern = "EEEE dd MMMM yyyy";

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));

        return dateFormat.format(date);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mDateFilter = getDateFilter(year, month, dayOfMonth);
        filterByDate();
    }

    private void filterByDate() {
        String filterParameter = getResources().getString(R.string.date_filter_label);
        mAdapter.filter(filterParameter, mDateFilter);
        clearFilterButton.show();
    }

    private void clearFilter() {
        mAdapter.setMeetingsList(mMeetingList);
        clearFilterButton.hide();
    }

    private void showRoomDialog() {

        mRoomDialog = new Dialog(mContext);
        mRoomDialog.setContentView(R.layout.dialog_searchable_spinner);
        mRoomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mRoomDialog.show();

        EditText researchEditText = mRoomDialog.findViewById(R.id.edit_text);
        ListView listView = mRoomDialog.findViewById(R.id.list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.dialog_listview_item, mMeetingRoomsList);

        listView.setAdapter(adapter);
        researchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRoomFilter = adapter.getItem(position);
                mRoomDialog.dismiss();
                filterByRoom();
            }
        });
    }

    private void filterByRoom() {
        String filterParameter = getResources().getString(R.string.room_filter_label);
        mAdapter.filter(filterParameter, mRoomFilter);
        clearFilterButton.show();
    }

    private void configureAndShowMeetingDetailsFragment(Meeting meeting) {

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        mMeetingDetailsFragment = MeetingDetailsFragment.newInstance(meeting);
        transaction.replace(R.id.frame_layout_meeting, mMeetingDetailsFragment);
        transaction.commitNow();
    }
}


