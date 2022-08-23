package com.waminiyi.mareu.utils;

import android.util.Log;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.waminiyi.mareu.R;

import org.hamcrest.Matcher;

public class DeleteViewAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on specific button";
    }

    @Override
    public void perform(UiController uiController, View view) {
        View button = view.findViewById(R.id.item_list_delete_button);
        if (button!=null){
            button.performClick();
        }else{
            Log.d("delete","not deleted because null");
        }
    }
}