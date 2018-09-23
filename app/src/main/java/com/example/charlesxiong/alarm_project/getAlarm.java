package com.example.charlesxiong.alarm_project;

import android.content.BroadcastReceiver;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.widget.Toast;

public class getAlarm  extends BroadcastReceiver implements AlarmInterfaceClass{

    @Override
    public void onReceive(Context context, Intent intent){

        Intent cancelAlarm = new Intent(context, CancelAlarm.class);
        cancelAlarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(cancelAlarm);
       /*
        Toast toast;
        toast = Toast.makeText(context, CREATED, duration);
        toast.show();
        */
    }

}
