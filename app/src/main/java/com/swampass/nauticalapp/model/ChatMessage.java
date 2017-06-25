package com.swampass.nauticalapp.model;

import java.util.Date;

/**
 * Created by madhur on 17/01/15.
 */
public class ChatMessage {

    private String messageText;
    private String userID;
   // private Date date;


    public ChatMessage()
    {

    }

    //plain text message
    public ChatMessage(String messageText, String userID) {
        this.messageText = messageText;
        this.userID = userID;
        //this.date = date;
        //this.multimedia = false;
    }

    //Multimedia Message
    public ChatMessage(String userID, String messageText, String contentType, String contentLocation){
        this.userID = userID;
        this.messageText = messageText;
        /*this.multimedia = true;
        this.contentType = contentType;
        //this.date = date;
        this.contentLocation = contentLocation;*/
    }

  /*  public void setDate(Date mDate) {this.date = date;}
    public Date getDate() {return date;}*/

    public String getUserID() {return userID;}
    public void setUserID(String userID) {this.userID = userID;}

    public void setMessageText(String messageText) {this.messageText = messageText;}
    public String getMessageText() { return messageText;}


}
