package com.example.nhdo.lecturehelper;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Helper class for handling SD card data transaction
 * @author Padmaja Krishna Kumar
 * @version 1.0
 * @since 14 Nov 2015
 */
public class LectureHelperUtils {

    private static final String LECTURE_HELPER = "LectureHelper";
    private static final String FILE_NAME_TO_SAVE = "";
    //private static final String THREE_GPP = ".3gp";

    /**
     * Method to save audio file to SD card
     * @param course_title, title of course
     * @param format, the file format, for example .mp3, .txt
     */
    public static boolean copyToStorage(File source_file, String course_title,String format) {

        File output_file = getEmptyFileWithStructuredPath(course_title, format);

        try {
            byte[] buffer = new byte[4096];
            int bytesToHold;

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source_file));
            BufferedOutputStream bos =  new BufferedOutputStream(new FileOutputStream(output_file));

            while ((bytesToHold = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesToHold);
            }

            bos.flush();
            bos.close();

            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static File getEmptyFileWithStructuredPath(String course_title,String format) {
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        StringBuffer dateInString = new StringBuffer();
        dateInString.append(month);
        dateInString.append("-");
        dateInString.append(date);
        dateInString.append("-");
        dateInString.append(year);
        StringBuffer timeInString = new StringBuffer();
        timeInString.append(hour);
        timeInString.append("_");
        timeInString.append(minute);
        timeInString.append("_");
        timeInString.append(second);
        Log.d("File Storage", "DateTime : " + dateInString + timeInString);

        String folderPathInString = Environment.getExternalStorageDirectory().toString() + "/"+ LECTURE_HELPER +"/" + course_title + "/" + dateInString + "/";

        File folderPath = new File(folderPathInString);
        if(!folderPath.exists()) {
            folderPath.mkdirs();
        }

        return new File(folderPath.getPath() + "/" + FILE_NAME_TO_SAVE + timeInString + format);

    }




    /**
     * Method to save audio file to SD card
     * @param file, input file to read and store to SD card
     * @param course_title, title of course
     */
    /* ORIGINAL CODE. I split this into two small functions.
    public static boolean saveToStorage(File file, String course_title,String format) {
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        StringBuffer dateInString = new StringBuffer();
        dateInString.append(month);
        dateInString.append("-");
        dateInString.append(date);
        dateInString.append("-");
        dateInString.append(year);
        StringBuffer timeInString = new StringBuffer();
        timeInString.append(hour);
        timeInString.append("_");
        timeInString.append(minute);
        timeInString.append("_");
        timeInString.append(second);
        Log.d("File Storage", "DateTime : " + dateInString + timeInString);

        String folderPathInString = Environment.getExternalStorageDirectory().toString() + "/"+ LECTURE_HELPER +"/" + course_title + "/" + dateInString + "/";

        File folderPath = new File(folderPathInString);
        if(!folderPath.exists()) {
            folderPath.mkdirs();
        }

        File fileMusic = new File(folderPath.getPath() + "/" + FILE_NAME_TO_SAVE + timeInString + format);

        try {

            byte[] buffer = new byte[4096];
            int bytesToHold;

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            BufferedOutputStream bos =  new BufferedOutputStream(new FileOutputStream(fileMusic));

            while ((bytesToHold = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesToHold);
            }

            bos.flush();
            bos.close();

            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }*/
}
