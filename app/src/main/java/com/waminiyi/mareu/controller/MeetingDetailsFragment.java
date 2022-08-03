package com.waminiyi.mareu.controller;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;
import com.waminiyi.mareu.view.AttendeesListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeetingDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingDetailsFragment extends Fragment {

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
    private Meeting mMeeting;
    private RecyclerView mMailRecyclerView;
    private AttendeesListAdapter mailAdapter;
    MaterialToolbar mToolbar;
    private int mImageVisibility;
    private Context mContext;

    private EditText mMeetingTopicEditText;
    private TextView mMeetingDateTextview;
    private TextView mMeetingTimeTextview;
    private TextView mMeetingRoomTextview;
    private TextView mMeetingAttendeesAddingTextview;
    private Button mMeetingSavingButton;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MEETING_LABEL = "meeting";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        mContext = view.getContext();

        mMeetingTopicEditText = view.findViewById(R.id.new_meeting_topic_edittext);
        mMeetingDateTextview = view.findViewById(R.id.new_meeting_date_textview);
        mMeetingTimeTextview = view.findViewById(R.id.new_meeting_time_textview);
        mMeetingRoomTextview = view.findViewById(R.id.new_meeting_room_textview);
        mMeetingAttendeesAddingTextview = view.findViewById(R.id.new_meeting_attendees_adding_textview);
        mMeetingSavingButton = view.findViewById(R.id.new_meeting_save_button);
        mMailRecyclerView = view.findViewById(R.id.attendees_mail_recyclerview);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMeetingDetails();
    }

    private void loadMeetingDetails() {
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

        mailAdapter.setMailsList(mAttendeesMailList);

    }
}