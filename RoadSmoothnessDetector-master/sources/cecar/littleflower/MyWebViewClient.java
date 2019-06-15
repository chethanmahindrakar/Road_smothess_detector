package cecar.littleflower;

import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class MyWebViewClient extends WebViewClient {
    MyWebViewClient() {
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType) {
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, BuildConfig.FLAVOR);
    }
}
