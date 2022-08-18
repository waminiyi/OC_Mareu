package com.waminiyi.mareu.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.waminiyi.mareu.R;

import java.util.List;

public class Meeting implements Parcelable {
    private final String mMeetingTime;
    private final String mMeetingDate;
    private final String mMeetingRoom;
    private final String mMeetingTopic;
    private final List<String> mMeetingAttendees;
    private int mColorIndex;

    public Meeting(String meetingDate, String meetingTime, String meetingRoom, String meetingTopic, List<String> meetingAttendees) {
        mMeetingDate = meetingDate;
        mMeetingTime = meetingTime;
        mMeetingRoom = meetingRoom;
        mMeetingTopic = meetingTopic;
        mMeetingAttendees = meetingAttendees;
    }

    protected Meeting(Parcel in) {
        mMeetingTime = in.readString();
        mMeetingDate = in.readString();
        mMeetingRoom = in.readString();
        mMeetingTopic = in.readString();
        mMeetingAttendees = in.createStringArrayList();
        mColorIndex = in.readInt();
    }

    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };

    public String getMeetingTime() {
        return mMeetingTime;
    }

    public String getMeetingRoom() {
        return mMeetingRoom;
    }

    public String getMeetingTopic() {
        return mMeetingTopic;
    }

    public List<String> getMeetingAttendees() {
        return mMeetingAttendees;
    }

    public String getMeetingDate() {
        return mMeetingDate;
    }

    public int getColorIndex() {
        return mColorIndex;
    }

    public void setColorIndexFrom(String[] roomsList) {

        int colorIndex = R.color.red;

        for (int i = 0; i < 10; i++) {
            if (roomsList[i].equals(mMeetingRoom)) {
                colorIndex = i;
                break;
            }
        }
        mColorIndex = colorIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMeetingTime);
        dest.writeString(mMeetingDate);
        dest.writeString(mMeetingRoom);
        dest.writeString(mMeetingTopic);
        dest.writeStringList(mMeetingAttendees);
        dest.writeInt(mColorIndex);
    }


}
