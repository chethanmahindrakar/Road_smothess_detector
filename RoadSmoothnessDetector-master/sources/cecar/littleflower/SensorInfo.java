package cecar.littleflower;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;

public class SensorInfo extends Activity implements OnItemSelectedListener {
    int mAPILevel = 8;
    TextView mMaxRange;
    TextView mMinDelay;
    TextView mName;
    TextView mPower;
    TextView mResolution;
    List<Sensor> mSenList = null;
    TextView mType;
    TextView mVendor;
    TextView mVersion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mAPILevel = VERSION.SDK_INT;
        this.mSenList = ((SensorManager) getSystemService("sensor")).getSensorList(-1);
        CSensorStates lSenName = new CSensorStates(this.mSenList);
        setContentView(C0012R.layout.sensorinfo);
        Spinner lSpinner = (Spinner) findViewById(C0012R.id.SIspinner1);
        this.mName = (TextView) findViewById(C0012R.id.SIName);
        this.mMaxRange = (TextView) findViewById(C0012R.id.SIMaximumRange);
        this.mMinDelay = (TextView) findViewById(C0012R.id.SIMinDelay);
        this.mPower = (TextView) findViewById(C0012R.id.SIPower);
        this.mResolution = (TextView) findViewById(C0012R.id.SIResolution);
        this.mType = (TextView) findViewById(C0012R.id.SIType);
        this.mVendor = (TextView) findViewById(C0012R.id.SIVendor);
        this.mVersion = (TextView) findViewById(C0012R.id.SIVersion);
        if (lSenName.getNum() > 0) {
            ArrayAdapter<CharSequence> lAdapter = new ArrayAdapter(this, 17367048, lSenName.getNames());
            lAdapter.setDropDownViewResource(17367049);
            lSpinner.setAdapter(lAdapter);
            lSpinner.setOnItemSelectedListener(this);
            lSpinner.setSelection(0);
            return;
        }
        lSpinner.setEnabled(false);
    }

    public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
        List<Sensor> lSenList = this.mSenList;
        this.mName.setText(((Sensor) lSenList.get(arg2)).getName());
        this.mMaxRange.setText(Float.toString(((Sensor) lSenList.get(arg2)).getMaximumRange()));
        if (this.mAPILevel > 8) {
            this.mMinDelay.setText(Integer.toString(((Sensor) lSenList.get(arg2)).getMinDelay()));
        } else {
            this.mMinDelay.setText("Not defined for Level<9");
        }
        this.mPower.setText(Float.toString(((Sensor) lSenList.get(arg2)).getPower()));
        this.mResolution.setText(Float.toString(((Sensor) lSenList.get(arg2)).getResolution()));
        this.mType.setText(Integer.toString(((Sensor) lSenList.get(arg2)).getType()));
        this.mVendor.setText(((Sensor) lSenList.get(arg2)).getVendor());
        this.mVersion.setText(Integer.toString(((Sensor) lSenList.get(arg2)).getVersion()));
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
