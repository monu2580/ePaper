package com.deepesh.epaper.Activities;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.deepesh.epaper.Model.Util;
import com.deepesh.epaper.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Date;

public class WebActivity extends AppCompatActivity {

    WebView webView;
    String url,title;
    ProgressBar bar;
    FrameLayout frameLayout;
    // Our created menu to use
    private Menu mymenu;

    SwipeRefreshLayout swipeRefreshLayout;
    ViewTreeObserver.OnScrollChangedListener onScrollChangedListener;

    Date date;

    //1.
    /*AdView adView;
    InterstitialAd interstitialAd;

    String interstitial_ads="ca-app-pub-8922748217311716/5328419355";
    String banner_ads="ca-app-pub-8922748217311716/3995032672";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        frameLayout=(FrameLayout)findViewById(R.id.frameLayout);
        bar=(ProgressBar)findViewById(R.id.bar);
        bar.setMax(100);


        try {

            date=new Date();
            swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
            swipeRefreshLayout.setEnabled(false);
        /*swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(webView.getScrollY()==0){
                    webView.reload();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });*/



            Intent rcv=getIntent();
            url=rcv.getStringExtra(Util.URL_KEY);
            title=rcv.getStringExtra(Util.TITLE_KEY);

            //Toolbaar
            //Toolbar toolbar = (
            // Toolbar)findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar);

            getSupportActionBar().setTitle(title);
            //toolbar.setSubtitle("Android-er.blogspot.com");
            //toolbar.setLogo(android.R.drawable.ic_menu_info_details);

        /*// add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }*/


            webView=(WebView)findViewById(R.id.webViewId);
            webView.setWebViewClient(new WebViewClient());
            //WebViewClient client=new WebViewClient();
            //webView.setWebViewClient(client);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setUseWideViewPort(true);


           // webView.getSettings().setDisplayZoomControls(true);

            /*// For API level below 18 (This method was deprecated in API level 18)
            webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            if (Build.VERSION.SDK_INT >= 19) {
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }
            else {
                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }*/

            //must be declare in menifest file
            //<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
            webView.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {


                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title+date);
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                            Toast.LENGTH_LONG).show();
                }
            });

            webView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    frameLayout.setVisibility(View.VISIBLE);
                    bar.setProgress(newProgress);
                    setTitle("Loading.....");
                    if(newProgress==100){
                        frameLayout.setVisibility(View.GONE);
                        setTitle(view.getTitle());
                    }
                    super.onProgressChanged(view, newProgress);
                }
            });

            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(url!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                webView.getSettings().setJavaScriptEnabled(true);
                                webView.getSettings().setDomStorageEnabled(true);
                                webView.setOverScrollMode(webView.OVER_SCROLL_NEVER);
                                webView.loadUrl(url);
                            }
                        });
                    }else {

                        webView.getSettings().setJavaScriptEnabled(true);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                webView.loadUrl("https://www.google.co.in");
                            }
                        });
                    }
                }

            }).start();




        }catch (Exception e){

        }


        //2.
        /*try {


            //AdMob (Banner Ads)
            adView=(AdView)findViewById(R.id.adView);

            //for Testing
            //MobileAds.initialize(this,"ca-app-pub-3940256099942544/6300978111");
            //AdRequest adRequest = new AdRequest.Builder().addTestDevice(Util.EMULATER_DEVICE_ID).build();

            //for Publishing
            MobileAds.initialize(this,Util.ADS_ID_ADMOB);
            AdRequest adRequest = new AdRequest.Builder().build();
            //AdRequest adRequest = new AdRequest.Builder().addTestDevice(Util.EMULATER_DEVICE_ID).build();
            adView.loadAd(adRequest);

            //Admob (Interstitial Ad)
            interstitialAd=new InterstitialAd(this);

            //for Testing
            //interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
            //interstitialAd.setAdUnitId(Util.TEST_INTERSTITIAL_ADD_ADMOB);

            //for publishing
            interstitialAd.setAdUnitId(Util.INTERSTITIAL_ADD_ADMOB);
            interstitialAd.loadAd(new AdRequest.Builder().build());
            interstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdLoaded() {

                    //super.onAdLoaded(); //Not Display Ads
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                }

                @Override
                public void onAdClosed() {
                    finish();
                }
            });
        }catch (Exception e){

        }*/




    }
   /* @Override
    protected void onStart() {

        swipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(webView.getScrollY()==0){
                    swipeRefreshLayout.setEnabled(true);

                }else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
        super.onStart();
    }

    @Override
    protected void onStop() {
        swipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener(onScrollChangedListener);
        super.onStop();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Add our menu
        getMenuInflater().inflate(R.menu.menu1, menu);

        // We should save our menu so we can use it to reset our updater.
        mymenu = menu;

        //
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final int id=item.getItemId();

        switch (id){
            case R.id.refreshIcon:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                webView.reload();
                            }
                        });
                    }
                }).start();

                //Toast.makeText(this, "Reloading..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.goback:
                //webView.zoomIn();
                webView.goBack();
                //Toast.makeText(this, "<- Backward", Toast.LENGTH_SHORT).show();
                break;
            case R.id.gonext:
                webView.zoomOut();
                webView.goForward();
                //Toast.makeText(this, "Forward ->", Toast.LENGTH_SHORT).show();
                break;
            case R.id.closeIcon:
                finish();
                //Toast.makeText(this, "Close", Toast.LENGTH_SHORT).show();
                //3.
                /*if(interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else {
                    finish();
                    Toast.makeText(this, "Close", Toast.LENGTH_SHORT).show();

                }*/
                break;

        }

        /*if(item.getItemId()==android.R.id.home){
            //onBackPressed();
            finish();
        }*/
        return super.onOptionsItemSelected(item);
    }

    public class myWebClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            bar.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            frameLayout.setVisibility(View.VISIBLE);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.clearHistory();
            webView.goBack();
            //finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
