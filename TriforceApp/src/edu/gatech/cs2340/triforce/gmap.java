package edu.gatech.cs2340.triforce;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

/**
 * Team Triforce (36) Back-end for gmap.xml. Allows users to see all tasks pinned to a gmap
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
public class gmap<T> extends MapActivity {

	MapView mapView;
	MapController mc;
	GeoPoint p, y;
	SQLiteDB db = new SQLiteDB(this);
	Context c = this;
	List<Task> tasks;
	Geocoder geocoder;
	double[] latt, lnng;
	int z;

	int x;

	/**
	 * DISPLAY THE MAP AT CERTAIN COORDINATES
	 */
	class MapOverlay extends com.google.android.maps.Overlay {
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			super.draw(canvas, mapView, shadow);

			// transalting geo Point to pixels
			Point screenPts = new Point();
			for (int i = 0; i < z; i++) {

				y = new GeoPoint((int) latt[i], (int) lnng[i]);
				mapView.getProjection().toPixels(y, screenPts);

				// add the pin
				Bitmap bmp = BitmapFactory.decodeResource(getResources(),
						R.drawable.pin);
				canvas.drawBitmap(bmp, screenPts.x, screenPts.y - 30, null);
			}
			return true;

		}
	}

	/**
	 * Initializes variables and functionality
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gmap);

		mapView = (MapView) findViewById(R.id.mapview);
		LinearLayout zoomLayout = (LinearLayout) findViewById(R.id.zoom);
		@SuppressWarnings("deprecation")
		View zoomView = mapView.getZoomControls();

		zoomLayout.addView(zoomView, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mapView.displayZoomControls(true);

		mc = mapView.getController();
		geocoder = new Geocoder(this, Locale.getDefault());

		// atlanta lat and lng found on google lol
		String coordinates[] = { "33.778", "84.3881" };
		double lat = Double.parseDouble(coordinates[0]);
		double lng = Double.parseDouble(coordinates[1]);

		db.open();
		try {
			tasks = db.getUserTasks(TriforceMain.currentUser, "All",
					"no date filter", "Both", c);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		x = tasks.size();
		latt = new double[tasks.size()];
		lnng = new double[tasks.size()];

		z = 0;
		for (int i = 0; i < x; i++) {
			Task t = tasks.get(i);
			try {
				if (t.getLocation()!= "") {
					List<Address> addressList = geocoder.getFromLocationName(
							t.getLocation(), 5);
					if (addressList != null && addressList.size() > 0) {
						lat = (double) (addressList.get(0).getLatitude() * 1000000);
						lng = (double) (addressList.get(0).getLongitude() * 1000000);

						latt[z] = lat;
						lnng[z] = lng;
						z++;
					}
				}
			} catch (IOException e) {
				z--;
				e.printStackTrace();
			}
		}
		MapOverlay mapOverlay = new MapOverlay();
		mapView.getOverlays().add(mapOverlay);

		mc.setZoom(3);
		mapView.invalidate();
	}

	/**
	 * Displays a route
	 */
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
