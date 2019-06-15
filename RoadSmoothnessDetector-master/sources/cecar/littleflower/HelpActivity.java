package cecar.littleflower;

import android.app.Activity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

public class HelpActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0012R.layout.help);
        TextView ltv1 = (TextView) findViewById(C0012R.id.htv1);
        ltv1.setText(" How to use this Application");
        Linkify.addLinks(ltv1, 15);
        TextView ltv2 = (TextView) findViewById(C0012R.id.htv2);
        ltv2.setText("1> To start gathering the data, click the 'Log Data' ");
        Linkify.addLinks(ltv2, 15);
        TextView ltv3 = (TextView) findViewById(C0012R.id.htv3);
        ltv3.setText("2> You will see 4 tabs on the screen. Click Sensors tab and select '3-axis Accelerometer', Then select Providers tab and select 'gps' and clcik 'GPS Status' tab and select 'GPS Status'");
        Linkify.addLinks(ltv3, 15);
        TextView ltv4 = (TextView) findViewById(C0012R.id.htv4);
        ltv4.setText("3> Click Control tab , Clcik Seek control to change the Euclidian value then clcik start logging button");
        Linkify.addLinks(ltv4, 15);
        TextView ltv5 = (TextView) findViewById(C0012R.id.htv5);
        ltv5.setText("4> First time you need to tune the Euclidian value such that when the vechile is running on a smooth road then there should not be Beeps, also when there is a hump or pot hole you must hear a beep sound from the this application. Repeat these steps till you meet these criteria. ");
        Linkify.addLinks(ltv5, 15);
        TextView ltv6 = (TextView) findViewById(C0012R.id.htv6);
        ltv6.setText("5> Enable GPS on your phone. Keep phone on a horizontal surface and click 'Start logging' to start  collecting Accelerometer info and GPS co-ordinates.");
        Linkify.addLinks(ltv6, 15);
        TextView ltv7 = (TextView) findViewById(C0012R.id.htv7);
        ltv7.setText("6> After running for 15-20 minutes, click  'Stop logging' button, wait for few seconds till the data is written in the local memory.  ");
        Linkify.addLinks(ltv7, 15);
        TextView ltv8 = (TextView) findViewById(C0012R.id.htv8);
        ltv8.setText("7> Click 'Upload' button. In the upload screen if you want to upload the recently saved  collected data , then click 'Click to Upload File' . If you want to upload old files then click 'Load File from local', In this case file that is located under 'SD CARD/Android/data/cecar.littleflower/files/' ");
        Linkify.addLinks(ltv8, 15);
        TextView ltv9 = (TextView) findViewById(C0012R.id.htv9);
        ltv9.setText("8> After upload, click 'View on Google Maps' to view data collected during the journey");
        Linkify.addLinks(ltv9, 15);
        ((TextView) findViewById(C0012R.id.htv10)).setText("If any suggestion, do reach us by email");
    }
}
