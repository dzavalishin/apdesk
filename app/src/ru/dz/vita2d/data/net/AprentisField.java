package ru.dz.vita2d.data.net;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class AprentisField {

	private String name;
	private Object val;
	private String valString;
	private String ref;
	private int num;
	private Set<String> refs = new HashSet<String>();

	public AprentisField(String name, Object val) {
		this.name = name;
		this.val = val;
		
		//System.out.println(val.getClass());
		//System.out.println(val);
		
		if (val == null)
			return;
		
		if (val instanceof String) {
			String vs = (String) val;
			valString = vs;
		}
		
		if (val instanceof JSONObject) {
			JSONObject vjo = (JSONObject) val;
			
			valString = vjo.getString("label");
			
			if(vjo.has("key"))
				ref = vjo.getString("key");

			if(vjo.has("value"))
				num = vjo.getInt("value");
		}
		
		if (val instanceof JSONArray) {
			JSONArray ja = (JSONArray) val;
			ja.forEach( le -> {
				
				//System.out.println(le);
				
				JSONObject ce = (JSONObject)le;
				
				String subLabel = ce.getString("label");
				String subKey = ce.getString("key");
				
				refs.add(subKey);
				
				if( valString == null )
					valString = subLabel;
				else
					valString += ", " + subLabel;
			});			
		}
		
		if(valString == null)
		{
			valString = val.toString();
			
			System.out.println(val.getClass());
			System.out.println(val);

		}
	}
	
	
	@Override
	public String toString() {
		return name + "='"+valString+"'";
	}

	public String dump() {
		return name + "='"+val+"'";
	}
}
