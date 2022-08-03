package com.waminiyi.mareu.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waminiyi.mareu.R;

public class AttendeesListViewHolder extends RecyclerView.ViewHolder{

    public TextView mailTextView;
    public ImageView avatarImage;

    public AttendeesListViewHolder(@NonNull View itemView) {
        super(itemView);

        mailTextView = itemView.findViewById(R.id.item_attendee_mail);
        avatarImage=itemView.findViewById(R.id.item_attendee_avatar);
    }
}
