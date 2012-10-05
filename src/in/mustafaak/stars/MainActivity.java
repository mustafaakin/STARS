package in.mustafaak.stars;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public void showMessage(String text, int duration) {
		Context context = getApplicationContext();
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	private EditText user;
	private EditText pass;

	ProgressDialog loggingDialog;
	
	String grades = "";
	
	class LoginToStarsTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				SRS s = new SRS();
				boolean login = s.login(user.getText().toString(), pass
						.getText().toString());
				if ( login){
					grades = s.getGrades().toString();
				}
				return login;
			} catch (Exception e) {
				showMessage("Error, sorry.", Toast.LENGTH_SHORT);
				e.printStackTrace();
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			loggingDialog.dismiss();
			showMessage(grades, Toast.LENGTH_LONG);
			if (result) {
				Intent i = new Intent();
				i.setClassName("in.mustafaak.stars", "in.mustafaak.stars.Menu");
				startActivity(i);
			} else {
				showMessage("You could not be logged in!", Toast.LENGTH_SHORT);
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button loginButton = (Button) this.findViewById(R.id.LoginButton);
		user = (EditText) this.findViewById(R.id.txtID);
		pass = (EditText) this.findViewById(R.id.txtPass);
		
		final SharedPreferences p = getPreferences(MODE_PRIVATE);
		if ( p.contains("user") && p.contains("pass")){
			user.setText(p.getString("user", ""));
			pass.setText(p.getString("pass", ""));
		}
		
		loginButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Editor e = p.edit();
				e.putString("user", user.getText().toString());
				e.putString("pass", pass.getText().toString());
				e.commit();
				loggingDialog = ProgressDialog.show(v.getContext(),
						"Logging in", "Trying to verify you in the system.", true);
				new LoginToStarsTask().execute(null);
			}
		});
	}
}
