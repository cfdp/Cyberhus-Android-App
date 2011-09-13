package niclas.hedam.cyberhus;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Index extends Activity {
	/** Called when the activity is first created. */

	// SET DEBUG TO TRUE, IF CHECKER SHOULD DOWNLOAD FROM DEBUG-SCRIPT
	// (chat2.cyberhus.dk)
	boolean Debug = false;

	// Objects that REQUIRES to be global in the class
	public ImageView img = null;
	public TextView txt = null;
	public Button but = null;
	public ToggleButton tog = null;
	public boolean crashed = false;
	public Database db = new Database();
	private final Handler mHandler = new Handler();
	private String html = null;
	/** Updates the label / TextView with the information from the other thread */
	private final Runnable showUpdate = new Runnable() {
		@Override
		public void run() {
			img = (ImageView) findViewById(R.id.Pointer);
			txt = (TextView) findViewById(R.id.Txt);
			but = (Button) findViewById(R.id.button1);
			tog = (ToggleButton) findViewById(R.id.toggleButton1);
			if (html.contains("green.png") == true || html.equals("3")) {
				img.setImageResource(R.drawable.green);
				txt.setText("Tryk på knappen under dig for at oprette forbindelse til Cyberhus chatrådgivning.");
				Log.d("Cyberhus", "Grøn");
				but.setEnabled(true);
				tog.setEnabled(false);
				if (tog.isChecked()) {
					final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
					final int icon = R.drawable.icon;
					final CharSequence text = "Chatrådgivningen er åben";
					final CharSequence contentTitle = "Chatrådgivningen er åben";
					final CharSequence contentText = "Klik her for at oprette forbindelse til chatrådgivningen.";
					final long when = System.currentTimeMillis();
					final Intent intent = new Intent(Index.this, Chat.class);
					final PendingIntent contentIntent = PendingIntent
							.getActivity(Index.this, 0, intent, 0);
					final Notification notification = new Notification(icon,
							text, when);
					notification.defaults |= Notification.DEFAULT_VIBRATE;
					notification.setLatestEventInfo(Index.this, contentTitle,
							contentText, contentIntent);
					notificationManager.notify(0, notification);
					tog.setChecked(false);
					finish();
				}
			} else if (html.contains("yellow.png") == true || html.equals("2")) {
				img.setImageResource(R.drawable.yellow);
				txt.setText("Der er optaget på chatrådgivningen. Skiltet opdateres hvert 20. sekund.");
				Log.d("Cyberhus", "Gul");
				but.setEnabled(false);
				tog.setEnabled(true);
			} else if (html.contains("red.png") == true || html.equals("1")) {
				img.setImageResource(R.drawable.red);
				txt.setText("Chatten er lukket. Den er åben mandag-torsdag klokken 14-19 og fredag 13-19.");
				Log.d("Cyberhus", "Rød");
				but.setEnabled(false);
				tog.setEnabled(true);
			} else {
				SetError();
			}
		}
	};
	private final Handler handler = new Handler();
	private final Runnable runnable = new Runnable() {

		@Override
		public void run() {
			final Thread checkUpdate = new Thread() {
				@Override
				public void run() {
					try {
						URL updateURL = null;
						if (Debug == true) {
							updateURL = new URL(
									"http://chat2.cybhus.dk/chat_status.php");
						} else {
							updateURL = new URL(
									"http://chat.cyberhus.dk/lyskryds.php?action=checklys");
						}
						final URLConnection conn = updateURL.openConnection();
						final InputStream is = conn.getInputStream();
						final BufferedInputStream bis = new BufferedInputStream(
								is);
						final ByteArrayBuffer baf = new ByteArrayBuffer(50);

						int current = 0;
						while ((current = bis.read()) != -1) {
							baf.append((byte) current);
						}
						/* Convert the Bytes read to a String. */
						html = new String(baf.toByteArray());
						mHandler.post(showUpdate);
						interrupt();
					} catch (final Exception e) {
						html = "error";
						mHandler.post(showUpdate);
						interrupt();
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

	/*
	 * Add this in your Activity
	 */
	private final int MENU_ITEM_0 = 0;

	private final int MENU_ITEM_1 = 1;

	private void Boot() {

		final ImageButton wb = (ImageButton) findViewById(R.id.wwwbutton);
		final ImageButton fb = (ImageButton) findViewById(R.id.fbutton);
		final ImageButton ib = (ImageButton) findViewById(R.id.ibutton);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		runnable.run();
		final Button chatter = (Button) findViewById(R.id.button1);
		chatter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				final Intent intent = new Intent(Index.this, Chat.class);
				intent.putExtra("debug", true);
				startActivity(intent);
				finish();
			}
		});
		wb.setBackgroundColor(Color.TRANSPARENT);
		fb.setBackgroundColor(Color.TRANSPARENT);
		ib.setBackgroundColor(Color.TRANSPARENT);
		wb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				final Intent newIntent = new Intent(Index.this,
						niclas.hedam.cyberhus.URL.class);
				newIntent.putExtra("url", "www");
				startActivityForResult(newIntent, 2);
			}
		});
		fb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				final Intent newIntent = new Intent(Index.this,
						niclas.hedam.cyberhus.URL.class);
				newIntent.putExtra("url", "f");
				startActivityForResult(newIntent, 6);
			}
		});
		ib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				final Intent newIntent = new Intent(Index.this,
						niclas.hedam.cyberhus.URL.class);
				newIntent.putExtra("url", "i");
				startActivityForResult(newIntent, 8);
			}
		});
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		if (db.IsRemembered()) {
			Boot();
		} else {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"For at gøre det nemmere for chatrådgiveren, kan du indstille dit navn, din alder og dit køn. Vil du indstille det nu?")
					.setCancelable(false)
					.setPositiveButton("Ja",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										final DialogInterface dialog,
										final int id) {
									// put your code here
								}
							})
					.setNegativeButton("Nej",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										final DialogInterface dialog,
										final int id) {
									// put your code here
									dialog.cancel();
									Boot();
								}
							});
			final AlertDialog alertDialog = builder.create();
			alertDialog.show();
		}

	}

	/**
	 * Add menu items
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		menu.add(0, MENU_ITEM_0, 0, "Skift til normal server");
		menu.add(0, MENU_ITEM_1, 0, "Skift til chat2.cybhus.dk");
		return true;
	}

	/**
	 * Define menu action
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ITEM_0:
			Debug = false;
			break;
		case MENU_ITEM_1:
			Debug = true;
			break;
		default:
			// put your code here
		}
		return false;
	}

	private void SetError() {
		img = (ImageView) findViewById(R.id.Pointer);
		txt = (TextView) findViewById(R.id.Txt);
		but = (Button) findViewById(R.id.button1);
		tog = (ToggleButton) findViewById(R.id.toggleButton1);

		but.setEnabled(false);
		img.setImageResource(R.drawable.red);
		Log.d("Cyberhus", "Fejl");
		tog.setEnabled(true);
		txt.setText("Der opstod en fejl under indlæsningen af chatten. Tjek din internetforbindelse.");

	}
}
