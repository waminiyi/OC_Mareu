package com.waminiyi.mareu.utils;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.waminiyi.mareu.R;
import com.waminiyi.mareu.controller.MeetingListFragment;
import com.waminiyi.mareu.controller.NewMeetingFragment;
import com.waminiyi.mareu.model.MailModel;
import com.waminiyi.mareu.view.MailAddingAdapter;

public class UIUtils {

    private NewMeetingFragment mNewMeetingFragment;
    private MeetingListFragment mMeetingListFragment;
    private Context mContext;
    private Dialog mRoomDialog;

    private EditText mResearchEditText;
    private ListView mListView;

    private ArrayAdapter<String> mArrayAdapter;

    public UIUtils() {
    }

    public UIUtils(NewMeetingFragment newMeetingFragment) {
        mNewMeetingFragment = newMeetingFragment;
        mContext = newMeetingFragment.getContext();
        mRoomDialog = new Dialog(mContext);
    }

    public UIUtils(MeetingListFragment meetingListFragment) {
        mMeetingListFragment = meetingListFragment;
        mContext = meetingListFragment.getContext();
        mRoomDialog = new Dialog(mContext);
    }

    public void configureAndShowMailDialog() {

        mRoomDialog.setContentView(R.layout.mail_dialog_searchable_spinner);
        mRoomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mRoomDialog.show();

        EditText researchEditText = mRoomDialog.findViewById(R.id.dialog_mail_edit_text);

        RecyclerView mailAddingRecyclerView = mRoomDialog.findViewById(R.id.dialog_mail_recycler_view);
        mailAddingRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        MailAddingAdapter mailAddingAdapter = new MailAddingAdapter(mNewMeetingFragment);

        mailAddingRecyclerView.setAdapter(mailAddingAdapter);
        configureClickOnMailItem(mailAddingRecyclerView, mailAddingAdapter);
        configureFilterOnMails(researchEditText, mailAddingAdapter);

        mRoomDialog.findViewById(R.id.mail_validation_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             mRoomDialog.dismiss();
            }
        });
    }

    public void configureClickOnMailItem(RecyclerView mailAddingRecyclerView, MailAddingAdapter mailAddingAdapter) {
        ItemClickSupport.addTo(mailAddingRecyclerView, R.layout.listview_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        MailModel newMailModel = mailAddingAdapter.getMailAt(position);
                        ImageButton actionButton = v.findViewById(R.id.new_meeting_mail_action_button);

                        if (!(mNewMeetingFragment.getAttendeesMailList().contains(newMailModel.getMail()))) {
                            mNewMeetingFragment.addAttendee(newMailModel);
                            actionButton.setImageResource(R.drawable.ic_checked);
                        } else {
                            mNewMeetingFragment.removeAttendee(newMailModel);
                            actionButton.setImageResource(R.drawable.ic_unchecked);
                        }
                        mNewMeetingFragment.mMailAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void configureFilterOnMails(EditText researchEditText, MailAddingAdapter mailAddingAdapter) {
        researchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mailAddingAdapter.filterMails(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void showRoomDialogAndReturnRoom() {

        showRoomDialog();
        configureFilterOnRooms();
        configureClickOnRoomAndSetRoom();
    }

    public void showRoomDialogAndReturnRoomFilter() {

        showRoomDialog();
        configureFilterOnRooms();
        configureClickOnRoomAndSetRoomFilter();
    }

    public void showRoomDialog() {

        mRoomDialog.setContentView(R.layout.dialog_searchable_spinner);
        mRoomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mRoomDialog.show();

        mResearchEditText = mRoomDialog.findViewById(R.id.edit_text);
        mListView = mRoomDialog.findViewById(R.id.list_view);

        mArrayAdapter = new ArrayAdapter<>(mContext, R.layout.dialog_listview_item, StringsUtils.getMeetingRoomsList(mContext));

        mListView.setAdapter(mArrayAdapter);
    }

    public void configureClickOnRoomAndSetRoom() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mNewMeetingFragment.setMeetingRoom(mArrayAdapter.getItem(position));
                mNewMeetingFragment.updateMeetingRoomTextView();
                mRoomDialog.dismiss();
            }

        });
    }

    public void configureClickOnRoomAndSetRoomFilter() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMeetingListFragment.setRoomFilter(mArrayAdapter.getItem(position));
                mRoomDialog.dismiss();
                mMeetingListFragment.filterByRoom();
            }

        });
    }

    public void configureFilterOnRooms() {
        mResearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mArrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
}