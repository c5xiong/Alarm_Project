package com.example.charlesxiong.alarm_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CancelAlarm extends AppCompatActivity {

    /*
     * This contains the context from the getAlarm class which is the context
     * of the edit_Alarm class
     */
    private Context context;
    /*
     * This is used to store the button in the Cancel_Alarm xml page
     */
    Button cancelRing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_cancel_alarm);
        context = getApplicationContext();

        /*This is the Uri object that contains the data in regards to which
         *ring should be played
         */
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        /*
         * This MediaPlayer object plays the Uri notification obj above
         */
        final MediaPlayer ring = MediaPlayer.create(context, notification);
        /*
         * This allows the MediaPlayer object above to play the ring in a loop
         */
        ring.setLooping(true);
        /*
         * This starts the ringing
         */
        ring.start();
        /*
         * This refers to the button that cancels the ring
         */
        cancelRing = findViewById(R.id.endRing);
        cancelRing.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    ring.stop();
                    finish();
                }
            }
        );
    }
}
