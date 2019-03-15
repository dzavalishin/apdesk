package ru.dz.vita2d.data.net;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;


public class AprentisRecord extends AprentisEntity {

	private Map<String,String> data = new HashMap<String, String>();
	
	//public AprentisRecord() {
		// TODO Auto-generated constructor stub
	//}

	public AprentisRecord(JSONObject jo) {
		loadFromJSON(jo);
	}


	protected void loadFromJSON( JSONObject jo )
	{
		//JSONArray list = jo.getJSONArray("list");
		//list.forEach( le -> {
			
			//System.out.println(le);
			
			//JSONObject ce = (JSONObject)le;
		JSONObject ce = jo;
			
			setKey( ce.getString("key") );
			System.out.println(getKey()+": ");
			
			JSONObject fields = ce.getJSONObject("fields");
			
			//System.out.println(fields);
			
			
			for( String key : fields.keySet() )
			{
				AprentisField af;
				
				if( fields.isNull(key) )
					af = new AprentisField( key, null );
				else
				{
					//String val = fields.getString(key);
					Object val = fields.get(key);
					//System.out.println(key + "=" + val + " ");
					af = new AprentisField( key, val );
				}
				System.out.println("   " + af );
			}
			
			System.out.println("");
		//});

		//for (int i = 0; i < arr.length(); i++) {			  arr.getJSONObject(i);			}
	}
	
}
