package org.lms.material.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.*;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    private void navigateToSettingsActivity() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private String getUrl() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("lms_server_url",null);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_UP) {
                    webView.evaluateJavascript("incrementVolume()", null);
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_UP) {
                    webView.evaluateJavascript("decrementVolume()", null);
                }
                break;
            case KeyEvent.KEYCODE_BACK:
                if (action == KeyEvent.ACTION_DOWN && webView != null && webView.canGoBack()) {
                    webView.goBack();
                }
                break;
            default:
                return super.dispatchKeyEvent(event);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);


        webView = findViewById(R.id.activity_main_webview);

        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // https://stackoverflow.com/questions/33079762/android-webview-uncaught-typeerror-cannot-read-property-getitem-of-null
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                // if there is any error opening the web page, navigate to settings
                // screen because site could not be loaded
                navigateToSettingsActivity();
            }
        });

        //webView.setWebChromeClient(new WebChromeClient() {
        //    @Override
        //    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        //        android.util.Log.d("************ WebView", consoleMessage.message());
        //        return true;
        //    }
        //});

        String url = getUrl();
        if (url == null || !Patterns.WEB_URL.matcher(url).matches()) {
            navigateToSettingsActivity();
        }
        webView.loadUrl(url);
    }
}
