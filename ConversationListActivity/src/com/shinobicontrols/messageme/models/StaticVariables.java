package com.shinobicontrols.messageme.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class StaticVariables {
public static HashMap<String,String> getPhoneNumber,getContactName;
public static List<String> contacts;
public static List<Conversation> convos;
public StaticVariables(){
	
}

public static void getContactList(Context ctx){
    	StaticVariables.contacts=new ArrayList<String>();
    	StaticVariables.getPhoneNumber= new HashMap<String,String>();
        ContentResolver cr = ctx.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        //Log.i(LOG_TAG, "get Contact List: Fetching complete contact list");

        ArrayList<String> contact_names = new ArrayList<String>();

        if (cur.getCount() > 0) 
        {
            while (cur.moveToNext()) 
            {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER.trim())).equalsIgnoreCase("1"))
                {
                    if (name!=null){
                        //contact_names[i]=name;

                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{id}, null);
                        while (pCur.moveToNext()) 
                        {
                            String PhoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            PhoneNumber = PhoneNumber.replaceAll("-", "");
                            if (PhoneNumber.trim().length() >= 10) {
                                PhoneNumber = PhoneNumber.substring(PhoneNumber.length() - 10);
                            }
                            contact_names.add(name + ":" + PhoneNumber);
StaticVariables.contacts.add(name);
StaticVariables.getPhoneNumber.put(name, PhoneNumber);
                            //i++;
                            break;
                        }
                        pCur.close();
                        pCur.deactivate();
                        // i++;
                    }
                }
            }
            cur.close();
            cur.deactivate();
        }

        String[] contactList = new String[contact_names.size()]; 

        for(int j = 0; j < contact_names.size(); j++){
            contactList[j] = contact_names.get(j);
        }
    }
}
