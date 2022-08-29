package com.waminiyi.mareu.utils;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.CoreMatchers.is;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.view.MeetingRecyclerViewAdapter;

public class RecyclerViewItemFilteringAssertion implements ViewAssertion {


    private final String filter;
    private final String tag;

    public RecyclerViewItemFilteringAssertion(String tag, String filter) {
        this.tag = tag;
        this.filter = filter;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;

        MeetingRecyclerViewAdapter meetingAdapter = (MeetingRecyclerViewAdapter) recyclerView.getAdapter();

        if (tag.equals("DATE")) {

            for (int position = 0; position < meetingAdapter.getItemCount(); position++) {
                Meeting meeting = meetingAdapter.getMeetingAt(position);
                assertThat(meeting.getMeetingDate(), is(filter));
            }
        }else if (tag.equals("ROOM")){
            for (int position = 0; position < meetingAdapter.getItemCount(); position++) {
                Meeting meeting = meetingAdapter.getMeetingAt(position);
                assertThat(meeting.getMeetingRoom(), is(filter));
            }
        }

    }

}
