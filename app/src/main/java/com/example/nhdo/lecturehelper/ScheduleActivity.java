package com.example.nhdo.lecturehelper;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {


    // for database
    private ListView obj;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        mydb = new DBHelper(this);
        final CourseList array_list = mydb.getAllCourses();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list.name);

        obj = (ListView) findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int id_to_search = array_list.id.get(position);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id",id_to_search);

                Intent intent = new Intent(getApplicationContext(), DisplayCourse.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.action_add_course:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                Intent intent = new Intent(getApplicationContext(), DisplayCourse.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

        /*
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_add_course){
            //Log.d("SchedulingActivity", "Add a new course!");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add a course");
            builder.setMessage("Please enter the necessary information");

            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.add_course_layout, null);
            builder.setView(dialogView);

            final EditText inputField = (EditText) dialogView.findViewById(R.id.addCourse_name);
            final TimePicker timePickerStart
                    = (TimePicker) dialogView.findViewById(R.id.addCourse_timePickerStart);

            final TimePicker timePickerEnd
                    = (TimePicker) dialogView.findViewById(R.id.addCourse_timePickerEnd);

            final String[] arr = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
            final ListView lvCheckBox = (ListView) dialogView.findViewById(R.id.addCourse_daysOfWeek);
            lvCheckBox.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            lvCheckBox.setAdapter
                    (new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arr));


            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    int len = lvCheckBox.getCount();
                    int selected_day_of_weeks[] = {0, 0, 0, 0, 0, 0, 0};
                    SparseBooleanArray checked = lvCheckBox.getCheckedItemPositions();
                    for (int e = 0; e < len; e++) {
                        if (checked.get(e)) {
                            selected_day_of_weeks[e] = 1;
                        }
                    }

                    int hour_start = timePickerStart.getCurrentHour();
                    int minute_start = timePickerStart.getCurrentMinute();
                    int hour_end = timePickerEnd.getCurrentHour();
                    int minute_end = timePickerEnd.getCurrentMinute();

                    if (hour_end < hour_start) {

                        Toast.makeText(getApplicationContext(), "End time cannot be earlier than start time!",
                                Toast.LENGTH_LONG).show();
                        return;

                    }

                    Course course = new Course(
                            inputField.getText().toString(),
                            selected_day_of_weeks,
                            new CourseTime(hour_start, minute_start),
                            new CourseTime(hour_end, minute_end));

                    course_list.AddNewCourse(course);

                    Log.d("Scheduling Activity: ", "hello!");

                }
            });

            builder.setNegativeButton("Cancel", null);

            builder.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
        */
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }


}