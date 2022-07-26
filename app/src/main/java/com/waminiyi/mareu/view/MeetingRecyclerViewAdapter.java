package com.waminiyi.mareu.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;
import com.waminiyi.mareu.services.RoomAndEmployeeDatabase;

import java.util.List;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingViewHolder> {

    private List<Meeting> mMeetingList;
    Context mContext;

    public MeetingRecyclerViewAdapter(List<Meeting> meetingList) {
        this.mMeetingList = meetingList;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_item, parent, false);
        return new MeetingViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        if (mMeetingList != null && position < mMeetingList.size()) {
            Meeting currentMeeting = mMeetingList.get(position);
            mContext = holder.iconImage.getContext();
            int color= RoomAndEmployeeDatabase.getInstance().getRandomImage(currentMeeting.getMeetingRoom());

            holder.roomNameTextView.setText(currentMeeting.getMeetingRoom());
            holder.timeTextView.setText(currentMeeting.getMeetingTime() + " - ");
            holder.topicTextView.setText(currentMeeting.getMeetingTopic() + " - ");
            holder.attendeesListTextView.setText(currentMeeting.getMeetingAttendees());
            holder.iconImage.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(color)));
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MeetingDatabase.getInstance().deleteMeeting(currentMeeting);
                    setMeetingsList(MeetingDatabase.getInstance().getMeetingList());
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.roomNameTextView.setText("No Meeting planned");
        }
    }


    public void setMeetingsList(List<Meeting> meetings) {
        mMeetingList = meetings;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mMeetingList != null)
            return mMeetingList.size();
        else return 0;
    }

    public Meeting getMeetingAt(int position) {
        return mMeetingList.get(position);
    }


}