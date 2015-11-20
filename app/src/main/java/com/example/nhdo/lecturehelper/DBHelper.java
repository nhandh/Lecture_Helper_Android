package com.example.nhdo.lecturehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by nhdo on 11/12/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CourseDatabase.db";
    public static final String COURSES_TABLE_NAME = "Courses";
    public static final String COURSES_COLUMN_ID = "id";
    public static final String COURSES_COLUMN_NAME = "name";
    public static final String COURSES_COLUMN_START_HOUR = "start_hour";
    public static final String COURSES_COLUMN_START_MINUTE = "start_minute";
    public static final String COURSES_COLUMN_END_HOUR = "end_hour";
    public static final String COURSES_COLUMN_END_MINUTE = "end_minute";
    public static final String COURSES_COLUMN_DAY_OF_WEEKS = "days_of_week";
    //private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table " + COURSES_TABLE_NAME + "( " +
                        COURSES_COLUMN_ID + " integer primary key, " +
                        COURSES_COLUMN_NAME + " text, " +
                        COURSES_COLUMN_START_HOUR + " integer, " +
                        COURSES_COLUMN_START_MINUTE + " integer, " +
                        COURSES_COLUMN_END_HOUR + " integer, " +
                        COURSES_COLUMN_END_MINUTE + " integer, " +
                        COURSES_COLUMN_DAY_OF_WEEKS + " text" +

                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE_NAME);
        onCreate(db);
    }

    public boolean InsertCourse(String name, int start_hour, int start_minute, int end_hour, int end_minute, String days_of_week) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSES_COLUMN_NAME, name);
        contentValues.put(COURSES_COLUMN_START_HOUR, start_hour);
        contentValues.put(COURSES_COLUMN_START_MINUTE, start_minute);
        contentValues.put(COURSES_COLUMN_END_HOUR, end_hour);
        contentValues.put(COURSES_COLUMN_END_MINUTE, end_minute);
        contentValues.put(COURSES_COLUMN_DAY_OF_WEEKS, days_of_week);
        db.insert(COURSES_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery
                ("select * from " + COURSES_TABLE_NAME + " where " + COURSES_COLUMN_ID + "=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, COURSES_TABLE_NAME);
        return numRows;
    }

    public boolean UpdateCourse(Integer id, String name, int start_hour, int start_minute, int end_hour, int end_minute, String days_of_week) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSES_COLUMN_NAME, name);
        contentValues.put(COURSES_COLUMN_START_HOUR, start_hour);
        contentValues.put(COURSES_COLUMN_START_MINUTE, start_minute);
        contentValues.put(COURSES_COLUMN_END_HOUR, end_hour);
        contentValues.put(COURSES_COLUMN_END_MINUTE, end_minute);
        contentValues.put(COURSES_COLUMN_DAY_OF_WEEKS, days_of_week);
        db.update(COURSES_TABLE_NAME, contentValues, COURSES_COLUMN_ID + "= ? ", new String[]{Integer.toString(id)});

        return true;
    }

    public Integer DeleteCourse(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(COURSES_TABLE_NAME,  COURSES_COLUMN_ID + "= ? ", new String[]{Integer.toString(id)});
    }

    public CourseList getAllCourses(){
        CourseList courses_list = new CourseList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + COURSES_TABLE_NAME, null);
        res.moveToFirst();
        while (!res.isAfterLast()){
            String course_name = res.getString(res.getColumnIndex(COURSES_COLUMN_NAME));
            Integer course_id = res.getInt(res.getColumnIndex(COURSES_COLUMN_ID));
            courses_list.name.add(course_name);
            courses_list.id.add(course_id);
            res.moveToNext();
        }
        return courses_list;
    }

    public String FindCourseNameAtCurrentTime(int day_of_week, CourseTime current_time){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs = db.rawQuery("select * from " + COURSES_TABLE_NAME, null);
        rs.moveToFirst();
        day_of_week -= 1; // to align SUNDAY to 0 (instead of 1)
        while (!rs.isAfterLast()){
            String days_of_week = rs.getString(rs.getColumnIndex(COURSES_COLUMN_DAY_OF_WEEKS));
            if (days_of_week.charAt(day_of_week) == '1'){
                int start_hour = rs.getInt(rs.getColumnIndex(COURSES_COLUMN_START_HOUR));
                int start_minute = rs.getInt(rs.getColumnIndex(COURSES_COLUMN_START_MINUTE));
                int end_hour = rs.getInt(rs.getColumnIndex(COURSES_COLUMN_END_HOUR));
                int end_minute = rs.getInt(rs.getColumnIndex(COURSES_COLUMN_END_MINUTE));
                CourseTime start_time = new CourseTime(start_hour, start_minute);
                CourseTime end_time = new CourseTime(end_hour, end_minute);
                if (start_time.isEarlierThan(current_time) && current_time.isEarlierThan(end_time)){
                    String current_course_name = rs.getString(rs.getColumnIndex(DBHelper.COURSES_COLUMN_NAME));
                    return current_course_name;
                }
            }
            rs.moveToNext();
        }
        return "none";
    }
}
