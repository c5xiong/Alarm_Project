package com.example.charlesxiong.alarm_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CancelAlarm extends AppCompatActivity {

    private Context context;
    private boolean shutOff = true;
    Button cancelRing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_alarm);
        context = getApplicationContext();

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);

        cancelRing = (Button) findViewById(R.id.endRing);
        while(shutOff){
            cancelRing.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    shutOff = false;
                }
            }
            );
            r.play();
        }
        finish();
    }

}
