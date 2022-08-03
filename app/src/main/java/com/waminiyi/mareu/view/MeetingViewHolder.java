package com.waminiyi.mareu.view;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waminiyi.mareu.R;

public class MeetingViewHolder extends RecyclerView.ViewHolder {

    public TextView roomNameTextView;
    public TextView timeTextView;
    public TextView topicTextView;
    public TextView attendeesListTextView;
    public ImageButton deleteButton;
    public ImageView iconImage;

    public MeetingViewHolder(@NonNull View itemView) {
        super(itemView);

        roomNameTextView = itemView.findViewById(R.id.item_room_textview);
        timeTextView = itemView.findViewById(R.id.item_time_textview);
        topicTextView = itemView.findViewById(R.id.item_topic_textview);
        attendeesListTextView = itemView.findViewById(R.id.item_attendees_textview);
        deleteButton=itemView.findViewById(R.id.item_list_delete_button);
        iconImage=itemView.findViewById(R.id.item_list_avatar);
    }
}