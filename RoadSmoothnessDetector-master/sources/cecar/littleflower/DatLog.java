package cecar.littleflower;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import com.cecar.util.RoadSensorData;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class DatLog extends TabActivity implements OnClickListener, SensorEventListener, LocationListener, Listener {
    TextView axtextView;
    TextView aytextView;
    TextView aztextView;
    private BufferedWriter[] bout = new BufferedWriter[1];
    int captureRate = 5;
    TextView counterText1;
    private SimpleDateFormat dtf = new SimpleDateFormat("dd.HH.mm.ss");
    float euc3dValue = 5.0f;
    int evno = 0;
    private String fileName = BuildConfig.FLAVOR;
    private DataOutputStream[] fout = new DataOutputStream[3];
    float gps_Accuracy;
    double gps_Altitude;
    double gps_Latitude;
    double gps_Longitude;
    long gps_time;
    float[] gravity = new float[3];
    TextView langtextView;
    TextView lattextView;
    boolean mGPSState;
    CLocProvStates mLPStates;
    CLogView mLV;
    LocationManager mLocMan;
    SensorManager mSenMan;
    CSensorStates mSenStates;
    private TextView[] mTVd = new TextView[2];
    TabWidget mTabWidget;
    WebView mWebView;
    Button mbtn_start;
    Button mbtn_stop;
    long prev_time;
    Vector<RoadSensorData> roadDataList = new Vector();
    private SeekBar seekControl = null;
    TextView seekText;
    int senseCounter = 0;

    /* renamed from: cecar.littleflower.DatLog$1 */
    class C00021 implements OnSeekBarChangeListener {
        int progressChanged = 0;

        C00021() {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            this.progressChanged = progress;
            DatLog.this.euc3dValue = ((float) progress) / 2.0f;
            DatLog.this.seekText.setText("Current Euclidian  Value : " + DatLog.this.euc3dValue + "   (range 1-20)");
            DatLog.this.seekText.setVisibility(0);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    /* renamed from: cecar.littleflower.DatLog$2 */
    class C00032 implements OnItemClickListener {
        C00032() {
        }

        public void onItemClick(AdapterView<?> adapterView, View aview, int position, long arg3) {
            DatLog.this.mSenStates.setActToggle(position);
        }
    }

    /* renamed from: cecar.littleflower.DatLog$3 */
    class C00043 implements OnItemClickListener {
        C00043() {
        }

        public void onItemClick(AdapterView<?> adapterView, View aview, int position, long arg3) {
            DatLog.this.mLPStates.setActToggle(position);
        }
    }

    /* renamed from: cecar.littleflower.DatLog$4 */
    class C00054 implements OnClickListener {
        C00054() {
        }

        public void onClick(View v) {
            DatLog.this.mGPSState = !DatLog.this.mGPSState;
            ((CheckedTextView) v).setChecked(DatLog.this.mGPSState);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        int i;
        super.onCreate(savedInstanceState);
        SensorManager lSenMan = (SensorManager) getSystemService("sensor");
        LocationManager lLocMan = (LocationManager) getSystemService("location");
        this.mSenMan = lSenMan;
        this.mLocMan = lLocMan;
        this.mSenStates = new CSensorStates(lSenMan.getSensorList(-1));
        this.mLPStates = new CLocProvStates(lLocMan.getAllProviders());
        this.mGPSState = false;
        this.mSenStates.setRate(this.captureRate);
        CSensorStates lSenStates = this.mSenStates;
        CLocProvStates lLPStates = this.mLPStates;
        read_prefs();
        setContentView(C0012R.layout.datlog);
        this.mLV = (CLogView) findViewById(C0012R.id.DLtv1);
        Button lbtn_start = (Button) findViewById(C0012R.id.DLbtn0);
        Button lbtn_event = (Button) findViewById(C0012R.id.DLbtn1);
        Button lbtn_stop = (Button) findViewById(C0012R.id.DLbtn2);
        Button lbtn_show = (Button) findViewById(C0012R.id.DLbtn4);
        this.lattextView = (TextView) findViewById(C0012R.id.latText1);
        this.langtextView = (TextView) findViewById(C0012R.id.langText1);
        this.axtextView = (TextView) findViewById(C0012R.id.axText1);
        this.aytextView = (TextView) findViewById(C0012R.id.ayText1);
        this.aztextView = (TextView) findViewById(C0012R.id.azText1);
        this.counterText1 = (TextView) findViewById(C0012R.id.counterText1);
        lbtn_start.setOnClickListener(this);
        lbtn_event.setOnClickListener(this);
        lbtn_stop.setOnClickListener(this);
        lbtn_show.setOnClickListener(this);
        lbtn_stop.setEnabled(false);
        this.mbtn_start = lbtn_start;
        this.mbtn_stop = lbtn_stop;
        this.seekControl = (SeekBar) findViewById(C0012R.id.seekBar1);
        this.seekText = (TextView) findViewById(C0012R.id.seekText);
        this.seekControl.setOnSeekBarChangeListener(new C00021());
        ListView llistview1 = (ListView) findViewById(C0012R.id.DLtab2);
        llistview1.setAdapter(new ArrayAdapter(this, 17367056, lSenStates.getNames()));
        for (i = 0; i < lSenStates.getNum(); i++) {
            llistview1.setItemChecked(i, lSenStates.getActive(i).booleanValue());
        }
        llistview1.setOnItemClickListener(new C00032());
        ListView llistview2 = (ListView) findViewById(C0012R.id.DLtab3);
        llistview2.setAdapter(new ArrayAdapter(this, 17367056, lLPStates.getNames()));
        for (i = 0; i < lLPStates.getNum(); i++) {
            llistview2.setItemChecked(i, lLPStates.getActive(i));
        }
        llistview2.setOnItemClickListener(new C00043());
        CheckedTextView lcheckview = (CheckedTextView) findViewById(C0012R.id.DLck1);
        lcheckview.setChecked(this.mGPSState);
        lcheckview.setOnClickListener(new C00054());
        TabHost lTabHost = getTabHost();
        lTabHost.addTab(lTabHost.newTabSpec("tab1").setIndicator("Control").setContent(C0012R.id.DLtab1));
        lTabHost.addTab(lTabHost.newTabSpec("tab2").setIndicator("Sensors").setContent(C0012R.id.DLtab2));
        lTabHost.addTab(lTabHost.newTabSpec("tab3").setIndicator("Providers").setContent(C0012R.id.DLtab3));
        if (this.mLPStates.isExist("gps")) {
            lTabHost.addTab(lTabHost.newTabSpec("tab4").setIndicator("GPS Status").setContent(C0012R.id.DLtab4));
        }
        lTabHost.setCurrentTab(0);
        this.mTabWidget = lTabHost.getTabWidget();
    }

    private void read_prefs() {
        int i;
        SharedPreferences lPrefs = getSharedPreferences("DatLogPrefs", 0);
        CLocProvStates lLPStates = this.mLPStates;
        CSensorStates lSenNames = this.mSenStates;
        for (i = 0; i < lSenNames.getNum(); i++) {
            lSenNames.setActive(i, lPrefs.getBoolean(lSenNames.getName(i), false));
            int rate = lPrefs.getInt(lSenNames.getName(i) + "_rate", 0);
            lSenNames.setRate(i, this.captureRate);
        }
        for (i = 0; i < lLPStates.getNum(); i++) {
            lLPStates.setActive(i, lPrefs.getBoolean(lLPStates.getName(i), false));
            lLPStates.setCriterion(i, lPrefs.getFloat(lLPStates.getName(i) + "_mindist", 0.0f), lPrefs.getLong(lLPStates.getName(i) + "_mintime", 0));
        }
        boolean z = lPrefs.getBoolean("gps_status", false);
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        int i;
        CSensorStates lSenStates = this.mSenStates;
        CLocProvStates lLPStates = this.mLPStates;
        Editor lPrefEd = getSharedPreferences("DatLogPrefs", 0).edit();
        for (i = 0; i < lLPStates.getNum(); i++) {
            lPrefEd.putBoolean(lLPStates.getName(i), lLPStates.getActive(i));
            lPrefEd.putFloat(lLPStates.getName(i) + "_mindist", lLPStates.getMinDist(i));
            lPrefEd.putLong(lLPStates.getName(i) + "_mintime", lLPStates.getMinTime(i));
        }
        lPrefEd.putBoolean("gps_status", this.mGPSState);
        for (i = 0; i < lSenStates.getNum(); i++) {
            lPrefEd.putBoolean(lSenStates.getName(i), lSenStates.getActive(i).booleanValue());
            lPrefEd.putInt(lSenStates.getName(i) + "_rate", lSenStates.getRate(i));
        }
        lPrefEd.commit();
        super.onDestroy();
    }

    public void onClick(View arg0) {
        if (arg0.getId() == C0012R.id.DLbtn0) {
            try {
                open_files();
                register_listeners();
                this.mLV.addtext("Started Logging");
            } catch (FileNotFoundException e) {
                this.mLV.addtext("File open error: Probably you do not have require permissions.");
                stop_recording();
            } catch (IOException e2) {
                this.mLV.addtext("File open error2 : Probably you do not have require permissions.");
                stop_recording();
            }
        } else if (arg0.getId() == C0012R.id.DLbtn1) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("FILENAME", this.fileName);
            startActivity(intent);
        } else if (arg0.getId() == C0012R.id.DLbtn2) {
            stop_recording();
            this.mLV.addtext("Stopped Logging");
        } else if (arg0.getId() == C0012R.id.DLbtn4) {
            show_registered();
        }
    }

    private void show_registered() {
        int i;
        CSensorStates lSenStates = this.mSenStates;
        CLocProvStates lLPStates = this.mLPStates;
        String nt = "Registered Sources:";
        int n = 0;
        for (i = 0; i < lSenStates.getNum(); i++) {
            if (lSenStates.getActive(i).booleanValue()) {
                nt = nt + "\n\t" + lSenStates.getName(i);
                n++;
            }
        }
        for (i = 0; i < lLPStates.getNum(); i++) {
            if (lLPStates.getActive(i)) {
                nt = nt + "\n\t" + lLPStates.getName(i);
                n++;
            }
        }
        if (this.mGPSState) {
            nt = nt + "\n\tGPS Status";
            n++;
        }
        if (n == 0) {
            nt = "No Registered Source.";
        }
        this.mLV.addtext(nt);
    }

    private void close_files() {
        BufferedWriter[] localbout = this.bout;
        this.senseCounter = 0;
        BufferedWriter bFile = this.bout[0];
        long beforeWrite = System.currentTimeMillis();
        this.mLV.addtext("Writing to File  ... " + this.fileName);
        int sz = this.roadDataList.size();
        int i = 0;
        while (i < sz) {
            try {
                RoadSensorData rsd = (RoadSensorData) this.roadDataList.get(i);
                bFile.write(convertintToString(rsd.getKey()) + "#");
                bFile.write(convertlongToString(rsd.getCapturedDate()) + "#");
                bFile.write(convertdoubleToString(rsd.getAltitude()) + "#");
                bFile.write(convertfloatToString(rsd.getGpsAccuracy()) + "#");
                bFile.write(convertdoubleToString(rsd.getLatitude()) + "#");
                bFile.write(convertdoubleToString(rsd.getLongitude()) + "#");
                bFile.write(convertfloatToString(rsd.getAx()) + "#");
                bFile.write(convertfloatToString(rsd.getAy()) + "#");
                bFile.write(convertfloatToString(rsd.getAz()) + "#");
                bFile.write(convertdoubleToString(rsd.getD3Distance()) + "#");
                bFile.write(convertdoubleToString(rsd.getInit_d3Distance()));
                bFile.write(10);
                i++;
            } catch (IOException io) {
                io.getMessage();
            }
        }
        this.mLV.addtext("Time take to write to file : " + (System.currentTimeMillis() - beforeWrite));
        try {
            bFile.close();
        } catch (IOException e) {
            this.mLV.addtext("File close error :");
        }
        this.roadDataList = new Vector();
    }

    private void open_files() throws FileNotFoundException, IOException {
        this.mbtn_start.setEnabled(false);
        this.mbtn_stop.setEnabled(true);
        this.mTabWidget.setEnabled(false);
        CSensorStates lSenStates = this.mSenStates;
        CLocProvStates lLPStates = this.mLPStates;
        BufferedWriter[] localbout = this.bout;
        if (lSenStates.getNumAct() > 0) {
            this.fileName = file_location("_msensors.txt").getName();
            this.mLV.addtext("fn :" + this.fileName);
            localbout[0] = new BufferedWriter(new FileWriter(file_location("_msensors.txt")));
            return;
        }
        localbout[0] = null;
    }

    private File file_location(String ntag) {
        boolean mExternalStorageAvailable;
        String state = Environment.getExternalStorageState();
        boolean mExternalStorageWriteable;
        if ("mounted".equals(state)) {
            mExternalStorageWriteable = true;
            mExternalStorageAvailable = true;
        } else if ("mounted_ro".equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageWriteable = false;
            mExternalStorageAvailable = false;
        }
        if (mExternalStorageAvailable && mExternalStorageWriteable) {
            return new File(getExternalFilesDir(null), this.dtf.format(new Date()) + ntag);
        }
        this.mLV.addtext("No external Storage.");
        return null;
    }

    private void register_listeners() {
        int i;
        CSensorStates lSenStates = this.mSenStates;
        CLocProvStates lLPStates = this.mLPStates;
        BufferedWriter[] localbout = this.bout;
        SensorManager lSenMan = this.mSenMan;
        LocationManager lLocMan = this.mLocMan;
        if (localbout[0] != null) {
            for (i = 0; i < lSenStates.getNum(); i++) {
                if (lSenStates.getActive(i).booleanValue()) {
                    lSenMan.registerListener(this, lSenMan.getDefaultSensor(lSenStates.getType(i)), lSenStates.getRate(i));
                }
            }
        }
        for (i = 0; i < lLPStates.getNum(); i++) {
            if (lLPStates.getActive(i)) {
                lLocMan.requestLocationUpdates(lLPStates.getName(i), lLPStates.getMinTime(i), lLPStates.getMinDist(i), this);
            }
        }
        lLocMan.addGpsStatusListener(this);
    }

    private void stop_recording() {
        this.mSenMan.unregisterListener(this);
        this.mLocMan.removeGpsStatusListener(this);
        this.mLocMan.removeUpdates(this);
        close_files();
        this.mbtn_start.setEnabled(true);
        this.mbtn_stop.setEnabled(false);
        this.mTabWidget.setEnabled(true);
    }

    private void dump_console() {
        try {
            DataOutputStream file = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file_location("_console.txt"))));
            file.writeChars(this.mLV.getText().toString());
            file.close();
        } catch (FileNotFoundException e) {
            this.mLV.addtext("Could open file for dumping");
        } catch (IOException e2) {
            this.mLV.addtext("Could not dump the console");
        }
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    private String convertintToString(int intValue) {
        return Integer.valueOf(intValue).toString();
    }

    private String convertlongToString(long longValue) {
        return Long.valueOf(longValue).toString();
    }

    private String convertfloatToString(float floatValue) {
        return Float.valueOf(floatValue).toString();
    }

    private String convertdoubleToString(double dValue) {
        return Double.valueOf(dValue).toString();
    }

    private double get3DSqroot(double x, double y, double z) {
        return Math.sqrt(((x * x) + (y * y)) + (z * z));
    }

    public void onSensorChanged(SensorEvent ev) {
        RoadSensorData rsData = new RoadSensorData();
        long tim = System.currentTimeMillis();
        if (this.prev_time == 0) {
            this.prev_time = tim;
        }
        if ((tim - this.prev_time) + 1 >= ((long) this.captureRate)) {
            this.prev_time = tim;
            int len = ev.values.length;
            try {
                this.senseCounter++;
                long diff1 = tim - this.gps_time;
                rsData.setKey(this.senseCounter);
                rsData.setCapturedDate(tim);
                rsData.setGpsAccuracy(this.gps_Accuracy);
                rsData.setAltitude(this.gps_Altitude);
                rsData.setLatitude(this.gps_Latitude);
                rsData.setLongitude(this.gps_Longitude);
                this.counterText1.setText("               Counter                :           " + convertlongToString((long) this.senseCounter));
                this.counterText1.setVisibility(0);
                this.lattextView.setText("               Latitude                :           " + convertdoubleToString(this.gps_Latitude));
                this.lattextView.setVisibility(0);
                this.langtextView.setText("               Longitude             :           " + convertdoubleToString(this.gps_Longitude));
                this.langtextView.setVisibility(0);
                float[] linear_acceleration = new float[]{(0.8f * this.gravity[0]) + (0.19999999f * ev.values[0]), (0.8f * this.gravity[1]) + (0.19999999f * ev.values[1]), (0.8f * this.gravity[2]) + (0.19999999f * ev.values[2])};
                linear_acceleration[0] = ev.values[0] - this.gravity[0];
                linear_acceleration[1] = ev.values[1] - this.gravity[1];
                linear_acceleration[2] = ev.values[2] - this.gravity[2];
                this.axtextView.setText("               X value                  :           " + convertfloatToString(linear_acceleration[0]));
                this.axtextView.setVisibility(0);
                rsData.setAx(linear_acceleration[0]);
                this.aytextView.setText("               Y value                  :           " + convertfloatToString(linear_acceleration[1]));
                this.aytextView.setVisibility(0);
                rsData.setAy(linear_acceleration[1]);
                this.aztextView.setText("               Z value                  :           " + convertfloatToString(linear_acceleration[2]));
                this.aztextView.setVisibility(0);
                rsData.setAz(linear_acceleration[2]);
                float eculdian3Ddistance = (float) get3DSqroot((double) linear_acceleration[0], (double) linear_acceleration[1], (double) linear_acceleration[2]);
                rsData.setD3Distance((double) eculdian3Ddistance);
                rsData.setInit_d3Distance((double) this.euc3dValue);
                if (eculdian3Ddistance > this.euc3dValue) {
                    new ToneGenerator(5, 2000).startTone(24);
                }
                this.roadDataList.add(rsData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onSensorChanged1(SensorEvent ev) {
        BufferedWriter bFile = this.bout[0];
        if (bFile != null) {
            long tim = System.currentTimeMillis();
            if (this.prev_time == 0) {
                this.prev_time = tim;
            }
            if ((tim - this.prev_time) + 1 >= ((long) this.captureRate)) {
                this.prev_time = tim;
                int len = ev.values.length;
                try {
                    this.senseCounter++;
                    long diff1 = tim - this.gps_time;
                    bFile.write(convertlongToString((long) this.senseCounter) + "#");
                    bFile.write(convertlongToString(tim) + "#");
                    bFile.write(convertfloatToString(this.gps_Accuracy) + "#");
                    bFile.write(convertdoubleToString(this.gps_Altitude) + "#");
                    bFile.write(convertdoubleToString(this.gps_Latitude) + "#");
                    bFile.write(convertdoubleToString(this.gps_Longitude) + "#");
                    this.counterText1.setText(convertlongToString((long) this.senseCounter));
                    this.counterText1.setVisibility(0);
                    this.lattextView.setText(convertdoubleToString(this.gps_Latitude));
                    this.lattextView.setVisibility(0);
                    this.langtextView.setText(convertdoubleToString(this.gps_Longitude));
                    this.langtextView.setVisibility(0);
                    bFile.write(len + "#");
                    float[] linear_acceleration = new float[3];
                    for (int i = 0; i < len; i++) {
                        this.gravity[0] = (0.8f * this.gravity[0]) + (0.19999999f * ev.values[0]);
                        this.gravity[1] = (0.8f * this.gravity[1]) + (0.19999999f * ev.values[1]);
                        this.gravity[2] = (0.8f * this.gravity[2]) + (0.19999999f * ev.values[2]);
                        linear_acceleration[0] = ev.values[0] - this.gravity[0];
                        linear_acceleration[1] = ev.values[1] - this.gravity[1];
                        linear_acceleration[2] = ev.values[2] - this.gravity[2];
                        bFile.write(convertfloatToString(linear_acceleration[i]) + "#");
                        if (i == 0) {
                            this.axtextView.setText(convertfloatToString(linear_acceleration[0]));
                            this.axtextView.setVisibility(0);
                        } else if (i == 1) {
                            this.aytextView.setText(convertfloatToString(linear_acceleration[1]));
                            this.aytextView.setVisibility(0);
                        } else if (i == 2) {
                            this.aztextView.setText(convertfloatToString(linear_acceleration[2]));
                            this.aztextView.setVisibility(0);
                        } else {
                            continue;
                        }
                    }
                    bFile.write(10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onLocationChanged(Location loc) {
        long tim = System.currentTimeMillis();
        int typ = loc.getProvider().length();
        try {
            this.gps_time = tim;
            this.gps_Accuracy = loc.getAccuracy();
            this.gps_Altitude = loc.getAltitude();
            this.gps_Latitude = loc.getLatitude();
            this.gps_Longitude = loc.getLongitude();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onProviderDisabled(String arg0) {
        this.mLV.addtext(arg0 + " provider disabled");
    }

    public void onProviderEnabled(String arg0) {
        this.mLV.addtext(arg0 + " provider enabled");
    }

    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    }

    public void onGpsStatusChanged(int status) {
        long tim = System.currentTimeMillis();
        GpsStatus lStatus = this.mLocMan.getGpsStatus(null);
        if (status == 3) {
            this.mLV.addtext("GPS_EVENT_FIRST_FIX - TTFX =" + lStatus.getTimeToFirstFix());
        } else if (status == 1) {
            this.mLV.addtext("GPS_EVENT_STARTED " + tim);
        } else if (status == 2) {
            this.mLV.addtext("GPS_EVENT_STOPPED " + tim);
        }
    }
}
