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
import androidx.appcompat.widget.Toolbar;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.MailModel;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;
import com.waminiyi.mareu.utils.ItemClickSupport;
import com.waminiyi.mareu.view.AttendeesListAdapter;
import com.waminiyi.mareu.view.MailAddingAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NewMeetingFragment extends Fragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private Dialog mRoomDialog;
    private List<String> mMeetingRoomsList;
    private List<String> mEmployeesMailList;
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
    private List<MailModel> mMailsModelList;

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
        mMailsModelList = new ArrayList<>();

        for (int i = 0; i < mEmployeesMailList.size(); i++) {
            MailModel mailModel = new MailModel(mEmployeesMailList.get(i), false);
            mMailsModelList.add(mailModel);
        }
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
            showRoomDialog();
        } else if (v == mNewMeetingAttendeesAddingTextview) {
            showMailDialog();
        } else if (v == mMeetingSavingButton) {
            saveMeeting();
        } else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }

    }

    public void showMailDialog() {

        mRoomDialog = new Dialog(mContext);
        mRoomDialog.setContentView(R.layout.mail_dialog_searchable_spinner);
        mRoomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mRoomDialog.show();

        TextView textView = mRoomDialog.findViewById(R.id.dialog_mail_research_label_textview);
        EditText researchEditText = mRoomDialog.findViewById(R.id.dialog_mail_edit_text);
        RecyclerView mailAddingRecyclerView = mRoomDialog.findViewById(R.id.dialog_mail_recycler_view);
        mailAddingRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        MailAddingAdapter mailAddingAdapter = new MailAddingAdapter(mContext, mMailsModelList);

        mailAddingRecyclerView.setAdapter(mailAddingAdapter);

        ItemClickSupport.addTo(mailAddingRecyclerView, R.layout.listview_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        MailModel newMailModel = mailAddingAdapter.getMailAt(position);
                        ImageButton actionButton = v.findViewById(R.id.new_meeting_mail_action_button);
                        int index = mMailsModelList.indexOf(newMailModel);
//
                        if (!mAttendeesMailList.contains(newMailModel.getMail())) {
                            mAttendeesMailList.add(newMailModel.getMail());
                            mMailsModelList.set(index, new MailModel(newMailModel.getMail(), true));
                            actionButton.setImageResource(R.drawable.ic_checked);
                        } else {
                            mAttendeesMailList.remove(newMailModel.getMail());
                            mMailsModelList.set(index, new MailModel(newMailModel.getMail(), false));
                            actionButton.setImageResource(R.drawable.ic_unchecked);
                        }
                        mailAdapter.notifyDataSetChanged();
                    }
                });

        researchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mailAddingAdapter.filterMails(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void showRoomDialog() {

        mRoomDialog = new Dialog(mContext);
        mRoomDialog.setContentView(R.layout.dialog_searchable_spinner);
        mRoomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mRoomDialog.show();

        TextView textView = mRoomDialog.findViewById(R.id.research_label_textview);
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
                mMeetingRoom = adapter.getItem(position);
                mNewMeetingRoomTextview.setText(mMeetingRoom);
                mRoomDialog.dismiss();
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
        @SuppressLint("DefaultLocale") String month_string = String.format("%02d", month + 1);
        @SuppressLint("DefaultLocale") String day_string = String.format("%02d", day);
        @SuppressLint("DefaultLocale") String year_string = String.format("%02d", year);
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

        mailAdapter = new AttendeesListAdapter(mContext, mAttendeesMailList, 1);

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