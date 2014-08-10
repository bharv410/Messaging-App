/*
Copyright 2014 Scott Logic Ltd

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/

package com.shinobicontrols.messageme.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by sdavies on 09/01/2014.
 */
public class DataProvider extends Observable {
    private static DataProvider ourInstance = new DataProvider();

    public static DataProvider getInstance() {
        return ourInstance;
    }

    private Map<String, Conversation> conversationMap;
    private List<Conversation> conversationList;

    private DataProvider() {
        // Create the store
        conversationMap = new HashMap<String, Conversation>();
        conversationList = new ArrayList<Conversation>();

        // Create some sample data
        addMessage(new Message("content1", "sender1", "recipient", new Date()));
        addMessage(new Message("content2", "sender1", "recipient", new Date()));
        addMessage(new Message("content3", "sender2", "recipient", new Date()));
        addMessage(new Message("content4", "sender3", "recipient", new Date()));
        addMessage(new Message("content5", "sender4", "recipient", new Date()));
        addMessage(new Message("content6", "sender4", "recipient", new Date()));
        addMessage(new Message("content7", "sender4", "recipient", new Date()));
        addMessage(new Message("content8", "sender4", "recipient", new Date()));
        addMessage(new Message("content9", "sender4", "recipient", new Date()));
        
       
    }

    public void addMessage(Message message) {
        if(conversationMap.containsKey(message.getSender())) {
            // Can add the message to an existing conversation
            conversationMap.get(message.getSender()).addMessage(message);
        } else {
            // Need to create a new conversation
            Conversation conversation = new Conversation(message.getSender());
            conversation.addMessage(message);
            conversationMap.put(message.getSender(), conversation);
            conversationList.add(conversation);
        }
        // Ensure that everything gets updated
        setChanged();
        notifyObservers();
    }

//    public List<Conversation> getConversations(Context ctx) {
//    	 Cursor cursor = ctx.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
//         cursor.moveToFirst();
//
//         do{
//             String msgData = "";
//             for(int idx=0;idx<cursor.getColumnCount();idx++)
//             {
//                 msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
//                 
//                 //addMessage(new Message(cursor.getString(idx), cursor.getColumnName(idx), "recipient", new Date()));
//             }
//             
//          }while(cursor.moveToNext());
//         
//         
//        return conversationList;
//    }

    public Map<String, Conversation> getConversationMap() {
        return conversationMap;
    }
    
    public List<Conversation> getConversations(Activity mActivity) {
//        List<Sms> lstSms = new ArrayList<Sms>();
//        Sms objSms = new Sms();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = mActivity.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        mActivity.startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
            	addMessage(new Message(c.getString(c.getColumnIndexOrThrow("body")), c.getString(c
                        .getColumnIndexOrThrow("address")), "recipient", new Date()));
//                objSms = new Sms();
//                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
//                objSms.setAddress(c.getString(c
//                        .getColumnIndexOrThrow("address")));
//                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
//                objSms.setReadState(c.getString(c.getColumnIndex("read")));
//                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
//                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
//                    objSms.setFolderName("inbox");
//                } else {
//                    objSms.setFolderName("sent");
//                }
//
//                lstSms.add(objSms);
                c.moveToNext();
            }
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        c.close();

        return conversationList;
    }
}
