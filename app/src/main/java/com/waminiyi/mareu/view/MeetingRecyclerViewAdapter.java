package com.waminiyi.mareu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;

import java.util.ArrayList;
import java.util.List;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingViewHolder> implements Filterable {

    private List<Meeting> mMeetingList;
    private List<Meeting> mMeetingListCopy;
    Context mContext;

    public MeetingRecyclerViewAdapter(List<Meeting> meetingList) {
        this.mMeetingList = meetingList;
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
          mContext = holder.iconImage.getContext();

            int colorIndex =currentMeeting.getColorIndex();
//            int meetingColor = RoomAndEmployeeDatabase.getInstance().getColorFromRoom(currentMeeting.getMeetingRoom());

            holder.roomNameTextView.setText(currentMeeting.getMeetingRoom());
            holder.timeTextView.setText(currentMeeting.getMeetingTime() + " - ");
            holder.topicTextView.setText(currentMeeting.getMeetingTopic() + " - ");
            holder.attendeesListTextView.setText(currentMeeting.getMeetingAttendees());

//            holder.iconImage.setBackgroundColor(mContext.getResources().getColor(meetingColor));
//            holder.iconImage.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(meetingColor) ));
            holder.iconImage.setColorFilter(mContext.getResources().getIntArray(R.array.colors_array)[colorIndex]);

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


    @Override
    public Filter getFilter() {
        return meetingListFilter;
    }

    private Filter meetingListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Meeting> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mMeetingListCopy);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Meeting meeting : mMeetingListCopy) {
                    if (meeting.getMeetingRoom().toLowerCase().contains(filterPattern)) {
                        filteredList.add(meeting);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            setMeetingsList(new ArrayList<Meeting>((List) results.values));
        }
    };


}