package ru.dz.aprentis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import ru.dz.aprentis.data.AprentisCategory;
import ru.dz.aprentis.data.AprentisCategoryReference;
import ru.dz.aprentis.data.AprentisRecord;
import ru.dz.aprentis.data.AprentisRecordReference;
import ru.dz.aprentis.data.AprentisRestCaller;
import ru.dz.aprentis.data.AprentisSystemRecordReference;

/**
 * Main entry point. Singleton.
 * @author dz
 *
 */

public class Engine {

	private static Engine me = new Engine();

	// -----------------------------------------------------
	// Server info
	// -----------------------------------------------------
	
	protected AprentisSystemRecordReference areaKey = null;
	protected AprentisSystemRecordReference profileKey = null;
	protected AprentisSystemRecordReference roleKey = null;
	protected AprentisSystemRecordReference userAKey = null;
	protected AprentisSystemRecordReference userCKey = null;
	
	// zts table records
	protected AprentisSystemRecordReference userKey = null;
	protected AprentisSystemRecordReference companyKey = null;
	
	protected String readableUserName = null; 
	private String loggedInUser = null;

	
	// -----------------------------------------------------
	// Static entry points
	// -----------------------------------------------------

	public static void start(String aprentisBaseUrl)
	{
		me.doStart(aprentisBaseUrl);
	}

	public static void login(String user, String password) throws IOException
	{
		me.doLogin(user, password);
	}

	

	public static AprentisCategory getCategory(AprentisCategoryReference ref)
	{
		return me.doGetCategory(ref);
	}


	public static AprentisRecord getRecord(AprentisRecordReference ref)
	{
		return me.doGetRecord(ref);
	}


	public static Engine get() { return me; }
	
	// -----------------------------------------------------
	// Class
	// -----------------------------------------------------


	private AprentisRestCaller arc = null;

	private void doStart(String aprentisBaseUrl) 
	{
		arc = new AprentisRestCaller(aprentisBaseUrl);

	}

	/**
	 * 
	 * @param user user login (email)
	 * @param password user password
	 * @return true if success
	 * @throws IOException if connection is lost
	 */
	private boolean doLogin(String user, String password) throws IOException 
	{
		checkStarted();

		JSONObject jo = arc.login(user, password);
		
		try {
			areaKey = parseSystemRecordRef( jo, "AreaKey" );
			profileKey = parseSystemRecordRef( jo, "ProfileKey" );
			roleKey = parseSystemRecordRef( jo, "RoleKey" );
			userAKey = parseSystemRecordRef( jo, "UserAKey" );
			userCKey = parseSystemRecordRef( jo, "UserCKey" );
			
			// zts table records
			userKey = parseSystemRecordRef( jo, "UserKey" );
			companyKey = parseSystemRecordRef( jo, "CompanyKey" );
			
			readableUserName = jo.getString("UserName"); 
			
			loggedInUser = user;
			
			
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	
	private AprentisSystemRecordReference parseSystemRecordRef(JSONObject jo, String key) {
		AprentisSystemRecordReference ref = new AprentisSystemRecordReference(jo.getString(key));
		//System.out.println(key+"="+ref);
		return ref;
	}


	private Map<String, AprentisCategory> categories = new HashMap<>();

	private AprentisCategory doGetCategory(AprentisCategoryReference ref) 
	{
		checkStarted();
		
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

	public void checkStarted() {
		if(arc == null)
			throw new RuntimeException("not started");
	}

	
	private AprentisRecord doGetRecord(AprentisRecordReference ref) 
	{
		AprentisCategoryReference catRef = ref.getCategoryReference();
		AprentisCategory cat = doGetCategory(catRef);
		return cat.getRecord(ref);
	}


	// -----------------------------------------------------
	// Getters
	// -----------------------------------------------------


	public AprentisSystemRecordReference getAreaKey() {		return areaKey;	}

	public AprentisSystemRecordReference getProfileKey() {		return profileKey;	}

	public AprentisSystemRecordReference getRoleKey() {		return roleKey;	}

	public AprentisSystemRecordReference getUserAKey() {		return userAKey;	}

	public AprentisSystemRecordReference getUserCKey() {		return userCKey;	}

	public AprentisSystemRecordReference getUserKey() {		return userKey;	}

	public AprentisSystemRecordReference getCompanyKey() {		return companyKey;	}

	public String getReadableUserName() {		return readableUserName;	}

	public String getLoggedInUser() {		return loggedInUser;	}

	
	
}
