package com.roger.parkfinder;

import android.content.ContentResolver;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;


public class SearchActivity extends ActionBarActivity {

    TextView parkFinder;
    ImageView logo;
    Button findParks;
    Button addFilters;
    ProgressBar pb;
    LocationManager locationManager;
    LocationListener locationListener;
    double latitude;
    double longitude;
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        parkFinder = (TextView) findViewById(R.id.title);
        logo = (ImageView) findViewById(R.id.logo);
        findParks = (Button) findViewById(R.id.findParks);
        addFilters = (Button) findViewById(R.id.addFilters);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void searchForParks (View v){
        if (displayGpsStatus()){
            pb.setVisibility(View.VISIBLE);
            Toast.makeText(getBaseContext(), "GPS ON", Toast.LENGTH_LONG).show();
            try {
                startActivityForResult(builder.build(this), 0);
                Toast.makeText(this, "SUCCESS", Toast.LENGTH_LONG).show();
            }
            catch (GooglePlayServicesRepairableException e){
            }
            catch (GooglePlayServicesNotAvailableException e){
            }
        }
        else{
            Toast.makeText(getBaseContext(), "GPS NOT ON", Toast.LENGTH_LONG).show();
        }
    }

    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
        return gpsStatus;
    }

}
