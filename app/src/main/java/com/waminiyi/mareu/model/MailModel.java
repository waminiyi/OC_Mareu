package com.waminiyi.mareu.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailModel mailModel = (MailModel) o;
        return Objects.equals(mMail, mailModel.getMail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(mMail);
    }
}
