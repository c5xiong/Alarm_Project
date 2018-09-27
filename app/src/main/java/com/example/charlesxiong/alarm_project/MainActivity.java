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
import android.view.ViewParent;
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

    private GestureDetector gestureDetector;

    ObjectAnimator animateButtonForAlarm;
    ObjectAnimator animateButtonForDelete;

    static int numOfAlarms = 0;
    static int previousID = 0;
    static int deleteID = 0;

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

        final LinearLayout mainMenu = findViewById(R.id.linearLayout);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(-200,0,200,0);
        mainMenu.setGravity(Gravity.CENTER);
        mainMenu.setLayoutParams(layoutParams);

        ConstraintLayout.LayoutParams layoutParamsForDelete = new ConstraintLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final LinearLayout alarmRow = new LinearLayout(this);
        alarmRow.setGravity(Gravity.CENTER);
        alarmRow.setLayoutParams(layoutParamsForDelete);
        alarmRow.setOrientation(LinearLayout.HORIZONTAL);

        gestureDetector = new GestureDetector(this, new SingleTapConfirm());

        if (requestCode == REQUEST_CODE_NEW) {
            if(resultCode == RESULT_OK){
                newAlarms = new Button(this);
                deleteAlarm = new Button(this);

                newAlarms.setText(stuff.getStringExtra("result"));
                newAlarms.setId(stuff.getIntExtra("ID", previousID));


                deleteAlarm.setText("DELETE");
                deleteAlarm.setId(-1);

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
                final View.OnClickListener deleteTrait = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        previousID = v.getId();
                        Button aboutToBeDeleted = findViewById(previousID);
                        mainMenu.removeView(alarmRow);
                    }
                };
                final buttonSwipeMechanic x = new buttonSwipeMechanic(MainActivity.this) {
                    @Override
                    public void onSwipeTop() {
                        Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSwipeRight(View x, ViewGroup y) {
                        Button b = (Button) y.getChildAt(0);
                        animateButtonForAlarm = ObjectAnimator.ofFloat(
                                x, "translationX", 200f);
                        animateButtonForDelete = ObjectAnimator.ofFloat(
                                b, "translationX", 200f);
                        animateButtonForAlarm.setDuration(0);
                        animateButtonForDelete.setDuration(0);
                        animateButtonForAlarm.start();
                        animateButtonForDelete.start();
                    }

                    @Override
                    public void onSwipeLeft(View y, ViewGroup x) {
                        Button b = (Button) x.getChildAt(0);
                        animateButtonForAlarm = ObjectAnimator.ofFloat(
                                y, "translationX", -200f);
                        animateButtonForDelete = ObjectAnimator.ofFloat(
                                b, "translationX", -200f);
                        animateButtonForAlarm.setDuration(0);
                        animateButtonForAlarm.start();
                    }

                    @Override
                    public void onSwipeBottom() {
                        Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                    }
                };
                ViewGroup.OnTouchListener l = new ViewGroup.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motion) {
                        /*
                         * Holds the location of the event in which the user pressed
                         * down upon the screen*
                         */
                        float x1 = 0;
                        /*
                         * Holds the location of the event in which the user lifts his
                         * or her finger from the screen
                         */
                        float x2 = 0;
                        /*
                         * Holds the difference between x1 and x2 in order to calculate
                         * the swipe distance
                         */
                        float result;
                        /*
                         * This is the minimum distance to swipe the button
                         */
                        final float MINIMUM_DIST_RIGHT = 250;
                        if (gestureDetector.onTouchEvent(motion)) {
                            Button b = (Button)view;
                            previousID = view.getId();
                            if(b.getText().toString().equals("DELETE")){
                                Button aboutToBeDeleted = findViewById(previousID);
                                mainMenu.removeView(alarmRow);
                            }
                            else {
                                Intent intent = new Intent(MainActivity.this,
                                        edit_Alarm.class);
                                startActivityForResult(intent, REQUEST_CODE_CHANGE);
                            }
                            return true;
                        }
                        else {
                            switch (motion.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    x1 = motion.getX();
                                    break;
                                case MotionEvent.ACTION_UP:
                                    x2 = motion.getX();
                                    result = x2 - x1;
                                    if (result > 0 && result > MINIMUM_DIST_RIGHT) {
                                        x.onSwipeRight(view, (ViewGroup) view.getParent());
                                    }
                                    else {
                                        x.onSwipeLeft(view, (ViewGroup) view.getParent());
                                    }
                                    break;
                            }
                            return false;
                        }
                    }
                };
                params.gravity = Gravity.CENTER;
                paramsForDelete.gravity = Gravity.END;
                newAlarms.setOnTouchListener(l);
                deleteAlarm.setOnTouchListener(l);
                newAlarms.setLayoutParams(params);
                deleteAlarm.setLayoutParams(paramsForDelete);

                alarmRow.addView(deleteAlarm);
                alarmRow.addView(newAlarms);
                mainMenu.addView(alarmRow);
            }
        }

        else if (requestCode == REQUEST_CODE_CHANGE){
            if(resultCode == RESULT_OK){
                Button oldToNew = findViewById(previousID - 1);
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
        }

    }
    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }


}