package com.waminiyi.mareu.controller;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.view.AttendeesListAdapter;

import java.util.List;


public class MeetingDetailsFragment extends Fragment {

    private Meeting mMeeting;
    private RecyclerView mMailRecyclerView;

    private TextView mMeetingTopicTextView;
    private TextView mMeetingDateTextview;
    private TextView mMeetingTimeTextview;
    private TextView mMeetingRoomTextview;
    private TextView mMeetingAttendeesLabelTextview;
    private Toolbar mTopAppBar;
    private int mLayoutMode;
    private FrameLayout mFrameLayout;

    private static final String MEETING_LABEL = "meeting";

    public MeetingDetailsFragment() {
    }

    public static MeetingDetailsFragment newInstance(Meeting meeting) {

        MeetingDetailsFragment fragment = new MeetingDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(MEETING_LABEL, meeting);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMeeting = getArguments().getParcelable(MEETING_LABEL);
        }
        mLayoutMode = getResources().getInteger(R.integer.layout_mode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting_details, container, false);
        Context context = view.getContext();

        mMeetingTopicTextView = view.findViewById(R.id.meeting_topic_textview);
        mMeetingDateTextview = view.findViewById(R.id.meeting_date_textview);
        mMeetingTimeTextview = view.findViewById(R.id.meeting_time_textview);
        mMeetingRoomTextview = view.findViewById(R.id.meeting_room_textview);
        mMeetingAttendeesLabelTextview = view.findViewById(R.id.meeting_attendees_label_textview);

        mMailRecyclerView = view.findViewById(R.id.attendees_mail_recyclerview);
        mTopAppBar = view.findViewById(R.id.meeting_top_app_bar);
        if (mLayoutMode == 2) {
            mFrameLayout = getActivity().findViewById(R.id.frame_layout_meeting);
        }
        mMailRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mTopAppBar.inflateMenu(R.menu.fragment_menu);
        mTopAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.close_fragment) {
                if (mLayoutMode == 2) {
                    getParentFragmentManager().beginTransaction().remove(MeetingDetailsFragment.this).commit();
                    mFrameLayout.setVisibility(View.GONE);

                } else {
                    getActivity().finish();
                }
                return true;
            }
            return false;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        configureRecyclerView();
        loadMeetingDetails();
    }

    private void loadMeetingDetails() {

        mMeetingAttendeesLabelTextview.setText(getResources().getString(R.string.attendees_list_placeholder));

        mMeetingTopicTextView.setText(mMeeting.getMeetingTopic());
        mMeetingRoomTextview.setText(mMeeting.getMeetingRoom());
        mMeetingDateTextview.setText(mMeeting.getMeetingDate());
        mMeetingTimeTextview.setText(mMeeting.getMeetingTime());
    }

    private void configureRecyclerView() {

        List<String> attendeesMailList = mMeeting.getMeetingAttendees();
        AttendeesListAdapter mailAdapter = new AttendeesListAdapter(this.getContext(), attendeesMailList, 2);
        mMailRecyclerView.setAdapter(mailAdapter);
    }
}