package in.mustafaak.stars;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class GradesActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grades);
		ArrayList<Grade> grades = (ArrayList<Grade>) getIntent()
				.getSerializableExtra("passThis");

		// Have no idea why this has 0 as a parameter,
		// actually its a R.... something but whatever it works
		setListAdapter(new GradeArrayAdapter(this, 0, grades));
	}

	class GradeArrayAdapter extends ArrayAdapter<Grade> {
		private final Context context;
		private final List<Grade> values;

		public GradeArrayAdapter(Context context, int textViewResourceId,
				List<Grade> objects) {
			super(context, textViewResourceId, objects);
			this.context = context;
			this.values = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			Grade g = values.get(position);
			if ( g.name.equals("seperator")){
				View seperatorView = inflater.inflate(R.layout.seperator, parent, false);
				TextView course = (TextView) seperatorView.findViewById(R.id.seperatorText);
				course.setText(g.course);
				return seperatorView ;
			} else {
				View rowView = inflater.inflate(R.layout.graderow, parent, false);
				TextView desc = (TextView) rowView.findViewById(R.id.RowDescription);
				TextView grade = (TextView) rowView.findViewById(R.id.RowGrade);				

				desc.setText(g.name);
				grade.setText(g.grade);			
				return rowView;
			}						
		}
	}
}
