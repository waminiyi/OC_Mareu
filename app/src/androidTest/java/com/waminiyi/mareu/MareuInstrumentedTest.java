package com.waminiyi.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

import com.waminiyi.mareu.controller.MeetingsListingActivity;
import com.waminiyi.mareu.controller.NewMeetingActivity;
import com.waminiyi.mareu.model.Meeting;
import com.waminiyi.mareu.model.MeetingDatabase;
import com.waminiyi.mareu.utils.DeleteViewAction;
import com.waminiyi.mareu.utils.RecyclerViewItemCountAssertion;
import com.waminiyi.mareu.utils.RecyclerViewItemFilteringAssertion;
import com.waminiyi.mareu.utils.StringsUtils;

import tools.fastlane.screengrab.FalconScreenshotStrategy;
import tools.fastlane.screengrab.Screengrab;
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy;
import tools.fastlane.screengrab.cleanstatusbar.BluetoothState;
import tools.fastlane.screengrab.cleanstatusbar.CleanStatusBar;
import tools.fastlane.screengrab.cleanstatusbar.MobileDataType;
import tools.fastlane.screengrab.locale.LocaleTestRule;


@RunWith(AndroidJUnit4.class)
public class MareuInstrumentedTest {

    private final int ITEMS_COUNT = 10;

    private MeetingsListingActivity mListingActivity;

    @Rule
    public ActivityTestRule<MeetingsListingActivity> mListingActivityRule =
            new ActivityTestRule(MeetingsListingActivity.class);


    @Before
    public void setUp() {
        mListingActivity = mListingActivityRule.getActivity();

        assertThat(mListingActivity, notNullValue());
    }


    /**
     * We ensure that our recyclerview is displaying all meetings
     */
    @Test
    public void meetingList_shouldNotBeEmpty() {

        onView(withId(R.id.recyclerview)).check(new RecyclerViewItemCountAssertion(ITEMS_COUNT));
    }

    /**
     * Check if the click on a meeting shows meeting details
     */

    @Test
    public void checkIfClickOnMeetingShowsMeetingDetails() {
        Meeting meeting = MeetingDatabase.getInstance().getMeetingList().get(0);
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.meeting_topic_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.meeting_topic_textview)).check(matches(withText(meeting.getMeetingTopic())));

    }

    /**
     * Test if filter is working
     */

    @Test
    public void filterByDateWithSuccess() {
        openContextualActionModeOverflowMenu();

        onView(withText(R.string.filter_by_date)).perform(click());

        onView(withResourceName("datePicker")).check(matches(isDisplayed()));
        onView(withResourceName("datePicker")).perform(PickerActions.setDate(2022, 05, 24));
        onView(withText(android.R.string.ok)).perform(click());

        onView(withId(R.id.recyclerview)).check(new RecyclerViewItemFilteringAssertion("DATE", StringsUtils.formatDate(2022, 04, 24)));


    }

    @Test
    public void filterByRoomWithSuccess() {
        openContextualActionModeOverflowMenu();

        onView(withText(R.string.filter_by_room)).perform(click());

        onView(withId(R.id.list_view)).check(matches(isDisplayed()));
        onView(withText(StringsUtils.getRoomsArray()[0])).perform(click());

        onView(withId(R.id.recyclerview)).check(new RecyclerViewItemFilteringAssertion("ROOM", StringsUtils.getRoomsArray()[0]));


    }

    /**
     * We test that when we delete an item, the item is no more shown
     */
    @Test
    public void removeMeetingWithSuccess() {

        onView(withId(R.id.recyclerview)).check(new RecyclerViewItemCountAssertion(ITEMS_COUNT));

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

        onView(withId(R.id.recyclerview)).check(new RecyclerViewItemCountAssertion(ITEMS_COUNT - 1));
    }

    @Test
    public void addMeetingWithSuccess() {

        String topic = "REUNION TEST";
        onView(withId(R.id.new_meeting_fab)).perform(click());

        //adding topic
        onView(withId(R.id.new_meeting_topic_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.new_meeting_topic_edittext)).perform(typeText(topic));


        //choosing date (here 2022-12-31)
        onView(withId(R.id.new_meeting_date_textview)).perform(click());
        onView(withResourceName("datePicker")).check(matches(isDisplayed()));
        onView(withResourceName("datePicker")).perform(PickerActions.setDate(2022, 12, 30));
        onView(withText(android.R.string.ok)).perform(click());

        //choosing time (here 10:30)
        onView(withId(R.id.new_meeting_time_textview)).perform(click());
        onView(withResourceName("timePicker")).check(matches(isDisplayed()));
        onView(withResourceName("timePicker")).perform(PickerActions.setTime(10, 30));
        onView(withText(android.R.string.ok)).perform(click());

        //choosing room
        onView(withId(R.id.new_meeting_room_textview)).perform(click());
        onView(withId(R.id.list_view)).check(matches(isDisplayed()));
        onView(withText(StringsUtils.getRoomsArray()[0])).perform(click());

//        //adding attendees
        onView(withId(R.id.new_meeting_attendees_adding_textview)).perform(click());
        onView(withId(R.id.dialog_mail_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.dialog_mail_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.dialog_mail_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.dialog_mail_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.dialog_mail_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.mail_validation_fab)).perform(click());


        //saving the new meeting
        onView(withId(R.id.new_meeting_save_button)).perform(click());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //check we have one more meeting and that this is REUNION TEST
        onView(withId(R.id.recyclerview)).check(new RecyclerViewItemCountAssertion(ITEMS_COUNT + 1));
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(topic + " - "))));
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(10, new DeleteViewAction()));

    }
}