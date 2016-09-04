package com.analyzeme.portalconnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Helen on 16.04.16.
 */
public class GovSpb {

	private final static String USER_AGENT = "Mozilla/5.0";

	public static String main() throws Exception {

		GovSpb http = new GovSpb();

		return http.sendGet();
	}

	private String sendGet() throws Exception {


		String url = "http://data.gov.spb.ru/api/v1/datasets/78/versions/latest/data/";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Authorization", "Token 6e5335b81c002d33a52b4de5972fe23283400fb8");

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return (response.toString());

	}
}
