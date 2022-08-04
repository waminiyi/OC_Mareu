package com.waminiyi.mareu.controller;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;
import com.waminiyi.mareu.view.AttendeesListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NewMeetingFragment extends Fragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private Dialog mRoomDialog;
    public static final String EXTRA_MEETING = "com.waminiyi.mareu.EXTRA_MEETING";
    private List<String> mMeetingRoomsList;
    private List<String> mEmployeesMailList;
    private final String room = "Room";
    private final String mail = "Mail";
    private String mMeetingTime = "";
    private String mMeetingRoom = "";
    private String mMeetingTopic = "";
    private String mMeetingDate = "";
    private List<String> mAttendeesMailList = new ArrayList<>();
    private MeetingDatabase mMeetingDatabase;
    private Meeting mMeeting;
    private RecyclerView mMailRecyclerView;
    private AttendeesListAdapter mailAdapter;
    private Toolbar mToolbar;
    private Context mContext;

    private EditText mNewMeetingTopicEditText;
    private TextView mNewMeetingDateTextview;
    private TextView mNewMeetingTimeTextview;
    private TextView mNewMeetingRoomTextview;
    private TextView mNewMeetingAttendeesAddingTextview;
    private Button mMeetingSavingButton;

    public NewMeetingFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMeetingDatabase = MeetingDatabase.getInstance();
        mMeetingRoomsList = Arrays.asList(getResources().getStringArray(R.array.rooms));
        mEmployeesMailList = Arrays.asList(getResources().getStringArray(R.array.random_mails));
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
        mToolbar = view.findViewById(R.id.new_meeting_top_app_bar);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.configureRecyclerView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mToolbar.inflateMenu(R.menu.fragment_menu);
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.close_fragment) {
                getActivity().finish();
                return true;
            }
            return false;
        });

        mNewMeetingDateTextview.setOnClickListener(this);
        mNewMeetingTimeTextview.setOnClickListener(this);
        mNewMeetingRoomTextview.setOnClickListener(this);
        mNewMeetingAttendeesAddingTextview.setOnClickListener(this);
        mMeetingSavingButton.setOnClickListener(this);

        mNewMeetingTopicEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mMeetingTopic = mNewMeetingTopicEditText.getText().toString();
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == mNewMeetingDateTextview) {
            showDatePickerDialog();
        } else if (v == mNewMeetingTimeTextview) {
            showTimePickerDialog();
        } else if (v == mNewMeetingRoomTextview) {
            showDialogSpinner(room, mMeetingRoomsList);
        } else if (v == mNewMeetingAttendeesAddingTextview) {
            showDialogSpinner(mail, mEmployeesMailList);
        } else if (v == mMeetingSavingButton) {
            saveMeeting();
        } else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }

    }

    public void showDialogSpinner(String target, List<String> dataSource) {
        // Initialize dialog
        mRoomDialog = new Dialog(mContext);
        // set custom dialog
        mRoomDialog.setContentView(R.layout.dialog_searchable_spinner);

        mRoomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mRoomDialog.show();

        TextView textView = mRoomDialog.findViewById(R.id.research_label_textview);
        EditText researchEditText = mRoomDialog.findViewById(R.id.edit_text);
        ListView listView = mRoomDialog.findViewById(R.id.list_view);

        switch (target) {
            case room:
                textView.setText(R.string.find_room_label);
                researchEditText.setHint(R.string.room_example_placeholder);
                dataSource = mMeetingRoomsList;
                break;
            case mail:
                textView.setText(R.string.find_attendee_label);
                researchEditText.setHint(R.string.mail_example_placeholder);
                dataSource = mEmployeesMailList;
                break;
        }

        // Initialize array adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.dialog_listview_item, dataSource);

        // set adapter
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
                switch (target) {
                    case room:
                        mMeetingRoom = adapter.getItem(position);
                        mNewMeetingRoomTextview.setText(mMeetingRoom);
                        mRoomDialog.dismiss();
                        break;
                    case mail:
                        String newMail = adapter.getItem(position);
                        if (!mAttendeesMailList.contains(newMail)) {
                            mAttendeesMailList.add(newMail);
                        }
                        break;
                }
                mailAdapter.notifyDataSetChanged();
            }
        });
    }


    public void showTimePickerDialog() {
        TimePickerFragment newTimePickerFragment = new TimePickerFragment();
        newTimePickerFragment.setListener(this);
        newTimePickerFragment.show(getParentFragmentManager(), getString(R.string.timePicker));
    }

    public void showDatePickerDialog() {
        DatePickerFragment newDatePickerFragment = new DatePickerFragment();
        newDatePickerFragment.setListener(this);
        newDatePickerFragment.show(getParentFragmentManager(), getString(R.string.datePicker));
    }

    public void processTimePickerResult(int hour, int minute) {

        @SuppressLint("DefaultLocale") String hour_string = String.format("%02d", hour);
        @SuppressLint("DefaultLocale") String minute_string = String.format("%02d", minute);
        mMeetingTime = (hour_string + "h" + minute_string);
        mNewMeetingTimeTextview.setText(mMeetingTime);

    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        mMeetingDate = (day_string + "/" + month_string + "/" + year_string);
        mNewMeetingDateTextview.setText(mMeetingDate);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        processDatePickerResult(year, month, dayOfMonth);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        processTimePickerResult(hourOfDay, minute);
    }

    private void saveMeeting() {

        if (!isTextValid(mMeetingTopic) || !isTextValid(mMeetingRoom) || !isTextValid(mMeetingDate) || !isTextValid(mMeetingTime) || mAttendeesMailList.size() == 0) {
            Toast.makeText(mContext, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        mMeeting = new Meeting(mMeetingDate, mMeetingTime, mMeetingRoom, mMeetingTopic, mAttendeesMailList);
        mMeeting.setColorIndex(getColorFromRoom(mMeetingRoom));
        mMeetingDatabase.addMeeting(mMeeting);

        getActivity().finish();
    }

    private boolean isTextValid(String text) {
        return !text.trim().isEmpty() && text.length() >= 2;
    }

    private void configureRecyclerView() {

        mMailRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mailAdapter = new AttendeesListAdapter(mContext, mAttendeesMailList);

        mMailRecyclerView.setAdapter(mailAdapter);

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