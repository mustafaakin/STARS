package in.mustafaak.stars;

import java.io.Serializable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Menu extends Activity {
	SRS srs;
	ProgressDialog loadingDialog;
	
	final String[] menus = {"Grades","Attendance","Transcript","Exams"};
	final int[] Rs = {R.drawable.grades, R.drawable.attendance, R.drawable.transcript, R.drawable.exams};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_menu);
	    srs = (SRS) getIntent().getSerializableExtra("SRS");

	    GridView grid = (GridView) findViewById(R.id.myGrid);
	    grid.setAdapter(new MenuChooseAdaper());

	    grid.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
	        	if ( position == 0){ // for now, until full implementation
					loadingDialog = ProgressDialog.show(view.getContext(),
							"Loading page", "Getting the " + menus[position], true);
					new OpenPageTask().execute(position);					
				} else {
					Context context = view.getContext();
					Toast toast = Toast.makeText(context, "Not implemented yet.", Toast.LENGTH_SHORT);
					toast.show();
				}
	        }
	    });
	}

	public class MenuChooseAdaper extends BaseAdapter {
		
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView i = new ImageView(parent.getContext());
	        i.setImageResource(Rs[position]);
	        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
	        final int w = (int) (36 * getResources().getDisplayMetrics().density + 0.5f);
	        i.setLayoutParams(new GridView.LayoutParams(w * 2, w * 2));
	        return i;
	    }

	    public final int getCount() {
	        return menus.length;
	    }

	    public final long getItemId(int position) {
	        return position;
	    }

		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	
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
