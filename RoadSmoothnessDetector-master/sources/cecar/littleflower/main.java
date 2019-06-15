package cecar.littleflower;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class main extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter(this, 17367043, new String[]{"Sensor Information", "Monitor Sensors", "Log Data", "Upload", "Help", "About"}));
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent lIntent = null;
        switch (position) {
            case 0:
                lIntent = new Intent(this, SensorInfo.class);
                break;
            case 1:
                lIntent = new Intent(this, SensorMonitor.class);
                break;
            case BuildConfig.VERSION_CODE /*2*/:
                lIntent = new Intent(this, DatLog.class);
                break;
            case 3:
                lIntent = new Intent(this, MainActivity.class);
                break;
            case 4:
                lIntent = new Intent(this, HelpActivity.class);
                break;
            case 5:
                lIntent = new Intent(this, About.class);
                break;
        }
        if (lIntent != null) {
            startActivity(lIntent);
        }
    }
}
