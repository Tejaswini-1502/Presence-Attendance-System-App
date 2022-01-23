package com.example.attendancesystem;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public static final String LOGIN_TABLE = "LOGIN_TABLE";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COURSES_TABLE = "COURSES";
    public static final String COLUMN_COURSE_ID = "COURSE_ID";
    public static final String COLUMN_COURSE_NAME = "COURSE_NAME";
    public static final String COLUMN_TEACHER_NAME = "TEACHER_NAME";

    public static final String ID = "ID";
    public static final String STUDENT_TABLE = "STUDENT_TABLE";
    public static final String COLUMN_STUDENT_NAME = "STUDENT_NAME";
    public static final String CLASSES_HELD = "CLASSES_HELD";
    public static final String CLASSES_ATTENDED = "CLASSES_ATTENDED";

    public static final String ID_NET = "ID";
    public static final String STUDENT_TABLE_NET = "STUDENT_TABLE_NET";
    public static final String COLUMN_STUDENT_NAME_NET = "STUDENT_NAME";
    public static final String CLASSES_HELD_NET = "CLASSES_HELD";
    public static final String CLASSES_ATTENDED_NET = "CLASSES_ATTENDED";

    public static final String ID_DBMS = "ID";
    public static final String STUDENT_TABLE_DBMS = "STUDENT_TABLE_DBMS";
    public static final String COLUMN_STUDENT_NAME_DBMS = "STUDENT_NAME";
    public static final String CLASSES_HELD_DBMS = "CLASSES_HELD";
    public static final String CLASSES_ATTENDED_DBMS = "CLASSES_ATTENDED";
    public Database(@Nullable Context context) {
        super(context, "presence.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createtable = "CREATE TABLE " + LOGIN_TABLE + "(" + COLUMN_USERNAME + " TEXT PRIMARY KEY," + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createtable);
        String createTableCourses = "CREATE TABLE " + COURSES_TABLE + " (" + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY," + COLUMN_COURSE_NAME + " TEXT," + COLUMN_TEACHER_NAME + " INTEGER, FOREIGN KEY(" + COLUMN_TEACHER_NAME + ") REFERENCES " + LOGIN_TABLE + "(" + COLUMN_USERNAME + "))";
        db.execSQL(createTableCourses);
        String createTableStudAndroid= " CREATE TABLE " + STUDENT_TABLE + "(" + ID + " INTEGER PRIMARY KEY ," + COLUMN_STUDENT_NAME + " TEXT," + CLASSES_HELD + " INT," + CLASSES_ATTENDED + " INT)";
        db.execSQL(createTableStudAndroid);
        String createTableStudNetworks = " CREATE TABLE " + STUDENT_TABLE_NET + "(" + ID_NET + " INTEGER PRIMARY KEY ," + COLUMN_STUDENT_NAME_NET + " TEXT," + CLASSES_HELD_NET + " INT," + CLASSES_ATTENDED_NET + " INT)";
        db.execSQL(createTableStudNetworks);
        String createTableStudDBMS = " CREATE TABLE " + STUDENT_TABLE_DBMS + "(" + ID_DBMS + " INTEGER PRIMARY KEY ," + COLUMN_STUDENT_NAME_DBMS + " TEXT," + CLASSES_HELD_DBMS + " INT," + CLASSES_ATTENDED_DBMS + " INT)";
        db.execSQL(createTableStudDBMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteRows = "DELETE FROM "+LOGIN_TABLE;
        db.execSQL(deleteRows);
    }
    boolean insert(login c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME,c.getUsername());
        cv.put(COLUMN_PASSWORD,c.getPwd());
        long insert = db.insert(LOGIN_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        return true;
    }
    boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+LOGIN_TABLE+" where "+COLUMN_USERNAME+" = ?",new String[]{username});
        if(cursor.getCount()>0){
            return true;
        }
        return false;
    }

    boolean checkUsernamePwd(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+LOGIN_TABLE+" where "+COLUMN_USERNAME+" = ? and "+COLUMN_PASSWORD+" = ?",new String[]{username,password});
        if(cursor.getCount()>0){
            return true;
        }
        return false;
    }
    boolean insertCourses(courses c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_COURSE_ID,c.getCourse_id());
        cv.put(COLUMN_COURSE_NAME,c.getCourse_name());
        cv.put(COLUMN_TEACHER_NAME,c.getTeacher_name());
        long insert = db.insert(COURSES_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        return true;
    }

    ArrayList<String> retrieveCourses(String user){
        ArrayList<String> res = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+COURSES_TABLE+" WHERE "+COLUMN_TEACHER_NAME+" = ?",new String[]{user});
        if(cursor.moveToFirst())
        {
            do{
                String course_name = cursor.getString(1);
                res.add(course_name);
            }while(cursor.moveToNext());
        }
        else{
        }
        cursor.close();
        db.close();
        return res;
    }

    public boolean addOne(StudentModel studentmodel)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(ID,studentmodel.getId());
        cv.put(COLUMN_STUDENT_NAME,studentmodel.getName());
        cv.put(CLASSES_HELD,studentmodel.getClasses_held());
        cv.put(CLASSES_ATTENDED,studentmodel.getClasses_attended());

        long insert = db.insert(STUDENT_TABLE, null, cv);
        if(insert==-1)
            return false;
        else
            return true;

    }

    public void updateClassHeld(){
        SQLiteDatabase db=this.getWritableDatabase();
        String update_statement1 ="UPDATE "+STUDENT_TABLE+" SET "+CLASSES_HELD+" = "+CLASSES_HELD+" + 1 ";
        db.execSQL(update_statement1);
    }

    public void updateAttendance(Integer id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String update_statement1 ="UPDATE "+STUDENT_TABLE+" SET "+CLASSES_ATTENDED+" = "+CLASSES_ATTENDED+" + 1 WHERE "+ID+" = "+"'"+id+"'";
        db.execSQL(update_statement1);

    }

    public boolean addOneNET(StudentModel studentmodel)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(ID_NET,studentmodel.getId());
        cv.put(COLUMN_STUDENT_NAME_NET,studentmodel.getName());
        cv.put(CLASSES_HELD_NET,studentmodel.getClasses_held());
        cv.put(CLASSES_ATTENDED_NET,studentmodel.getClasses_attended());

        long insert = db.insert(STUDENT_TABLE_NET, null, cv);
        if(insert==-1)
            return false;
        else
            return true;

    }

    public void updateClassHeldNET(){
        SQLiteDatabase db=this.getWritableDatabase();
        String update_statement1 ="UPDATE "+STUDENT_TABLE_NET+" SET "+CLASSES_HELD_NET+" = "+CLASSES_HELD_NET+" + 1 ";
        db.execSQL(update_statement1);
    }

    public void updateAttendanceNET(Integer id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String update_statement1 ="UPDATE "+STUDENT_TABLE_NET+" SET "+CLASSES_ATTENDED_NET+" = "+CLASSES_ATTENDED_NET+" + 1 WHERE "+ID_NET+" = "+"'"+id+"'";
        db.execSQL(update_statement1);

    }

    public boolean addOneDBMS(StudentModel studentmodel)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(ID_DBMS,studentmodel.getId());
        cv.put(COLUMN_STUDENT_NAME_DBMS,studentmodel.getName());
        cv.put(CLASSES_HELD_DBMS,studentmodel.getClasses_held());
        cv.put(CLASSES_ATTENDED_DBMS,studentmodel.getClasses_attended());

        long insert = db.insert(STUDENT_TABLE_DBMS, null, cv);
        if(insert==-1)
            return false;
        else
            return true;

    }

    public void updateClassHeldDBMS(){
        SQLiteDatabase db=this.getWritableDatabase();
        String update_statement1 ="UPDATE "+STUDENT_TABLE_DBMS+" SET "+CLASSES_HELD_DBMS+" = "+CLASSES_HELD_DBMS+" + 1 ";
        db.execSQL(update_statement1);
    }

    public void updateAttendanceDBMS(Integer id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String update_statement1 ="UPDATE "+STUDENT_TABLE_DBMS+" SET "+CLASSES_ATTENDED_DBMS+" = "+CLASSES_ATTENDED_DBMS+" + 1 WHERE "+ID_DBMS+" = "+"'"+id+"'";
        db.execSQL(update_statement1);

    }

    public ArrayList<StudentModel> viewAttendence1() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_TABLE, null);
        ArrayList<StudentModel> courseModalArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                courseModalArrayList.add(new StudentModel(cursor.getInt(0), cursor.getString(1),cursor.getInt(2), cursor.getInt(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return courseModalArrayList;
    }

    public ArrayList<StudentModel> viewAttendence2() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_TABLE_NET, null);
        ArrayList<StudentModel> courseModalArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                courseModalArrayList.add(new StudentModel(cursor.getInt(0), cursor.getString(1),cursor.getInt(2), cursor.getInt(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return courseModalArrayList;
    }

    public ArrayList<StudentModel> viewAttendence3() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_TABLE_DBMS, null);
        ArrayList<StudentModel> courseModalArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                courseModalArrayList.add(new StudentModel(cursor.getInt(0), cursor.getString(1),cursor.getInt(2), cursor.getInt(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return courseModalArrayList;
    }
}