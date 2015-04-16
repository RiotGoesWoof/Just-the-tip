package edu.carthage.zorpix.phonechooser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zorpix on 1/14/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;

    private DBHelper(Context context) {
        super(context.getApplicationContext(), "Tips.db", null, 1);
    }

    public synchronized static DBHelper getInstance(Context context) {
        if(instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Waiters (waiterID INTEGER PRIMARY KEY NOT NULL, name TEXT NOT NULL, tipID INTEGER NOT NULL, restaurant TEXT NOT NULL, notes TEXT)");
        db.execSQL("CREATE TABLE Tip (tipID INTEGER PRIMARY KEY NOT NULL, waiterID INTEGER NOT NULL, percent TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }

    public boolean checkWaiter(String name, String tipercent, String restaurant, String notes)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor results = db.rawQuery("SELECT Waiters.name FROM Waiters WHERE Waiters.name = \'" + name + "\' AND Waiters.restaurant = \'" + restaurant + "\'", null);

        if (results.getCount()==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void addWaiter(String name, String tipercent, String restaurant, String notes)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("restaurant", restaurant);
        cv.put("notes", notes);

        db.insert("Waiters", null, cv);
    }
    public void addTip(String name, String tipercent, String restaurant)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor results = db.rawQuery("SELECT Waiters.waiterID FROM Waiters", null);

        results.moveToLast();
        SQLiteDatabase wdb = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("percent", tipercent);
        cv.put("waiterID", results.getInt(0));

        wdb.insert("Tip", null, cv);
    }

    public Cursor getTips(String name, String restaurant)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor results = db.rawQuery("SELECT Tip.Percent as _id FROM Tip, Waiters WHERE Waiters.name = '" + name + "\' AND Waiters.restaurant = \'" + restaurant + "\' AND Waiters.waiterID = Tip.waiterID", null);

        return results;
    }
    public Cursor getNotes(String name, String restaurant)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor results = db.rawQuery("SELECT Waiters.notes FROM Waiters WHERE Waiters.name = '" + name + "\' AND Waiters.restaurant = \'" + restaurant + "\'", null);

        return results;
    }
};