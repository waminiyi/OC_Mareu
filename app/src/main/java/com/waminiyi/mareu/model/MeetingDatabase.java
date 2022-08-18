package com.waminiyi.mareu.model;

import com.waminiyi.mareu.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeetingDatabase {

    private static MeetingDatabase INSTANCE;
    private List<Meeting> mMeetingList = new ArrayList<>();
    String[] rooms = new String[]{"Salle red", "Salle maroon", "Salle yellow", "Salle lime","Salle green","Salle aqua","Salle teal","Salle blue","Salle navy","Salle fuchsia"};


    public MeetingDatabase() {
        Meeting meeting = new Meeting("29/07/2022", "08h00", "Salle red", "Réunion A", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));
        Meeting meeting1 = new Meeting("28/04/2022", "09h15", "Salle maroon", "Réunion B", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));
        Meeting meeting2 = new Meeting("27/05/2022", "10h30", "Salle yellow", "Réunion C", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));
        Meeting meeting3 = new Meeting("26/06/2022", "11h45", "Salle lime", "Réunion D", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));
        Meeting meeting4 = new Meeting("29/07/2022", "08h00", "Salle green", "Réunion E", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));
        Meeting meeting5 = new Meeting("28/04/2022", "09h15", "Salle aqua", "Réunion F", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));
        Meeting meeting6 = new Meeting("27/05/2022", "10h30", "Salle teal", "Réunion G", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));
        Meeting meeting7 = new Meeting("26/06/2022", "11h45", "Salle blue", "Réunion H", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));
        Meeting meeting8 = new Meeting("29/07/2022", "08h00", "Salle navy", "Réunion I", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));
        Meeting meeting9 = new Meeting("28/04/2022", "09h15", "Salle fuchsia", "Réunion J", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));
        Meeting meeting10 = new Meeting("27/05/2022", "10h30", "Salle red", "Réunion K", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));
        Meeting meeting11 = new Meeting("26/06/2022", "11h45", "Salle maroon", "Réunion L", Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com"));

        meeting.setColorIndexFrom(rooms);
        meeting1.setColorIndexFrom(rooms);
        meeting2.setColorIndexFrom(rooms);
        meeting3.setColorIndexFrom(rooms);
        meeting4.setColorIndexFrom(rooms);
        meeting5.setColorIndexFrom(rooms);
        meeting6.setColorIndexFrom(rooms);
        meeting7.setColorIndexFrom(rooms);
        meeting8.setColorIndexFrom(rooms);
        meeting9.setColorIndexFrom(rooms);
        meeting10.setColorIndexFrom(rooms);
        meeting11.setColorIndexFrom(rooms);

        mMeetingList.addAll(Arrays.asList(meeting, meeting1, meeting2, meeting3, meeting4, meeting5, meeting6, meeting7, meeting8, meeting9, meeting10, meeting11));
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
