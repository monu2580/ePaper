package com.deepesh.epaper.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.deepesh.epaper.Activities.WebActivity;

/**
 * Created by Deepesh on 04-01-2018.
 */

public class MyWebClient extends WebViewClient {


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        // TODO Auto-generated method stub
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // TODO Auto-generated method stub

        view.loadUrl(url);
        return true;

    }
    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        // TODO Auto-generated method stub
        //super.onReceivedError(view, errorCode, description, failingUrl);
    }
    @Override
    public void onPageFinished(WebView view, String url) {
        // TODO Auto-generated method stub
        super.onPageFinished(view, url);

    }
}
