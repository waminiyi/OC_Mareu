package com.waminiyi.mareu.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.MailModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class StringsUtils {



    public static String formatDate(int year, int month, int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, day);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy", new Locale("fr", "FR"));

        return dateFormat.format(date);
    }

    public static String formatTime(int hour, int minute) {

        @SuppressLint("DefaultLocale") String hour_string = String.format("%02d", hour);
        @SuppressLint("DefaultLocale") String minute_string = String.format("%02d", minute);
        return (hour_string + "h" + minute_string);
    }


    public static List<MailModel> getMailsModelList(Context context) {
        List<String> employeesMailList = Arrays.asList(context.getResources().getStringArray(R.array.random_mails));
        List<MailModel> mailsModelList = new ArrayList<>();

        for (int i = 0; i < employeesMailList.size(); i++) {
            MailModel mailModel = new MailModel(employeesMailList.get(i), false);
            mailsModelList.add(mailModel);
        }
        return mailsModelList;
    }

    public static List<String> getMeetingRoomsList(Context context) {
        return Arrays.asList(context.getResources().getStringArray(R.array.rooms));
    }

    public static String[] getRoomsArray(){
        return new String[]{"Salle red", "Salle maroon", "Salle yellow", "Salle lime", "Salle green", "Salle aqua", "Salle teal", "Salle blue", "Salle navy", "Salle fuchsia"};
    }

}
