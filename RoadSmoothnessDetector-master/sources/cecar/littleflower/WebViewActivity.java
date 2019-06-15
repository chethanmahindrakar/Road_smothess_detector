package cecar.littleflower;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebViewActivity extends Activity {
    private static final int FILECHOOSER_RESULTCODE = 1;
    private ValueCallback<Uri> mUploadMessage;
    ProgressBar progressBar;
    private WebView webView;

    public class myWebClient extends WebViewClient {
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    /* renamed from: cecar.littleflower.WebViewActivity$1 */
    class C00161 extends myWebClient {
        C00161() {
            super();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE && this.mUploadMessage != null) {
            Uri result;
            if (intent == null || resultCode != -1) {
                result = null;
            } else {
                result = intent.getData();
            }
            this.mUploadMessage.onReceiveValue(result);
            this.mUploadMessage = null;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0012R.layout.upload);
        this.webView = (WebView) findViewById(C0012R.id.webView1);
        this.webView.getSettings().setJavaScriptEnabled(true);
        String pageURL = getIntent().getExtras().getString("PageURL");
        Toast.makeText(this, "Loading : " + pageURL, FILECHOOSER_RESULTCODE).show();
        this.webView = new WebView(this);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.loadUrl(pageURL);
        this.webView.setWebViewClient(new C00161());
        setContentView(this.webView);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
