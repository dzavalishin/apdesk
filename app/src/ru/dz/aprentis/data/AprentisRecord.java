package ru.dz.aprentis.data;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.json.JSONObject;


public class AprentisRecord extends AprentisEntity 
{

	private Set<AprentisField> fields = new HashSet<AprentisField>();

	public AprentisRecord(JSONObject jo) {
		loadFromJSON(jo);
	}


	protected void loadFromJSON( JSONObject jo )
	{
		JSONObject ce = jo;

		setKey( ce.getString("key") );
		//System.out.println(getKey()+": ");

		JSONObject jsFields = ce.getJSONObject("fields");


		for( String key : jsFields.keySet() )
		{
			AprentisField af;

			if( jsFields.isNull(key) )
				af = new AprentisField( key, null );
			else
			{
				Object val = jsFields.get(key);
				af = new AprentisField( key, val );
			}
			//System.out.println("   " + af );
			fields.add(af);
		}

		//System.out.println("");
	}


	public void forEachField(Consumer<AprentisField> c) {
		fields.forEach(c);		
	}


	public String getTtile() {
		// TODO find some better title!
		return getKey();
	}

}
