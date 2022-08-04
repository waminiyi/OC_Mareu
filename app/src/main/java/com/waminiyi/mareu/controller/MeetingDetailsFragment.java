package com.waminiyi.mareu.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;
import com.waminiyi.mareu.utils.ItemClickSupport;
import com.waminiyi.mareu.view.AttendeesListAdapter;
import com.waminiyi.mareu.view.MeetingRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeetingDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingDetailsFragment extends Fragment {

    private List<String> mAttendeesMailList = new ArrayList<>();
    private Meeting mMeeting;
    private RecyclerView mMailRecyclerView;
    private AttendeesListAdapter mailAdapter;

    private TextView mMeetingTopicTextView;
    private TextView mMeetingDateTextview;
    private TextView mMeetingTimeTextview;
    private TextView mMeetingRoomTextview;
    private TextView mMeetingAttendeesLabelTextview;
    private ImageView mImageView;
    private Toolbar mToolbar;


    private static final String MEETING_LABEL = "meeting";

    public MeetingDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MeetingDetailsFragment.
     */

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
        mImageView = view.findViewById(R.id.meeting_image_view);
        mMailRecyclerView = view.findViewById(R.id.attendees_mail_recyclerview);
        mToolbar=view.findViewById(R.id.meeting_top_app_bar);

        mMailRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
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
    }

    @Override
    public void onResume() {
        super.onResume();
        configureRecyclerView();
        loadMeetingDetails();
    }

    private void loadMeetingDetails() {
        mMeetingTopicTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_topic, 0, 0, 0);
        mMeetingAttendeesLabelTextview.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_group, 0, 0, 0);

        mMeetingAttendeesLabelTextview.setText(getResources().getString(R.string.attendees_list_placeholder));

        mMeetingTopicTextView.setText(mMeeting.getMeetingTopic());
        mMeetingRoomTextview.setText(mMeeting.getMeetingRoom());
        mMeetingDateTextview.setText(mMeeting.getMeetingDate());
        mMeetingTimeTextview.setText(mMeeting.getMeetingTime());

        if (mImageView != null) {
            Glide.with(this)
                    .load(getResources().getStringArray(R.array.images_array)[mMeeting.getColorIndex()]).apply(RequestOptions.centerCropTransform())
                    .into(mImageView);
        }
    }

    private void configureRecyclerView() {

        mAttendeesMailList = mMeeting.getMeetingAttendees();
        mailAdapter = new AttendeesListAdapter(this.getContext(), mAttendeesMailList);
        mMailRecyclerView.setAdapter(mailAdapter);

    }

}