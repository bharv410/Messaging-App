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
import java.util.List;
import java.util.Map;

/**
 * Created by sdavies on 09/01/2014.
 */
public class Conversation {

    private String sender;
    private List<Message> messages;

    public Conversation(String sender) {
        this.sender = sender;
        this.messages = new ArrayList<Message>();
    }

    public Conversation(String sender, List<Message> messages) {
        this.sender = sender;
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }
    public String getSender() {
        return sender;
    }
    public void addMessage(Message message) {
        this.messages.add(message);
    }
    @Override
    public String toString() {
//    	if(StaticVariables.getContactName!=null){
//    		System.out.println("not null");
//    		System.out.println("not null");
//    		System.out.println("not null");
//    		System.out.println("not null");
//    		
//    		if(StaticVariables.getContactName.containsKey(sender.replaceAll("[^0-9]+", ""))){
//    			System.out.println("contains it");
//    			return StaticVariables.getContactName.get(sender);
//    		}else{
//    			System.out.println(sender);
//    			System.out.println(sender.replaceAll("[^0-9]+", ""));
//    		}
//    		
//    		for (Map.Entry<String, String> entry : StaticVariables.getContactName.entrySet()) {
//    		    String key = entry.getKey();
//    		    String value = entry.getValue();
//    		    System.out.println("----Contact name"+key+"#:"+value);
//    		    // ...
//    		}
//    		
//    		for (Map.Entry<String, String> entry : StaticVariables.getPhoneNumber.entrySet()) {
//    		    String key = entry.getKey();
//    		    String value = entry.getValue();
//    		    System.out.println("# "+key+"Contact Name:"+value);
//    		    // ...
//    		}
//    		
//    	}else{
//    		System.out.println("IS null");
//    		System.out.println("IS null");
//    		System.out.println("IS null");
//    		System.out.println("IS null");
//    	}
    		return sender;	
    }
}
