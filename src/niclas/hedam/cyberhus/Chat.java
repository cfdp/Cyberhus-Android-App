package niclas.hedam.cyberhus;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
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
	    mWebView.loadUrl("http://chat.cybhus.dk/client.php");
	    ImageButton closeButton = (ImageButton)this.findViewById(R.id.imageButton1);
	    closeButton.setOnClickListener(new OnClickListener() {
	      @Override
		public void onClick(View v) {
	    	Activity indexActivity = new Index();
	    	indexActivity.finish();
	        finish();
	        System.exit(0);
	      }
	    });
	    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	    notificationManager.cancelAll();
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}