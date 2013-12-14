package com.Ichif1205.yuzukosyo.connection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public abstract class BaseHttpConnection {
	private final int DEFAULT_TIMEOUT = 1000;

	protected void httpConnection() throws Exception {
		new Thread(new Runnable() {

			@Override
			public void run() {
				final HttpClient client = new DefaultHttpClient();
				final HttpParams params = client.getParams();
				HttpConnectionParams.setConnectionTimeout(params,
						getConnectionTimeout());
				HttpConnectionParams.setSoTimeout(params,
						getConnectionTimeout());
				try {
					HttpUriRequest request = null;
					if (getPostParamater() != null) {
						HttpPost post = new HttpPost(getUrl());
						post.setEntity(new UrlEncodedFormEntity(
								getPostParamater(), "UTF-8"));
						request = post;
					} else {
						request = new HttpGet(getUrl());
					}

					HttpResponse response = client.execute(request);
					if (response.getStatusLine().getStatusCode() > 400) {

					} else {
						final InputStream in = response.getEntity().getContent();
						final InputStreamReader reader = new InputStreamReader(in);
						final BufferedReader buffer = new BufferedReader(reader);

						final StringBuilder builder = new StringBuilder();
						String sLine;
						while ((sLine = buffer.readLine()) != null) {
							builder.append(sLine);
						}
						getResponse(builder.toString());
						
						in.close();
						buffer.close();
						reader.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}).start();
	}
	
	public void exec() {
		try {
			httpConnection();
		} catch (Exception e) {
		}
	}

	/**
	 * タイムアウトの時間
	 * 
	 * @return
	 */
	protected int getConnectionTimeout() {
		return DEFAULT_TIMEOUT;
	}

	/**
	 * POSTパラメータをセット
	 * 
	 * @return
	 */
	abstract protected ArrayList<NameValuePair> getPostParamater();

	/**
	 * URLをセット
	 * 
	 * @return
	 */
	abstract protected String getUrl();

	/**
	 * HTTPのレスポンスを取得
	 * 
	 * @param response
	 */
	abstract protected void getResponse(String response);
}
