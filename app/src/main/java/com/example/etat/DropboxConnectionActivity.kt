package com.example.etat

import android.app.Activity
import com.dropbox.core.DbxException
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.users.FullAccount
import java.io.FileInputStream
import java.io.InputStream

class DropboxConnectionActivity : Activity() {
    private val dataFileName = "mockdata.json"
    // In the class declaration section:
// private DropboxAPI<AndroidAuthSession> mDBApi;
    protected fun onCreate() {
        val config = DbxRequestConfig.newBuilder("dropbox/etat").build()
        val client =
            DbxClientV2(config, APP_SECRET)
        var account: FullAccount? = null
        try {
            account = client.users().currentAccount
        } catch (e: DbxException) {
            e.printStackTrace()
        }
        println(account!!.name.displayName)
        // Get files and folder metadata from Dropbox root directory
        try {
            var result = client.files().listFolder("")
            while (true) {
                for (metadata in result.entries) {
                    println(metadata.pathLower)
                }
                if (!result.hasMore) {
                    break
                }
                result = client.files().listFolderContinue(result.cursor)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        uploadData(client)
    }

    /*  // And later in some initialization function:
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);

        // MyActivity below should be your activity class name
        mDBApi.getSession().startOAuth2Authentication(this@DropboxConnectionActivity);
    }*/
/*
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
    }*/
    fun uploadData(client: DbxClientV2) { /* File file = new File(dataFileName); // save data on each edit (since few edits)
        FileInputStream inputStream = new FileInputStream(file);
        DropBoxManager.Entry response = mDBApi.putFile("/" + dataFileName , inputStream, file.length(), null, null);
        Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);
*/
// File file = new File(dataFileName); // save data on each edit (since few edits)
//FileInputStream inputStream = new FileInputStream(file);
        try {
            val `in`: InputStream = FileInputStream(dataFileName)
            val metadata = client.files().uploadBuilder("/$dataFileName")
                .uploadAndFinish(`in`)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val APP_KEY = "a0vixo3s9saqjkd"
        private const val APP_SECRET = "fxn3url2grhb8ko"
    }
}