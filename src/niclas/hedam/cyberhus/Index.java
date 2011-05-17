package niclas.hedam.cyberhus;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Index extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        runnable.run();
        Button chatter = (Button)this.findViewById(R.id.button1);
        chatter.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
        	  Intent intent = new Intent(Index.this, Chat.class);
              startActivity(intent);
        	  finish();
          }
        });
    }
    private Handler mHandler = new Handler();
    private String html = null;
    /** Updates the label / TextView with the information from the other thread */
    private Runnable showUpdate = new Runnable(){
        public void run(){
        	ImageView img = (ImageView)findViewById(R.id.Pointer);
        	TextView txt = (TextView)findViewById(R.id.Txt);
        	Button but = (Button)findViewById(R.id.button1);
    		ToggleButton tog = (ToggleButton)findViewById(R.id.toggleButton1);
        	if(html.contains("green.png") == true){
        		img.setImageResource(R.drawable.green);
        		txt.setText("Tryk på knappen under dig for at oprette forbindelse til Cyberhus chatrådgivning.");
        		Log.d("Cyberhus","Grøn");
        		but.setEnabled(true);
        		tog.setEnabled(false);
        		if(tog.isChecked()){
        			NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        			int icon = R.drawable.icon;
        			CharSequence text = "Chatrådgivningen er åben";
        			CharSequence contentTitle = "Chatrådgivningen er åben";
        			CharSequence contentText = "Klik her for at oprette forbindelse til chatrådgivningen.";
        			long when = System.currentTimeMillis();

        			Intent intent = new Intent(Index.this, Chat.class);
        			PendingIntent contentIntent = PendingIntent.getActivity(Index.this, 0, intent, 0);

        			Notification notification = new Notification(icon,text,when);
        			notification.defaults |= Notification.DEFAULT_VIBRATE;

        			notification.setLatestEventInfo(Index.this, contentTitle, contentText, contentIntent);

        			notificationManager.notify(0, notification);
        			tog.setChecked(false);
        			finish();
        		}
        	}
        	else if(html.contains("yellow.png") == true){
        		img.setImageResource(R.drawable.yellow);
        		txt.setText("Der er optaget på chatrådgivningen. Skiltet opdateres hvert 20. sekund.");
        		Log.d("Cyberhus","Gul");
        		but.setEnabled(false);
        		tog.setEnabled(true);
        	}
        	else if(html.contains("red.png") == true){
        		img.setImageResources(R.drawable.red);
        		txt.setText("Chatten er lukket. Den er åben mandag-torsdag klokken 14-19 og fredag 13-19.");
        		Log.d("Cyberhus","Rød");
        		but.setEnabled(false);
        		tog.setEnabled(true);
        	}else{
        		but.setEnabled(false);
        		img.setImageResource(R.drawable.red);
        		Log.d("Cyberhus","Fejl");
        		tog.setEnabled(true);
        		txt.setText("Der opstod en fejl under indlæsningen af chatten. Tjek din internetforbindelse.");
        		
        	}
        }
    };
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {

    	public void run() {
    		Thread checkUpdate = new Thread() {
    	        public void run() {
    	            try {
    	                //URL updateURL = new URL("http://chat.cyberhus.dk/lyskryds.php?action=checklys");
    	            	URL updateURL = new URL("http://frax.dk/method.php");
    	                URLConnection conn = updateURL.openConnection();
    	                InputStream is = conn.getInputStream();
    	                BufferedInputStream bis = new BufferedInputStream(is);
    	                ByteArrayBuffer baf = new ByteArrayBuffer(50);
    	 
    	                int current = 0;
    	                while((current = bis.read()) != -1){
    	                    baf.append((byte)current);
    	                }
    	 
    	                /* Convert the Bytes read to a String. */
    	                html = new String(baf.toByteArray());
    	                mHandler.post(showUpdate);
    	                this.interrupt();
    	            } catch (Exception e) {
    	            	Log.d("Cyberhus",e.getMessage());
    	            	this.interrupt();
    	            }
    	        }
    	    };
    	    checkUpdate.start();
    		/*
    		 * Now register it for running next time
    		 */

    		handler.postDelayed(this, 20000);
    	}

    };
}
