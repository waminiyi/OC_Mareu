package com.waminiyi.mareu.model;


public class Meeting {
    private String mMeetingTime;
    private String mMeetingDate;
    private String mMeetingRoom;
    private String mMeetingTopic;
    private String mMeetingAttendees;
    private int mColorIndex;

    public Meeting(String meetingDate, String meetingTime, String meetingRoom, String meetingTopic, String meetingAttendees) {
        mMeetingDate=meetingDate;
        mMeetingTime = meetingTime;
        mMeetingRoom = meetingRoom;
        mMeetingTopic = meetingTopic;
        mMeetingAttendees = meetingAttendees;
    }

    public String getMeetingTime() {
        return mMeetingTime;
    }

    public String getMeetingRoom() {
        return mMeetingRoom;
    }

    public String getMeetingTopic() {
        return mMeetingTopic;
    }

    public String getMeetingAttendees() {
        return mMeetingAttendees;
    }

    public String getMeetingDate() {
        return mMeetingDate;
    }

    public int getColorIndex() {
        return mColorIndex;
    }

    public void setColorIndex(int colorIndex) {
        mColorIndex = colorIndex;
    }
}
