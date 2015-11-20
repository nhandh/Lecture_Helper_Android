package com.example.nhdo.lecturehelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddNoteActivity extends AppCompatActivity {

    private static final String TEXT_FILE_FORMAT = ".txt";
    EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        note = (EditText) findViewById(R.id.addNote_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveNote(View view){
        File output_file = LectureHelperUtils.getEmptyFileWithStructuredPath
                (MainActivity.current_course_name, TEXT_FILE_FORMAT);

        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(output_file, true));
            buf.append(note.getText().toString());
            buf.newLine();
            buf.close();
            note.setText("");
            Toast.makeText(this, "Note saved", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();
        }





    }
}
