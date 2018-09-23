package com.example.charlesxiong.alarm_project;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.view.TouchDelegate.ABOVE;
import static android.view.ViewGroup.LayoutParams.*;

public class MainActivity extends AppCompatActivity implements AlarmInterfaceClass {

    /*static final int REQUEST_CODE_CHANGE = 2;
    static final int REQUEST_CODE_NEW = 3;
    static final int REQUEST_CODE_DELETE = 4;*/

    private GestureDetector gestureDetector;

    ObjectAnimator animateButtonForAlarm;
    ObjectAnimator animateButtonForDelete;
    static int numOfAlarms = 0;
    static int previousID = 0;

    Button newAlarms;
    Button deleteAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    protected void addAlarm(View view){
        Intent intent = new Intent(this, edit_Alarm.class);
        startActivityForResult(intent, REQUEST_CODE_NEW);
     }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent stuff) {

        LinearLayout mainMenu = (LinearLayout) findViewById(R.id.linearLayout);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(-700,0,200,0);
        mainMenu.setGravity(Gravity.CENTER);
        mainMenu.setLayoutParams(layoutParams);

        ConstraintLayout.LayoutParams layoutParamsForDelete = new ConstraintLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //layoutParamsForDelete.setMargins(-300,0,300,0);
        LinearLayout alarmRow = new LinearLayout(this);
        alarmRow.setGravity(Gravity.CENTER);
        alarmRow.setLayoutParams(layoutParamsForDelete);
        alarmRow.setOrientation(LinearLayout.HORIZONTAL);

        /*ORIENTATION DOESNT WORK FOR RELATIVELAYOUT*/

        gestureDetector = new GestureDetector(this, new SingleTapConfirm());

        if (requestCode == REQUEST_CODE_NEW) {
            if(resultCode == RESULT_OK){
                newAlarms = new Button(this);
                deleteAlarm = new Button(this);

                newAlarms.setText(stuff.getStringExtra("result"));
                newAlarms.setId(stuff.getIntExtra("ID", 0));

                deleteAlarm.setText("DELETE");
                deleteAlarm.setId(stuff.getIntExtra("ID", 0));

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)newAlarms.getLayoutParams();
                LinearLayout.LayoutParams paramsForDelete =(LinearLayout.LayoutParams) deleteAlarm.getLayoutParams();
                if(params != null) {
                    params.width = 1250;
                    params.height = 200;
                    paramsForDelete.width = 200;
                    paramsForDelete.height = 200;
                }
                else {
                    params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    paramsForDelete = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.width = 1250;
                    params.height = 200;
                    paramsForDelete.width = 200;
                    paramsForDelete.height = 200;
                }
                params.gravity = Gravity.CENTER;
                paramsForDelete.gravity = Gravity.END;
                newAlarms.setLayoutParams(params);
                deleteAlarm.setLayoutParams(paramsForDelete);
                alarmRow.addView(newAlarms);
                alarmRow.addView(deleteAlarm);
                mainMenu.addView(alarmRow);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("Never Saved");
            }
        }
        else if (requestCode == REQUEST_CODE_CHANGE){
            if(resultCode == RESULT_OK){
                Button oldToNew = findViewById(previousID);
                oldToNew.setText(stuff.getStringExtra("result"));
                previousID = 0;
            }
        }
        else{
            if(resultCode == RESULT_OK){
                Button oldToNew = findViewById(previousID);
                mainMenu.removeView(oldToNew);
                previousID = 0;
            }
        }/*
        newAlarms.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //setContentView(R.layout.activity_main);
                previousID = newAlarms.getId();
                Intent intent = new Intent(MainActivity.this, edit_Alarm.class);
                startActivityForResult(intent, REQUEST_CODE_CHANGE);
            }
        });*/
        //TEMPORARY CODE AS WELL UNTIL I UNDERSTAND HOW THE SWIPE MATH WORKS
        newAlarms.setOnTouchListener(new buttonSwipeMechanic(MainActivity.this) {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (gestureDetector.onTouchEvent(arg1)) {
                    previousID = newAlarms.getId();
                    Intent intent = new Intent(MainActivity.this, edit_Alarm.class);
                    startActivityForResult(intent, REQUEST_CODE_CHANGE);
                    return true;
                }
                else {
                    onSwipeBottom();
                    onSwipeTop();
                    //onSwipeRight();
                    onSwipeLeft();
                    return false;
                }
            }
                @Override
                public void onSwipeTop () {
                    Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onSwipeRight () {
                    animateButtonForAlarm = ObjectAnimator.ofFloat(
                            newAlarms, "translationX", 100f);
                    animateButtonForDelete = ObjectAnimator.ofFloat(
                            newAlarms, "translationX", 100f);
                    animateButtonForAlarm.setDuration(1);
                    animateButtonForDelete.setDuration(1);
                    animateButtonForAlarm.start();
                    animateButtonForDelete.start();
                }
                @Override
                public void onSwipeLeft () {
                    animateButtonForAlarm = ObjectAnimator.ofFloat(
                            newAlarms, "translationX", -100f);
                    animateButtonForDelete = ObjectAnimator.ofFloat(
                            deleteAlarm, "translationX", -100f);
                    animateButtonForAlarm.setDuration(1);
                    animateButtonForDelete.setDuration(1);
                    animateButtonForAlarm.start();
                    animateButtonForDelete.start();
                    Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onSwipeBottom () {
                    Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                }
        });
    }
    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }


}
