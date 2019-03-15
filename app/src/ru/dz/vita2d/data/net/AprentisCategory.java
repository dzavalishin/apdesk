package ru.dz.vita2d.data.net;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class AprentisCategory extends AprentisEntity 
{

	public AprentisCategory() {
		// TODO Auto-generated constructor stub
	}
	private Map<String,AprentisRecord> records = new HashMap<String, AprentisRecord>();
	


	protected void loadData( JSONObject jo )
	{
		JSONArray list = jo.getJSONArray("list");
		list.forEach( le -> {
			
			//System.out.println(le);
			
			JSONObject ce = (JSONObject)le;
			
			AprentisRecord ar = new AprentisRecord(ce);
			
			records.put(ar.getKey(), ar);
			
			/*
			setKey( ce.getString("key") );
			System.out.print(getKey()+": ");
			
			JSONObject fields = ce.getJSONObject("fields");
			
			System.out.println(fields);
			*/
		});

		//for (int i = 0; i < arr.length(); i++) {			  arr.getJSONObject(i);			}
	}

}
