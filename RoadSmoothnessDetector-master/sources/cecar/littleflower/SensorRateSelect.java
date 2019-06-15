package cecar.littleflower;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;

public class SensorRateSelect extends Activity implements OnClickListener {
    CSensorStates mSenStates;
    RadioGroup mrg;

    /* renamed from: cecar.littleflower.SensorRateSelect$1 */
    class C00131 implements OnClickListener {
        C00131() {
        }

        public void onClick(View v) {
            SensorRateSelect.this.setResult(0);
            SensorRateSelect.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0012R.layout.sensorrateselect);
        Button btndiscard = (Button) findViewById(C0012R.id.SRSbutton1);
        Button btnsave = (Button) findViewById(C0012R.id.SRSbutton2);
        this.mrg = (RadioGroup) findViewById(C0012R.id.SRSradioGroup1);
        btndiscard.setOnClickListener(new C00131());
        btnsave.setOnClickListener(this);
        CSensorStates lSenStates = (CSensorStates) getIntent().getParcelableExtra("sensor_states");
        this.mSenStates = lSenStates;
        if (lSenStates.getNum() > 0) {
            switch (lSenStates.getRate(0)) {
                case 0:
                    this.mrg.check(C0012R.id.SRSradio0);
                    return;
                case 1:
                    this.mrg.check(C0012R.id.SRSradio1);
                    return;
                case BuildConfig.VERSION_CODE /*2*/:
                    this.mrg.check(C0012R.id.SRSradio3);
                    return;
                case 3:
                    this.mrg.check(C0012R.id.SRSradio2);
                    return;
                default:
                    return;
            }
        }
    }

    protected void onPause() {
        super.onPause();
        setResult(0);
        finish();
    }

    public void onClick(View arg0) {
        int rate;
        switch (this.mrg.getCheckedRadioButtonId()) {
            case C0012R.id.SRSradio0:
                rate = 0;
                break;
            case C0012R.id.SRSradio1:
                rate = 1;
                break;
            case C0012R.id.SRSradio2:
                rate = 2;
                break;
            case C0012R.id.SRSradio3:
                rate = 3;
                break;
            default:
                rate = 2;
                break;
        }
        Editor lPrefEd = getSharedPreferences("SensorMonPrefs", 0).edit();
        CSensorStates lSenStates = this.mSenStates;
        for (int i = 0; i < lSenStates.getNum(); i++) {
            lPrefEd.putInt(lSenStates.getName(i) + "_rate", rate);
        }
        lPrefEd.commit();
        setResult(-1);
        finish();
    }
}
