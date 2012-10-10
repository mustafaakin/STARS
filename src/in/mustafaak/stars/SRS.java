package in.mustafaak.stars;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SRS  implements Serializable{

	// It is not serializable, must make it like this..
	public static HttpClient client = new DefaultHttpClient();
	
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

	public ArrayList<Grade> getGrades() throws Exception{
		ArrayList<Grade> grades = new ArrayList<Grade>();
		Document doc = Jsoup.parse(getPage(Page.Grade));
		Elements gradeDiv = doc.select(".gradeDiv");
		
		for ( Element el : gradeDiv){
			String[] h4 = el.select("h4").get(0).text().split(" ");
			String currentCourse = h4[h4.length - 2] + " " + h4[h4.length - 1];
			
			Grade seperator = new Grade();
			seperator.course = currentCourse;
			seperator.name = "seperator";
			grades.add(seperator);
			
			Elements trs = el.select("tr");
			for ( Element tr: trs){
				Elements tds = tr.select("td");
				if ( tds.size() > 1){
					Grade grade = new Grade();
					grade.course = currentCourse;
					grade.name = tds.get(0).text();
					grade.type = tds.get(1).text();
					grade.date = tds.get(2).text();
					grade.grade = tds.get(3).text();
					grade.comment = tds.get(4).text();
					grades.add(grade);
				} else {
					
				}
			}
		}
		return grades;
	}
	
	enum Page {
		Grade, Attendance, Transcript 
	}
	private String getPage(Page page) throws Exception{
		String url = "https://stars.bilkent.edu.tr/srs/ajax/";
		if ( page.equals(Page.Grade)) url += "gradeAndAttend/grade.php";
		else if ( page.equals(Page.Attendance)) url += "gradeAndAttend/attend.php";
		
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		return readResponse(response);
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
