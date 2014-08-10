package com.shinobicontrols.messageme;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

import com.shinobicontrols.messageme.models.DataProvider;
import com.shinobicontrols.messageme.models.StaticVariables;

public class SplashScreen extends Activity {
ProgressBar pb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
    getActionBar().hide();
    
    
		setContentView(R.layout.activity_splash_screen);
		pb=(ProgressBar)findViewById(R.id.loadingMessagesProgressBar);
		
		new PrefetchData().execute();
	}
	private class PrefetchData extends AsyncTask<Void, Void, Void> {
		 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls 
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
        	StaticVariables.convos=DataProvider.getInstance().getConversations(SplashScreen.this);
        	StaticVariables.getContactList(getApplicationContext());
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pb.setVisibility(ProgressBar.INVISIBLE);
            Intent i = new Intent(SplashScreen.this, ConversationListActivity.class);
            startActivity(i);
 
            // close this activity
            finish();
        }
 
    }	
}