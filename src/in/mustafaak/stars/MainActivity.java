package in.mustafaak.stars;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

	class LoginToStarsTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				SRS s = new SRS();
				boolean login = s.login(user.getText().toString(), pass
						.getText().toString());
				return login;
			} catch (Exception e) {
				showMessage("WTF", Toast.LENGTH_SHORT);
				e.printStackTrace();
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			showMessage(result ? "OK" : "NOT OK", Toast.LENGTH_SHORT);
			if (result) {
				Intent i = new Intent();				
				i.setClassName("in.mustafaak.stars", "in.mustafaak.stars.Menu");
				startActivity(i);
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

		loginButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showMessage("Please wait, logging in to the system..",
						Toast.LENGTH_LONG);
				new LoginToStarsTask().execute(null);
			}
		});
	}
}
