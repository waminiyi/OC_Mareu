package com.waminiyi.mareu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waminiyi.mareu.R;
import com.waminiyi.mareu.model.MailModel;
import com.waminiyi.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MailAddingAdapter extends RecyclerView.Adapter<AttendeesListViewHolder> {

    private Context mContext;
    private List<MailModel> mMailsModelList;
    private List<MailModel> mMailsListCopy;

    public MailAddingAdapter(Context context, List<MailModel> mailsModelList) {
        this.mContext = context;
        this.mMailsModelList = mailsModelList;
        mMailsListCopy = new ArrayList<>(mailsModelList);
    }

    public MailModel getMailAt(int position) {
        return mMailsModelList.get(position);
    }

    @NonNull
    @Override
    public AttendeesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        return new AttendeesListViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendeesListViewHolder holder, int position) {
        if (mMailsModelList != null && position < mMailsModelList.size()) {
            MailModel mailModel = getMailAt(position);
            boolean selected = mailModel.isSelected();

            holder.mailTextView.setText(mailModel.getMail());
            holder.avatarImage.setImageResource(R.drawable.ic_mood);

            if (selected) {
                holder.actionButton.setImageResource(R.drawable.ic_checked);
            } else {
                holder.actionButton.setImageResource(R.drawable.ic_unchecked);
            }
        }
    }

    public void setMailsModelList(List<MailModel> mailsModelList) {
        mMailsModelList = mailsModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mMailsModelList != null)
            return mMailsModelList.size();
        else return 0;
    }

    public void filterMails(CharSequence constraint) {
        List<MailModel> filteredList = new ArrayList<>();
        if (constraint == null || constraint.length() == 0) {
            filteredList.addAll(mMailsListCopy);
        } else {
            String filterPattern = constraint.toString().toLowerCase().trim();
            for (MailModel mailModel : mMailsListCopy) {
                if (mailModel.getMail().toLowerCase().contains(filterPattern)) {
                    filteredList.add(mailModel);
                }
            }
        }
        setMailsModelList(filteredList);
    }

}
