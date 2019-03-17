package ru.dz.aprentis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ru.dz.aprentis.data.AprentisCategory;
import ru.dz.aprentis.data.AprentisCategoryReference;
import ru.dz.aprentis.data.AprentisRecord;
import ru.dz.aprentis.data.AprentisRecordReference;
import ru.dz.aprentis.data.AprentisRestCaller;

/**
 * Main entry point. Singleton.
 * @author dz
 *
 */

public class Engine {

	private static Engine me = new Engine();

	// -----------------------------------------------------
	// Static entry points
	// -----------------------------------------------------

	public static void start(String aprentisBaseUrl)
	{
		me.doStart(aprentisBaseUrl);
	}

	public static AprentisCategory getCategory(AprentisCategoryReference ref)
	{
		return me.doGetCategory(ref);
	}


	public static AprentisRecord getRecord(AprentisRecordReference ref)
	{
		return me.doGetRecord(ref);
	}


	// -----------------------------------------------------
	// Class
	// -----------------------------------------------------


	private AprentisRestCaller arc = null;

	private void doStart(String aprentisBaseUrl) 
	{
		arc = new AprentisRestCaller(aprentisBaseUrl);

	}

	private Map<String, AprentisCategory> categories = new HashMap<>();

	private AprentisCategory doGetCategory(AprentisCategoryReference ref) 
	{
		if(arc == null)
			throw new RuntimeException("not started");
		
		AprentisCategory have = null;
		
		synchronized (categories) {

			have = categories.get(ref.getAsString());

			if( have == null )
			{
				try {
					have = arc.loadCategory(ref);
					categories.put(ref.getAsString(), have);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return have;
	}

	
	private AprentisRecord doGetRecord(AprentisRecordReference ref) 
	{
		AprentisCategoryReference catRef = ref.getCategoryReference();
		AprentisCategory cat = doGetCategory(catRef);
		return cat.getRecord(ref);
	}

	
	
}
