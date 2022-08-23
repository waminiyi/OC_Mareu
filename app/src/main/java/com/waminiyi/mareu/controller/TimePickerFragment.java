package com.waminiyi.mareu.controller;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.text.format.DateFormat;

import java.util.Calendar;


public class TimePickerFragment extends DialogFragment {
    private Context context;
    private final TimePickerDialog.OnTimeSetListener mListener;


    public TimePickerFragment(TimePickerDialog.OnTimeSetListener listener) {
        mListener = listener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(context, mListener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

}