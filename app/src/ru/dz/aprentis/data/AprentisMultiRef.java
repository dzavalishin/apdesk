package ru.dz.aprentis.data;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class AprentisMultiRef extends AprentisFieldValue 
{

	private Map<String,String> refs = new HashMap<String,String>();
	private String valString;

	public AprentisMultiRef(JSONArray ja) {
		ja.forEach( le -> {
			
			//System.out.println(le);
			
			JSONObject ce = (JSONObject)le;
			
			String subLabel = ce.getString("label");
			String subKey = ce.getString("key");
			
			refs.put(subKey,subLabel);
			
			if( valString == null )
				valString = subLabel;
			else
				valString += ", " + subLabel;
		});			
	}

	@Override
	public String toString() { return valString; }
	
}
