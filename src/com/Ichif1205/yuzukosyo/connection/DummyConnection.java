package com.Ichif1205.yuzukosyo.connection;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

import android.util.Log;

/**
 * HTTP接続時の処理の例
 */
public class DummyConnection extends BaseHttpConnection {
	private static final String TAG = DummyConnection.class.getSimpleName();
	private static final String URL = "http://www6228ui.sakura.ne.jp/Yuzukosyo/dummy.php";

	public DummyConnection() {

	}

	@Override
	protected ArrayList<NameValuePair> getPostParamater() {
		return null;
	}

	@Override
	protected String getUrl() {
		return URL;
	}

	@Override
	protected void getResponse(String response) {
		Log.d(TAG, response);
	}
}
