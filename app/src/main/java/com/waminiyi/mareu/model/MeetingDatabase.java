package com.waminiyi.mareu.model;

import java.util.ArrayList;
import java.util.List;

public class MeetingDatabase {

    private static MeetingDatabase INSTANCE;
    private List<Meeting>  mMeetingList = new ArrayList<>();

    public MeetingDatabase() {
      Meeting meeting = new Meeting("mMeetingDate","mMeetingTime", "mMeetingRoom", "mMeetingTopic", "mMeetingAttendeesShowingTextView");
      mMeetingList.add(meeting);
    }

   public static MeetingDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (MeetingDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MeetingDatabase();
                }
            }
        }
        return INSTANCE;
    }



    public List<Meeting> getMeetingList() {

        return mMeetingList;
    }

    public void deleteMeeting(Meeting meeting){
        mMeetingList.remove(meeting);
    }

    public void addMeeting(Meeting meeting){
        mMeetingList.add(meeting);
    }
}
