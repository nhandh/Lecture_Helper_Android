package com.example.nhdo.lecturehelper;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static String current_course_name;
    CourseTime  current_time;
    int current_day_of_week;
    TextView textView;
    DBHelper mydb;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_TAKE_VIDEO = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.main_tv_current_course);
        current_time = new CourseTime(0, 0);
        mydb = new DBHelper(this);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_TAKE_PHOTO) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "The photo is saved!", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUEST_TAKE_VIDEO) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "The video is saved!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addPhoto(View view) {
        final String PICTURE_FORMAT = ".jpg";
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = LectureHelperUtils.getEmptyFileWithStructuredPath
                    (current_course_name, PICTURE_FORMAT);

            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void addVideo(View view) {
        final String VIDEO_FORMAT = ".mp4";
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            File videoFile = LectureHelperUtils.getEmptyFileWithStructuredPath
                    (current_course_name, VIDEO_FORMAT);
            if (videoFile != null) {
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(videoFile));
                startActivityForResult(takeVideoIntent, REQUEST_TAKE_PHOTO);
            }
        }
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
