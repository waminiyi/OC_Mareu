package com.waminiyi.mareu.view;

import static com.waminiyi.mareu.controller.NewMeetingFragment.mMailsModelList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.MailModel;
import com.waminiyi.mareu.utils.ItemClickSupport;

import java.util.List;

public class AttendeesListAdapter extends RecyclerView.Adapter<AttendeesListViewHolder> {

    private List<String> mMailList;
    private Context mContext;
    private int mTarget;

    public AttendeesListAdapter(Context context, List<String> mailList, int target) {
        this.mMailList = mailList;
        this.mContext = context;
        this.mTarget = target;
    }

    @NonNull
    @Override
    public AttendeesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        return new AttendeesListViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendeesListViewHolder holder, int position) {
        if (mMailList != null && position < mMailList.size()) {
            String mail = mMailList.get(position);

            holder.mailTextView.setText(mail);

            if (mTarget == 2) {
                holder.actionButton.setVisibility(View.GONE);
            } else {
                holder.actionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MailModel newMailModel = new MailModel(mail, true);

                        mMailList.remove(mail);
                        setMailsList(mMailList);

                        int index = mMailsModelList.indexOf(newMailModel);
                        mMailsModelList.set(index, new MailModel(mail, false));
                    }
                });
            }
        }
    }

    public void setMailsList(List<String> mails) {
        mMailList = mails;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mMailList != null)
            return mMailList.size();
        else return 0;
    }

    public String getMailAt(int position) {
        return mMailList.get(position);
    }
}
