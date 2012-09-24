package in.mustafaak.stars;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Menu extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		ListView listView = (ListView) findViewById(R.id.lstMenu);
		String[] values = new String[] { "Grades", "Schedule", "Transcript", "Exams"};

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(),
						"You clicked: " + position, Toast.LENGTH_LONG)
						.show();
			}
		});

	}
}
