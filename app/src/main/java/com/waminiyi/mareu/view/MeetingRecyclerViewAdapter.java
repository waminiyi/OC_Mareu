package com.waminiyi.mareu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;

import java.util.ArrayList;
import java.util.List;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingViewHolder> {

    private List<Meeting> mMeetingList;
    private List<Meeting> mMeetingListCopy;
    private Context mContext;

    public MeetingRecyclerViewAdapter(Context context, List<Meeting> meetingList) {
        this.mMeetingList = meetingList;
        this.mContext=context;
        mMeetingListCopy = new ArrayList<>(meetingList);
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

            int colorIndex = currentMeeting.getColorIndex();

            holder.roomNameTextView.setText(currentMeeting.getMeetingRoom());
            holder.timeTextView.setText(currentMeeting.getMeetingTime() + " - ");
            holder.topicTextView.setText(currentMeeting.getMeetingTopic() + " - ");
            holder.attendeesListTextView.setText(String.join(", ", currentMeeting.getMeetingAttendees()));

            holder.iconImage.setColorFilter(mContext.getResources().getIntArray(R.array.colors_array)[colorIndex]);

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MeetingDatabase.getInstance().deleteMeeting(currentMeeting);
                    setMeetingsList(MeetingDatabase.getInstance().getMeetingList());
                }
            });
        } else {

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

    public void filter(String parameter, CharSequence constraint) {
        List<Meeting> filteredList = new ArrayList<>();
        if (constraint == null || constraint.length() == 0) {
            filteredList.addAll(mMeetingListCopy);
        } else {
            String filterPattern = constraint.toString().toLowerCase().trim();
            if (parameter.equals(mContext.getString(R.string.room_filter_label))) {
                for (Meeting meeting : mMeetingListCopy) {
                    if (meeting.getMeetingRoom().toLowerCase().contains(filterPattern)) {
                        filteredList.add(meeting);
                    }
                }
            } else if (parameter.equals(mContext.getString(R.string.date_filter_label))) {
                for (Meeting meeting : mMeetingListCopy) {
                    if (meeting.getMeetingDate().toLowerCase().contains(filterPattern)) {
                        filteredList.add(meeting);
                    }
                }
            }
        }
        setMeetingsList(filteredList);
    }
}