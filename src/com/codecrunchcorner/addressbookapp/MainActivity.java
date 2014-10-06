package com.codecrunchcorner.addressbookapp;

import java.util.ArrayList;
import java.util.HashMap;


import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/*VERSION 1.0 Completed: 4/9/14
 * Final Challenges: 
 * Getting the Address Field to be large enough to show an entire address adequately.
 * Getting a comma in front of the last name on the opening screen.
 */
public class MainActivity extends ListActivity {

	public static final String TAG = "MainActivity";
	//Intents highlight operations that should be performed.
	Intent intent;
	TextView contactId;
	
	//This helper object will handle mucking around our SQL database
	DBTools dbTools = new DBTools(this);

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//get all database info and prepare to display it
		ArrayList<HashMap<String, String>> contactList = dbTools.getAllContacts();
		dbTools.getAllContacts(); //REMEMBER: This method is also responsible for ordering our contacts by last name or whatever
		
		//if there is saved data to get
		if(contactList.size() != 0){
			
			ListView listView = getListView();
			listView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
				//when an Item is clicked get its text view here
					contactId = (TextView) view.findViewById(R.id.contactId);	
				
					//turn that id into a string now
				String contactIdValue = contactId.getText().toString();
				
				//signal that we are about to change screens to the Edit Contact screen
				Intent theIntent = new Intent(getApplication(), EditContact.class);
				
				//This screen will need extra data. Put it in here.
				theIntent.putExtra("contactId", contactIdValue);
				
				//now, actually switch to the next screen
				startActivity(theIntent);
				
				
				}	
			});
			
			/*This adapts the contact names to show up on the main contacts page
			 * BUG SUSPECTED: due to inadequate information under "contact_entry.xml" this thing is showing contacts 
			 * as invisible.
			 * 
			 * If I want to show more or less info on the default screen, I need to edit this and the contact_entry.xml file
			 * */
			ListAdapter adapter = new SimpleAdapter(
					MainActivity.this, contactList, R.layout.contact_entry,
					new String[] {"contactId", "lastName", "firstName", "phoneNumber"},
					new int[] {R.id.contactId, R.id.lastName, R.id.firstName, R.id.phoneNumber});
			
			setListAdapter(adapter);
		}
	}

	/*Previously we used an onClick Listener to traverse through the views. Here, we set what to do with OnClick in the manifest. This method, being different,
	 * resulted in a crash as I tried to work it. The bug is now resolved. What was going on in the Manifest was the issue.
	 *BUG RESOLVED: Needed to "declare" new activities in the manifest using this alternative methods to forming an onClick listener
	 */
	public void showAddContact(View view){
		Log.d(TAG, "Trying to show the view with id " + view);
		
		Intent theIntent = new Intent(getApplication(), NewContact.class);
		//Intent theIntent = new Intent(getApplicationContext(), NewContact.class);
		
		startActivity(theIntent);
	}
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

}
