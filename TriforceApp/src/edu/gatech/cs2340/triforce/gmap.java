package edu.gatech.cs2340.triforce;

import android.R;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.google.android.maps.MapActivity; 
import com.google.android.maps.MapView; 
import com.google.android.maps.MapView.LayoutParams;

public class gmap extends MapActivity{
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	
		mapView = (MapView) findViewById(R.id.mapView);
	    LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
	    View zoomView = mapView.getZoomControls(); 
	 
        zoomLayout.addView(zoomView,
    		   new LinearLayout.LayoutParams(
	           LayoutParams.WRAP_CONTENT, 
	           LayoutParams.WRAP_CONTENT)); 
	    mapView.displayZoomControls(true);
	    mapView.setStreetView(true);
	
	
	}
	
	@Override 
	protected boolean isRouteDisplayed(){
		return false; 
	}
}
