package com.codecrunchcorner.addressbookapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTools extends SQLiteOpenHelper {

	public DBTools(Context applicationContext) {
		super(applicationContext, "contactbook.db", null, 1);

	}

	@Override
	// dbtools creates this table when its called with the following parameters
	public void onCreate(SQLiteDatabase database) {

		String query = "CREATE TABLE contacts( contactId INTEGER PRIMARY KEY, firstName TEXT, "
				+ "lastName TEXT, phoneNumber TEXT, emailAddress TEXT, homeAddress TEXT)";

		database.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {

		String query = "DROP TABLE IF EXISTS contacts";

		database.execSQL(query);
		onCreate(database);

	}

	public void insertContact(HashMap<String, String> queryValues) {

		SQLiteDatabase database = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put("firstName", queryValues.get("firstName"));
		values.put("lastName", queryValues.get("lastName"));
		values.put("phoneNumber", queryValues.get("phoneNumber"));
		values.put("emailAddress", queryValues.get("emailAddress"));
		values.put("homeAddress", queryValues.get("homeAddress"));

		//Now we insert the data in the form of ContentValues \
		//into the table named 'contacts'
		database.insert("contacts", null, values);

		database.close();

	}

	
	public int updateContact(HashMap<String, String> queryValues) {

		//open the database for reading and writing
		SQLiteDatabase database = this.getWritableDatabase();

		//Time to store the hash map key value pairs
		ContentValues values = new ContentValues();

		values.put("firstName", queryValues.get("firstName"));
		values.put("lastName", queryValues.get("lastName"));
		values.put("phoneNumber", queryValues.get("phoneNumber"));
		values.put("emailAddress", queryValues.get("emailAddress"));
		values.put("homeAddress", queryValues.get("homeAddress"));

		//update as so...
		return database.update("contacts", values, "contactID" + " = ?",
				new String[] { queryValues.get("contactId") });
	}

	public void deleteContact(String id) {
		SQLiteDatabase database = this.getWritableDatabase();
		String deleteQuery = "DELETE FROM contacts WHERE contactId='" + id
				+ "'";

		database.execSQL(deleteQuery);
	}

	public ArrayList<HashMap<String, String>> getAllContacts() {

		//this array list will contain data from every row in the current
		//database, and each row key/value stored in hashmap
		ArrayList<HashMap<String, String>> contactArrayList = new ArrayList<HashMap<String, String>>();
		
		//This string holds our SQL statement. We'll need a cursor to hold on to things as it performs this query
		String selectQuery = "SELECT * FROM contacts ORDER BY lastName";

		SQLiteDatabase database = this.getWritableDatabase();

		Cursor cursor = database.rawQuery(selectQuery, null);

		// cycle through data
		if (cursor.moveToFirst()) {

			do {
				HashMap<String, String> contactMap = new HashMap<String, String>();

				contactMap.put("contactId", cursor.getString(0));
				contactMap.put("firstName", cursor.getString(1));
				contactMap.put("lastName", cursor.getString(2));
				contactMap.put("phoneNumber", cursor.getString(3));
				contactMap.put("emailAddress", cursor.getString(4));
				contactMap.put("homeAddress", cursor.getString(5));

				contactArrayList.add(contactMap);

			} while (cursor.moveToNext());//move cursor to next row

		}
		//finally, return the contact list
		return contactArrayList;

	}

	public HashMap<String, String> getContactInfo(String id) {
		HashMap<String, String> contactMap = new HashMap<String, String>();

		//unlike with the other methods, open the database for reading only
		SQLiteDatabase database = this.getReadableDatabase();
		
		//here's our SQLStatement
		String selectQuery = "SELECT * FROM contacts WHERE contactId='" + id
				+ "'";

		//now we have our cursor perform the rawQuery. 
		//Once done, our contact HashMap will get what it needs
		//from the Cursor
		
		Cursor cursor = database.rawQuery(selectQuery, null);

		// cycle through data
		if (cursor.moveToFirst()) {

			do {
				contactMap.put("contactId", cursor.getString(0));
				contactMap.put("firstName", cursor.getString(1));
				contactMap.put("lastName", cursor.getString(2));
				contactMap.put("phoneNumber", cursor.getString(3));
				contactMap.put("emailAddress", cursor.getString(4));
				contactMap.put("homeAddress", cursor.getString(5));

			} while (cursor.moveToNext());

		}
		return contactMap;
	}
}
