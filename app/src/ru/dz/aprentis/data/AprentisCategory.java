package ru.dz.aprentis.data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.dz.aprentis.data.ref.AprentisRecordReference;

public class AprentisCategory extends AprentisEntity 
{
	private Map<String,AprentisRecord> records = new HashMap<String, AprentisRecord>();

	public AprentisCategory() {
		// TODO Auto-generated constructor stub
	}
	


	protected void loadData( JSONObject jo )
	{
		JSONArray list = jo.getJSONArray("list");
		list.forEach( le -> 
		{			
			JSONObject ce = (JSONObject)le;			
			AprentisRecord ar = new AprentisRecord(ce);			
			records.put(ar.getKey(), ar);			
		});

		//for (int i = 0; i < arr.length(); i++) {			  arr.getJSONObject(i);			}
	}


	// TODO parse and store metadata
	public void loadMetaData(JSONObject mjo) {
		System.out.println(mjo);
		
	}
	
	

	public void forEachRecord(Consumer<AprentisRecord> c) {
		for (AprentisRecord r : records.values()) {
			c.accept(r);
		}		
	}



	public AprentisRecord getRecord(AprentisRecordReference arr) 
	{
		return records.get(arr.getAsString());
	}




}
