package com.example.jsonexercise;

import java.io.*;
import java.net.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.json.*;

import android.annotation.SuppressLint;
import android.util.*;

public class JsonParser {
	static InputStream is = null;
	static JSONObject jobj = null;
	static String json = "";
	private String errMsg = "";
	private int errCode = 0;
	private static final String TAG_ID = "id";
	private static final String TAG_FIRST_NAME = "first_name";
	private static final String TAG_LAST_NAME = "last_name";
	private static final String TAG_USER_NAME = "username";
	private static final String TAG_GENDER = "gender";
	private static final String TAG_LINK = "link";
	public JsonParser() {

	}
	
	@SuppressLint("NewApi")
	public JSONObject getJSONFromUrl(String urls){
		MainActivity m = new MainActivity();
		UserInfo i = new UserInfo();
		DefaultHttpClient httpClient = new DefaultHttpClient();	
		HttpPost httpPost = new HttpPost(urls);
		URL url = null;
		String line = null;
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try{

			url = new URL(urls);
			HttpURLConnection conn = null;
			conn = (HttpURLConnection) url.openConnection();
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));

				while ((line = reader.readLine())!=null) {
					sb.append(line + "\n");
				}

			} else {
				m.setSTATE(false);
				i.setErrorCode(conn.getResponseCode());
				i.setErrorMsg(conn.getResponseMessage());
				System.out.println("code : " + conn.getResponseCode());
				System.out.println("msg : " + conn.getResponseMessage());

				//JSONObject item; item.getJSONObject("error").getString("...");
				
			}
			reader.close();
			json = sb.toString();

		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result" + e.toString());
		}
		
		try{

			jobj = new JSONObject(json);
		}catch (JSONException e){
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		
		
		return jobj;
		
	}
	

}
