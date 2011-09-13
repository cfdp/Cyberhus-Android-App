package niclas.hedam.cyberhus;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;

/*
 * Name (String) = The name of the default user (null=Not defined)
 * Age (String) = The age of the default user (null=Not defined) 
 * Sex (String) = The sex (null=Not defined; Male; Female;)
 */

public class Database extends Activity {

	SharedPreferences p = PreferenceManager
			.getDefaultSharedPreferences(getApplicationContext());

	String GetValue(final String key) {
		return p.getString(key, null);
	}

	boolean IsRemembered() {
		if(p.getString("Name", null) != null || p.getString("Age", null) != null || p.getString("Sex", null) != null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
	}
	
	void SetValues(String Name, String Age, String Sex){
	Editor e = p.edit();
	e.putString("Name", Name);
	e.putString("Age", Age);
	e.putString("Sex", Sex);
	e.commit();
	}
}
