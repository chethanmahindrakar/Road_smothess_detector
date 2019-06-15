package cecar.littleflower;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SensorMonitor extends Activity implements SensorEventListener {
    private int mNSen = 0;
    private SensorManager mSenMan;
    private CSensorStates mSenStates;
    private int[] mTVIndex = new int[10];
    private TextView[] mTVd = new TextView[10];
    private TextView[] mTVh = new TextView[10];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0012R.layout.sensormonitor);
        setTVRef();
        this.mSenMan = (SensorManager) getSystemService("sensor");
        this.mSenStates = new CSensorStates(this.mSenMan.getSensorList(-1));
    }

    protected void onPause() {
        this.mSenMan.unregisterListener(this);
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        read_prefs();
        adjust_view();
        CSensorStates lSenStates = this.mSenStates;
        SensorManager lSenMan = this.mSenMan;
        for (int i = 0; i < lSenStates.getNum(); i++) {
            if (lSenStates.getActive(i).booleanValue()) {
                lSenMan.registerListener(this, lSenMan.getDefaultSensor(lSenStates.getType(i)), lSenStates.getRate(i));
            }
        }
    }

    private void setTVRef() {
        TextView[] lTVh = this.mTVh;
        TextView[] lTVd = this.mTVd;
        lTVh[0] = (TextView) findViewById(C0012R.id.SMtvh1);
        lTVh[1] = (TextView) findViewById(C0012R.id.SMtvh2);
        lTVh[2] = (TextView) findViewById(C0012R.id.SMtvh3);
        lTVh[3] = (TextView) findViewById(C0012R.id.SMtvh4);
        lTVh[4] = (TextView) findViewById(C0012R.id.SMtvh5);
        lTVh[5] = (TextView) findViewById(C0012R.id.SMtvh6);
        lTVh[6] = (TextView) findViewById(C0012R.id.SMtvh7);
        lTVh[7] = (TextView) findViewById(C0012R.id.SMtvh8);
        lTVh[8] = (TextView) findViewById(C0012R.id.SMtvh9);
        lTVh[9] = (TextView) findViewById(C0012R.id.SMtvh10);
        lTVd[0] = (TextView) findViewById(C0012R.id.SMtvd1);
        lTVd[1] = (TextView) findViewById(C0012R.id.SMtvd2);
        lTVd[2] = (TextView) findViewById(C0012R.id.SMtvd3);
        lTVd[3] = (TextView) findViewById(C0012R.id.SMtvd4);
        lTVd[4] = (TextView) findViewById(C0012R.id.SMtvd5);
        lTVd[5] = (TextView) findViewById(C0012R.id.SMtvd6);
        lTVd[6] = (TextView) findViewById(C0012R.id.SMtvd7);
        lTVd[7] = (TextView) findViewById(C0012R.id.SMtvd8);
        lTVd[8] = (TextView) findViewById(C0012R.id.SMtvd9);
        lTVd[9] = (TextView) findViewById(C0012R.id.SMtvd10);
    }

    private void read_prefs() {
        SharedPreferences lPrefs = getSharedPreferences("SensorMonPrefs", 0);
        CSensorStates lSenNames = this.mSenStates;
        for (int i = 0; i < lSenNames.getNum(); i++) {
            lSenNames.setActive(i, lPrefs.getBoolean(lSenNames.getName(i), false));
            lSenNames.setRate(i, lPrefs.getInt(lSenNames.getName(i) + "_rate", 3));
        }
    }

    private void adjust_view() {
        int lnsen = 0;
        CSensorStates lSenNames = this.mSenStates;
        TextView[] lTVh = this.mTVh;
        TextView[] lTVd = this.mTVd;
        for (int i = 0; i < lSenNames.getNum() && lnsen < 10; i++) {
            if (lSenNames.getActive(i).booleanValue()) {
                lTVh[lnsen].setText(lSenNames.getName(i));
                lTVh[lnsen].setVisibility(0);
                lTVd[lnsen].setText(BuildConfig.FLAVOR);
                lTVd[lnsen].setVisibility(0);
                this.mTVIndex[lnsen] = lSenNames.getType(i);
                lnsen++;
            }
        }
        this.mNSen = lnsen;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0012R.menu.sensurmonitormenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (!item.hasSubMenu()) {
            Intent lIntent;
            if (item.getItemId() == C0012R.id.SMselect) {
                lIntent = new Intent(this, SensorSelect.class);
                lIntent.putExtra("sensor_states", this.mSenStates);
                startActivityForResult(lIntent, 1);
            } else if (item.getItemId() == C0012R.id.SMrate) {
                lIntent = new Intent(this, SensorRateSelect.class);
                lIntent.putExtra("sensor_states", this.mSenStates);
                startActivityForResult(lIntent, 2);
            }
        }
        return true;
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    public void onSensorChanged(SensorEvent event) {
        int[] lTVIndex = this.mTVIndex;
        for (int i = 0; i < this.mNSen; i++) {
            if (lTVIndex[i] == event.sensor.getType()) {
                String dtext = BuildConfig.FLAVOR;
                float[] data = event.values;
                int m = 0;
                while (m < data.length - 1) {
                    dtext = dtext.concat(String.valueOf(data[m])).concat("\t");
                    m++;
                }
                this.mTVd[i].setText(dtext.concat(String.valueOf(data[m])));
            }
        }
    }
}
