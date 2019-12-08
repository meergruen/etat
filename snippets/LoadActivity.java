

public class LoadActivity extends Activity {

	protected void onCreate() {
		EtatApp app = (EtatApp) getApplicationContext();
		app.initializeData();
	}
	
}
