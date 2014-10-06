package com.codecrunchcorner.addressbookapp;

import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditContact extends Activity {

	EditText firstName;
	EditText lastName;
	EditText phoneNumber;
	EditText emailAddress;
	EditText homeAddress;

	DBTools dbTools = new DBTools(this);

	// we need to properly display the current information before we can start
	// editing it
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_contact);

		// getting the info from the EditText field
		firstName = (EditText) findViewById(R.id.firstName);
		lastName = (EditText) findViewById(R.id.lastName);
		phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		emailAddress = (EditText) findViewById(R.id.emailAddress);
		homeAddress = (EditText) findViewById(R.id.homeAddress);

		// return the last intent
		Intent theIntent = getIntent();

		//get the extended data provided by this activity. 
		//we used putExtra("contactId", contactIdValue) back in main_activity
		//and we need to pass the baton along to our Hashmap
		String contactId = theIntent.getStringExtra("contactId");

		HashMap<String, String> contactList = dbTools.getContactInfo(contactId);

		//first, make sure there is something in the context list
		if (contactList.size() != 0) {
			
			//next, put the values in the EditText boxes so that these things
			//are auto-filled
			firstName.setText(contactList.get("firstName"));
			lastName.setText(contactList.get("lastName"));
			phoneNumber.setText(contactList.get("phoneNumber"));
			emailAddress.setText(contactList.get("emailAddress"));
			homeAddress.setText(contactList.get("homeAddress"));

		}
	}

	// When the Edit Button is pushed, this happens
	public void editContact(View view) {

		HashMap<String, String> queryValuesMap = new HashMap<String, String>();

		// getting the info from the EditText field
		firstName = (EditText) findViewById(R.id.firstName);
		lastName = (EditText) findViewById(R.id.lastName);
		phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		emailAddress = (EditText) findViewById(R.id.emailAddress);
		homeAddress = (EditText) findViewById(R.id.homeAddress);

		// return the last intent
		Intent theIntent = getIntent();
		
		//get the extended data provided by this activity. 
		//we used putExtra("contactId", contactIdValue) back in main_activity
		//and we need to pass the baton along to our Hashmap
		String contactId = theIntent.getStringExtra("contactId");

		//put the values from the EditText into the Hashmap 
		//for preparation to ship to our SQL tool class
		queryValuesMap.put("contactId", contactId);
		queryValuesMap.put("firstName", firstName.getText().toString());
		queryValuesMap.put("lastName", lastName.getText().toString());
		queryValuesMap.put("phoneNumber", phoneNumber.getText().toString());
		queryValuesMap.put("emailAddress", emailAddress.getText().toString());
		queryValuesMap.put("homeAddress", homeAddress.getText().toString());

		// now have DBTools use its update the contact for this Hashmap
		dbTools.updateContact(queryValuesMap);

		//go back to the main screen after this button press is done.
		this.callMainActivity(view);
	}

	// When the delete button is pushed, this happens.
	public void deleteContact(View view) {

		// return the last intent
				Intent theIntent = getIntent();
				String contactId = theIntent.getStringExtra("contactId");
				
				dbTools.deleteContact(contactId);

				//go back to the main screen after we're done
				this.callMainActivity(view);
	}

	private void callMainActivity(View view) {
		Intent objIntent = new Intent(getApplication(), MainActivity.class);

		startActivity(objIntent);
	}
}
