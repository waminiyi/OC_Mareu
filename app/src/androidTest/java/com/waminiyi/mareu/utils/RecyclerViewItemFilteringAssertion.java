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

//    public static Matcher<View> hasItem(Matcher<View> matcher) {
//        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
//
//            @Override public void describeTo(Description description) {
//                description.appendText("has item: ");
//                matcher.describeTo(description);
//            }
////
////            @Override protected boolean matchesSafely(RecyclerView view) {
//                MeetingRecyclerViewAdapter meetingAdapter=(MeetingRecyclerViewAdapter)view.getAdapter();
//
//                for (int position = 0; position < meetingAdapter.getItemCount(); position++) {
//                    Meeting meeting = meetingAdapter.getMeetingAt(position);
//
//                    if (meeting.getMeetingDate().contains()) {
//                        return true;
//                    }
//                }
//                return false;

    //                RecyclerView.Adapter adapter = view.getAdapter();
//                for (int position = 0; position < adapter.getItemCount(); position++) {
//                    int type = adapter.getItemViewType(position);
//                    RecyclerView.ViewHolder holder = adapter.createViewHolder(view, type);
//                    adapter.onBindViewHolder(holder, position);
//                    if (matcher.matches(holder.itemView)) {
//                        return true;
//                    }
////                }
////                return false;
//            }
//        };
//    }
//
//     {
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
