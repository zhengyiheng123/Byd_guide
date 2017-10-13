package com.example.asus.xyd_order.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;

import com.example.asus.xyd_order.net.result.CityListBean;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter {

    static final String KEY_ROWID = "id";
    static final String KEY_NAME = "city_name";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "contacts";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
            "create table "+DATABASE_TABLE+"( "+KEY_ROWID+" integer primary key , " + KEY_NAME+" text not null);";
    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context cxt)
    {
        this.context = cxt;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            try
            {
                db.execSQL(DATABASE_CREATE);
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            Log.wtf(TAG, "Upgrading database from version "+ oldVersion + "to "+
                    newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //open the database
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //close the database
    public void close()
    {
        DBHelper.close();
    }

    //delete a particular contact
    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" +rowId, null) > 0;
    }
    public boolean insert(int id,String city_name){
        ContentValues args = new ContentValues();
        args.put(KEY_ROWID, id);
        args.put(KEY_NAME, city_name);
        return db.insert(DATABASE_TABLE,null,args)>0;
    }
    public void deleteAll(){
        db.execSQL("delete from "+DATABASE_TABLE);
    }
    //retreves all the contacts
    public List<CityListBean.RegionsBean> getAllContacts()
    {
        List<CityListBean.RegionsBean> cityListBean=new ArrayList<>();
        Cursor cursor=db.query(DATABASE_TABLE, new String[]{ KEY_ROWID,KEY_NAME}, null, null, null, null, null);
        while (cursor.moveToNext()){
            cityListBean.add(new CityListBean.RegionsBean(cursor.getInt(cursor.getColumnIndex(KEY_ROWID)),cursor.getString(cursor.getColumnIndex(KEY_NAME)),0));
        }
        return cityListBean;
    }
    //retreves a particular contact
//    public Cursor getContact(long rowId) throws SQLException
//    {
//        Cursor mCursor =
//                db.query(true, DATABASE_TABLE, new String[]{ KEY_ROWID,
//                        KEY_NAME, KEY_EMAIL}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
//        if (mCursor != null)
//            mCursor.moveToFirst();
//        return mCursor;
//    }
    //updates a contact
//    public boolean updateContact(long rowId, String name, String email)
//    {
//        ContentValues args = new ContentValues();
//        args.put(KEY_NAME, name);
//        args.put(KEY_EMAIL, email);
//        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" +rowId, null) > 0;
//    }
}