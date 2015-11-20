package com.example.nhdo.lecturehelper;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static String current_course_name;
    CourseTime  current_time;
    int current_day_of_week;
    TextView textView;
    DBHelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.main_tv_current_course);
        current_time = new CourseTime(0, 0);
        mydb = new DBHelper(this);

        // TODO: change this to Update function that get called by (change in database OR after each minute)

    }

    @Override
    protected void onResume(){
        // get current time and update the current course name
        super.onResume();
        Calendar calendar = Calendar.getInstance();
        current_day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        current_time.hour = calendar.get(Calendar.HOUR_OF_DAY);
        current_time.minute  = calendar.get(Calendar.MINUTE);
        current_course_name = mydb.FindCourseNameAtCurrentTime(current_day_of_week, current_time);
        textView.setText(current_course_name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void addNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    public void addAudio(View view) {
        Intent intent = new Intent(this, AudioRecorder.class);
        startActivity(intent);
    }

    public void goToScheduleActivity(View view){
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }




}
