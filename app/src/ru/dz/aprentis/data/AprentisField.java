package ru.dz.aprentis.data;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.dz.aprentis.data.type.AprentisBool;
import ru.dz.aprentis.data.type.AprentisDocument;
import ru.dz.aprentis.data.type.AprentisEnum;
import ru.dz.aprentis.data.type.AprentisImage;
import ru.dz.aprentis.data.type.AprentisMultiRef;
import ru.dz.aprentis.data.type.AprentisNull;
import ru.dz.aprentis.data.type.AprentisNumeric;
import ru.dz.aprentis.data.type.AprentisReference;
import ru.dz.aprentis.data.type.AprentisString;

public class AprentisField {

	private String name;
	private Object inputVal;

	private String valString;
	//private String ref;
	//private int num;

	private AprentisFieldValue value;

	public AprentisField(String name, Object val) {
		this.name = name;
		this.inputVal = val;

		//System.out.println(val.getClass());
		//System.out.println(val);

		if (val == null)
		{
			value = new AprentisNull();
			return;
		}

		if (val instanceof Double) {
			Double dv = (Double) val;
			value = new AprentisNumeric(dv);
			valString = value.toString();
		}
		
		if (val instanceof Boolean) {
			value = new AprentisBool((Boolean)val);
			valString = value.toString();
		}
		
		if (val instanceof String) {
			String vs = (String) val;
			valString = vs;
			value = new AprentisString(vs);
		}

		if (val instanceof JSONObject) {
			JSONObject vjo = (JSONObject) val;

			if(vjo.has("mime"))
			{
				// pic
				String title = vjo.getString("title");
				valString = title;
				value = new AprentisImage(title, vjo);
			}
			else if(vjo.has("url"))
			{
				// file
				valString = vjo.getString("label");
				value = new AprentisDocument(valString, vjo.getString("url"));
			}
			else
			{

				valString = vjo.getString("label");

				if(vjo.has("key"))
				{
					String ref = vjo.getString("key");
					value = new AprentisReference(valString,ref);
				}

				if(vjo.has("value"))
				{
					int num = vjo.getInt("value");
					value = new AprentisEnum(valString,num);
				}
			}
		}

		if (val instanceof JSONArray) {
			JSONArray ja = (JSONArray) val;
			value = new AprentisMultiRef(ja);
			valString = value.toString();
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
		//return name + "='"+valString+"'";
		if( value == null ) return "(null)";
		return value.toString();
	}

	public String dump() {
		return name + "='"+inputVal+"'";
	}


	public AprentisFieldValue getValue() { 		return value;	}


	public String getName() { return name; }


	//public void setValue(AprentisFieldValue value) {		this.value = value;	}
}
