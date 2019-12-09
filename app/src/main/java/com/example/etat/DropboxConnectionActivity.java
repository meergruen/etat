package com.example.etat;

import android.app.Activity;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;

public class DropboxConnectionActivity extends Activity {

	final static private String APP_KEY = "a0vixo3s9saqjkd";
	final static private String APP_SECRET = "fxn3url2grhb8ko";


	private String dataFileName = "mockdata.json";
	
	// In the class declaration section:
	private DropboxAPI<AndroidAuthSession> mDBApi;

	protected void onCreate() {
		// And later in some initialization function:
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		AndroidAuthSession session = new AndroidAuthSession(appKeys);
		mDBApi = new DropboxAPI<AndroidAuthSession>(session);

		// MyActivity below should be your activity class name
		mDBApi.getSession().startOAuth2Authentication(this@DropboxConnectionActivity);
	}

	protected void onResume() {
		super.onResume();

		if (mDBApi.getSession().authenticationSuccessful()) {
			try {
				// Required to complete auth, sets the access token on the session
				mDBApi.getSession().finishAuthentication();

				String accessToken = mDBApi.getSession().getOAuth2AccessToken();
			} catch (IllegalStateException e) {
				Log.i("DbAuthLog", "Error authenticating", e);
			}
		}
	}
	
	void uploadData() {
		File file = new File(dataFileName); // save data on each edit (since few edits)
		FileInputStream inputStream = new FileInputStream(file);
		Entry response = mDBApi.putFile("/" + dataFileName , inputStream, file.length(), null, null);
		Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);
	}
]
