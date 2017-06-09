package com.swampass.nauticalapp.model;

import java.util.Date;

/**
 * Created by madhur on 17/01/15.
 */
public class ChatMessage {

    private String messageText;
    private UserType userType;
    private Status messageStatus;
    private String userID;
    private Date date;

    public ChatMessage(String messageText, String userID, Date date) {
        this.messageText = messageText;
        this.userID = userID;
        this.date = date;

    }

    public void setDate(Date mDate) {this.date = date;}
    public Date getDate() {return date;}

    public String getUserID() {return userID;}
    public void setUserID(String userID) {this.userID = userID;}

    public void setMessageText(String messageText) {this.messageText = messageText;}
    public String getMessageText() { return messageText;}

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public UserType getUserType() {return userType;}

    public void setMessageStatus(Status messageStatus) { this.messageStatus = messageStatus;}
    public Status getMessageStatus() {return messageStatus;}
}
