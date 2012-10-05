package in.mustafaak.stars;

import java.io.Serializable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Menu extends Activity {
	SRS srs;
	ProgressDialog loadingDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		srs = (SRS) getIntent().getSerializableExtra("SRS");

		ListView listView = (ListView) findViewById(R.id.lstMenu);
		final String[] values = new String[] { "Grades", "Schedule", "Transcript",
				"Exams" };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if ( position == 0){ // for now, until full implementation
					loadingDialog = ProgressDialog.show(view.getContext(),
							"Loading page", "Getting the " + values[position], true);
					new OpenPageTask().execute(position);					
				} else {
					Context context = view.getContext();
					Toast toast = Toast.makeText(context, "Not implemented yet.", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});
	}

	class OpenPageTask extends AsyncTask<Integer, Void, Void> {
		int pageToOpen;
		Serializable passThis;

		@Override
		protected Void doInBackground(Integer... params) {
			pageToOpen = params[0];
			try {
				if (pageToOpen == 0) {
					passThis = srs.getGrades();
				}
			} catch (Exception e) {

			} 
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			loadingDialog.dismiss();
			Intent i = new Intent();
			String[] activities = { "in.mustafaak.stars.GradesActivity" };			
			i.setClassName("in.mustafaak.stars", activities[pageToOpen]);
			i.putExtra("passThis", passThis);			
			startActivity(i);
		}
	}
}
