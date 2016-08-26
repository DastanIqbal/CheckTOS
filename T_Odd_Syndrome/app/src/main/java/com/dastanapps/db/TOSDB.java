package com.dastanapps.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dastanapps.dastanLib.log.Logger;
import com.dastanapps.db.bean.UserCheckupInfoB;

import java.util.ArrayList;

import static com.dastanapps.db.DBConstant.COL_UCI_CHECKUP_INFO;
import static com.dastanapps.db.DBConstant.COL_UCI_ID;
import static com.dastanapps.db.DBConstant.COL_UCI_PROBABILITY;
import static com.dastanapps.db.DBConstant.COL_UCI_TIMESTAMP;
import static com.dastanapps.db.DBConstant.TBL_USER_CHECKUP_INFO;

/**
 * Created by IQBAL-MEBELKART on 10/28/2015.
 */
public class TOSDB extends SQLiteOpenHelper {

    private static final String TAG = TOSDB.class.getSimpleName();

    private String CREATE_TBL_USER_CHECKUP_INFO = "CREATE TABLE " +
            TBL_USER_CHECKUP_INFO + " ( " +
            COL_UCI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_UCI_CHECKUP_INFO + " TEXT NOT NULL," +
            COL_UCI_PROBABILITY + " REAL NOT NULL," +
            COL_UCI_TIMESTAMP + " INTEGER NOT NULL)";

    public void clearTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TBL_USER_CHECKUP_INFO);
        db.close();
    }

    public TOSDB(Context context) {
        super(context, "tos_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TBL_USER_CHECKUP_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean checkRowExist(SQLiteDatabase db, String table, String row, String id) {
        Cursor cursor = db.query(table, null, row + "=?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst() && cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Insert UserInfo
     *
     * @param userInfoB
     * @return id
     */
    public long insertUserCheckupInfo(UserCheckupInfoB userInfoB) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_UCI_CHECKUP_INFO, userInfoB.checkup_info);
        cv.put(COL_UCI_TIMESTAMP, userInfoB.timestamp);
        cv.put(COL_UCI_PROBABILITY, userInfoB.percent);


        long rowInsert = db.insert(TBL_USER_CHECKUP_INFO, null, cv);
        Logger.i(TAG, "Row Inserted " + rowInsert);
        db.close();
        return rowInsert;
    }

    /**
     * update UserInfo
     *
     * @param userInfoB
     * @return id
     */
    public void updateUserCheckupInfo(UserCheckupInfoB userInfoB) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_UCI_CHECKUP_INFO, userInfoB.checkup_info);
        cv.put(COL_UCI_TIMESTAMP, System.currentTimeMillis());
        cv.put(COL_UCI_PROBABILITY, userInfoB.percent);

        long rowUpdate = db.update(TBL_USER_CHECKUP_INFO, cv, COL_UCI_ID + "=?", new String[]{String.valueOf(userInfoB.id)});
        Logger.i(TAG, "Row Update " + rowUpdate);
        db.close();
    }

    /**
     * Delete checkup Info by Id
     *
     * @param id
     */
    public void deleteUserCheckupInfo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_UCI_ID, id);

        long rowDelete = db.delete(TBL_USER_CHECKUP_INFO, COL_UCI_ID + "=?", new String[]{String.valueOf(id)});
        Logger.i(TAG, "Row Delete " + rowDelete);
        db.close();
    }

    /**
     * Get Particular User checkInfo
     *
     * @param id
     * @return
     */
    public UserCheckupInfoB getUserCheckupInfo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TBL_USER_CHECKUP_INFO, null, COL_UCI_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idd = cursor.getInt(cursor.getColumnIndex(COL_UCI_ID));
                String checkupInfo = cursor.getString(cursor.getColumnIndex(COL_UCI_CHECKUP_INFO));
                long timestamp = cursor.getLong(cursor.getColumnIndex(COL_UCI_TIMESTAMP));
                float percent = cursor.getFloat(cursor.getColumnIndex(COL_UCI_PROBABILITY));

                UserCheckupInfoB userInfoB = new UserCheckupInfoB();
                userInfoB.id = idd;
                userInfoB.checkup_info = checkupInfo;
                userInfoB.timestamp = timestamp;
                userInfoB.percent = percent;

                return userInfoB;
            }
        }
        db.close();
        return null;
    }


    public int getTotalCountUserCheckup() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + TBL_USER_CHECKUP_INFO, null);
        return c.getCount();
    }

    /**
     * Get All User checkup Info
     *
     * @return
     */
    public ArrayList<UserCheckupInfoB> getAllUserCheckUpInfo(int lastId) {
        ArrayList<UserCheckupInfoB> usercheckupInfoList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TBL_USER_CHECKUP_INFO, null, COL_UCI_ID + " > ?", new String[]{String.valueOf(lastId)}, null, null, COL_UCI_TIMESTAMP + " desc", " 10");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int idd = cursor.getInt(cursor.getColumnIndex(COL_UCI_ID));
                    usercheckupInfoList.add(getUserCheckupInfo(idd));

                } while (cursor.moveToNext());
            }
        }
        db.close();
        return usercheckupInfoList;
    }
}
