package edu.gatech.cs2340.triforce;

import edu.gatech.cs2340.r.R;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Team Triforce (36) Adapter for the listing of tasks
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
public class ListArrayAdapter extends ArrayAdapter<Task> {

	private final List<Task> list;
	private final Activity context;
	private View view;
	static int currTaskId = -1;

	/**
	 * Adapter for the list of tasks with its checkbox
	 * 
	 * @param context
	 * @param list
	 *            List of tasks
	 */
	public ListArrayAdapter(Activity context, List<Task> list) {
		super(context, R.layout.row_layout, list);
		this.context = context;
		this.list = list;
	}

	/**
	 * Class for holding the view
	 */
	static class ViewHolder {
		protected TextView text;
		protected CheckBox checkbox;
	}

	/**
	 * Getter for the view
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.row_layout, null);
			view.setClickable(true);
			view.setOnClickListener(new OnClickListener() {
				/**
				 * OnClickListener for when a task is clicked to be displayed
				 */
				@Override
				public void onClick(View v) {
					currTaskId = list.get(position).getTaskId();
					v.setBackgroundColor(Color.rgb(255, 140, 0));
					Intent viewTask = new Intent(
							"edu.gatech.cs2340.triforce.VIEWTASKACTIVITY");
					context.startActivity(viewTask);
				}

			});
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) view.findViewById(R.id.label);
			viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
			viewHolder.checkbox.setFocusable(false);
			viewHolder.checkbox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						/**
						 * OnCheckedChangeListener for when a check box is
						 * clicked to mark complete or incomplete of a task
						 */
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							Task element = (Task) viewHolder.checkbox.getTag();
							element.setSelected(buttonView.isChecked());

							View row = (View) buttonView.getParent();
							TextView descrip = (TextView) row
									.findViewById(R.id.label);
							if (isChecked) {
								descrip.setPaintFlags(descrip.getPaintFlags()
										| Paint.STRIKE_THRU_TEXT_FLAG);
							} else {
								descrip.setPaintFlags(257);
							}
						}
					});
			view.setTag(viewHolder);
			viewHolder.checkbox.setTag(list.get(position));
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.text.setText(list.get(position).getName());
		holder.checkbox.setChecked(list.get(position).isSelected());
		return view;
	}
}