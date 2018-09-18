package com.example.charlesxiong.alarm_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class change_Alarm extends AppCompatActivity implements AlarmInterfaceClass {

    /* This variable is designed to hold the time the user wants to be
     * notified at.
     */
    TextView time;
    /* This is the variable that holds the object that allows the user to
     * choose the time to be awaken at
     */
    TimePicker choice;
    //This holds the string for the time itself
    String format;
    //This is the calendar that accesses the time
    Calendar calendar;
    //This holds the AM string depending on the time the user chose
    final String morning = "AM";
    //This holds the PM string depending on the time the user chose
    final String evening = "PM";

    //This holds the request code value
    final int requestCode = 1;
    //This is used to hold the scrollview of the main page.
    private ScrollView scrollLayout;
    //This is the constraints behind the alarm text forms
    final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    //This holds the dialog that is used to choose times
    TimePickerDialog switchTime;
    //This holds the unique id of the alarm
    int idNumber = 0;

    //This constant value represents the save value
    final static boolean SAVE = true;
    //This constant value represents the cancel value
    final static boolean CANCEL = false;
    /* This variable returns the user's decision to save the alarm or cancel
     * out the changes
     */
    static boolean choiceOfUser = false;
    @SuppressLint("WrongViewCast")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__alarm);
        calendar = Calendar.getInstance();
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button changeTime = (Button) findViewById(R.id.changeAlarmTime);
        time = (TextView)findViewById(R.id.userSelectedTime);

        int hour = 0;
        int minute = 0;
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        createAlarm(hour, minute);

        /*if(hour >= 12){
            format = morning;
        }
        else{
            format = evening;
        }
        */
        time.setText(hour + " : " + minute + " " + format);
        //timeOutput = new TextView(this);
        //Problem is right below: null object reference
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                    /*setContentView(R.layout.activity_main);
                    scrollLayout = (ScrollView) findViewById(R.id.scrollView);
                    ((ViewGroup)(time.getParent())).removeView(time);
                    time.setVisibility(View.GONE);
                    time.setLayoutParams(lparams);
                    System.out.println("Currently in saveClick()" +" " + scrollLayout);
                    scrollLayout.addView(time);
                    time.setVisibility(View.VISIBLE);*/
                //setContentView(scrollLayout);
                System.out.println("**************************************************************");
                choiceOfUser = SAVE;
                setContentView(R.layout.activity_main);
                    /*LinearLayout mainMenu = (LinearLayout) findViewById(R.id.LinearLayout);
                    //scrollLayout = (ScrollView) findViewById(R.id.mainScroll);
                    if(choiceOfUser){
                        TextView newAlarm = new TextView(getApplicationContext());
                        newAlarm.setText(time.getText().toString());
                        mainMenu.addView(newAlarm,0);
                        //scrollLayout.fullScroll(ScrollView.FOCUS_DOWN);
                    }*/
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",time.getText().toString());
                returnIntent.putExtra("ID", idNumber);
                setResult(RESULT_OK,returnIntent);
                finish();
            }

        });
        changeTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                createAlarm(0, 0);
            }
        });
    }
    protected void createAlarm(int hr, int minutes){
        calendar = Calendar.getInstance();
        switchTime = new TimePickerDialog(
                change_Alarm.this,
                onTimeSetListener,
                hr,
                minutes,
                false

        );
        switchTime.setTitle("Set your alarm");
        switchTime.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hr, int minute) {
            Calendar now = Calendar.getInstance();
            Calendar newTime = (Calendar) now.clone();

            newTime.set(Calendar.SECOND, 0);
            newTime.set(Calendar.MILLISECOND, 0);
            newTime.set(Calendar.HOUR_OF_DAY, hr);
            newTime.set(Calendar.MINUTE, minute);

            if((newTime.compareTo(now)) <= 0){
                newTime.add(Calendar.DATE, 1);
            }
            setAlarm(newTime);
        }
    };

    private void setAlarm(Calendar target){
        time.setText(""+ target.getTime());
        Intent intent = new Intent(getBaseContext(), getAlarm.class);
        idNumber = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), idNumber, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(
                Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                target.getTimeInMillis(), pendingIntent);
    }
    /*protected void save(View view){
        MainActivity update = new MainActivity();
        time.setText(hour + " : " + minute + " " + format);
        setContentView(R.layout.activity_main);
        scrollLayout.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));
        scrollLayout.addView(timeOutput);
        finish();
    }*/
    /*protected void save(){
        System.out.println("Currently in save()");
    }*/
}

