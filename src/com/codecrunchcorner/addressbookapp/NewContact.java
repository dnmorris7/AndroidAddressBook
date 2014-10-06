package com.codecrunchcorner.addressbookapp;

import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NewContact extends Activity{
	public static final String TAG = "New Contact";
	EditText firstName;
	EditText lastName;
	EditText phoneNumber;
	EditText emailAddress;
	EditText homeAddress;
	
	DBTools dbTools = new DBTools(this);
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.d(TAG, "Trying to show the view with id " + savedInstanceState);
		
		setContentView(R.layout.add_new_contact);
		
		firstName = (EditText) findViewById(R.id.firstName);
		lastName = (EditText) findViewById(R.id.lastName);
		phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		emailAddress = (EditText) findViewById(R.id.emailAddress);
		homeAddress = (EditText) findViewById(R.id.homeAddress);
		
	}
	
	//This receives from the add_new_contact .xml methods and gives DBTools what it needs passed to do the SQL commands
	public void addNewContact(View view){
		
		HashMap<String, String> queryValuesMap = new HashMap<String, String>();
		queryValuesMap.put("firstName", firstName.getText().toString());
		queryValuesMap.put("lastName", lastName.getText().toString());
		queryValuesMap.put("phoneNumber", phoneNumber.getText().toString());
		queryValuesMap.put("emailAddress", emailAddress.getText().toString());
		queryValuesMap.put("homeAddress", homeAddress.getText().toString());		
		
		dbTools.insertContact(queryValuesMap);
		
		this.callMainActivity(view);
		
	}

	private void callMainActivity(View view) {
		Intent theIntent = new Intent(getApplication(), MainActivity.class);
		startActivity(theIntent);
	}
	
}
