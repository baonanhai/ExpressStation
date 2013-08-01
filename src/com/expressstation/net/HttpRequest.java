package com.expressstation.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpRequest {
	public static final String DEFAULT_POST_URL = "http://localhost:9000";

	public static InputStream ConnectByGet(String GET_URL, String JsonContent)
			throws IOException {
		URL getUrl = new URL(GET_URL + "?" + JsonContent);
		URLConnection connection = getUrl.openConnection();
		connection.connect();
		InputStream in = getUrl.openStream();
		return in;
	}

	public static String ConnectByPost(String POST_URL, String JsonContent)
			throws IOException {
		URL postUrl = new URL(POST_URL);
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/json");
		connection.connect();

		OutputStreamWriter out = new OutputStreamWriter(
				connection.getOutputStream());
		out.write(JsonContent);

		out.flush();
		out.close();

		InputStream in = connection.getInputStream();
		String content = InputStreamToString(in);
		
		in.close();
		connection.disconnect();
		return content;
	}
	
	private static String InputStreamToString(InputStream inputStream) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}
}