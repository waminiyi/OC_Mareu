package com.waminiyi.mareu.controller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.waminiyi.mareu.R;
import com.waminiyi.mareu.databinding.ActivityNewMeetingBinding;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewMeetingActivity extends AppCompatActivity implements View.OnClickListener {

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
    private MeetingDatabase mMeetingDatabase = MeetingDatabase.getInstance();
    private ActivityNewMeetingBinding binding;
    private Meeting mMeeting;
    private ArrayAdapter<String> mListAdapter;
    private int mImageVisibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        mImageVisibility = getResources().getInteger(R.integer.grid_column_count);
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_MEETING)) {
            mMeeting = intent.getParcelableExtra(EXTRA_MEETING);

            loadMeetingDetails();

        } else {
            getSupportActionBar().setTitle(R.string.new_meeting_label);

            mListAdapter = new ArrayAdapter<>(this, R.layout.listview_item, mAttendeesMailList);
            binding.attendeesMailListview.setAdapter(mListAdapter);

            if (binding.newMeetingImageView != null) {
                binding.newMeetingImageView.setVisibility(View.GONE);
            }

            binding.newMeetingDateTextview.setOnClickListener(this);
            binding.newMeetingTimeTextview.setOnClickListener(this);
            binding.newMeetingRoomTextview.setOnClickListener(this);
            binding.newMeetingAttendeesAddingTextview.setOnClickListener(this);
            binding.newMeetingSaveButton.setOnClickListener(this);

            mMeetingRoomsList = Arrays.asList(getResources().getStringArray(R.array.rooms));
            mEmployeesMailList = Arrays.asList(getResources().getStringArray(R.array.random_mails));

            binding.newMeetingTopicEdittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    binding.newMeetingTopicEdittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    binding.newMeetingTopicEdittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                    mMeetingTopic = binding.newMeetingTopicEdittext.getText().toString();
                }
            });

        }

    }

    @Override
    public void onClick(View v) {
        if (v == binding.newMeetingDateTextview) {
            showDatePickerDialog();
        } else if (v == binding.newMeetingTimeTextview) {
            showTimePickerDialog();
        } else if (v == binding.newMeetingRoomTextview) {
            showDialogSpinner(room, mMeetingRoomsList);
        } else if (v == binding.newMeetingAttendeesAddingTextview) {
            showDialogSpinner(mail, mEmployeesMailList);
        } else if (v == binding.newMeetingSaveButton) {
            saveMeeting();
        } else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }

    }

    private void loadMeetingDetails() {
        getSupportActionBar().setTitle(R.string.meeting_details_label);

        binding.newMeetingSaveButton.setVisibility(View.GONE);
        binding.newMeetingTopicEdittext.setEnabled(false);
        binding.newMeetingTopicEdittext.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_topic, 0, 0, 0);

        binding.newMeetingTopicEdittext.setBackground(null);

        if (binding.newMeetingImageView != null) {
            binding.newMeetingImageView.setVisibility(View.VISIBLE);
        }

        binding.newMeetingAttendeesAddingTextview.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_group, 0, 0, 0);

        binding.newMeetingAttendeesAddingTextview.setText(getResources().getString(R.string.attendees_list_placeholder));

        binding.newMeetingTopicEdittext.setText(mMeeting.getMeetingTopic());
        binding.newMeetingRoomTextview.setText(mMeeting.getMeetingRoom());
        binding.newMeetingDateTextview.setText(mMeeting.getMeetingDate());

        binding.newMeetingTimeTextview.setText(mMeeting.getMeetingTime());

        if (binding.newMeetingImageView != null) {
            Glide.with(this)
                    .load(getResources().getStringArray(R.array.images_array)[mMeeting.getColorIndex()]).apply(RequestOptions.centerCropTransform())
                    .into(binding.newMeetingImageView);
        }

        mAttendeesMailList = mMeeting.getMeetingAttendees();
        mListAdapter = new ArrayAdapter<>(this, R.layout.listview_item, mAttendeesMailList);
        binding.attendeesMailListview.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();

    }

    public void showDialogSpinner(String target, List<String> dataSource) {
        // Initialize dialog
        mRoomDialog = new Dialog(NewMeetingActivity.this);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(NewMeetingActivity.this, R.layout.listview_item, dataSource);

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
                        binding.newMeetingRoomTextview.setText(mMeetingRoom);
                        mRoomDialog.dismiss();
                        break;
                    case mail:
                        String newMail = adapter.getItem(position);
                        if (!mAttendeesMailList.contains(newMail)) {
                            mAttendeesMailList.add(newMail);
                        }
                        break;
                }
                mListAdapter.notifyDataSetChanged();
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
        binding.newMeetingTimeTextview.setText(mMeetingTime);

    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        mMeetingDate = (day_string + "/" + month_string + "/" + year_string);
        binding.newMeetingDateTextview.setText(mMeetingDate);

    }

    private void saveMeeting() {

        if (!isTextValid(mMeetingTopic) || !isTextValid(mMeetingRoom) || !isTextValid(mMeetingDate) || !isTextValid(mMeetingTime) || mAttendeesMailList.size() == 0) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();

        mMeeting = new Meeting(mMeetingDate, mMeetingTime, mMeetingRoom, mMeetingTopic, mAttendeesMailList);
        data.putExtra(EXTRA_MEETING, mMeeting);

        setResult(RESULT_OK, data);
        finish();
    }

    private boolean isTextValid(String text) {
        return !text.trim().isEmpty() && text.length() >= 2;
    }
}