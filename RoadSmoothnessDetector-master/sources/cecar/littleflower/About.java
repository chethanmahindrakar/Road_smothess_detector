package cecar.littleflower;

import android.app.Activity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

public class About extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0012R.layout.about);
        TextView ltv1 = (TextView) findViewById(C0012R.id.Atv1);
        ltv1.setText(" Road Smoothness Detector");
        Linkify.addLinks(ltv1, 15);
        TextView ltv2 = (TextView) findViewById(C0012R.id.Atv2);
        ltv2.setText(" This application is being developed by Chethan Mahindrakar and Atmik Ajoy, to detect the road smoothness using accelrometer and GPS");
        Linkify.addLinks(ltv2, 15);
        ((TextView) findViewById(C0012R.id.Atv3)).setText("   Any suggestion you can reach us by sending email.");
    }
}
