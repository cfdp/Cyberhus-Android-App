package niclas.hedam.cyberhus;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

/*
 * Name (String) = The name of the default user (null=Not defined)
 * Age (String) = The age of the default user (null=Not defined) 
 * Sex (String) = The sex (null=Not defined; Male; Female;)
 */

public class Database extends Activity {

	public String GetValue(final SharedPreferences p, final String key) {
		return p.getString(key, null);
	}

	public boolean IsRemembered(final SharedPreferences p) {
		if (p.getString("Name", null) != null
				|| p.getString("Age", null) != null
				|| p.getString("Sex", null) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void SetValues(final SharedPreferences p, final String Name,
			final String Age, final String Sex) {
		final Editor e = p.edit();
		e.putString("Name", Name);
		e.putString("Age", Age);
		e.putString("Sex", Sex);
		e.commit();
	}
}
