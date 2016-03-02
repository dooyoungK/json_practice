package com.example.jsonexercise;

import java.util.*;
import java.util.regex.*;

import org.json.*;

import android.net.*;
import android.os.*;
import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private static final String url = "http://graph.facebook.com/";
	private static final String TAG_ID = "id";
	private static final String TAG_FIRST_NAME = "first_name";
	private static final String TAG_LAST_NAME = "last_name";
	private static final String TAG_USER_NAME = "username";
	private static final String TAG_GENDER = "gender";
	private static final String TAG_LINK = "link";
	private static final String TAG_LOCALE = "locale";
	private static String ID = null;

	private static Context ctx;
	private static EditText editId;
	private static Button viewBt;
	private static TextView EditTitle;
	private static TextView IDtextView, FNtextView, LNtextView, GentextView, UNtextView, LinktextView;
	private static TextView ID_data_textView, FN_data_textView, LN_data_textView, Gen_data_textView, UN_data_textView, Link_data_textView;

	private static boolean STATE = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ctx = getApplicationContext();

		editId = (EditText) findViewById(R.id.editId);
		viewBt = (Button) findViewById(R.id.view_bt);
		viewBt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ID = editId.getText().toString();
				new JSONParse().execute();

			}
		});

	}

	protected String checkUserId(String id) {


				boolean ok = Pattern.matches("^[0-9]+$", id);
				if(ok)
				{
					return id;
				}
				
				return null;
	}

	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;
		private boolean isWifiAvail, isWifiConn, isMobileAvail, isMobileConn;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage(MainActivity.this.getString(R.string.loading_dlg_msg));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

			EditTitle = (TextView) findViewById(R.id.edit_title);
			IDtextView = (TextView) findViewById(R.id.item1);
			FNtextView = (TextView) findViewById(R.id.item2);
			LNtextView = (TextView) findViewById(R.id.item3);
			GentextView = (TextView) findViewById(R.id.item4);
			UNtextView = (TextView) findViewById(R.id.item5);
			LinktextView = (TextView) findViewById(R.id.item6);

			ID_data_textView = (TextView) findViewById(R.id.item1_data);
			FN_data_textView = (TextView) findViewById(R.id.item2_data);
			LN_data_textView = (TextView) findViewById(R.id.item3_data);
			Gen_data_textView = (TextView) findViewById(R.id.item4_data);
			UN_data_textView = (TextView) findViewById(R.id.item5_data);
			Link_data_textView = (TextView) findViewById(R.id.item6_data);

			// network check
			ConnectivityManager cMan = (ConnectivityManager) ctx
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cMan
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

			isWifiAvail = netInfo.isAvailable();
			isWifiConn = netInfo.isConnected();
			netInfo = cMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			isMobileAvail = netInfo.isAvailable();
			isMobileConn = netInfo.isConnected();
			if (isWifiConn == false && isMobileConn == false) {
				setSTATE(false);
				Log.w("Internet", "Internet is not connected");
				AlertDialog.Builder alert = new AlertDialog.Builder(
						MainActivity.this);
				alert.setTitle(R.string.dlg_title);
				alert.setPositiveButton(R.string.dlg_bt,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								Init();
							}
						});
				alert.setMessage(R.string.dlg_msg);
				alert.show();

			} else {
				setSTATE(true);
				Log.i("Internet", "Internet connection is OK");
			}

		}

		@Override
		protected JSONObject doInBackground(String... args) {
			if (Looper.myLooper() == null) {
				Looper.prepare();
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			JsonParser jParser = new JsonParser();

			JSONObject json = jParser.getJSONFromUrl(url + checkUserId(ID));

			return json;

		}

		@Override
		protected void onPostExecute(JSONObject json) {
			pDialog.dismiss();
			UserInfo info = new UserInfo();
			if (getSTATE()) {
				@SuppressWarnings("unchecked")
				Iterator<String> i = json.keys();
				try {
					Init();
					while (i.hasNext()) {
						String key = i.next();
						if (key.equals(TAG_ID)) {
							info.setID(json.getLong(TAG_ID));
							IDtextView.setText(R.string.id);
							ID_data_textView.setText(info.getID());
						} else if (key.equals(TAG_FIRST_NAME)) {
							info.setFirstName(json.getString(TAG_FIRST_NAME));
							FNtextView.setText(R.string.first_name);
							FN_data_textView.setText(info.getFirstName());
						} else if (key.equals(TAG_LAST_NAME)) {
							info.setLastName(json.getString(TAG_LAST_NAME));
							LNtextView.setText(R.string.last_name);
							LN_data_textView.setText(info.getLastName());
						} else if (key.equals(TAG_GENDER)) {
							info.setGender(json.getString(TAG_GENDER));
							GentextView.setText(R.string.gender);
							Gen_data_textView.setText(info.getGender());
						} else if (key.equals(TAG_USER_NAME)) {
							info.setUserName(json.getString(TAG_USER_NAME));
							UNtextView.setText(R.string.username);
							UN_data_textView.setText(info.getUserName());
						} else if (key.equals(TAG_LOCALE)) {
							info.setLocale(json.getString(TAG_LOCALE));
						} else if (key.equals(TAG_LINK)) {
							info.setLink(json.getString(TAG_LINK));
							LinktextView.setText(R.string.link);
							Link_data_textView.setText(info.getLink());
						}

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else {
				try {
					Init(); //float error message
					IDtextView.setText(R.string.err_code);
					ID_data_textView.setText("" + info.getErrorCode());
					FNtextView.setText(R.string.err_msg);
					FN_data_textView.setText(info.getErrorMsg());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void Init() {

		IDtextView.setText("");
		FNtextView.setText("");
		LNtextView.setText("");
		GentextView.setText("");
		UNtextView.setText("");
		LinktextView.setText("");

		ID_data_textView.setText("");
		FN_data_textView.setText("");
		LN_data_textView.setText("");
		Gen_data_textView.setText("");
		UN_data_textView.setText("");
		Link_data_textView.setText("");

	}

	public boolean getSTATE() {
		return STATE;
	}

	public void setSTATE(boolean sTATE) {
		STATE = sTATE;
	}

}
