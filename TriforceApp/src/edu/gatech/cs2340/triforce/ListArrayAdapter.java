package edu.gatech.cs2340.triforce;

import edu.gatech.cs2340.r.R;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Team Triforce (36) Adapter for the listing of tasks
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
public class ListArrayAdapter extends ArrayAdapter<Task> implements
		OnClickListener {

	private final List<Task> list;
	private final Activity context;

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
		protected ImageButton editButton;
	}

	/**
	 * Getter for the view
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.row_layout, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) view.findViewById(R.id.label);
			viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
			viewHolder.editButton = (ImageButton) view
					.findViewById(R.id.edit_row_task);
			viewHolder.checkbox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							Task element = (Task) viewHolder.checkbox.getTag();
							element.setSelected(buttonView.isChecked());

						}
					});
			viewHolder.editButton.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edit_row_task:
			Intent editTask = new Intent(
					"edu.gatech.cs2340.triforce.EDITTASKACTIVITY");
			break;
		}
	}
}