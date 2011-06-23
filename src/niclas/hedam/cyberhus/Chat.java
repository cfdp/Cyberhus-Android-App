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
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.browser);
	    mWebView = (WebView) findViewById(R.id.webview);
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    mWebView.setWebViewClient(new WebViewClient());
	    mWebView.getSettings().setUserAgentString("cyberApp");
	    mWebView.loadUrl("http://chat.cyberhus.dk/client.php");
	    ImageButton closeButton = (ImageButton)this.findViewById(R.id.imageButton1);
	    closeButton.setBackgroundColor(Color.TRANSPARENT);
	    closeButton.setOnClickListener(new OnClickListener() {
	      @Override
		public void onClick(View v) {
	        finish();
	        System.exit(0);
	      }
	    });
	    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	    notificationManager.cancelAll();
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}