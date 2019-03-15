package ru.dz.vita2d.data.type;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.dz.vita2d.data.DataConvertor;
import ru.dz.vita2d.data.func.FieldConsumer;

/**
 * Common stuff for entity and unit type.
 * @author dz
 *
 */
public abstract class AbstractEntityType implements IEntityType {

	protected final String plural;
	protected final String single;

	protected final String displayName;
	protected final String pluralDisplayName;

	protected AbstractEntityType( String name, String displayName, String pluralDisplayName ) {
		this.displayName = displayName;
		this.pluralDisplayName = pluralDisplayName;
		plural = name+"s";
		single = name;
	}

	public String getDisplayName() {		return displayName;	}
	public String getPluralDisplayName() {		return pluralDisplayName;	}

	/**
	 * Single form.
	 * @return type name as in JSON data.
	 */
	@Override
	public String getObjectTypeName() { return single; }
	
	/**
	 * Plural form.
	 * @return type name as in requests.
	 */
	@Override
	public String getPluralTypeName() { return plural; }
	
	/**
	 * Default implementation.
	 */
	public void forEachRecord(JSONObject json, Consumer<JSONObject> jsonRecordConsumer)
	{
		JSONArray a = json.getJSONArray("list");

		a.forEach( li -> { 
			//System.out.println("li = "+li); 
			JSONObject lio = (JSONObject) li;

			if( (this == ServerUnitType.OBJECTS) || (this == ServerUnitType.SINGLE_OBJECTS))
			{
				JSONObject jRecord = lio.getJSONObject("obj");
				jsonRecordConsumer.accept(jRecord);
			}
			else
			{
				String jid = getPluralTypeName();
				if(this == ServerUnitType.DOCUMENTS) jid = "files"; // Oh, my god.
				
				JSONArray ja = lio.getJSONArray(jid);

				ja.forEach(jae -> {
					JSONObject jRecord = (JSONObject) jae;
					jsonRecordConsumer.accept(jRecord);
				} );
			}

		});

	}
	
	
	
	@Override
	public void forEachField(JSONObject jsonRecord, FieldConsumer fieldNameandDataConsumer)
	{
		jsonRecord.keySet().forEach(fName -> 
		{ 
			Object data = jsonRecord.get(fName);
			// TODO no readable name here!
			DataConvertor.parseAnything(fName, data, (fieldName,fieldVal) -> {
				String readableValue = DataConvertor.readableValue( getObjectTypeName(), fieldVal );

				fieldNameandDataConsumer.accept(fName, data.toString(), readableValue);
				
				// TODO update set of possible values in per type cache
				//tc.updateFieldValuesStats(fieldName, readableValue);
			});


		} );
		
	}
	
	
	
	
	
	
	
	
}
