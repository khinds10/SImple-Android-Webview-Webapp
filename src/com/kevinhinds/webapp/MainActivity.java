package com.kevinhinds.webapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Android Application WebApp
 * 
 * @author khinds
 */
public class MainActivity extends Activity {

	/** setup the webview and the webapp host it will point to */
	protected WebView webview;
	protected String siteHost = "kevinhinds.com";

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/** load the site in the local app webview */
		webview = (WebView) findViewById(R.id.webapp);
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webview.setWebViewClient(new MyWebViewClient());
		webview.loadUrl("http://" + siteHost + "/");
	}

	/** custom webview client that will keep local site URLs inside the application */
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			/** for the website itself do not override; let my WebView load the page */
			if (Uri.parse(url).getHost().equals(siteHost)) {
				return false;
			}

			/** the link is not for a page on my site, so launch another Activity that handles URLs */
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(intent);
			return true;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		/** check if the key event was the Back button and if there's history */
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack();
			return true;
		}

		/**
		 * If it wasn't the Back key or there's no web page history, bubble up to the default system
		 * behavior (probably exit the activity)
		 */
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/** load the menu */
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/** do operations based on menu selection */
		switch (item.getItemId()) {
		case R.id.menu_exit:
			System.exit(0);
			break;
		}
		return true;
	}
}