package edu.gatech.cs2340.triforce;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

public class chekableList extends LinearLayout implements Checkable{
	private CheckedTextView _checkbox;
	public chekableList(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	//@Override
//	protected void onFinishInflate(){
		//super.onFinishInflate();
		//for(int i = 0; i < 5; i++){
			//database part
		//	View v = 
		//}
	//}
	
	@Override	
	public boolean isChecked() {
		return _checkbox != null ? _checkbox.isChecked() : false; 
	}

	@Override
	public void setChecked(boolean arg0) {
		if(_checkbox != null){
			_checkbox.setChecked(arg0);
		}
	}

	@Override
	public void toggle() {
		if(_checkbox != null){
			_checkbox.toggle();
		}
	} 
}

