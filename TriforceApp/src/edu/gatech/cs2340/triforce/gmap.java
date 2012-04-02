package edu.gatech.cs2340.triforce;

import edu.gatech.cs2340.r.R;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;

public class gmap extends MapActivity {

	MapView mapView;
	MapController mc;
	GeoPoint p;
	  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gmap);

		mapView = (MapView) findViewById(R.id.mapview);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
        
        mc = mapView.getController();
        //atlanta lat and lng found on google lol 
        String coordinates[] = {"20.65","240.42"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
        
        p = new GeoPoint(
        		(int)(lat *1E6), 
        		(int)(lng*1E6));
        
        mc.animateTo(p);
        mc.setZoom(3);
        mapView.invalidate();
	
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
