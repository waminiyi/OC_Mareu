package com.waminiyi.mareu.model;


public class Meeting {
    private String mMeetingTime;
    private String mMeetingDate;
    private String mMeetingRoom;
    private String mMeetingTopic;
    private String mMeetingAttendees;

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

    public void setMeetingTime(String meetingTime) {
        mMeetingTime = meetingTime;
    }

    public String getMeetingRoom() {
        return mMeetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        mMeetingRoom = meetingRoom;
    }

    public String getMeetingTopic() {
        return mMeetingTopic;
    }

    public void setMeetingTopic(String meetingTopic) {
        mMeetingTopic = meetingTopic;
    }

    public String getMeetingAttendees() {
        return mMeetingAttendees;
    }

    public void setMeetingAttendees(String meetingAttendees) {
        mMeetingAttendees = meetingAttendees;
    }

    public String getMeetingDate() {
        return mMeetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        mMeetingDate = meetingDate;
    }
}
