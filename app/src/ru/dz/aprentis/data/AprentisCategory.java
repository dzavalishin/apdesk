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

	private String name = null;
	private String mainFieldName = null;
	private String visibleName;
	private String visibleNamePlural;
	private String internalName;

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
	public void loadMetaData(JSONObject jo) {
		//System.out.println(jo);
		
		name = jo.getString("name");
		// TODO "related"
		
		JSONObject f = jo.getJSONObject("fields");
		
		if( f.has("___mainfieldname"))
		{
			JSONObject mfn = f.getJSONObject("___mainfieldname");
			mainFieldName = mfn.getString("label");
		}
		
		//if( f.has("___visiblename") )
		visibleName = f.getString("___visiblename");		
		visibleNamePlural = f.getString("___visiblenameplural");
		internalName = f.getString("___internalname");
		
		System.out.println(visibleNamePlural);
	}
	
	

	public void forEachRecord(Consumer<AprentisRecord> c) {
		for (AprentisRecord r : records.values()) {
			r.performFullLoad();
			c.accept(r);
		}		
	}



	public AprentisRecord getRecord(AprentisRecordReference arr) 
	{
		AprentisRecord r = records.get(arr.getAsString());
		r.performFullLoad();
		return r;
	}



	public String getMainFieldName() {		return mainFieldName;	}
	public String getVisibleName() {		return visibleName;	}
	public String getVisibleNamePlural() {		return visibleNamePlural;	}
	public String getInternalName() {		return internalName;	}



}
