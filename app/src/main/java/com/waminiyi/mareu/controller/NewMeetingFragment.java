package com.waminiyi.mareu.controller;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.EditText;

import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.MailModel;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;
import com.waminiyi.mareu.utils.StringsUtils;
import com.waminiyi.mareu.utils.UIUtils;
import com.waminiyi.mareu.view.AttendeesListAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewMeetingFragment extends Fragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private List<String> mMeetingRoomsList;
    private String mMeetingTime = "";
    private String mMeetingRoom = "";
    private String mMeetingTopic = "";
    private String mMeetingDate = "";
    private List<String> mAttendeesMailList = new ArrayList<>();
    private MeetingDatabase mMeetingDatabase;
    private RecyclerView mMailRecyclerView;
    public AttendeesListAdapter mMailAdapter;
    private Context mContext;
    private List<MailModel> sMailsModelList;

    private EditText mNewMeetingTopicEditText;
    private TextView mNewMeetingDateTextview;
    private TextView mNewMeetingTimeTextview;
    private TextView mNewMeetingRoomTextview;
    private TextView mNewMeetingAttendeesAddingTextview;
    private FloatingActionButton mMeetingSavingButton;
    private MeetingsListingActivity mMeetingsListingActivity;

    public NewMeetingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mMeetingDatabase = MeetingDatabase.getInstance();
        mMeetingRoomsList = StringsUtils.getMeetingRoomsList(this.getContext());
        sMailsModelList = StringsUtils.getMailsModelList(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_meeting, container, false);
        mContext = view.getContext();

        mNewMeetingTopicEditText = view.findViewById(R.id.new_meeting_topic_edittext);
        mNewMeetingDateTextview = view.findViewById(R.id.new_meeting_date_textview);
        mNewMeetingTimeTextview = view.findViewById(R.id.new_meeting_time_textview);
        mNewMeetingRoomTextview = view.findViewById(R.id.new_meeting_room_textview);
        mNewMeetingAttendeesAddingTextview = view.findViewById(R.id.new_meeting_attendees_adding_textview);
        mMeetingSavingButton = view.findViewById(R.id.new_meeting_save_button);
        mMailRecyclerView = view.findViewById(R.id.new_meeting_attendees_mail_recyclerview);
        mMeetingsListingActivity = (MeetingsListingActivity) getActivity();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.configureRecyclerView();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.close_fragment) {
            configureAndShowMeetingListFragment();
            mMeetingsListingActivity.showNewMeetingFab();
        }
        return true;

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mNewMeetingDateTextview.setOnClickListener(this);
        mNewMeetingTimeTextview.setOnClickListener(this);
        mNewMeetingRoomTextview.setOnClickListener(this);
        mNewMeetingAttendeesAddingTextview.setOnClickListener(this);
        mMeetingSavingButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.new_meeting_date_textview) {
            showDatePickerDialog();
        } else if (v.getId() == R.id.new_meeting_time_textview) {
            showTimePickerDialog();
        } else if (v.getId() == R.id.new_meeting_room_textview) {
            UIUtils uiUtils = new UIUtils(this);
            uiUtils.showRoomDialogAndReturnRoom();
        } else if (v.getId() == R.id.new_meeting_attendees_adding_textview) {
            UIUtils uiUtils = new UIUtils(this);
            uiUtils.configureAndShowMailDialog();
        } else if (v.getId() == R.id.new_meeting_save_button) {
            saveMeeting();
        } else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }
    }

    public void setMeetingRoom(String meetingRoom) {
        mMeetingRoom = meetingRoom;
    }

    public void updateMeetingRoomTextView() {
        mNewMeetingRoomTextview.setText(mMeetingRoom);
    }

    public void showTimePickerDialog() {
        TimePickerFragment timePickerFragment = new TimePickerFragment(this);
        timePickerFragment.show(getParentFragmentManager(), getString(R.string.timePicker));
    }

    public void showDatePickerDialog() {
        DatePickerFragment datePickerFragment = new DatePickerFragment(this, true);
        datePickerFragment.show(getParentFragmentManager(), getString(R.string.datePicker));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mMeetingDate = StringsUtils.formatDate(year, month, dayOfMonth);
        mNewMeetingDateTextview.setText(mMeetingDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mMeetingTime = StringsUtils.formatTime(hourOfDay, minute);
        mNewMeetingTimeTextview.setText(mMeetingTime);
    }

    private void saveMeeting() {
        mMeetingTopic = mNewMeetingTopicEditText.getText().toString();

        if (!isTextValid(mMeetingTopic) || !isTextValid(mMeetingRoom) || !isTextValid(mMeetingDate) || !isTextValid(mMeetingTime) || mAttendeesMailList.size() == 0) {

            if (!isTextValid(mMeetingTopic)) {
                mNewMeetingTopicEditText.setError("Veuillez ajouter un titre ");
            }

            updateTextView(mNewMeetingRoomTextview, mMeetingRoom, "Veuillez choisir une salle");
            updateTextView(mNewMeetingDateTextview, mMeetingDate, "Veuillez choisir une date");
            updateTextView(mNewMeetingTimeTextview, mMeetingTime, "Veuillez choisir une heure");
            updateTextView(mNewMeetingAttendeesAddingTextview, String.join(", ", mAttendeesMailList), "Veuillez ajouter des invités");

            Toast.makeText(mContext, "Veuillez remplir les champs manquants", Toast.LENGTH_SHORT).show();
            return;
        }

        Meeting meeting = new Meeting(mMeetingDate, mMeetingTime, mMeetingRoom, mMeetingTopic, mAttendeesMailList);
        String[] rooms = getResources().getStringArray(R.array.rooms);
        meeting.setColorIndexFrom(rooms);
        mMeetingDatabase.addMeeting(meeting);

        configureAndShowMeetingListFragment();
        mMeetingsListingActivity.showNewMeetingFab();

    }

    private boolean isTextValid(String text) {
        return !text.trim().isEmpty() && text.length() >= 2;
    }

    private void configureRecyclerView() {

        mMailRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mMailAdapter = new AttendeesListAdapter(this, mAttendeesMailList, 1);
        mMailRecyclerView.setAdapter(mMailAdapter);
    }

    private void updateTextView(TextView textView, String condition, String errorMessage) {
        if (!isTextValid(condition)) {
            textView.requestFocus();
            textView.setError(errorMessage);
        } else {
            textView.setError(null);
        }
    }

    public void addAttendee(MailModel newMailModel) {
        int index = sMailsModelList.indexOf(newMailModel);
        mAttendeesMailList.add(newMailModel.getMail());
        sMailsModelList.set(index, new MailModel(newMailModel.getMail(), true));
    }

    public void removeAttendee(MailModel newMailModel) {
        int index = sMailsModelList.indexOf(newMailModel);
        mAttendeesMailList.remove(newMailModel.getMail());
        sMailsModelList.set(index, new MailModel(newMailModel.getMail(), false));
    }

    public List<String> getAttendeesMailList() {
        return mAttendeesMailList;
    }

    public List<MailModel> getMailsModelList() {
        return sMailsModelList;
    }

    private void configureAndShowMeetingListFragment() {

        MeetingListFragment mMeetingListFragment = new MeetingListFragment();

        getParentFragmentManager().beginTransaction().replace(R.id.frame_layout_main, mMeetingListFragment).commit();

    }

}
