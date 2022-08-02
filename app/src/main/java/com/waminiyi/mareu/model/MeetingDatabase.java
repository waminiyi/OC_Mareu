package com.waminiyi.mareu.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeetingDatabase {

    private static MeetingDatabase INSTANCE;
    private List<Meeting> mMeetingList = new ArrayList<>();

    public MeetingDatabase() {
        Meeting meeting = new Meeting("29/07/2022", "08h00", "Salle red", "Réunion A", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));
        Meeting meeting1 = new Meeting("28/04/2022", "09h15", "Salle maroon", "Réunion B", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));
        Meeting meeting2 = new Meeting("27/05/2022", "10h30", "Salle yellow", "Réunion C", Arrays.asList("abdel@lamzone.com","albertine@lamzone.com", "ana-maria@lamzone.com"));
        Meeting meeting3 = new Meeting("26/06/2022", "11h45", "Salle lime", "Réunion D", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));
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

    public void deleteMeeting(Meeting meeting) {
        mMeetingList.remove(meeting);
    }

    public void addMeeting(Meeting meeting) {
        mMeetingList.add(meeting);
    }
}
