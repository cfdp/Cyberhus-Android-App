package niclas.hedam.cyberhus;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

public class Chat extends Activity {
	WebView mWebView;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browser);
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.getSettings().setUserAgentString("cyberApp");
		if (getIntent().getExtras().getBoolean("debug") == true) {
			// Findes ikke - Google er tester
			mWebView.loadUrl("http://google.com/search?q=Cyberhus");
		} else {
			mWebView.loadUrl("http://chat.cyberhus.dk/client.php");
		}
		final ImageButton closeButton = (ImageButton) findViewById(R.id.imageButton1);
		closeButton.setBackgroundColor(Color.TRANSPARENT);
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				finish();
				System.exit(0);
			}
		});
		final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}