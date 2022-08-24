package com.waminiyi.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.waminiyi.mareu.controller.MeetingsListingActivity;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import tools.fastlane.screengrab.Screengrab;

import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy;
import tools.fastlane.screengrab.locale.LocaleTestRule;

@RunWith(AndroidJUnit4.class)
public class ScreengrabTest {

    private MeetingsListingActivity mListingActivity;

    @ClassRule
    public static final LocaleTestRule localeTestRule = new LocaleTestRule();

    @Rule
    public ActivityTestRule<MeetingsListingActivity> mListingActivityRule =
            new ActivityTestRule(MeetingsListingActivity.class);

    @Before
    public void setUp() {

        mListingActivity = mListingActivityRule.getActivity();

        assertThat(mListingActivity, notNullValue());
    }


    @Test
    public void testTakeScreenshot() {

        Screengrab.screenshot("before_button_click");

        onView(withId(R.id.new_meeting_fab)).perform(click());

        Screengrab.screenshot("after_button_click");
    }
}