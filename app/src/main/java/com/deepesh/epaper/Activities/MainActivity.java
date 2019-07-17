package com.deepesh.epaper.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.deepesh.epaper.Adapter.GridViewAdapter;
import com.deepesh.epaper.Model.Product;
import com.deepesh.epaper.Model.Util;
import com.deepesh.epaper.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ViewStub stubGrid;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private List<Product> productList;
    private int currentViewMode = 0;
    private Menu mymenu;
    int position1;

    //1.
   /* AdView adView;
    InterstitialAd interstitialAd;
    String ads_id="ca-app-pub-8922748217311716~7930687493";
    String banner_ads="ca-app-pub-8922748217311716/2254198293";
    String interstitial_ads="ca-app-pub-8922748217311716/3931732730";*/

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 2;

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Admob(Bannner Ad)x
        //2. adView=(AdView)findViewById(R.id.adView);

        //MobileAds.initialize(this,"ca-app-pub-3940256099942544/6300978111"); //for testing
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build(); /***to get device_id
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(Util.EMULATER_DEVICE_ID).build();

        //3.
        /*MobileAds.initialize(this,Util.ADS_ID_ADMOB);
        AdRequest adRequest = new AdRequest.Builder().build();  //for Publishing
        adView.loadAd(adRequest);*/


        stubGrid = (ViewStub) findViewById(R.id.stub_grid);
        stubGrid.inflate();
        gridView = (GridView) findViewById(R.id.mygridview);


        //get list of product
        getProductList();

        //Get current view mode in share reference
        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//Default is view listview
        //Register item lick
        gridView.setOnItemClickListener(this);

        //switchView();

        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item1, productList);
        gridView.setAdapter(gridViewAdapter);

        if (Build.VERSION.SDK_INT >= 23) {
            // Pain in A$$ Marshmallow+ Permission APIs
            checkAndRequestPermissions();

        } else {
            // Pre-Marshmallow

        }
    }


    private  boolean checkAndRequestPermissions() {
        int permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        //setUpView();

        return true;
    }
    public List<Product> getProductList() {

        productList = new ArrayList<>();
        productList.add(new Product(R.drawable.epaper_pkhabar, "प्रभात खबर", "http://epaper.prabhatkhabar.com/","https://www.prabhatkhabar.com/"));
        productList.add(new Product(R.drawable.epaperhindustan, "हिन्दुस्तान ", "http://epaper.livehindustan.com/","https://www.livehindustan.com/"));
        productList.add(new Product(R.drawable.epaper_aujala, "अमर उजाला", "http://epaper.amarujala.com/","https://www.amarujala.com/"));
        productList.add(new Product(R.drawable.epaper_pkeshra, "पंजाब केसरी", "http://epaper.punjabkesari.in/","http://www.punjabkesari.in/"));
        productList.add(new Product(R.drawable.epaper_nvtimes, "नवभारत टाइम्स", "http://epaper.navbharattimes.com/","https://navbharattimes.indiatimes.com/"));
        productList.add(new Product(R.drawable.epaper_rpatrika, "राजस्थान पत्रिका", "http://epaper.patrika.com/","https://www.patrika.com/"));
        productList.add(new Product(R.drawable.epaper_dbhaskar, "दैनिक भास्कर", "https://www.bhaskar.com/ ","http://epaper.bhaskar.com/"));
        productList.add(new Product(R.drawable.epaper_djagran, "दैनिक जागरण", "https://www.jagran.com/","http://epaper.jagran.com/"));
        productList.add(new Product(R.drawable.epaper_jansata, "जनसत्ता", "http://epaper.jansatta.com/","https://www.jansatta.com/"));
        productList.add(new Product(R.drawable.epaper_stoday, "संजीवनी टुडे", "http://www.sanjeevnitoday.com/epapers","http://www.sanjeevnitoday.com/"));
        productList.add(new Product(R.drawable.epaper_utam_hindu, "उत्तम हिन्दू", "http://epaper.uttamhindu.com/", ""));
        productList.add(new Product(R.drawable.epaper_desh_bandhu, "देशबन्धु", "http://www.deshbandhu.co.in/epaper.aspx",""));
        productList.add(new Product(R.drawable.epaper_divya_himanchal, "दिव्या हिमाचल", "http://epaper.divyahimachal.com/",""));
        productList.add(new Product(R.drawable.epaper_hari_bhumi, "हरी भूमि", "http://epaper.haribhoomi.com/",""));
        productList.add(new Product(R.drawable.epaper_parichay_times, "परिचय टाइम्स", "http://epaper.parichaytimes.in/",""));
        productList.add(new Product(R.drawable.epaper_prava_sakshi, "प्रभासाक्षी", "http://www.prabhasakshi.com/",""));
        productList.add(new Product(R.drawable.epaper_danik_navjoti, "दैनिक नवज्योति", "http://epaper.navajyoti.net/htdocs/",""));
        productList.add(new Product(R.drawable.epaper_panchjan, "पाञ्चजन्य", "http://www.panchjanya.com/",""));
        productList.add(new Product(R.drawable.epaper_mahanagar, "हमारा महानगर", "http://www.hamaramahanagar.in/",""));
        productList.add(new Product(R.drawable.epaper_navharat, "नवभारत", "http://epaper.enavabharat.com/",""));
        productList.add(new Product(R.drawable.epaper_saamna, "सामना", "http://epaper.saamana.com/",""));

        return productList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        position1=position;
        Toast.makeText(getApplicationContext(), productList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,WebActivity.class);
        intent.putExtra(Util.URL_KEY,productList.get(position).getEpaperUrl());
        intent.putExtra(Util.TITLE_KEY,productList.get(position).getTitle());
        startActivity(intent);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        mymenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id) {
            case R.id.shareIcon:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                StringBuilder sb = new StringBuilder();
                sb.append("Hindi Newspaper-हिंदी समाचार सबसे पहले, सबसे तेज......");
                sb.append("https://play.google.com/store/apps/details?id="+ this.getPackageName());
                //sharingIntent.addFlags(ActivityFlags.ClearWhenTaskReset);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "AndroidSolved");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sb.toString());
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;

            case R.id.starIcon:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + this.getPackageName())));
                } catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
                }
                /*Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.youtube"));
                startActivity(intent);*/
                break;
        }
            return super.onOptionsItemSelected(item);
    }

}
