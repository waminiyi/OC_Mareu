package com.waminiyi.mareu.services;


import com.waminiyi.mareu.R;

import java.util.ArrayList;
import java.util.List;

public class RoomAndEmployeeDatabase {
    private static RoomAndEmployeeDatabase INSTANCE;
    private List<String> mMeetingRoomsList;
    private List<String> mEmployeesMailList;
    private int meetingColor;

    public RoomAndEmployeeDatabase() {
    }

    public static RoomAndEmployeeDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (RoomAndEmployeeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RoomAndEmployeeDatabase();
                }
            }
        }
        return INSTANCE;
    }


    public List<String> getRoomList() {
        mMeetingRoomsList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            mMeetingRoomsList.add("Salle " + i);
        }

        return mMeetingRoomsList;
    }


    public int getColorFromRoom(String room) {

        int[] colors = {R.color.red, R.color.blue, R.color.yellow, R.color.aqua,
                R.color.maroon, R.color.navy, R.color.lime, R.color.teal,
                R.color.fuchsia, R.color.green};



        if (room.equals("Salle red")) {
            meetingColor = colors[0];
        } else if (room.equals("Salle maroon")) {
            meetingColor = colors[1];
        } else if (room.equals("Salle yellow")) {
            meetingColor = colors[2];
        } else if (room.equals("Salle 4")) {
            meetingColor = colors[3];
        } else if (room.equals("Salle 5")) {
            meetingColor = colors[4];
        } else if (room.equals("Salle 6")) {
            meetingColor = colors[5];
        } else if (room.equals("Salle 7")) {
            meetingColor = colors[6];
        } else if (room.equals("Salle 8")) {
            meetingColor = colors[7];
        } else if (room.equals("Salle 9")) {
            meetingColor = colors[8];
        } else if (room.equals("Salle 10")) {
            meetingColor = colors[9];
        } else {
            meetingColor = colors[0];
        }
        return meetingColor;
    }
}
