package com.waminiyi.mareu.model;

import com.waminiyi.mareu.R;

import java.util.ArrayList;
import java.util.List;

public class MeetingDatabase {

    private static MeetingDatabase INSTANCE;
    private List<Meeting>  mMeetingList = new ArrayList<>();

    public MeetingDatabase() {
      Meeting meeting = new Meeting("Reunion 1","08h00", "Salle red", "Sujet 1", "abdel@lamzone.com, albertine@lamzone.com, ana-maria@lamzone.com");
        Meeting meeting1 = new Meeting("Reunion 2","09h15", "Salle maroon", "Sujet 2", "abdel@lamzone.com, albertine@lamzone.com, ana-maria@lamzone.com");
        Meeting meeting2 = new Meeting("Reunion 3","10h30", "Salle yellow", "Sujet 3", "abdel@lamzone.com, albertine@lamzone.com, ana-maria@lamzone.com");
        Meeting meeting3 = new Meeting("Reunion 4","11h45", "Salle lime", "Sujet 4", "abdel@lamzone.com, albertine@lamzone.com, ana-maria@lamzone.com");
meeting.setColorIndex(0);
        meeting1.setColorIndex(1);
        meeting2.setColorIndex(2);
        meeting3.setColorIndex(3);

        mMeetingList.add(meeting);
        mMeetingList.add(meeting1);
        mMeetingList.add(meeting2);
        mMeetingList.add(meeting3);
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
