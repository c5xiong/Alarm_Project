package com.example.charlesxiong.alarm_project;

import android.content.BroadcastReceiver;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class getAlarm  extends BroadcastReceiver implements AlarmInterfaceClass{

    @Override
    public void onReceive(Context context, Intent intent){
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
        Intent cancelAlarm = new Intent(context, CancelAlarm.class);
        cancelAlarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(cancelAlarm);
       /*
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
        final String CREATED = "Created Alarm";
        final int duration = Toast.LENGTH_SHORT;
        Toast toast;
        toast = Toast.makeText(context, CREATED, duration);
        toast.show();*/
    }
}
