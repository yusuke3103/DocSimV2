package SearchAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;

import servlet.Start;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class BingSearch {
	protected String accountKey = "khAG/f4L2/E67nh3YubgFiPT66xvJ/QTpUvXLnNIVxY";
	protected String url = "https://api.datamarket.azure.com/Bing/SearchWeb/v1/Web?";
	protected String format = "json";
	protected String Market = "'ja-JP'";
	private static String keyword = "";

	public void run(){
		byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
		String accountKeyEnc = new String(accountKeyBytes);

		try {
			String bingURL = url;
			bingURL += "Query=" + URLEncoder.encode(keyword, "utf-8");
			bingURL += "&$format=" + format;
			bingURL += "&Market=" + URLEncoder.encode(Market, "utf-8");

			System.err.println(bingURL);

			URL url = new URL(bingURL);

			URLConnection con = url.openConnection();
			con.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
			InputStream is = con.getInputStream();

			String line, json = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			while ((line = reader.readLine()) != null) {
				json += line;
			}

			JsonFactory factry = new JsonFactory();
			JsonParser parser = factry.createJsonParser(json);
			
			while(parser.nextToken() != JsonToken.END_OBJECT){
				String name = parser.getCurrentName();
				if(name!=null){
					if(name.equals("results")){
						while(parser.nextToken()!=JsonToken.END_ARRAY){
							String title = null;
							String siteurl = null;
							String spenit = null;
							while(parser.nextToken() != JsonToken.END_OBJECT){
								parser.nextToken();
								name = parser.getCurrentName();
								if(name.equals("Title")){
									title = parser.getText();
								}else if(name.equals("Description")){
									spenit = parser.getText();
								}else if(name.equals("Url")){
									siteurl = parser.getText();
								}else if(name.equals("__metadata")){
									parser.skipChildren();
								}
							}
							Start.setWebPage(title,siteurl,spenit);
						}
					}
				}
				parser.nextToken();
			}
			is.close();
			reader.close();
			parser.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void setKeyword(String key){
		this.keyword="'" + key + "'";
	}
}
