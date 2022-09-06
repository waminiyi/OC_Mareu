package com.waminiyi.mareu.model;

import com.waminiyi.mareu.utils.StringsUtils;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

public class MeetingDatabase {

    private static MeetingDatabase INSTANCE;
    private final List<Meeting> mMeetingList = new ArrayList<>();


    public MeetingDatabase() {
        mMeetingList.addAll(generateMeetingsExamples());
    }

    public List<Meeting> generateMeetingsExamples() {
        List<Meeting> meetingExamples = new ArrayList<>();
        String[] rooms = StringsUtils.getRoomsArray();
        for (int i = 0; i < 10; i++) {

            String meetingDate = StringsUtils.formatDate(2022, i, i + 20);
            String meetingTime = StringsUtils.formatTime(i + 7, 30);
            String meetingRoom = rooms[i];
            String meetingTopic = "Réunion " + String.valueOf(i + 1);
            List<String> meetingAttendees = Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com", "jean@lamzone.com", "elise@lamzone.com", "julien@lamzone.com");

            Meeting meeting = new Meeting(meetingDate, meetingTime, meetingRoom, meetingTopic, meetingAttendees);
            meeting.setColorIndexFrom(rooms);
            meetingExamples.add(meeting);
        }
        return meetingExamples;
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

    public Meeting getMeetingAt(int position) {
        return mMeetingList.get(position);
    }

    public List<Meeting> getMeetingListFilteredByDate(CharSequence constraint) {
        List<Meeting> filteredList;
        if (constraint == null || constraint.length() == 0) {
            filteredList = mMeetingList;
        } else {
            filteredList = new ArrayList<>();
            String filterPattern = constraint.toString().toLowerCase().trim();
            for (Meeting meeting : mMeetingList) {
                if (meeting.getMeetingDate().toLowerCase().contains(filterPattern)) {
                    filteredList.add(meeting);
                }
            }
        }
        return filteredList;
    }

    public List<Meeting> getMeetingListFilteredByRoom(CharSequence constraint) {

        List<Meeting> filteredList;
        if (constraint == null || constraint.length() == 0) {
            filteredList = mMeetingList;
        } else {
            filteredList = new ArrayList<>();
            String filterPattern = constraint.toString().toLowerCase().trim();
            for (Meeting meeting : mMeetingList) {
                if (meeting.getMeetingRoom().toLowerCase().contains(filterPattern)) {
                    filteredList.add(meeting);
                }
            }
        }
        return filteredList;
    }
}
