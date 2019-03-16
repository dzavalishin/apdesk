package ru.dz.aprentis.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;


public class AprentisRecord extends AprentisEntity {

	//private Map<String,String> data = new HashMap<String, String>();
	private Set<AprentisField> fields = new HashSet<AprentisField>();
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
			
			JSONObject jsFields = ce.getJSONObject("fields");
			
			//System.out.println(fields);
			
			
			for( String key : jsFields.keySet() )
			{
				AprentisField af;
				
				if( jsFields.isNull(key) )
					af = new AprentisField( key, null );
				else
				{
					//String val = fields.getString(key);
					Object val = jsFields.get(key);
					//System.out.println(key + "=" + val + " ");
					af = new AprentisField( key, val );
				}
				System.out.println("   " + af );
				fields.add(af);
			}
			
			System.out.println("");
		//});

		//for (int i = 0; i < arr.length(); i++) {			  arr.getJSONObject(i);			}
	}


	public void forEachField(Consumer<AprentisField> c) {
		fields.forEach(c);		
	}
	
}
