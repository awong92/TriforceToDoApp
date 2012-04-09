package edu.gatech.cs2340.triforce;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.r.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.*;
import android.location.*;
import android.widget.*;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.MapView.LayoutParams;


public class gmap<T> extends MapActivity {

	MapView mapView;
	MapController mc;
	GeoPoint p;
	SQLiteDB db = new SQLiteDB(this);
	Context c = this;
	List<Task> tasks;
	Geocoder geocoder;
	double [] latt,lnng;
	int x;
	
	class MapOverlay extends com.google.android.maps.Overlay
	{
		@Override 
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when)
		{
			super.draw(canvas, mapView, shadow);
			
			//transalting geo Point to pixels
			Point screenPts = new Point();
			x = latt.length;
			for(int i = 0; i<x; i++){
				
				p = new GeoPoint((int)latt[i],(int)lnng[i]);
				mapView.getProjection().toPixels(p, screenPts);
			
				//add the pin
				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pin);
				canvas.drawBitmap(bmp, screenPts.x, screenPts.y-30,null);
			}
			return true;
			
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gmap);

		mapView = (MapView) findViewById(R.id.mapview);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        @SuppressWarnings("deprecation")
		View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
        
        mc = mapView.getController();
        //atlanta lat and lng found on google lol 
        String coordinates[] = {"33.778","275.602"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
        db.open();
        try {
			tasks =  db.getUserTasks(TriforceMain.currentUser, "All", "no date filter", "Both", c);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		x = tasks.size();
		for(int i = 0; i<x; i++){
			Task t = tasks.get(i);
			if(t == null){}
			else{
			try {
				List<Address> addressList = geocoder.getFromLocationName(t.getLocation(), 5);
				if(addressList != null && addressList.size()>0){
					lat = (double) (addressList.get(0).getLatitude()*1000000);
					lng = (double) (addressList.get(0).getLongitude()*1000000);
				
					latt[i] = lat;
					lnng[i] = lng;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
        p = new GeoPoint(
        		(int)(lat*1E6), 
        		(int)(lng*1E6));
        
        mc.animateTo(p);
        mc.setZoom(16);
        mapView.invalidate();
        
        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listofOverlays = mapView.getOverlays();
        listofOverlays.clear();
        listofOverlays.add(mapOverlay);
        
        mapView.invalidate();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
