package cecar.littleflower;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
    private static int SPLASH_TIME_OUT = 5000;

    /* renamed from: cecar.littleflower.SplashScreen$1 */
    class C00151 implements Runnable {
        C00151() {
        }

        public void run() {
            SplashScreen.this.startActivity(new Intent(SplashScreen.this, main.class));
            SplashScreen.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0012R.layout.activity_splash);
        new Handler().postDelayed(new C00151(), (long) SPLASH_TIME_OUT);
    }
}
