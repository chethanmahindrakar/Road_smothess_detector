package cecar.littleflower;

import android.app.ListActivity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SensorSelect extends ListActivity implements OnClickListener {
    CSensorStates mSenStates = null;

    /* renamed from: cecar.littleflower.SensorSelect$1 */
    class C00141 implements OnClickListener {
        C00141() {
        }

        public void onClick(View v) {
            SensorSelect.this.setResult(0);
            SensorSelect.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0012R.layout.sensorselect);
        Button btnsave = (Button) findViewById(C0012R.id.SSbutton2);
        ((Button) findViewById(C0012R.id.SSbutton1)).setOnClickListener(new C00141());
        btnsave.setOnClickListener(this);
        CSensorStates lSenStates = (CSensorStates) getIntent().getParcelableExtra("sensor_states");
        this.mSenStates = lSenStates;
        setListAdapter(new ArrayAdapter(this, 17367056, lSenStates.getNames()));
        ListView listView = getListView();
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(2);
        for (int i = 0; i < lSenStates.getNum(); i++) {
            listView.setItemChecked(i, lSenStates.getActive(i).booleanValue());
        }
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        this.mSenStates.setActive(position, l.isItemChecked(position));
    }

    public void onClick(View v) {
        CSensorStates lSenStates = this.mSenStates;
        Editor lPrefEd = getSharedPreferences("SensorMonPrefs", 0).edit();
        for (int i = 0; i < lSenStates.getNum(); i++) {
            lPrefEd.putBoolean(lSenStates.getName(i), lSenStates.getActive(i).booleanValue());
        }
        lPrefEd.commit();
        setResult(-1);
        finish();
    }

    protected void onPause() {
        super.onPause();
        setResult(0);
        finish();
    }
}
