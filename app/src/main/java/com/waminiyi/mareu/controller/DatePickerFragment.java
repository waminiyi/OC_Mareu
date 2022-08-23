package com.waminiyi.mareu.controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment {

    private Context context;
    private final DatePickerDialog.OnDateSetListener mListener;

    private final boolean mIsForNewMeeting;

    public DatePickerFragment(DatePickerDialog.OnDateSetListener listener, boolean isForNewMeeting) {
        this.mListener = listener;
        mIsForNewMeeting = isForNewMeeting;
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
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(context, mListener, year, month, day);

        if (mIsForNewMeeting) {
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }
        return dialog;
    }

}
