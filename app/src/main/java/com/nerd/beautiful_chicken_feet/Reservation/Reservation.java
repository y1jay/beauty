package com.nerd.beautiful_chicken_feet.Reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nerd.beautiful_chicken_feet.R;

public class Reservation extends AppCompatActivity {
    WebView webView;
    RequestQueue requestQueue;
    String m_redirect_url = "http://www.iamport.kr/mobile/landing";
    private static final String APP_SCHEME = "imp73346050";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        webView = findViewById(R.id.webView);

        requestQueue = Volley.newRequestQueue(Reservation.this);

        webView.setWebViewClient(new InicisWebViewClient(this));
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }

        Intent intent = getIntent();
        Uri intentData = intent.getData();

        if (intentData == null) {
            webView.loadUrl("http://www.iamport.kr");
        } else {
            //isp 인증 후 복귀했을 때 결제 후속조치
            String url = intentData.toString();
            if (url.startsWith(APP_SCHEME)) {
                String redirectURL = url.substring(APP_SCHEME.length() + 3);
                webView.loadUrl(redirectURL);
            }

        }
    }
}
