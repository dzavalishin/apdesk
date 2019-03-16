package ru.dz.aprentis.data.type;

import java.util.Base64;

import org.json.JSONObject;

import ru.dz.aprentis.data.AprentisFieldValue;

public class AprentisImage extends AprentisFieldValue {

	private String title;
	private String mime;

	// One must be nonzero
	private String url;
	private byte[] binary;

	public AprentisImage(String title, JSONObject jo) {
		this.title = title;
		mime = jo.getString("mime");
		
		if(jo.has("base64"))
		{
			binary = Base64.getDecoder().decode(jo.getString("base64"));
		}
		else if(jo.has("url"))
		{
			url = jo.getString("url"); // Lazy load
		}
		else
			throw new RuntimeException("No base64 or url fields");
	}

	// TODO getImage
	
	@Override
	public String toString() { return title; }
	
}
