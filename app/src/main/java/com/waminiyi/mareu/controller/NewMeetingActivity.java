package com.waminiyi.mareu.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;
import com.waminiyi.mareu.services.RoomAndEmployeeDatabase;

import java.util.ArrayList;
import java.util.List;

public class NewMeetingActivity extends AppCompatActivity {

    private EditText mMeetingTopicEditText;
    private TextView mMeetingTimeTextView;
    private TextView mMeetingDateTextView;
    private TextView mMeetingRoomTextView;
    private TextView mMeetingAttendeesAddingTextView;
    private TextView mMeetingAttendeesShowingTextView;
    private Button mMeetingSavingButton;

    private Dialog mRoomDialog;

    public static final String EXTRA_TIME = "com.waminiyi.mareu.EXTRA_TIME";
    public static final String EXTRA_TOPIC = "com.waminiyi.mareu.EXTRA_TOPIC";
    public static final String EXTRA_ROOM = "com.waminiyi.mareu.EXTRA_ROOM";
    public static final String EXTRA_MAIL = "com.waminiyi.mareu.EXTRA_MAIL";
    public static final String EXTRA_DATE = "com.waminiyi.mareu.EXTRA_DATE";


    private List<String> mMeetingRoomsList;
    private List<String> mEmployeesMailList;

    private final String room = "Room";
    private final String mail = "Mail";

    private String mMeetingTime;
    private String mMeetingRoom;
    private String mMeetingTopic;
    private String mMeetingDate;
    private String mMeetingAttendees;
    private List<String> mAttendeesMailList;
    private MeetingDatabase mMeetingDatabase = MeetingDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        mMeetingTimeTextView = findViewById(R.id.new_meeting_time_textview);
        mMeetingTopicEditText = findViewById(R.id.new_meeting_topic_edittext);
        mMeetingRoomTextView = findViewById(R.id.new_meeting_room_textview);
        mMeetingAttendeesAddingTextView = findViewById(R.id.new_meeting_attendees_adding_textview);
        mMeetingAttendeesShowingTextView = findViewById(R.id.new_meeting_attendees_showing_textview);
        mMeetingSavingButton = findViewById(R.id.new_meeting_save_button);
        mMeetingDateTextView = findViewById(R.id.new_meeting_date_textview);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_TIME)) {
            mMeetingSavingButton.setVisibility(View.GONE);
            mMeetingTopicEditText.setEnabled(false);
            mMeetingTopicEditText.setFocusable(false);
            mMeetingTopicEditText.setBackground(null);
            mMeetingRoomTextView.setEnabled(false);
            mMeetingDateTextView.setEnabled(false);
            mMeetingTimeTextView.setEnabled(false);
            mMeetingAttendeesAddingTextView.setVisibility(View.GONE);
            mMeetingTopicEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

            mMeetingTopicEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_topic, 0, 0, 0);
            mMeetingTopicEditText.setText(intent.getStringExtra(EXTRA_TOPIC));
            mMeetingRoomTextView.setText(intent.getStringExtra(EXTRA_ROOM));
            mMeetingDateTextView.setText(intent.getStringExtra(EXTRA_DATE));
            mMeetingTimeTextView.setText(intent.getStringExtra(EXTRA_TIME));
            mMeetingAttendeesShowingTextView.setEnabled(false);
            mMeetingAttendeesShowingTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_group, 0, 0, 0);
            mMeetingAttendeesShowingTextView.setText(intent.getStringExtra(EXTRA_MAIL));
            getSupportActionBar().setTitle("Détails de la réunion");


        } else {
            getSupportActionBar().setTitle("Nouvelle réunion");

            mAttendeesMailList = new ArrayList<>();
            if (mAttendeesMailList.size() == 0) {
                mMeetingAttendeesShowingTextView.setVisibility(View.GONE);
            }

            mMeetingDateTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog();
                }
            });

            mMeetingTimeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTimePickerDialog();
                }
            });

            mMeetingRoomsList = RoomAndEmployeeDatabase.getInstance().getRoomList();
            mEmployeesMailList = RoomAndEmployeeDatabase.getInstance().getEmployeesMailList();


            mMeetingTopicEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mMeetingTopicEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    mMeetingTopicEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                    mMeetingTopic = mMeetingTopicEditText.getText().toString();
                }
            });


            mMeetingRoomTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogSpinner(room, mMeetingRoomsList);
                }
            });

            mMeetingAttendeesAddingTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogSpinner(mail, mEmployeesMailList);


                }
            });

            mMeetingSavingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    saveMeeting();
                }
            });

        }

    }

    public void showDialogSpinner(String target, List<String> dataSource) {
        // Initialize dialog
        mRoomDialog = new Dialog(NewMeetingActivity.this);

        // set custom dialog
        mRoomDialog.setContentView(R.layout.dialog_searchable_spinner);

        // set custom height and width
        mRoomDialog.getWindow().setLayout(650, 800);

        // set transparent background
        mRoomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // show dialog
        mRoomDialog.show();

        // Initialize and assign variable
        TextView textView = mRoomDialog.findViewById(R.id.research_label_textview);
        EditText researchEditText = mRoomDialog.findViewById(R.id.edit_text);
        ListView listView = mRoomDialog.findViewById(R.id.list_view);

        switch (target) {
            case room:
                textView.setText("Rechercher une salle");
                researchEditText.setHint("exemple: salle 1");
                dataSource = mMeetingRoomsList;
                break;
            case mail:
                textView.setText("Rechercher un collaborateur");
                researchEditText.setHint("exemple@lamzone.com");
                dataSource = mEmployeesMailList;
                break;
        }

        // Initialize array adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(NewMeetingActivity.this, android.R.layout.simple_list_item_1, dataSource);

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
                        // when item selected from list
                        // set selected item on textView
                        mMeetingRoom = adapter.getItem(position);
                        mMeetingRoomTextView.setText(mMeetingRoom);
                        // Dismiss dialog
                        mRoomDialog.dismiss();
                        break;
                    case mail:
                        String newMail = adapter.getItem(position);
                        String previousText;
                        String separator = " , ";
                        if (!mAttendeesMailList.contains(newMail)) {
                            mAttendeesMailList.add(newMail);

                            if (mAttendeesMailList.size() >= 2) {

                                mMeetingAttendees = mMeetingAttendees+ separator + newMail;
                                mMeetingAttendeesShowingTextView.setText(mMeetingAttendees);
                            } else {
                                mMeetingAttendees = newMail;
                                mMeetingAttendeesShowingTextView.setText(mMeetingAttendees);
                            }
                            mMeetingAttendeesShowingTextView.setVisibility(View.VISIBLE);
                        }
                        break;
                }


            }
        });
    }

    public void showTimePickerDialog() {
        DialogFragment newTimePickerFragment = new TimePickerFragment();
        newTimePickerFragment.show(getSupportFragmentManager(), getString(R.string.timePicker));
    }

    public void showDatePickerDialog() {
        DialogFragment newDatePickerFragment = new DatePickerFragment();
        newDatePickerFragment.show(getSupportFragmentManager(), getString(R.string.datePicker));
    }

    public void processTimePickerResult(int hour, int minute) {

        @SuppressLint("DefaultLocale") String hour_string = String.format("%02d", hour);
        @SuppressLint("DefaultLocale") String minute_string = String.format("%02d", minute);
        mMeetingTime = (hour_string + "h" + minute_string);
        mMeetingTimeTextView.setText(mMeetingTime);

    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        mMeetingDate = (day_string + "/" + month_string + "/" + year_string);
        mMeetingDateTextView.setText(mMeetingDate);

    }

    private void saveMeeting() {

        String topic = mMeetingTopicEditText.getText().toString();
        String room = mMeetingRoomTextView.getText().toString();
        String time = mMeetingTimeTextView.getText().toString();
        String attendees = mMeetingAttendeesShowingTextView.getText().toString();

        if (!isTextValid(topic) || !isTextValid(room) || !isTextValid(time) || !isTextValid(attendees)) {

            if (!isTextValid(topic)) {
                mMeetingTopicEditText.setError("Veuillez donner un titre à la réunion");
            }

            if (!isTextValid(room)) {
                mMeetingRoomTextView.setError("Veuillez choisir une salle");
            }

            if (!isTextValid(time)) {
                mMeetingTimeTextView.setError("Veuillez définir l'heure de la réunion");
            }

            if (!isTextValid(attendees)) {
                mMeetingAttendeesAddingTextView.setError("Veuillez ajouter des participants");
            }
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TOPIC, mMeetingTopic);
        data.putExtra(EXTRA_ROOM, mMeetingRoom);
        data.putExtra(EXTRA_TIME, mMeetingTime);
        data.putExtra(EXTRA_MAIL, mMeetingAttendees);
        data.putExtra(EXTRA_DATE, mMeetingDate);

        setResult(RESULT_OK, data);
        finish();
    }

    private boolean isTextValid(String text) {
        return !text.trim().isEmpty() && text.length() >= 2;
    }

}