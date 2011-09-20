package niclas.hedam.cyberhus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class URL extends Activity {
	WebView mWebView;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credits);
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.getSettings().setUserAgentString("cyberApp");
		final String param1 = getIntent().getExtras().getString("url");
		Log.d("Cyberhus", param1);
		if (param1.contains("www")) {
			mWebView.loadUrl("http://cyberhus.dk");
		} else if (param1.contains("f")) {
			mWebView.loadUrl("http://touch.facebook.com/cyberhus");
		} else {
			mWebView.loadUrl("http://cyberhus.dk/smartphone");
		}
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}
