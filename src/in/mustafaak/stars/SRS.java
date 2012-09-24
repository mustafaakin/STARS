package in.mustafaak.stars;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class SRS {
	private HttpClient client = new DefaultHttpClient();

	private static String readResponse(HttpResponse response)
			throws IOException {
		InputStream in = response.getEntity().getContent();
		BufferedInputStream bis = new BufferedInputStream(in);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while (result != -1) {
			byte b = (byte) result;
			buf.write(b);
			result = bis.read();
		}
		return buf.toString();
	}


	public boolean login(String ID, String password) throws Exception {	
		HttpPost post = new HttpPost("https://stars.bilkent.edu.tr/srs/ajax/login.php");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("ID", ID));
		nameValuePairs.add(new BasicNameValuePair("PWD", password));

		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = client.execute(post);
		return readResponse(response).contains("HOME");
	}
}
