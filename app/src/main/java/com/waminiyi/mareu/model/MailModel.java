package com.waminiyi.mareu.model;

public class MailModel {

    private String mMail;
    private boolean mSelected;

    public MailModel(String mail, boolean selected) {
        mMail = mail;
        mSelected = selected;
    }

    public String getMail() {
        return mMail;
    }

    public void setMail(String mail) {
        mMail = mail;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }
}
