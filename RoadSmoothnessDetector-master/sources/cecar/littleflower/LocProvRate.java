package cecar.littleflower;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LocProvRate extends Activity implements OnClickListener {
    Button bt1;
    Button bt2;
    EditText et1;
    EditText et2;
    long mindist;
    long mintime;

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(C0012R.layout.locprovrate);
        Intent lIntent = getIntent();
        this.mindist = (long) lIntent.getFloatExtra("mindist", 0.0f);
        this.mintime = lIntent.getLongExtra("mintime", 0);
        this.et1 = (EditText) findViewById(C0012R.id.LPRet1);
        this.et2 = (EditText) findViewById(C0012R.id.LPRet2);
        this.bt1 = (Button) findViewById(C0012R.id.LPRbut1);
        this.bt2 = (Button) findViewById(C0012R.id.LPRbut2);
        this.et1.setText(Long.toString(this.mindist));
        this.et2.setText(Long.toString(this.mintime));
        this.bt1.setOnClickListener(this);
        this.bt2.setOnClickListener(this);
        super.onCreate(savedInstanceState);
    }

    protected void onPause() {
        super.onPause();
        setResult(0);
        finish();
    }

    public void onClick(View but) {
        int id = but.getId();
        if (id == C0012R.id.LPRbut1) {
            this.mindist = Long.valueOf(this.et1.getText().toString().trim()).longValue();
            this.mintime = Long.valueOf(this.et2.getText().toString().trim()).longValue();
            Intent lintent = new Intent();
            lintent.putExtra("mindist", (float) this.mindist);
            lintent.putExtra("mintime", this.mintime);
            setResult(-1, lintent);
            finish();
        } else if (id == C0012R.id.LPRbut2) {
            setResult(0);
            finish();
        }
    }
}
