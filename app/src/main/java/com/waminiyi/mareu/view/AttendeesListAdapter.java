package com.waminiyi.mareu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waminiyi.mareu.R;
import java.util.List;

public class AttendeesListAdapter extends RecyclerView.Adapter<AttendeesListViewHolder> {

    private List<String> mMailList;
    Context mContext;

    public AttendeesListAdapter(List<String> mailList) {
        this.mMailList = mailList;
    }

    @NonNull
    @Override
    public AttendeesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        return new AttendeesListViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendeesListViewHolder holder, int position) {
        if (mMailList != null && position < mMailList.size()) {
            String mail = mMailList.get(position);
            mContext = holder.avatarImage.getContext();

            holder.mailTextView.setText(mail);

//            holder.timeTextView.setText(currentMeeting.getMeetingTime() + " - ");
//            holder.topicTextView.setText(currentMeeting.getMeetingTopic() + " - ");
//            holder.attendeesListTextView.setText(String.join(", ", currentMeeting.getMeetingAttendees()));
//
//            holder.iconImage.setColorFilter(mContext.getResources().getIntArray(R.array.colors_array)[colorIndex]);
//
//            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    MeetingDatabase.getInstance().deleteMeeting(currentMeeting);
//                    setMeetingsList(MeetingDatabase.getInstance().getMeetingList());
//                }
//            });
        }
//        else {
//
//            holder.roomNameTextView.setText("No Meeting planned");
//        }
    }

    public void setMailsList(List<String> mails) {
        mMailList = mails;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mMailList != null)
            return mMailList.size();
        else return 0;
    }

    public String getMailAt(int position) {
        return mMailList.get(position);
    }
}
