package com.waminiyi.mareu;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;
import com.waminiyi.mareu.utils.StringsUtils;
import com.waminiyi.mareu.view.MeetingRecyclerViewAdapter;

import java.util.Arrays;
import java.util.List;


@RunWith(JUnit4.class)
public class MareuUnitTests {

    private MeetingDatabase database;

    @Before
    public void setup() {
        database = MeetingDatabase.getInstance();
    }

    /**
     * Testing if meeting listing is working fine
     */

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = database.getMeetingList();
        assertEquals(10, meetings.size());
    }

    /**
     * Testing if meeting adding is working fine
     */
    @Test
    public void addMeetingWithSuccess() {

        String[] rooms = StringsUtils.getRoomsArray();
        String meetingDate = StringsUtils.formatDate(2022, 11, 31);
        String meetingTime = StringsUtils.formatTime(10, 00);
        String meetingRoom = rooms[0];
        String meetingTopic = "Réunion importante ";
        List<String> meetingAttendees = Arrays.asList("abdel@lamzone.com", "albertine@lamzone.com", "ana-maria@lamzone.com");

        Meeting meeting = new Meeting(meetingDate, meetingTime, meetingRoom, meetingTopic, meetingAttendees);
        meeting.setColorIndexFrom(rooms);
        database.addMeeting(meeting);

        List<Meeting> meetingList = database.getMeetingList();

        assertTrue(meetingList.contains(meeting));
        database.deleteMeeting(meeting);

    }

    /**
     * Testing if meeting deletion is working fine
     */

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = database.getMeetingList().get(0);
        database.deleteMeeting(meetingToDelete);
        assertFalse(database.getMeetingList().contains(meetingToDelete));
        database.addMeeting(meetingToDelete);
    }

    @Test
    public void filterMeetingsByDateWithSuccess() {
        List<Meeting> mMeetingInitialList = database.getMeetingList();

        assertEquals(10, mMeetingInitialList.size());

        String meetingDate = StringsUtils.formatDate(2022, 2, 22);

        List<Meeting> meetingfilteredList = database.getMeetingListFilteredByDate(meetingDate);

        for (int i = 0; i < meetingfilteredList.size(); i++) {
            Meeting meeting = meetingfilteredList.get(i);
            assertThat(meeting.getMeetingDate(), is(meetingDate));
        }
    }

    @Test
    public void filterMeetingsByRoomWithSuccess() {
        List<Meeting> mMeetingInitialList = database.getMeetingList();

        assertEquals(10, mMeetingInitialList.size());

        String meetingRoom = StringsUtils.getRoomsArray()[0];

        List<Meeting> meetingfilteredList = database.getMeetingListFilteredByRoom(meetingRoom);

        for (int i = 0; i < meetingfilteredList.size(); i++) {
            Meeting meeting = meetingfilteredList.get(i);
            assertThat(meeting.getMeetingRoom(), is(meetingRoom));
        }
    }

}