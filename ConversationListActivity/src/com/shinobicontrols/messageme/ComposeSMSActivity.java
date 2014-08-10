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

package com.shinobicontrols.messageme;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.NavUtils;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ComposeSMSActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_sms);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        // Enable the 'back' button
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose_sm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if(id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ConversationListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_compose_sms, container, false);

            // Wire up some buttons
            rootView.findViewById(R.id.composeButtonCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavUtils.navigateUpTo(getActivity(), new Intent(getActivity(), ConversationListActivity.class));
                }
            });

            rootView.findViewById(R.id.composeButtonSend).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	TextView toField=(TextView)rootView.findViewById(R.id.composeEditTextTo);
                	TextView messageField=(TextView)rootView.findViewById(R.id.composeEditTextMessage);
                    String recipient = (toField).getText().toString();
                    String message   = (messageField).getText().toString();

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(recipient, "ME", message, null, null);
                    Toast.makeText(getActivity(), "Sending...", Toast.LENGTH_SHORT).show();
                    toField.setText("");
                    messageField.setText("");
                }
            });

            return rootView;
        }

        @Override
        public void onViewStateRestored(Bundle savedInstanceState) {
            super.onViewStateRestored(savedInstanceState);

            // Discover whether we're default
            final String packageName = getActivity().getPackageName();
            if(!Telephony.Sms.getDefaultSmsPackage(getActivity()).equals(packageName)) {
                // Not default
                View vg = getView().findViewById(R.id.composeNotDefault);
                vg.setVisibility(View.VISIBLE);

                // Add click listener to the set default button
                Button button = (Button)getView().findViewById(R.id.composeButtonSetDefault);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName);
                        startActivity(intent);
                    }
                });
            } else {
                // App is default
                View vg = getView().findViewById(R.id.composeNotDefault);
                vg.setVisibility(View.GONE);
            }
        }
    }

}
