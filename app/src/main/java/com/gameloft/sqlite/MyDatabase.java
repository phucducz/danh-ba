package com.gameloft.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 19/10/2017.
 */

public class MyDatabase extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Contact_Manager";

    private static final String TABLE_CONTACT = "Contact";

    private static final String COLUMN_CONTACT_ID = "Contact_Id";
    private static final String COLUMN_CONTACT_NAME = "Contact_Name";
    private static final String COLUMN_CONTACT_PHONE = "Contact_Phone";
    private static final String COLUMN_CONTACT_ADDRESS = "Contact_Adress";
    private static final String COLUMN_CONTACT_DATE = "Contact_Date";
    private static final String COLUMN_CONTACT_TIME = "Contact_Time";
    private static final String COLUMN_CONTACT_GENDER = "Contact_Gender";


    public MyDatabase(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelpter.onCreate ... ");

        String script = "CREATE TABLE " + TABLE_CONTACT + " (" +
                COLUMN_CONTACT_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_CONTACT_NAME + " TEXT, " +
                COLUMN_CONTACT_PHONE + " TEXT, " +
                COLUMN_CONTACT_ADDRESS + " TEXT, " +
                COLUMN_CONTACT_DATE + " TEXT, " +
                COLUMN_CONTACT_TIME + " TEXT, " +
                COLUMN_CONTACT_GENDER + " TEXT" + ")";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");

        db.execSQL("DROP TABLE IF EXISTS" + TABLE_CONTACT);

        onCreate(db);
    }

    public int addContact(Contact contact){
        Log.i(TAG, "MyDatabaseHelper.addContact ... " + contact.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_NAME, contact.getName());
        values.put(COLUMN_CONTACT_PHONE, contact.getPhone());
        values.put(COLUMN_CONTACT_ADDRESS, contact.getAddress());
        values.put(COLUMN_CONTACT_DATE, contact.getDate());
        values.put(COLUMN_CONTACT_TIME, contact.getTime());
        values.put(COLUMN_CONTACT_GENDER, contact.getGender());

        db.insert(TABLE_CONTACT, null, values);

        db = this.getReadableDatabase();
        String id = "SELECT * FROM " + TABLE_CONTACT +" ORDER BY " + COLUMN_CONTACT_ID + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(id, null);
        if (cursor != null)
            cursor.moveToFirst();
        //Log.i(TAG,cursor.getString(0));
        db.close();
        return Integer.parseInt(cursor.getString(0));
    }

    public Contact getContact(int id){
        Log.i(TAG, "MyDatabaseHelper.getContact ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACT, new String[]{ COLUMN_CONTACT_ID, COLUMN_CONTACT_NAME, COLUMN_CONTACT_PHONE, COLUMN_CONTACT_ADDRESS, COLUMN_CONTACT_DATE, COLUMN_CONTACT_TIME, COLUMN_CONTACT_GENDER},
                COLUMN_CONTACT_ID + " =?", new String[]{String.valueOf(id)},null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3)
                ,cursor.getString(4),cursor.getString(5),cursor.getString(6));

        return contact;
    }

    public List<Contact> getAllContacts(){
        Log.i(TAG, "MyDatabaseHelper.getAllContacts ... " );

        List<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                contact.setAddress(cursor.getString(3));
                contact.setDate(cursor.getString(4));
                contact.setTime(cursor.getString(5));
                contact.setGender(cursor.getString(6));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public int getNotesCount(){
        Log.i(TAG, "MyDatabaseHelper.getContactsCount ... " );

        String countQuery = "SELECT * FROM " + TABLE_CONTACT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }

    public int updateContact(Contact contact){
        Log.i(TAG, "MyDatabaseHelper.updateContact ... "  + contact.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_NAME, contact.getName());
        values.put(COLUMN_CONTACT_PHONE, contact.getPhone());
        values.put(COLUMN_CONTACT_ADDRESS, contact.getAddress());
        values.put(COLUMN_CONTACT_DATE, contact.getDate());
        values.put(COLUMN_CONTACT_TIME, contact.getTime());
        values.put(COLUMN_CONTACT_GENDER, contact.getGender());

        return  db.update(TABLE_CONTACT, values, COLUMN_CONTACT_ID + " =?", new String[]{String.valueOf(contact.getId())});

    }
    public void deleteContact(Contact contact){
        Log.i(TAG, "MyDatabaseHelper.deleteContact ... " + contact.getName() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACT, COLUMN_CONTACT_ID + " =?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public  void deleteAllContact(){
        Log.i(TAG, "MyDatabaseHelper.deleteALlContacts ... " );

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CONTACT,null,null);
        db.close();
    }
}
